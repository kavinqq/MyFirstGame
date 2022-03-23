package company.gameobj.background.component;

import company.Global;
import company.gameobj.GameObject;
import company.gameobj.buildings.Building;
import company.gametest9th.utils.CommandSolver;

import java.awt.*;
import java.awt.event.MouseEvent;

public class BuildingButton extends GameObject implements CommandSolver.MouseCommandListener {

    private boolean canDragged;
    private boolean canBuild; //可否建造，
    private Image img;
    private int ox;
    private int oy;
    public BuildingButton(int x, int y) {
        super(x, y, Global.FOUNDATION_HEIGHT, Global.FOUNDATION_WIDTH);
        ox=x;
        oy=y;
        canDragged=false;
        canBuild=true;//Fixme_待做
        //img= SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Arsenal());
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img,painter().left(),painter().top(),painter().width(), painter().height(), null);
    }

    public void setImg(Image img){
        this.img=img;
    }


    @Override
    public void update() {

    }

    public Building build(int x,int y){
        //System.out.println("Building_Success");
        return new Building(x,y);
    }

    //回到原位
    private void originPosition(){
        //System.out.println("回到原位");
        //System.out.println("ox:"+ox+" oy:"+oy);
        offset(ox,oy);
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
                if(isClicked(e.getX(),e.getY())){
                    canDragged=true;
                }else{
                    canDragged=false;
                }
                originPosition();
                break;
            }

            case DRAGGED:{
                if(canDragged){
                    //System.out.println("BUTTON_DRAGGED");
                    centerOffset(e.getX(),e.getY());
                }
                break;
            }
            case RELEASED:{
                //System.out.println("Button_Release");
                if(canDragged && canBuild){
                    build(e.getX(),e.getY());
                }
                //System.out.println("BUTTON_RELEASED");
                originPosition();
                break;
            }

        }
    }
}
