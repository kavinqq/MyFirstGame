package company.scene;

import company.Global;
import company.controllers.SceneController;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

import static company.gameobj.BuildingController.*;

import company.gameobj.Rect;
import company.gameobj.background.HintDialog;
import company.gametest9th.utils.*;
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
import oldMain.City;

import java.awt.*;

import static company.Global.*;

/**
 * 遊戲主場景
 */
public class MainScene extends Scene implements CommandSolver.KeyListener {


    private Image resourceBarUI; // 上方的資源欄位UI

    private Image citizenNumIcon;// 村民數量Icon
    private Image soldierNumIcon;// 士兵數量Icon

    private Image steelIcon;// 鋼鐵數量Icon
    private Image treeIcon;//  木頭數量Icon
    private Image gasIcon; //   瓦斯數量Icon
    private Image timeIcon; //  時間Icon

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
    private BuildingArea buildingArea;

    //前一個滑鼠狀態
    private CommandSolver.MouseState preState;
    private boolean hasSetTarget;
    private int mouseX;
    private int mouseY;

    //時間
    Delay delay;
    private int timeSpeed; //時間的速度
    int thisRoundTimePass; //要跳過的時間

    //與建築相關

    City city; //城市
    BuildingType type;

    // 提示詞
    private String message;
    private HintDialog hintDialog;

    //當前滑鼠位置
    private int currentMouseX;
    private int currentMouseY;

    private long startTime;
    private int nowTime;
    private String outputTimeStr;

    @Override
    public void sceneBegin() {

        startTime = System.nanoTime();// 進入場景之後紀錄開始時間


        city = new City();// city本體
        currentObjs = new ArrayList<>(); // 目前的框選單位列表

        //背景
        background = new Background(0, 0, SCREEN_X, SCREEN_Y);


        //建築物選單
        buildingOption = new BuildingOption();


        //測試:建築物
        building1 = new SawMill(500, 500);
        building2 = new SteelMill(1200, 500);
        buildingArea=new BuildingArea();


        //base = new Base(SCREEN_X / 2 - (BUILDING_WIDTH + 120), SCREEN_Y / 2 - (BUILDING_HEIGHT), BUILDING_WIDTH + 100, BUILDING_HEIGHT + 100);
        base = new Base(SCREEN_X / 2, SCREEN_Y / 2);


        //提示框:
        hintDialog = HintDialog.instance();
        message = "";

        // 測試: 預設有3個 村民
        citizens = new Citizens(3);

        // 村民出生位置現在都是測試
        for (int i = 0; i < 3; i++) {
            citizens.add(new Citizen(200, 250 + (i * 100)));
        }

        //時間速度
        timeSpeed = 120000;
        delay = new Delay(timeSpeed);
        delay.loop();
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

        // UI 圖片
        resourceBarUI = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().resourceBarUI());

