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
    public interface ButtonReleased{
        public void action(BuildingButton bb);
    }
    public interface ButtonDragging{
        public void draw(BuildingButton bb);
    }

    public boolean isReleased;
    public ButtonEntered be;
    public ButtonReleased br;
    public ButtonDragging bd;

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
    private int count;
    //先判斷滑鼠是否在按鈕上
    private boolean isMoveOnButton;
    //是否先 點擊為在同一個位置按下後放開
    boolean isClick;
    //是否按到 有按下就觸發
    private boolean isPressed;
    //拖曳  有放開就觸發
    private boolean isDragging;

    //半透明灰色圖
    private Image grayOpacity;
    public BuildingButton(int x, int y,int id) {
        super(x, y, Global.BUILDING_WIDTH, Global.BUILDING_HEIGHT);
        this.id=id;
        grayOpacity=SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().whiteGrayOpacity());
        ox=x;
        oy=y;
        isMoveOnButton =false;
        count=0;
        canBuild=true;//Fixme_待做
    }

    public boolean isPressed(){
        return isPressed;
    }


    @Override
    public void paintComponent(Graphics g) {
        if(bd!=null){
            bd.draw(this);
        }
        //畫出建造中建築物
        g.drawImage(img,+painter().left(),painter().top(),painter().width(), painter().height(), null);
     }

    public void setImg(Image img){
        this.img=img;
    }


    @Override
    public void update() {

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

    public boolean isDragging() {
        return isDragging;
    }

    public boolean isReleased() {
        return isReleased;
    }

    public int getId(){
        return this.id;
    }

    public void setPressed(boolean bool) {
        isPressed=bool;
    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        //System.out.println(canDragged);
        if(state==null){
            return;
        }
        switch (state){
            case MOVED: {
                //滑鼠移動時回到原位
                originPosition();
                //若移開不能拖曳
                isDragging = false;
                //移動至上方顯示資訊
                if (this.isEntered(e.getX(), e.getY())) {
                    isMoveOnButton = true;
                    type = BuildingType.getBuildingTypeByInt(id);
                    //外部控制按鈕
                    if (be != null) {
                        be.action(this);
                    }
                } else {
                    isMoveOnButton = false;
                }
                break;
            }
            case PRESSED:{
                if(isMoveOnButton){
                    isMoveOnButton=false;
                    isPressed=true;
                }else{
                    isPressed=false;
                }
                break;
            }
            case DRAGGED:{
                //要先點擊後拖曳，確保只能拖移一個物件
                if(isPressed){
                    if(isEntered(previousX, previousY) && isDragging){
                        moveToCenterPoint(e.getX(),e.getY());
                    }else{
                        isDragging =true;
                    }
                }
                break;
            }
            case RELEASED:{
                originPosition();
                isClick=false;
                isPressed=false;
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
