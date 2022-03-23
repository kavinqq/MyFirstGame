package company.scene;

import company.gameobj.background.Background;
import company.Global;
import company.gameobj.background.component.*;
import company.gameobj.Citizen;
import company.gameobj.Road;


import company.gameobj.buildings.MouseController;

import company.gameobj.buildings.Base;
import company.gameobj.buildings.Building;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.CommandSolver;

import java.awt.*;

import static company.Global.*;

/**
 * 遊戲主場景
 */
public class MainScene extends Scene implements CommandSolver.KeyListener{


    private Base base;
    private Background background;
    private BuildingOption buildingOption;
    private Citizen citizen;
    private Citizen currentCitizen;
    private boolean canControlCitizen;
    private Building building;
    private MouseController buildingController;
    private Road road;


    @Override
    public void sceneBegin() {
        //背景
        background=new Background(0,0, SCREEN_X, SCREEN_Y);
        //建築物選單
        buildingOption=new BuildingOption();
        //
        //buildingController=new MouseController(new Building(50,50));


        base = new Base(SCREEN_X / 2 - (BUILDING_WIDTH + 120), SCREEN_Y /2 - (BUILDING_HEIGHT), BUILDING_WIDTH + 100, BUILDING_HEIGHT + 100);


        citizen = new Citizen(200,250,7, Animator.State.WALK);


        currentCitizen = citizen;
        canControlCitizen = false;
    }

    @Override
    public void sceneEnd() {
    }

    @Override
    public void paint(Graphics g) {
        // 背景
        background.paint(g);

        //建築物選單
        buildingOption.paint(g);

        //狀態攔範圍測試
        g.drawRect(STATUS_BAR_X, STATUS_BAR_Y, STATUS_BAR_WEIGHT, STATUS_BAR_HEIGHT);

        // 主堡
        base.paint(g);

        //建築物選單範圍測試
        g.drawRect(BUILDING_OPTION_X, BUILDING_OPTION_Y,BUILDING_OPTION_WIDTH,BUILDING_OPTION_HEIGHT);


        g.setColor(Color.black);
        g.drawRect(LAND_X, LAND_Y, LAND_WIDTH, LAND_HEIGHT);

        //test人物
        citizen.paint(g);
    }

    @Override
    public void update() {
        // 更新 村民狀態
        citizen.update();

        currentCitizen.mouseToMove();
    }



    private boolean canCatchRockFactory;

    @Override
    public CommandSolver.MouseCommandListener mouseListener() {

        return (e, state, trigTime) -> {
            if(state == null) {
                return;
            }

            //選單控制
            buildingOption.mouseTrig(e,state,trigTime);


            switch (state) {
                case ENTERED: {
                    System.out.println("ENTERED");
                    System.out.println("x:"+e.getX()+" y:"+e.getY());
                    break;

                }

                case CLICKED: {
//                    System.out.println("CLICKED");
                    if(e.getX() > currentCitizen.painter().left() && e.getX() < currentCitizen.painter().right()
                            && e.getY() > currentCitizen.painter().top() && e.getY() < currentCitizen.painter().bottom()){
                        canControlCitizen = true;
                    }

                    break;
                }


//                case MOVED: {
//
//                    break;
//                }

                case DRAGGED: {
                    //System.out.println("DRAGGED");
                    break;
                }

                case RELEASED: {
                 //   System.out.println("RELEASED");
                    break;
                }

                case PRESSED: {
//                    System.out.println("PRESSED");
                    if(canControlCitizen){
                        currentCitizen.setTarget(e.getX(), e.getY());
                    }
                    break;
                }



                case EXITED: {
                    System.out.println("EXITED");
                    break;
                }
            }
        };
    }


    @Override
    public CommandSolver.KeyListener keyListener() {
        return this;
    }

    @Override
    public void keyPressed(int commandCode, long trigTime) {
        if(commandCode == Global.UP || commandCode == Global.DOWN || commandCode == Global.LEFT || commandCode == Global.RIGHT){
            citizen.keyToMove(commandCode);
        }
    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {

    }

    @Override
    public void keyTyped(char c, long trigTime) {

    }
}
