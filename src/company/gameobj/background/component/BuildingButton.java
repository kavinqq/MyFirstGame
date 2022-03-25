package company.gameobj.background.component;

import company.Global;
import company.gameobj.GameObject;
import company.gameobj.background.HintDialog;
import company.gametest9th.utils.CommandSolver;
import static company.gameobj.BuildingController.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class BuildingButton extends GameObject implements CommandSolver.MouseCommandListener {
    public interface ButtonInterface{
        public int entered(BuildingButton bb);
    }

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
    private int id;
    private boolean canUseButton;

    //當前buttonId
    public static int buttonId;
    public BuildingButton(int x, int y) {
        super(x, y, Global.BUILDING_WIDTH, Global.BUILDING_HEIGHT);
        ox=x;
        oy=y;
        canUseButton=false;
        canBuild=true;//Fixme_待做
        //img= SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Arsenal());
    }


    //外面改變Button
    public void changeButtonInterface(){
        type = BuildingType.getBuildingTypeByInt(id);
        bni.entered(this);
    }


    public void clearInterface(){
        //HintDialog.instance().setHintMessage("");
        bni=null;
    }


    @Override
    public void paintComponent(Graphics g) {
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
        //System.out.println("回到原位");
        setPainterStartFromTopLeft(ox,oy);
    }
    //可否使用button
    public boolean isCanUseButton(){
        return canUseButton;
    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        //System.out.println(canDragged);
        if(state==null){
            return;
        }

        switch (state){
            case MOVED:{
                //移動至上方顯示資訊
                if(this.isEntered(e.getX(), e.getY())){
                    if(bni!=null) {
                        changeButtonInterface();
                    }
                    canUseButton=true;
                    //System.out.println("在物件上面");
                }else{
                    canUseButton=false;
                }

                originPosition();
                break;
            }
            case CLICKED:{
                buttonId=id;
                break;
            }
            case DRAGGED:{
                if(isEntered(previousX, previousY)){
                    moveToCenterPoint(e.getX(),e.getY());
                }
                break;
            }
            case RELEASED:{
                buttonId=0;

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

    public void setId(int id) {
        this.id=id;
    }

    public int getId(){
        return this.id;
    }
}
