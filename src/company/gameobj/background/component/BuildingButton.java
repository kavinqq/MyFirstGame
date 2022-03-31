package company.gameobj.background.component;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.GameObject;
import company.gameobj.Rect;
import company.gameobj.message.HintDialog;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import static company.Global.*;
import static company.Global.OPTION_GAP_Y;
import static company.gameobj.BuildingController.*;

import java.awt.*;
import java.awt.event.MouseEvent;

public class BuildingButton extends GameObject implements CommandSolver.MouseCommandListener {


    public interface ButtonEntered{
        public void action(BuildingButton bb);
    }

    public interface Draw{
        public void buttonDragging(BuildingButton bb);
        public void buttonReleased(BuildingButton bb);
    }

    public boolean isReleased;
    public ButtonEntered be;
    //public ButtonReleased br;
    public Draw dbd;

    public BuildingType type;
    //可否建造，
    private boolean canBuild;

    private Image img;
    //原點x
    private int ox;
    //原點y
    private int oy;
    //上一次座標X
    private int previousX;
    //上一次座標Y
    private int previousY;
    //按鈕id
    private final int id;
    //點擊次數
    private int countPressed;
    //先判斷滑鼠是否在按鈕上
    private boolean isMoveOnButton;
    //是否先 點擊為在同一個位置按下後放開
    boolean isClick;
    //是否按到 有按下就觸發
    private boolean isPressed;
    //拖曳且有物件
    private boolean isDragging;
    //拖曳
    private boolean isDragged;
    //緩衝有些正常感應不夠靈敏
    private boolean isShuffle;
    //isRedImg
    private boolean isVisible;
    //是否是現在的按鈕Id
    public static int currentId;
    //是否是現在的按鈕
    private static boolean isCurrent;

    private boolean isPressing;

    private boolean canDragging;

    private Rect[] redRects; //最底層紅
    private Rect greenRect;


    public BuildingButton(int x, int y,int id) {
        super(x, y, Global.BUILDING_WIDTH, Global.BUILDING_HEIGHT);
        canDragging=false;
        this.id=id;
        isVisible=false;
        ox=x;
        oy=y;
        isMoveOnButton =false;
    }

    public int getCountPressed() {
        return countPressed;
    }

    public void decCountPressed(){
        countPressed--;
    }

    public boolean isPressed(){
        return isPressed;
    }

    public void setImg(Image img){
        this.img=img;
    }

    public int build(int x,int y){
        return id; //待修
    }

    public void setCanDragging(boolean b){
        canDragging=b;
    }

    public boolean isCurrent(){
        return isCurrent;
    }
    //回到原位
    private void originPosition(){
        if(painter().left()!=ox || painter().top()!=oy){
            setPainterStartFromTopLeft(ox,oy);
        }
    }

    //滑鼠是否再按鈕上
    public boolean isMoveOnButton(){
        return isMoveOnButton;
    }

    public boolean isDragged(){
        return isDragged;
    }

    public boolean isDragging() {
        return isDragging;
    }

    public boolean isReleased() {
        return isReleased;
    }

    public boolean isShuffle(){
        return isShuffle;
    }

    public int getId(){
        return this.id;
    }

    public void setPressed(boolean bool) {
        isPressed=bool;
    }

    //從外部控制綠色方形
    public void setGreenRect(Rect rect) {
        if(rect == null){
            return;
        }
        this.greenRect = rect;
    }

    //從綠色方形上在覆蓋紅色方形
    public void setRedRects(Rect[] rects){
        if(rects == null){
            return;
        }
        this.redRects=rects;
    }

    public Rect getGreenRect(){
        return greenRect;
    }

    public Rect[] getRedRects(){
        return redRects;
    }

    @Override
    public void paintComponent(Graphics g) {
        if(isDragging){
            //最底層紅
            g.setColor(Color.red);
            g.fillRect(painter().left(),painter().top(),painter().width(), painter().height());
            //綠色
            if(greenRect != null && this.detectRange().overlap(greenRect)){
                g.setColor(Color.green);
                g.fillRect(greenRect.left(), greenRect.top(), greenRect.width(), greenRect.height());
                if(redRects!= null){
                    g.setColor(Color.red);
                    for (int i = 0; i < redRects.length; i++) {
                        System.out.println("abba");
                        if(greenRect.overlap(redRects[i])){
                            g.fillRect(redRects[i].left(),redRects[i].top(),redRects[i].width(),redRects[i].height());
                        }
                    }
                }
            }
            g.setColor(Color.black);
        }
        //畫出建造中建築物
        g.drawImage(img,+painter().left(),painter().top(),painter().width(), painter().height(), null);
    }

    @Override
    public void update() {
        ////印出建築物提示文字
        if(isMoveOnButton) {
            type = BuildingType.getBuildingTypeByInt(getId());
            HintDialog.instance().setHintMessage(type.instance().getName());
        }
    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        switch (state){
            case MOVED: {
                //若移開不能拖曳
                isDragging = false;
                //移動至上方顯示資訊
                if (this.isEntered(e.getX(), e.getY())) {
                    currentId=0;
                    isMoveOnButton = true;
                } else {
                    isMoveOnButton = false;
                }
                //滑鼠移動時回到原位
                originPosition();
                break;
            }
            case PRESSED:{
                countPressed++;
                isPressed=true;
                isReleased=false;
                if(isMoveOnButton){
                    isPressing=true;
                    currentId=id;
                    isMoveOnButton=false;
                }else{
                    isPressing=false;
                }
                break;
            }
            case DRAGGED:{
                isDragged=true;
                //要先點擊後拖曳，確保只能拖移一個物件
                if(isPressing && canDragging){ //應該用isPressing但會卡
                    if(isDragging){
                        //後面幾偵拖曳
                        if(isEntered(previousX,previousY)){
                            moveToCenterPoint(e.getX(),e.getY());
                        }
                    }else{
                        //第一偵拖曳
                        if(isEntered(e.getX(), e.getY())){
                            currentId=id;
                            moveToCenterPoint(e.getX(),e.getY());
                        }
                        isDragging =true;
                    }
                }
                break;
            }

            case RELEASED:{
                originPosition();
                currentId=0;
                isDragged=false;
                isDragging =false;
                isPressed=false;
                isReleased=true;
                isMoveOnButton=false;
                break;
            }
        }
        previousX =e.getX();
        previousY =e.getY();
    }
}
