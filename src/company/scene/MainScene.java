package company.scene;

import company.Global;
import company.controllers.SceneController;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

import static company.gameobj.BuildingController.*;

import company.gameobj.BuildingController;
import company.gameobj.Rect;
import company.gameobj.message.HintDialog;
import company.gameobj.message.ToastController;
import company.gametest9th.utils.*;
import company.gameobj.GameObject;
import company.gameobj.background.Background;
import company.gameobj.background.component.*;
import company.gameobj.buildings.Building;
import company.gameobj.creature.human.Citizen;
import company.gameobj.buildings.Base;
import company.gameobj.creature.human.Citizens;
import company.gameobj.creature.human.Human;
import oldMain.City;

import java.awt.*;

import static company.Global.*;
import static company.gameobj.BuildingController.BuildingType.*;
import static company.gameobj.BuildingController.BuildingType.ARSENAL;

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

    //現在位置
    private int currentX;
    private int currentY;


    //前一個滑鼠狀態
    private CommandSolver.MouseState preState;
    private boolean hasSetTarget;
    private int mouseX;
    private int mouseY;

    //現實時間
    Delay realityDelay;
    private int realityTimeSpeed; //時間的速度
    //遊戲時間
    Delay gameDelay;
    private int gameTimeSpeed;

    int thisRoundTimePass; //要跳過的時間

    //與建築相關

    City city; //城市
    BuildingType type;
    private BuildingArea buildingArea;
    //private boolean preOnBuildArea[][];

    boolean isPreAllNonBuildGrid;
    // 提示詞
    private String message;

    private long startTime;
    private int nowTime;
    private String outputTimeStr;

    boolean preDragging;

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
        buildingArea = new BuildingArea();


        //base = new Base(SCREEN_X / 2 - (BUILDING_WIDTH + 120), SCREEN_Y / 2 - (BUILDING_HEIGHT), BUILDING_WIDTH + 100, BUILDING_HEIGHT + 100);
        base = new Base(SCREEN_X / 2, SCREEN_Y / 2);

        // 測試: 預設有3個 村民
        citizens = new Citizens(3);

        // 村民出生位置現在都是測試
        for (int i = 0; i < 1; i++) {
            citizens.add(new Citizen(906, 602 + (i * 100)));
        }
        //現實時間速度
        realityTimeSpeed = 120000;
        realityDelay = new Delay(realityTimeSpeed);
        realityDelay.loop();

        gameTimeSpeed=120;
        gameDelay=new Delay(gameTimeSpeed);
        gameDelay.loop();


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

        // 建築物基座
        buildingArea.paint(g);

        //建築物選單
        buildingOption.paint(g);
        g.setColor(Color.red);

        //城市
        city.paint(g);



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
        g.drawString(outputTimeStr, ICON_START_X + ICON_GAP * 5 + 100, ICON_HEIGHT);


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
        //畫當前按鈕
        if(buildingOption.getCurrentButton()!=null){
            buildingOption.getCurrentButton().paint(g);
        }



        //提示框
        HintDialog.instance().paint(g);
        ToastController.instance().paint(g);

    }
    private boolean canBuild;
    @Override
    public void update() {
        // 把 毫秒 換算回 秒 (1秒 = 10的9次方 * 1毫秒)
        nowTime = Math.round((System.nanoTime() - startTime) / 1000000000);

        // 如果 < 60 => 只顯示秒  否則 顯示分&&秒 (應該不可能有人玩一小時吧)
        if (nowTime < 60) {
            outputTimeStr = nowTime + " 秒";
        } else {
            outputTimeStr = nowTime / 60 + " 分 " + nowTime % 60 + " 秒";
        }


        //建築物相關測試
        buildingOption.update();
        buildingArea.update();

        city.update();
        //判斷現在有無選取按鈕
        if (buildingOption.getCurrentButton() != null) {
            //取得現在選取的按鈕 及 建築類型
            BuildingButton currentButton = buildingOption.getCurrentButton();
            type = BuildingType.getBuildingTypeByInt(currentButton.getId());
            //點選按鈕時 判斷可否蓋建築物
            if (currentButton.getCountPressed()>=0 && currentButton.isPressed() && !currentButton.isDragging() && !currentButton.isReleased()) {
                currentButton.decCountPressed();
                if (city.getBuildingNum() == city.MAX_CAN_BUILD) {
                    canBuild=false;
                    currentButton.setCanDragging(false);
                    ToastController.instance().print("建築物已蓋滿");
                    //System.out.println("你的城市 經過多年風風雨雨 鐵與血的灌溉\n如今 從杳無人煙之地 成了 充斥著滿滿的高樓大廈 人車馬龍的繁華之地\n你的城市 已沒有地方可以建造新的建築了");
                }
                else if (City.getTechLevel() < type.instance().getTechLevelNeedBuild()) {
                    canBuild=false;
                    currentButton.setCanDragging(false);
                    ToastController.instance().print("科技等級不足");
                }
                else if (!type.instance().isEnoughBuild(city.getResource())) {
                    canBuild=false;
                    currentButton.setCanDragging(false);
                    ToastController.instance().print("物資不足");
                }else {
                    canBuild=true;
                    currentButton.setCanDragging(true);
                }
            }
            //建造階段
            if(canBuild){
                //判斷是否進入可建造區
                for (int i = 0; i < buildingArea.lengthY(); i++) {
                    for (int j = 0; j < buildingArea.lengthX(); j++) {
                        //將重疊區域回傳給buildingOption
                        Rect greenRect = currentButton.overlapObject(buildingArea.get(i, j));
                        currentButton.setGreenRect(greenRect);

                        //滑鼠放開時，判斷上一偵是否在建造區中
                        if (currentButton.isReleased && buildingArea.get(i, j).isOnBuildGrid()) {//
                            //建造房子
                            city.build(type, currentX - BUILDING_WIDTH / 2, currentY - BUILDING_HEIGHT / 2);
                            ToastController.instance().print(type.instance().getName() + "建造成功");
                        }
                        //判斷是否蓋在建築區上
                        buildingArea.get(i, j).setOnBuildGrid(buildingArea.get(i, j).isCover(currentButton));
                    }
                }
                //滑鼠放開瞬間 判斷上一偵是否是拖曳且不再區域內
                if (currentButton.isReleased && isPreAllNonBuildGrid && preDragging) {
                    ToastController.instance().print("此處不能蓋房子");
                }
                preDragging = currentButton.isDragging();
                isPreAllNonBuildGrid = buildingArea.isAllNonOnBuildGrid();
            }
        }

        //升級
        if(Building.SelectBuilding!=null && BuildingController.BuildingNode.selectBuildingNode!=null){
            //show出可以升級的建築，且有可以升級的才執行選項
            //city.showCanUpgradeBuilding();
            //選取要升級的種類
            if (city.isNoLab() && city.isNoArsenal()) {
                //break;
            }
            BuildingController.BuildingNode selectBuilding=BuildingController.BuildingNode.selectBuildingNode;
            BuildingType type = BuildingType.getBuildingTypeByInt(selectBuilding.getBuilding().getId());
            //如過建築鏈表中有可以升級的建築就會被顯示出來
            if (city.canUpgradeBuilding(type) && selectBuilding.getBuilding().upGradeIcon.getCountClick()>0) {
                //顯示可以升級的建築細節，取得可升級建築陣列
                ArrayList<BuildingController.BuildingNode> canUpgradeTypeList = city.showAndGetCanUpgradeTypeDetail(type);
                //若陣列不為空，代表有閒置的研究所或兵工廠可以使用
                if (canUpgradeTypeList != null) {
                    switch (type) {
                        case LAB: {
                            if (city.isUpgradingTech()) {
                                ToastController.instance().print("科技已在升級中，請等待此次升級結束");
                                //System.out.println("科技已在升級中，請等待此次升級結束");
                            } else {
                                city.upgradeTechLevel();
                                ToastController.instance().print("科技升級中");
                                //System.out.println("科技升級中");
                            }
                            break;
                        }
                        case ARSENAL: {
                            //choose = inputInt("要升級1.士兵 2.飛機", 1, 2);
                            int aaaa=2;
                            switch (aaaa) {//choose
                                case 1: {
                                    if (city.isUpgradingSoldier()) {
                                        System.out.println("士兵已在升級中，請等待此次升級結束");
                                    } else {
                                        city.upgradeSoldier();
                                        System.out.println("士兵升級中");
                                    }
                                    break;
                                }
                                case 2: {
                                    if (city.isUpgradingPlane()) {
                                        System.out.println("飛機已在升級中");
                                    } else {
                                        city.upgradePlane();
                                        System.out.println("飛機升級中");
                                    }
                                    break;
                                }
                            }
                            break;
                        }
                        default: {
                            city.upgrade(selectBuilding);
                            ToastController.instance().print("安排升級中");
                            //System.out.println("安排升級中");
                        }
                    }
                }
            } else {
                if (type == LAB && city.getFreeLabNum() == 0) {
                    //ToastController.instance().print("沒有閒置的研究所");
                    //System.out.println("沒有閒置的研究所");
                }
                if (type == ARSENAL && city.getFreeArsenalNum() == 0) {
                    //ToastController.instance().print("沒有閒置的兵工廠");
                    //System.out.println("沒有閒置的兵工廠");
                }
                if (City.getTechLevel() < type.instance().getTechLevelNeedUpgrade()) {
                    //ToastController.instance().print("科技等級不足");
                    //System.out.println("科技等級不足");
                }
                if (!type.instance().isEnoughUpgrade(city.getResource())) {
                    //ToastController.instance().print("物資不足");
                    //System.out.println("物資不足");
                }
            }
        }























        if (gameDelay.count()) {
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
                    if (random(0, 2) == 1) {
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
//            if(citizen.getMoveStatus()==Animator.State.STAND){
//                continue;
//            }
            //如果人物走到與建築重疊了，將其拉回剛好接觸但不重疊的位置並且讓人物知道這個方向被擋住了，換個方向
            if (citizen.painter().overlap(base.painter())) {
                System.out.println("Overlap!!!");
                if (base.isCovering(citizen.targetX(), citizen.targetY())) {
                    citizen.stop();
                }
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
                            citizen.translateY(base.painter().bottom() - citizen.painter().top());
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
                    //System.out.println("blocked direction: " + citizen.getBlockedDir());
//                    if(base.isCovering(citizen.targetX(),citizen.targetY())){
//                        citizen.stop();
//                        System.out.println("stop");
//                        return;
//                    }
                    switch (citizen.getBlockedDir()) {
                        case LEFT: {
                            if (!citizen.touchRightOf(base)) {
                                citizen.setNoBlockedDir();
//                                citizen.setWalkingDir(Direction.LEFT);
                            }
                            break;
                        }
                        case RIGHT: {
                            if (!citizen.touchLeftOf(base)) {
                                citizen.setNoBlockedDir();
//                                citizen.setWalkingDir(Direction.RIGHT);
                            }
                            break;
                        }
                        case UP: {
                            if (!citizen.touchBottomOf(base)) {
                                citizen.setNoBlockedDir();
//                                citizen.setWalkingDir(Direction.UP);
                            }
                            break;
                        }
                        case DOWN: {
                            if (!citizen.touchTopOf(base)) {
                                citizen.setNoBlockedDir();
//                                citizen.setWalkingDir(Direction.DOWN);
                            }
                            break;
                        }
                    }
                } else {
                    //System.out.println("No blocked direction");
                }
            }
            /*
            switch (citizen.getWalkingDir()){
                case RIGHT:{
                   if(citizen.touchRight()){
                       citizen.translateX(-1*(citizen.painter().right()-SCREEN_X));
                       citizen.stop();
                   }
                    break;
                }
                case LEFT:{
                    if(citizen.touchLeft()){
                        citizen.translateX(-1 * citizen.painter().left());
                        citizen.stop();
                    }
                    break;
                }

                case UP:{
                    if(citizen.touchTop()){
                        citizen.translateY(-1 * citizen.painter().top());
                        citizen.stop();
                    }
                    break;
                }

                case DOWN:{
                    if(citizen.touchBottom()){
                        citizen.translateY(-1*(citizen.painter().bottom()- SCREEN_Y));
                        citizen.stop();
                    }
                    break;
                }
            }
             */
        }
        if (!city.isAlive()) {
            StartScene startScene = new StartScene(); //還沒有結束畫面已此充當結束遊戲
            //SceneController.getInstance().change(startScene);
        }
    }


    @Override
    public CommandSolver.MouseCommandListener mouseListener() {

        return (e, state, trigTime) -> {


            if (state == null) {
                return;
            }

            currentX = e.getX();
            currentY = e.getY();

            HintDialog.instance().mouseTrig(e, state, trigTime);

            city.mouseTrig(e,state,trigTime);

            //如果現在沒有框選
            if (!canUseBoxSelection) {
                // 選單控制
                buildingOption.mouseTrig(e, state, trigTime);
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

                    if (e.getButton() == MouseEvent.BUTTON2) {
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
