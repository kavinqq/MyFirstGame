package company.gameobj.background.component;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.GameObject;
import company.gameobj.Rect;
import company.gameobj.message.HintDialog;
import company.gameobj.message.MultiIHintDialog;
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
    //按下且有物件
    private boolean isPressing;
    //外部控制 若按下是不能建造，則不能拖曳
    private boolean canDragging;

    private MultiIHintDialog multiIHintDialog;
    private Rect[] redRects; //最底層紅
    private Rect greenRect;


    public BuildingButton(int x, int y,int id) {
        super(x, y, Global.BUILDING_WIDTH, Global.BUILDING_HEIGHT);
        canDragging=false;
        this.id=id;
        ox=x;
        oy=y;
        isMoveOnButton =false;
        //多行文字
        multiIHintDialog=new MultiIHintDialog(HintDialog.State.ABSOLUTE,painter().left()-300,painter().top());

        type = BuildingType.getBuildingTypeByInt(getId());

        multiIHintDialog.add("建築物名稱\t:"+type.instance().getName() +"\n");
        multiIHintDialog.add("");
        multiIHintDialog.add(type.instance().toString());
        multiIHintDialog.add("");
        multiIHintDialog.add("建造所需木材\t:"+type.instance().getWoodCostCreate());
        multiIHintDialog.add("");
        multiIHintDialog.add("建造所需鋼鐵:\t"+type.instance().getSteelCostCreate());
        multiIHintDialog.add("");
        multiIHintDialog.add("建造所需瓦斯:\t"+type.instance().getGasCostCreate());
        multiIHintDialog.add("");
        multiIHintDialog.add("升級所需木材:\t"+type.instance().getWoodCostLevelUp());
        multiIHintDialog.add("");
        multiIHintDialog.add("升級所需鋼鐵:\t"+type.instance().getSteelCostLevelUp());
        multiIHintDialog.add("");
        multiIHintDialog.add("升級所需瓦斯:\t"+type.instance().getGasCostLevelUp());

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

    //不符合蓋建築條件時 不能拖曳
    public void setCanDragging(boolean b){
        canDragging=b;
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

    public boolean isDragging() {
        return isDragging;
    }

    public boolean isReleased() {
        return isReleased;
    }

    public boolean getCanBuild(){
        return canBuild;
    }

    public int getId(){
        return this.id;
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

    //綠色區域為可蓋區域
    public Rect getGreenRect(){
        return greenRect;
    }

    //紅色區域為已有存在的建築物
    public Rect[] getRedRects(){
        return redRects;
    }


    @Override
    public void paintComponent(Graphics g) {
        //拖曳中的按鈕
        if(isDragging){
            //最底層紅
            g.setColor(Color.red);
            g.fillRect(painter().left(),painter().top(),painter().width(), painter().height());
            //綠色區域
            if(greenRect != null && this.detectRange().overlap(greenRect)){
                canBuild=true;
                g.setColor(Color.green);
                g.fillRect(greenRect.left(), greenRect.top(), greenRect.width(), greenRect.height());
                //紅色區域
                if(redRects!= null){
                    g.setColor(Color.red);
                    for (int i = 0; i < redRects.length; i++) {
                        if(greenRect.overlap(redRects[i])){
                            canBuild=false;
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

    public MultiIHintDialog getMultiIHintDialog(){
        return multiIHintDialog;
    }

    @Override
    public void update() {
        multiIHintDialog.update();
    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        multiIHintDialog.mouseTrig(e,state,trigTime);
        switch (state){
            case MOVED: {
                //若移開不能拖曳
                isDragging = false;
                //移動至上方顯示資訊
                if (this.isEntered(e.getX(), e.getY())) {
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
                if(isEntered(e.getX(), e.getY())){
                    isPressing=true;
                    isMoveOnButton=false;
                }else{
                    isPressing=false;
                }
                break;
            }
            case DRAGGED:{
                //要先點擊後拖曳，確保只能拖移一個物件
                if(isPressing && canDragging){
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
                        isDragging =true;
                    }
                }
                break;
            }

            case RELEASED:{
                originPosition();
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