        // Icon圖片
        citizenNumIcon = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().citizenNumIcon());
        soldierNumIcon = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().soldierNumIcon());
        treeIcon = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().treeIcon());
        steelIcon = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().steelIcon());
        gasIcon = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().gasIcon());
        timeIcon = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().timeIcon());

    }

    @Override
    public void sceneEnd() {
    }


    @Override
    public void paint(Graphics g) {
        // 背景
        background.paint(g);

        buildingArea.paint(g);

        //建築物選單
        buildingOption.paint(g);
        g.setColor(Color.red);


        //提示框
        hintDialog.paint(g);


        //資源欄 UI
        // (70,0,500,60) => 我只取這個UI來源的 中間黑色部分
        g.drawImage(resourceBarUI, 0, 0, WINDOW_WIDTH, STATUS_BAR_HEIGHT, 70, 0, 500, 60, null);

        // 每一組Icon + 搭配的數字 [還沒搭配到的 我先不寫]
        // 樹木資源
        g.drawImage(treeIcon, ICON_START_X, ICON_START_Y, ICON_WIDTH, ICON_HEIGHT, null);

        // 鋼鐵資源
        g.drawImage(steelIcon, ICON_START_X + ICON_GAP * 1, ICON_START_Y, ICON_WIDTH, ICON_HEIGHT, null);

        // 瓦斯資源
        g.drawImage(gasIcon, ICON_START_X + ICON_GAP * 2, ICON_START_Y, ICON_WIDTH, ICON_HEIGHT, null);

        // 市民數量
        g.drawImage(citizenNumIcon, ICON_START_X + ICON_GAP * 3, ICON_START_Y, ICON_WIDTH, ICON_HEIGHT, null);

        //士兵數量
        g.drawImage(soldierNumIcon, ICON_START_X + ICON_GAP * 4, ICON_START_Y, ICON_WIDTH, ICON_HEIGHT, null);


        //遊戲時間
        g.drawImage(timeIcon, ICON_START_X + ICON_GAP * 5, ICON_START_Y, ICON_WIDTH, ICON_HEIGHT, null);
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g.drawString(outputTimeStr,ICON_START_X + ICON_GAP * 5 + 100,ICON_HEIGHT);


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
            boxSelection.paint(g);
        }


        // 用血量條來判斷操控哪個人物
        if (currentObj != null) {
            g.setColor(Color.GREEN);
            g.fillRect(currentObj.painter().left(), currentObj.painter().bottom() + 3, currentObj.painter().width(), 10);
            g.setColor(Color.black);
        }

        // 如果現在框選的遊戲物件列表有東西 而且 也沒有單一操控某個物件時
        if (!currentObjs.isEmpty()) {
            for (GameObject gameObject : currentObjs) {
                g.setColor(Color.GREEN);
                g.fillRect(gameObject.painter().left(), gameObject.painter().bottom() + 3, gameObject.painter().width(), 10);
                g.setColor(Color.black);
            }
        }

    }

    @Override
    public void update() {

            buildingOption.update();
            //判斷是否進入可建造區
        if (buildingOption.getMouseRect() != null) {
            for (int i = 0; i < buildingArea.lengthY(); i++) {
                for (int j = 0; j < buildingArea.lengthX(); j++) {
                    if(buildingArea.get(i,j).isCover(buildingOption.getMouseRect())){
                        //System.out.println("OK");


                    }
                }
            }
        }
        buildingOption.clearMouseRect();
//建築物相關測試


        // 把 毫秒 換算回 秒 (1秒 = 10的9次方 * 1毫秒)
        nowTime = Math.round((System.nanoTime() - startTime) / 1000000000) ;

        // 如果 < 60 => 只顯示秒  否則 顯示分&&秒 (應該不可能有人玩一小時吧)
        if(nowTime < 60){
            outputTimeStr = nowTime + " 秒";
        } else {
            outputTimeStr = nowTime / 60 + " 分 " + nowTime % 60 + " 秒";
        }


        //建築物相關測試

        type = BuildingType.getBuildingTypeByInt(buildingOption.getCurrentIdByButton());

        //city.getBuildingsNum()
        //建造成功與否
        if (type != null) {
            if (city.getBuildingNum() != city.MAX_CAN_BUILD && city.canBuildBuilding(type)) {
                city.build(type);
                System.out.println(type.instance().getName() + "建造中");
            } else {
                if (city.getBuildingNum() == city.MAX_CAN_BUILD) {
                    message = "a建築物已蓋滿";
                    System.out.println("你的城市 經過多年風風雨雨 鐵與血的灌溉\n如今 從杳無人煙之地 成了 充斥著滿滿的高樓大廈 人車馬龍的繁華之地\n你的城市 已沒有地方可以建造新的建築了");
                }
                if (City.getTechLevel() < type.instance().getTechLevelNeedBuild()) {
                    message = "b科技等級不足";
                    System.out.println("科技等級不足");
                }
                if (!type.instance().isEnoughBuild(city.getResource())) {
                    message = "c物資不足";
                    System.out.println("物資不足");
                }
            }
        }
        //提示框
        //hintDialog.setHintMessage(message);
        buildingOption.setCurrentIdByButton(0); //fix 取過後強制設成0

        //時間
        if(delay.count()){
            //city.showInfo();
            thisRoundTimePass = 1;
            city.doCityWorkAndTimePass(thisRoundTimePass);
        }

        // 框選Box狀態on
        if (canUseBoxSelection) {

            // 更新box的狀態
            boxSelection.update();

            // 把框到的市民 加入到 tmpCurrentObjs
            List<Human> tmpCurrentObjs = citizens.getBoxCitizens(boxSelection.getBox());

            if (!tmpCurrentObjs.isEmpty()) {
                currentObjs = new ArrayList<>(tmpCurrentObjs);
            }
        }

        // 如果 現在有操控單位 單選 && 有設定要前往的目標
        if (currentObj != null && hasSetTarget) {
            // 走過去
            currentObj.setTarget(mouseX, mouseY);

            // reset boolean
            hasSetTarget = false;
        }

        // 如果存有當前框選的所有物件的陣列 有東西
        if (!currentObjs.isEmpty() && hasSetTarget) {
            int count = 0;
            for (Human human : currentObjs) {
                if (count != 0) {
                    if (Global.random(0, 2) == 1) {
                        mouseX += 74;
                    } else {
                        mouseY += 74;
                    }
                }
                human.setTarget(mouseX, mouseY);
                count++;
            }

            // reset boolean
            hasSetTarget = false;
        }


        // 更新所有村民狀態
        citizens.updateAll();

        for (Citizen citizen : citizens.getAllCitizens()) {
            if (citizen.painter().overlap(base.painter())) {
                switch (citizen.getWalkingDir()) {
                    case RIGHT: {
                        if (citizen.touchLeftOf(base)) {
                            citizen.translateX(-1 * (citizen.painter().right() - base.painter().left()));
                            citizen.setBlockedDir(Direction.RIGHT);
                        }
                        break;
                    }
                    case LEFT: {
                        if (citizen.touchRightOf(base)) {
                            citizen.translateX(base.painter().right() - citizen.painter().left());
                            citizen.setBlockedDir(Direction.LEFT);
                        }
                        break;
                    }
                    case UP: {
                        if (citizen.touchBottomOf(base)) {
                            citizen.translateY(citizen.painter().top() - base.painter().bottom());
                            citizen.setBlockedDir(Direction.UP);
                        }
                        break;
                    }
                    case DOWN: {
                        if (citizen.touchTopOf(base)) {
                            citizen.translateY(-1 * (citizen.painter().bottom() - base.painter().top()));
                            citizen.setBlockedDir(Direction.DOWN);
                        }
                        break;
                    }
                }
            } else {
                if (citizen.getBlockedDir() != null) {
                    System.out.println("out");
                    System.out.println(citizen.getBlockedDir());
                    switch (citizen.getBlockedDir()) {
                        case LEFT: {
                            if (!citizen.touchRightOf(base)) {
                                citizen.setNoBlockedDir();
                                citizen.setWalkingDir(Direction.LEFT);
                            }
                            break;
                        }
                        case RIGHT: {
                            if (!citizen.touchLeftOf(base)) {
                                citizen.setNoBlockedDir();
                                citizen.setWalkingDir(Direction.RIGHT);
                            }
                            break;
                        }
                        case UP: {
                            if (!citizen.touchBottomOf(base)) {
                                citizen.setNoBlockedDir();
                                citizen.setWalkingDir(Direction.UP);
                            }
                            break;
                        }
                        case DOWN: {
                            if (!citizen.touchTopOf(base)) {
                                citizen.setNoBlockedDir();
                                citizen.setWalkingDir(Direction.DOWN);
                            }
                            break;
                        }
                    }
                }
            }

            switch (citizen.getWalkingDir()) {
                case RIGHT: {
                    if (citizen.touchRight()) {
                        citizen.stop();
                    }
                    break;
                }
                case LEFT: {
                    if (citizen.touchLeft()) {
                        citizen.stop();
                    }
                    break;
                }
                case UP: {
                    if (citizen.touchTop()) {
                        citizen.stop();
                    }
                    break;
                }
                case DOWN: {
                    if (citizen.touchBottom()) {
                        citizen.stop();
                    }
                    break;
                }
            }
        }




        if(!city.isAlive()){
            StartScene startScene=new StartScene(); //還沒有結束畫面已此充當結束遊戲
            //SceneController.getInstance().change(startScene);
        }
    }


    @Override
    public CommandSolver.MouseCommandListener mouseListener() {

        return (e, state, trigTime) -> {

            if (state == null) {
                return;
            }

            //如果現在沒有框選
            if (!canUseBoxSelection) {

                // 選單控制
                buildingOption.mouseTrig(e, state, trigTime);


                //當前滑鼠位置
                currentMouseX = e.getX();
                currentMouseY = e.getY();

                hintDialog.setCuurentXY(currentMouseX, currentMouseY);
            } else {
                hintDialog.setHintMessage("");
            }

            // 如果現在可以使用框選系統
            if (canUseBoxSelection) {
                // 將當前的 滑鼠監聽 傳給 框選系統
                boxSelection.mouseTrig(e, state, trigTime);
            }

            // 根據不同的滑鼠事件去分類要做的事情
            switch (state) {

                case DRAGGED: {
//                    System.out.println("Drag " +trigTime);
                    break;
                }

                // 滑鼠放開瞬間觸發
                case RELEASED: {
//                    System.out.println("RELEASED "+trigTime);


                    // 如果現在能用Box框選模式的話
                    if (canUseBoxSelection) {
                        // 關掉他
                        canUseBoxSelection = false;
                    }

                    break;
                }

                // 按下去就觸發
                case PRESSED: {
//                    System.out.println("PRESSED "+trigTime);



                    // 把座標丟給citizens 讓他 判斷有沒有村民 符合條件
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        currentObj = citizens.getCitizen(e.getX(), e.getY());
                    }


                    // 什麼時候開啟框選模式?? => 當你沒點到一個可控單位時
                    if (canCommand(e.getX(), e.getY())) {
                        canUseBoxSelection = true;
                    }

                    // 如我現在有能操控的單位 (單選 或 框選)
                    // 按下右鍵 => 設定要前往的(x,y)
                    if ((currentObj != null || !currentObjs.isEmpty()) && e.getButton() == MouseEvent.BUTTON3) {
                        mouseX = e.getX();
                        mouseY = e.getY();
                        hasSetTarget = true;
                    }

                    break;
                }

                case CLICKED: {

//                    System.out.println("CLICKED "+trigTime);

                    if(e.getButton() == MouseEvent.BUTTON2) {
                        System.out.println("X: " + e.getX());
                        System.out.println("Y: " + e.getY());
                    }

                    break;
                }
                case EXITED: {

                    //                  System.out.println("EXIT");
                    break;
                }

                case WHEEL_MOVED: {
//             System.out.println("WHEEL_MOVED");

                    break;
                }

                case MOVED: {
//System.out.println("MOVED");
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
        // 按下s的同時 所有操控單位停止移動
        if (c == 's' || c == 'S') {
            if (currentObj != null) {
                currentObj.stop();
            }

            if (currentObjs != null) {
                for (Human human : currentObjs) {
                    human.stop();
                }
            }
        }
    }
}
