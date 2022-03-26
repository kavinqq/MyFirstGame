package company.gameobj.background.component;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.GameObject;
import company.gameobj.Rect;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;
import static company.gameobj.BuildingController.*;

import java.awt.*;
import java.awt.event.MouseEvent;

public class BuildingButton extends GameObject implements CommandSolver.MouseCommandListener {

    public interface ButtonInterface{
        public void entered(BuildingButton bb);
    }
    public boolean isReleased;
    public ButtonInterface bni;

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

    //紀錄拖曳到的正方形，隨滑鼠移動
    private Rect MouseRect;

    //點擊次數
    private int count;

    //先判斷滑鼠是否在按鈕上
    private boolean isMoveOnButton;
    //是否先 點擊為在同一個位置按下後放開
    boolean isClick;
    //是否按到 有按下就觸發
    private boolean isPressed;
    //拖曳  有放開就觸發
    private boolean isDragged;

    //半透明灰色圖
    private Image grayOpacity;
    public BuildingButton(int x, int y,int id) {
        super(x, y, Global.BUILDING_WIDTH, Global.BUILDING_HEIGHT);
        this.id=id;
        grayOpacity=SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().whiteGrayOpacity());
        ox=x;
        oy=y;
        MouseRect =new Rect(x,y, Global.BUILDING_WIDTH, Global.BUILDING_HEIGHT);
        isMoveOnButton =false;
        count=0;
        canBuild=true;//Fixme_待做
    }

    public boolean isPressed(){
        return isPressed;
    }

    //外面改變Button
    public void changeButtonInterface(){
        type = BuildingType.getBuildingTypeByInt(id);
        bni.entered(this);
    }



    @Override
    public void paintComponent(Graphics g) {
        //畫出建造中建築物
        g.drawImage(img,+painter().left(),painter().top(),painter().width(), painter().height(), null);
        //計算數量好像不用
        g.setColor(Color.white);
        g.setFont(new Font("Dialog", Font.BOLD, 40));
        //int buildingNum=getBuildingTypeByInt(id).list().size();
        if(count>0){
            g.drawImage(grayOpacity,painter().left(),painter().top(),painter().width(), painter().height(),null);
            g.drawString(String.valueOf(count),painter().left(),painter().top()+30);
        }
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

    //可否使用button
    public boolean isMoveOnButton(){
        return isMoveOnButton;
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
            case MOVED:{
                //若移開不能拖曳
                isDragged=false;
                //移動至上方顯示資訊
                if(this.isEntered(e.getX(), e.getY())){
//                    isDragged=true;
                    //isClick=true;
                    isMoveOnButton =true;
                    //外部控制按鈕
                    if(bni!=null) {
                        changeButtonInterface();
                    }
                }else{
                    //sClick=false;
                    isMoveOnButton =false;
                }
                originPosition();
                break;
            }
            case CLICKED:{
                System.out.println("Click");
                if(isMoveOnButton){
                    isClick=true;
                    isPressed=true;
                }else{
                    isClick=false;
                }
            }
            case PRESSED:{
                System.out.println("Pressed");
                if(isMoveOnButton){
                    isClick=true;
                    isPressed=true;
                }else{
                    isPressed=false;
                }
                break;
            }
            case DRAGGED:{
                //要先點擊後拖曳
                if(isClick){
                    if(isEntered(previousX, previousY)){
                        isDragged=true;
                        moveToCenterPoint(e.getX(),e.getY());
                        MouseRect.setCenter(e.getX(),e.getY());
                    }
                }
                break;
            }
            case RELEASED:{
                isClick=false;
                isDragged=false;
                isReleased=true;
                isMoveOnButton=false;
                if(canBuild){
                    build(e.getX(),e.getY());
                }
                originPosition();
                break;
            }

        }
        previousX =e.getX();
        previousY =e.getY();
    }


    public int getId(){
        return this.id;
    }

    public boolean isReleased() {
        return isReleased;
    }

    public boolean isDragged() {
        return isDragged;
    }
    //取得紀錄方塊
    public Rect getMouseRect(){
        return MouseRect;
    }

}
