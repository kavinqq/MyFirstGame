package company.scene;


import java.util.List;
import java.util.ArrayList;

import company.controllers.SceneController;
import company.gameobj.Box;
import company.gameobj.GameObject;
import company.gameobj.background.Background;
import company.gameobj.background.component.*;
import company.gameobj.creature.human.Citizen;
import company.gameobj.Road;
import company.gameobj.buildings.Base;
import company.gameobj.creature.human.Citizens;
import company.gameobj.creature.human.Human;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import java.awt.*;

import static company.Global.*;

/**
 * 遊戲主場景
 */
public class MainScene extends Scene implements CommandSolver.KeyListener {

    private Image verticalBar;
    private Image horizontalBar;

    private List<Human> currentObjs;
    private int mouseX;
    private int mouseY;
    private int boxTargetX;
    private int boxTargetY;

    private Base base;
    private Background background;
    private BuildingOption buildingOption;
    private Citizen currentObj; // 當前操控的物件
    private boolean isControlObjNow; // 現在是否有在操控一個物件
    private Citizens citizens;// 所有村民類別


    // 框選
    private Box box;
    private boolean isBoxSelectionMode;
    private boolean canDrawBox;
    private boolean isBoxVisible;

    private Road road;


    @Override
    public void sceneBegin() {

        currentObjs = new ArrayList<>();

        horizontalBar = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().horizontalBar());
        verticalBar = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().verticalBar());

        //背景
        background = new Background(0, 0, SCREEN_X, SCREEN_Y);
        //建築物選單
        buildingOption = new BuildingOption();
        //
        //buildingController=new MouseController(new Building(50,50));

        base = new Base(SCREEN_X / 2 - (BUILDING_WIDTH + 120), SCREEN_Y / 2 - (BUILDING_HEIGHT), BUILDING_WIDTH + 100, BUILDING_HEIGHT + 100);

        // 測試: 預設有3個 村民
        citizens = new Citizens(3);

        for (int i = 0; i < 3; i++) {
            citizens.add(new Citizen(200, 250 + (i * 100), Animator.State.STAND));
        }

        // 當前操控的物件
        currentObj = null;
        // 正在操控某個物件
        isControlObjNow = false;

        // 框選的 框框
        box = new Box();
        // 預設框選模式off
        isBoxSelectionMode = false;
        // 預設可以畫出框選模式的框框 off
        canDrawBox = false;
        // 是否能看到框框
        isBoxVisible = false;

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
        g.drawImage(horizontalBar, -50, 50, 1400, 10, null);
        g.drawRect(STATUS_BAR_X, STATUS_BAR_Y, STATUS_BAR_WIDTH, STATUS_BAR_HEIGHT);

        // 主堡
        base.paint(g);

        //建築物選單範圍測試

        g.drawRect(BUILDING_OPTION_X, BUILDING_OPTION_Y, BUILDING_OPTION_WIDTH, BUILDING_OPTION_HEIGHT);


        g.setColor(Color.black);
        g.drawRect(LAND_X, LAND_Y, LAND_WIDTH, LAND_HEIGHT);


        // 畫出每一個村民
        citizens.paintAll(g);

        // 如果 框選模式on 而且 也可以畫出框
        if (isBoxSelectionMode && canDrawBox && isBoxVisible) {
            box.paint(g);
        }

        // 用血量條來判斷操控哪個人物
        if (currentObj != null) {
            g.setColor(Color.GREEN);
            g.fillRect(currentObj.painter().left(), currentObj.painter().bottom() + 3, currentObj.painter().width(), 10);
            g.setColor(Color.black);
        }

        // 如果現在框選的遊戲物件列表有東西 而且 也沒有單一操控某個物件時
        if(!currentObjs.isEmpty() && currentObj == null){
            for(GameObject gameObject: currentObjs){
                g.setColor(Color.GREEN);
                g.fillRect(gameObject.painter().left(), gameObject.painter().bottom() + 3, gameObject.painter().width(), 10);
                g.setColor(Color.black);
            }
        }

    }

    @Override
    public void update() {
        // 更新所有村民狀態
        citizens.updateAll();

        if(isBoxSelectionMode && canDrawBox && isBoxVisible){
            box.update();
            currentObjs = citizens.getBoxCitizens(box);
        }

        // 如果現在框選的遊戲物件列表有角色 而且 也沒有單一操控某個角色時
        if(!currentObjs.isEmpty() && currentObj == null && !isBoxVisible){
            for(Human human: currentObjs) {
                human.setTarget(mouseX, mouseY);
            }
        }
    }

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

                    if (citizens.getCitizen(e.getX(), e.getY()) != null) {
                        currentObj = citizens.getCitizen(e.getX(), e.getY());
                        isControlObjNow = true;
                    }


                    break;
                }

                case DRAGGED: {

//                    System.out.println("Drag");

                    // 如果現在 框選模式On
                    if (isBoxSelectionMode) {
                        box.setEndXY(e.getX(), e.getY());
                        // 可以畫出位置
                        canDrawBox = true;
                        isBoxVisible = true;
                    }

                    break;
                }


                case RELEASED: {

                    System.out.println("RELEASED");

                    if (isBoxSelectionMode) {

                        isBoxSelectionMode = false;
                        canDrawBox = false;
                        isBoxVisible = false;
                    }

                    break;
                }


                case PRESSED: {
                    System.out.println("PRESSED");

                    mouseX = e.getX();
                    mouseY = e.getY();


                    // 如果點到可控單位
                    if (isControlObjNow && currentObj != null) {
                        currentObj.setTarget(e.getX(), e.getY());
                    }

                    // 如果沒點到可控單位
                    if (currentObj == null) {
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
                    if (isBoxSelectionMode) {
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
        if (commandCode == ESC) {
            currentObj = null;
            currentObjs.clear();
        }
    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {

    }

    @Override
    public void keyTyped(char c, long trigTime) {

    }
}
