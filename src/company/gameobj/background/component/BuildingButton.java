package company.gameobj.background.component;

import company.Global;
import company.gameobj.GameObject;
import company.gameobj.buildings.Building;
import company.gameobj.buildings.SawMill;
import company.gametest9th.utils.CommandSolver;

import java.awt.*;
import java.awt.event.MouseEvent;

public class BuildingButton extends GameObject implements CommandSolver.MouseCommandListener {

    private boolean canBuild; //可否建造，
    private Image img;
    //原點x
    private int ox;
    //原點y
    private int oy;
    //上一次座標X
    private int previousX;
    //上一次座標Y
    private int previousY;
    public BuildingButton(int x, int y) {
        super(x, y, Global.BUILDING_WIDTH, Global.BUILDING_HEIGHT);
        ox=x;
        oy=y;
        canBuild=true;//Fixme_待做
        //img= SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Arsenal());
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

    public Building build(int x,int y){
        //System.out.println("Building_Success");
        return new SawMill(x, y); //待修
    }

    //回到原位
    private void originPosition(){
        //System.out.println("回到原位");
        //System.out.println("ox:"+ox+" oy:"+oy);
        moveToPoint(ox,oy);
    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        //System.out.println(canDragged);
        if(state==null){
            return;
        }
        switch (state){
            case MOVED:{
                //System.out.println("MoveButtonTest");
                originPosition();
                break;
            }
            case DRAGGED:{
            //System.out.println("x:"+e.getX()+" y:"+e.getY());
                if(isEntered(previousX, previousY)){
                    //System.out.println("BUTTON_DRAGGED");
                    moveToCenterPoint(e.getX(),e.getY());
                }
                break;
            }
            case RELEASED:{
                //System.out.println("Button_Release");
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
}
