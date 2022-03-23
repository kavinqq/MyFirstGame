package company.scene;

import company.gameobj.Box;
import company.gameobj.background.Background;
import company.Global;
import company.gameobj.background.component.*;
//import company.gameobj.Citizen;
import company.gameobj.creature.human.Citizen;
import company.gameobj.Road;
import company.gameobj.buildings.Base;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.CommandSolver;

import java.awt.*;

import static company.Global.*;

/**
 * 遊戲主場景
 */
public class MainScene extends Scene implements CommandSolver.KeyListener {


    private Base base;
    private Background background;
    private BuildingOption buildingOption;
    private Citizen citizen;
    private Citizen currentCitizen;
    private boolean canControlCitizen;

    // 框選
    private Box box;
    private boolean isBoxSelectionMode;
    private boolean canDrawBox;

    private Road road;


    @Override
    public void sceneBegin() {
        //背景
        background = new Background(0, 0, SCREEN_X, SCREEN_Y);
        //建築物選單
        buildingOption = new BuildingOption();
        //
        //buildingController=new MouseController(new Building(50,50));

        base = new Base(SCREEN_X / 2 - (BUILDING_WIDTH + 120), SCREEN_Y / 2 - (BUILDING_HEIGHT), BUILDING_WIDTH + 100, BUILDING_HEIGHT + 100);


        //citizen = new Citizen(200, 250, 7, Animator.State.WALK);
        citizen = new Citizen(200, 250, Animator.State.STAND);


        currentCitizen = citizen;
        canControlCitizen = false;

        box = new Box();
        isBoxSelectionMode = false;
        canDrawBox = false;

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
        g.drawRect(BUILDING_OPTION_X, BUILDING_OPTION_Y, BUILDING_OPTION_WIDTH, BUILDING_OPTION_HEIGHT);


        g.setColor(Color.black);
        g.drawRect(LAND_X, LAND_Y, LAND_WIDTH, LAND_HEIGHT);

        //test人物
        citizen.paint(g);

        if (isBoxSelectionMode && canDrawBox) {
            box.paint(g);
        }

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

            if (state == null) {
                return;
            }

            //選單控制
            buildingOption.mouseTrig(e, state, trigTime);


            switch (state) {


                case CLICKED: {
                    System.out.println("CLICKED");

                    if (currentCitizen.isClicked(e.getX(), e.getY())) {

                        canControlCitizen = true;
                    }

                    if(isBoxSelectionMode){
                        isBoxSelectionMode = false;
                    }

                    break;
                }

                case DRAGGED: {
                    if(isBoxSelectionMode){
                        canDrawBox = true;
                        box.setEndXY(e.getX(), e.getY());
                    }

                    break;
                }


                case RELEASED: {
                    System.out.println("RELEASED");

                    if(isBoxSelectionMode) {
                        System.out.println("TURN OFF");
                        isBoxSelectionMode = false;
                        canDrawBox = false;
                        box.setStartXY(-1, -1);
                        box.setEndXY(-1, -1);
                    }

                    break;
                }


                case PRESSED: {
                    System.out.println("PRESSED");

                    // 如果點到可控單位
                    if (canControlCitizen) {
                        currentCitizen.setTarget(e.getX(), e.getY());
                    }

                    // 如果沒點到可控單位
                    if (!currentCitizen.isClicked(e.getX(), e.getY())) {
                        // 如果框選模式on
                        isBoxSelectionMode = true;
                        box.setStartXY(e.getX(), e.getY());
                    }

                    break;
                }

                case ENTERED: {
                    System.out.println("ENTER");
                    break;
                }

                case EXITED: {
                    System.out.println("EXIT");
                    break;
                }

                case WHEEL_MOVED: {
                    System.out.println("WHEEL_MOVED");

                    break;
                }

                case MOVED: {
                    // 如果點太快 沒有released
                    if(isBoxSelectionMode){
                        isBoxSelectionMode = false;
                    }
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

    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {

    }

    @Override
    public void keyTyped(char c, long trigTime) {

    }
}
