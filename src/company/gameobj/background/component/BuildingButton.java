package company.gameobj.background.component;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.GameObject;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;
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



    public BuildingButton(int x, int y,int id) {
        super(x, y, Global.BUILDING_WIDTH, Global.BUILDING_HEIGHT);
        this.id=id;
        ox=x;
        oy=y;
        isMoveOnButton =false;
        countPressed=0;
        canBuild=true;//Fixme_待做
    }

    public int getCountPressed() {
        return countPressed;
    }

    public void decCountPresed(){
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

    //回到原位
    private void originPosition(){
        setPainterStartFromTopLeft(ox,oy);
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

    @Override
    public void paintComponent(Graphics g) {
        //畫出建造中建築物
        g.drawImage(img,+painter().left(),painter().top(),painter().width(), painter().height(), null);
    }

    @Override
    public void update() {

    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        //System.out.println(canDragged);
        if(state==null){
            return;
        }
        switch (state){
            case MOVED: {
                //若移開不能拖曳

                isDragging = false;
                //移動至上方顯示資訊
                if (this.isEntered(e.getX(), e.getY())) {
                    isMoveOnButton = true;
                    isShuffle=true;
                } else {
                    isShuffle=false;
                    isMoveOnButton = false;
                }
                //滑鼠移動時回到原位
                originPosition();
                break;
            }
            case PRESSED:{
                //isReleased=false;
                if(isMoveOnButton){
                    isReleased=false;
                    countPressed++;
                    isMoveOnButton=false;
                    isPressed=true;
                }else{
                    isPressed=false;
                }
                break;
            }
            case DRAGGED:{
                isDragged=true;
                //要先點擊後拖曳，確保只能拖移一個物件
                if(isShuffle){ //應該用isPressed但會卡
                    if(isDragging){
                        //後面幾偵拖曳
                        if(isEntered(previousX,previousY)){
                            moveToCenterPoint(e.getX(),e.getY());
                        }
                    }else{
                        //第一偵拖曳
                        if(isEntered(e.getX(), e.getY())){
                            moveToCenterPoint(e.getX(),e.getY());
                        }
                        isReleased=false;
                        isMoveOnButton=false;
                        isDragging =true;
                    }
                }
                break;
            }

            case RELEASED:{
                originPosition();
                isDragged=false;
                isClick=false;
                isPressed=true;
                isDragging =false;
                isReleased=true;
                isMoveOnButton=false;
                break;
            }
        }
        previousX =e.getX();
        previousY =e.getY();
    }
}
