package company.gameobj.background.component;

import company.Global;
import company.gameobj.GameObject;
import company.gametest9th.utils.CommandSolver;
import static company.gameobj.BuildingController.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class BuildingButton extends GameObject implements CommandSolver.MouseCommandListener {
    public interface ButtonInterface{
        public void entered(BuildingButton bb);
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
    private final int id;
    //此按鈕可否使用
    private boolean isMoveOnButton;

    private boolean isClickButton;

    public BuildingButton(int x, int y,int id) {
        super(x, y, Global.BUILDING_WIDTH, Global.BUILDING_HEIGHT);
        this.id=id;
        ox=x;
        oy=y;
        isMoveOnButton =false;
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
    public boolean isMoveOnButton(){
        return isMoveOnButton;
    }
    public boolean isClickButton(){
        return isClickButton;
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
                    isMoveOnButton =true;
                    //System.out.println("在物件上面");
                }else{
                    isMoveOnButton =false;
                }
                originPosition();
                break;
            }
            case CLICKED:{
                isClickButton=true;
                break;
            }
            case DRAGGED:{
                if(isEntered(previousX, previousY)){
                    moveToCenterPoint(e.getX(),e.getY());
                }
                break;
            }
            case RELEASED:{
                isClickButton=false;
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
}
