package company.scene;

import company.controllers.SceneController;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

import company.gametest9th.utils.BoxSelection;
import company.gameobj.GameObject;
import company.gameobj.background.Background;
import company.gameobj.background.component.*;
import company.gameobj.buildings.Building;
import company.gameobj.buildings.SawMill;
import company.gameobj.buildings.SteelMill;
import company.gameobj.creature.human.Citizen;
import company.gameobj.buildings.Base;
import company.gameobj.creature.human.Citizens;
import company.gameobj.creature.human.Human;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;
import oldMain.City;

import java.awt.*;

import static company.Global.*;

/**
 * 遊戲主場景
 */
public class MainScene extends Scene implements CommandSolver.KeyListener {

    private Image verticalBar; // 將來的 欄位bar圖片
    private Image horizontalBar; // 將來的 欄位bar圖片

    private List<Human> currentObjs; // 測試: 目前的框選單位列表[設定: 只有人類才能被框選 建築物要用點的]

    private Base base;  // 主堡
    private Background background; // 背景
    private BuildingOption buildingOption; // 建築物選單

    private Citizen currentObj; // 當前操控的物件
    private Citizens citizens;// 所有村民類別

    private BoxSelection boxSelection; // 框選模式
    private boolean canUseBoxSelection; // 是否能用框選模式

    //測試: 建築物
    private Building building1;
    private Building building2;

    //前一個滑鼠狀態
    private CommandSolver.MouseState preState;
    private boolean hasSetTarget;
    private int mouseX;
    private int mouseY;

    //城市
    City city;
    @Override
    public void sceneBegin() {
        city=new City();
        currentObjs = new ArrayList<>(); // 目前的框選單位列表

        horizontalBar = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().horizontalBar());
        verticalBar = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().verticalBar());

        //背景
        background = new Background(0, 0, SCREEN_X, SCREEN_Y);


        //建築物選單
        buildingOption = new BuildingOption();


        //測試:建築物
        building1 = new SawMill(500, 500);
        building2 = new SteelMill(1200, 500);


        //base = new Base(SCREEN_X / 2 - (BUILDING_WIDTH + 120), SCREEN_Y / 2 - (BUILDING_HEIGHT), BUILDING_WIDTH + 100, BUILDING_HEIGHT + 100);
        base = new Base(SCREEN_X / 2, SCREEN_Y / 2);


        // 測試: 預設有3個 村民
        citizens = new Citizens(3);

        // 村民出生位置現在都是測試
        for (int i = 0; i < 3; i++) {
            citizens.add(new Citizen(200, 250 + (i * 100), Animator.State.STAND));
        }

        // 當前操控的物件
        currentObj = null;

        // 框選模式
        boxSelection = new BoxSelection();
        canUseBoxSelection = false;
        preState = null;

        // 是否設定前往目標
        hasSetTarget = false;

        // 上一幀滑鼠X,Y
        mouseX = 0;
        mouseY = 0;

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

        //測試 建築
        building1.paint(g);
        building2.paint(g);

        // 主堡
        base.paint(g);

        //建築物選單範圍測試

        g.drawRect(BUILDING_OPTION_X, BUILDING_OPTION_Y, BUILDING_OPTION_WIDTH, BUILDING_OPTION_HEIGHT);


        g.setColor(Color.black);
        g.drawRect(LAND_X, LAND_Y, LAND_WIDTH, LAND_HEIGHT);


        // 畫出每一個村民
        citizens.paintAll(g);

        if (canUseBoxSelection) {
            boxSelection.getBox().paint(g);
        }


        // 用血量條來判斷操控哪個人物
        if (currentObj != null) {
            g.setColor(Color.GREEN);
            g.fillRect(currentObj.painter().left(), currentObj.painter().bottom() + 3, currentObj.painter().width(), 10);
            g.setColor(Color.black);
        }

        // 如果現在框選的遊戲物件列表有東西 而且 也沒有單一操控某個物件時
        if (!currentObjs.isEmpty() && currentObj == null) {
            for (GameObject gameObject : currentObjs) {
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

        // 框選Box狀態
        if (canUseBoxSelection) {
            boxSelection.getBox().update();

            currentObjs = citizens.getBoxCitizens(boxSelection.getBox());
        }


        if(currentObj != null && hasSetTarget){
            currentObj.setTarget(mouseX, mouseY);
        }

        // 如果存有當前框選的所有物件的陣列 有東西
        if (!currentObjs.isEmpty() && hasSetTarget) {
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

            // record previous mouse state
            preState = state;

            // fix: 框選按超快 會有一個Pressed Lose掉 直接assign兩個 released
            if (preState == CommandSolver.MouseState.RELEASED) {
                state = CommandSolver.MouseState.PRESSED;
            }

            // 選單控制
            buildingOption.mouseTrig(e, state, trigTime);

            // 如果現在可以使用框選系統
            if (canUseBoxSelection) {
                // 將當前的 滑鼠監聽 傳給 框選系統
                boxSelection.mouseTrig(e, state, trigTime);
            }

            // 根據不同的滑鼠事件去分類要做的事情
            switch (state) {

                case CLICKED: {

                    System.out.println("CLICKED");

                    if(e.getButton() == MouseEvent.BUTTON2) {
                        System.out.println("X: " + e.getX());
                        System.out.println("Y: " + e.getY());
                    }

                    break;
                }

                case DRAGGED: {
//                    System.out.println("Drag");
                    break;
                }

                // 滑鼠放開瞬間觸發
                case RELEASED: {

                    System.out.println("RELEASED");

                    // 如果現在能用Box框選模式的話
                    if (canUseBoxSelection) {
                        // 關掉他
                        canUseBoxSelection = false;
                    }

                    break;
                }

                // 按下去就觸發
                case PRESSED: {
                    System.out.println("PRESSED");

                    if(currentObj == null) {
                        // 把座標丟給citizens 讓他 判斷有沒有村民 符合條件
                        currentObj = citizens.getCitizen(e.getX(), e.getY());
                    }

                    // 什麼時候開啟框選模式?? => 當你沒點到一個可控單位時
                    if (currentObj == null && currentObjs.isEmpty()) {
                        canUseBoxSelection = true;
                    } else {
                        // 如我現在有能操控的單位 (單選 或 框選)
                        // 按下右鍵 => 設定要前往的(x,y)
                        if(e.getButton() == MouseEvent.BUTTON3) {
                            mouseX = e.getX();
                            mouseY = e.getY();
                            hasSetTarget = true;
                        }
                    }

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

        // 按下ESC => Reset 所有操控單位
        if (commandCode == ESC) {
            hasSetTarget = false;
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
