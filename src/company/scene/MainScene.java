package company.scene;

import company.Global;


import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.List;
import java.util.ArrayList;

import static company.Global.*;
import static company.gameobj.BuildingController.*;

import company.controllers.AudioResourceController;
import company.controllers.SceneController;
import company.gameobj.BuildingController;
import company.gameobj.FogOfWar;
import company.gameobj.Rect;
import company.gameobj.buildings.Arsenal;
import company.gameobj.buildings.Base;
import company.gameobj.buildings.Building;
import company.gameobj.creature.Creature;
import company.gameobj.creature.enemy.Enemy;
import company.gameobj.creature.enemy.zombies.Zombie;
import company.gameobj.creature.enemy.zombies.ZombieKingdom;
import company.gameobj.creature.human.*;
import company.gameobj.resourceObjs.ResourceObj;
import company.gameobj.resourceObjs.ResourceSystem;
import company.gameobj.message.ToastController;
import company.gametest9th.utils.*;
import company.gameobj.GameObject;
import company.gameobj.background.Background;
import company.gameobj.background.component.*;

import oldMain.City;

import java.awt.*;


import static company.gameobj.BuildingController.BuildingType.values;

/**
 * 遊戲主場景
 */
public class MainScene extends Scene implements CommandSolver.KeyListener {

    private Building base;  // 主堡
    private Background background; // 背景
    private BuildingOption buildingOption; // 建築物選單
    private TarmacArr tarmacArr; //停機坪
    private Human currentObj; // 當前操控的物件(單選)
    private BuildingButton currentButton; //當前操控按鈕
    private BuildingController.BuildingNode currentBuildNode; //操控當前建築物結點

    private List<Human> controlHumans;// 當前操控的物件(多選)

    private BoxSelection boxSelection; // 框選模式
    private boolean canUseBoxSelection; // 是否能用框選模式

    //滑鼠相關
    private int targetX; // 滑鼠右鍵點擊的 目標X
    private int targetY; // 滑鼠右鍵點擊的 目標Y

    private boolean hasSetTarget; // 是否設定了目標

    private int currentMouseX;// 這一帧當下的滑鼠X
    private int currentMouseY;// 這一帧當下的滑鼠Y

    //與建築相關
    City city; //城市

    ZombieKingdom zombieKingdom;


    private BuildingArea buildingArea; //可建造區

    private boolean preDragging; //上一偵有無拖曳

    private boolean canBuild;//判斷可否建造

    private boolean isPreAllNonBuildGrid;//判斷上一偵是否按鈕沒碰到全部建築格

    // 時間相關
    private long startTime; // 開始時間

    private Delay gameDelay; //遊戲時間

    private int gameTimeSpeed;//遊戲進行時間

    private int thisRoundTimePass; //要跳過的時間

    private boolean preCanBuild;

    // 資源
    private ResourceSystem resourceSystem;

    // 戰爭迷霧

    private FogOfWar fogOfWar;

    private List<Effect> effects = new ArrayList<>();

    //TODO: Del
    //private ZombieNormal zombieNormal;

    // 點選 命令 圖示
    private Image chooseUnit;

    @Override
    public void sceneBegin() {

        startTime = System.nanoTime();// 進入場景之後紀錄開始時間

        //當前操控的物件(單選)
        currentObj = null;

        // 設定: 只有人物可以多選
        controlHumans = new ArrayList<>();

        //背景
        background = new Background(SCREEN_X, SCREEN_Y, SCREEN_WIDTH, SCREEN_HEIGHT);

        //建築物選單
        buildingOption = new BuildingOption();

        //停機坪
        tarmacArr = new TarmacArr();

        //可建築區
        buildingArea = new BuildingArea();

        //當前操控的按鈕
        currentButton = null;

        //圖片
        chooseUnit = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().chooseUnit());


        //上一幀是否可建造
        preCanBuild = true;

        // city本體
        city = new City();
        zombieKingdom = new ZombieKingdom();


        // 測試: 預設有 ? 個 村民

        city.setCitizens(Global.CitizenNum);


        //設定遊戲時間 60偵為遊戲一小時(oldMain的流動一小時)
        gameTimeSpeed = 60;
        gameDelay = new Delay(gameTimeSpeed);
        gameDelay.loop();

        // 當前操控的物件
        currentObj = null;

        // 框選模式
        boxSelection = new BoxSelection();
        canUseBoxSelection = false;

        // 是否設定前往目標
        hasSetTarget = false;

        // 目標X Y
        targetX = 0;
        targetY = 0;

        // 資源
        resourceSystem = new ResourceSystem();

        // 迷霧
        fogOfWar = new FogOfWar();

        AudioResourceController.getInstance().loop(new Path().sound().bgm2(), 3);

        //TODO: Del
//        zombieNormal = new ZombieNormal(1500, 700);
    }

    /**
     * 釋放此場景資源
     */

    @Override
    public void sceneEnd() {

        buildingArea = null;

        buildingOption = null;

        currentButton = null;

        currentBuildNode = null;

        currentObj = null;

        controlHumans = null;

        background = null;

        city = null;

        zombieKingdom = null;

        fogOfWar = null;

        //TODO: Del
//        zombieNormal = null;
    }

    @Override
    public void paint(Graphics g) {

        // 背景
        background.paint(g);

        // 建築物基座
        buildingArea.paint(g);


        // 如果現在可以使用框選
        if (canUseBoxSelection) {

            // 畫出框選
            boxSelection.paint(g);
        }


        //畫出遊戲內所有的資源堆
        resourceSystem.paint(g);


        //城市
        city.paint(g);

        zombieKingdom.paint(g);

//        zombieNormal.paint(g);

        //畫出戰爭迷霧
        fogOfWar.paint(g);

        // 用血量條來判斷操控哪個人物
        if (currentObj != null && currentObj.getVisible()) {
            g.drawImage(chooseUnit, currentObj.painter().left(), currentObj.painter().top() - 20, chooseUnit.getWidth(null), 20, null);
        }


        // 如果現在框選的遊戲物件列表有東西 而且 也沒有單一操控某個物件時
        if (!controlHumans.isEmpty()) {
            for (Human human : controlHumans) {
                // 如果該物件現在能看的到 才畫他
                if (human.getVisible()) {
                    g.drawImage(chooseUnit, human.painter().left(), human.painter().top() - 20, chooseUnit.getWidth(null), 20, null);
                }
            }
        }

        //建築物選單
        buildingOption.paint(g);

        // 狀態欄
        StatusBar.instance().paint(g);

        //畫當前按鈕
        if (currentButton != null) {
            currentButton.paint(g);
        }

        //提示框
        ToastController.instance().paint(g);

        for (Effect effect : effects) {
            effect.paint(g);
        }
    }


    @Override
    public void update() {

        // 更新StatusBar 各項數據
        StatusBar.instance().setTimeString(startTime);
        StatusBar.instance().updateResource(city.getResource().getTotalWood(), city.getResource().getTotalSteel(), city.getResource().getTotalGas(), city.getTotalCitizen(), city.getTotalSoldier());


        // 處理鏡頭移動
        if ((currentMouseX >= SCREEN_WIDTH || currentMouseX <= 8) || (currentMouseY <= 0 || currentMouseY >= SCREEN_HEIGHT - 8)) {


            // 得到向量 螢幕中心(XY) - 滑鼠的位置(XY) [我沒用絕對值 讓他帶正負值進去]
            Vector vector = new Vector(SCREEN_WIDTH / 2 - currentMouseX, SCREEN_HEIGHT / 2 - currentMouseY);

            // 向量換算成 單位向量
            vector = vector.normalize();

            // 設定鏡頭移動的單位量(放在Global是因為 他跟每個人有關)
            Global.setCameraMoveVX((int) (vector.vx() * CAMERA_SPEED));
            Global.setCameraMoveVY((int) (vector.vy() * CAMERA_SPEED));
        }


//         如果這一帧的鏡頭移動量 != 0 [必須移動]
        if (CAMERA_MOVE_VX != 0 || CAMERA_MOVE_VY != 0) {

            //停機坪
            tarmacArr.cameraMove();

            // city裡面所有東西移動
            city.cameraMove();

            // 所有資源堆移動
            resourceSystem.cameraMove();

            // 建築物基座移動
            buildingArea.buildingAreaCameraMove();

            // 迷霧移動
            fogOfWar.cameraMove();

            zombieKingdom.cameraMove();

            for (Effect effect : effects) {
                effect.resetObjectXY();
            }

            // Reset 鏡頭移動量
            CAMERA_MOVE_VX = 0;
            CAMERA_MOVE_VY = 0;
        }


        //建築物相關測試
        buildingOption.update();
        buildingArea.update();

        //City更新
        city.update();
        zombieKingdom.update();
        //TODO: Del
//        zombieNormal.update();

        //判斷現在有無選取按鈕
        if (currentButton != null) {

            //取得現在選取的按鈕 及 建築類型
            BuildingType type = BuildingType.getBuildingTypeByInt(currentButton.getId());

            //點選按鈕時 判斷可否蓋建築物
            if (currentButton.getCountPressed() >= 0 && currentButton.isPressed() && !currentButton.isDragging() && !currentButton.isReleased()) {
                currentButton.decCountPressed();
                if (city.getBuildingNum() >= city.MAX_CAN_BUILD) {
                    canBuild = false;
                    ToastController.instance().print("建造-建築物已蓋滿 最大建築物量為" + city.MAX_CAN_BUILD);
                } else if (City.getTechLevel() < type.instance().getTechLevelNeedBuild()) {
                    canBuild = false;
                    ToastController.instance().print("建造-科技等級不足 科技等級需要" + type.instance().getTechLevelNeedBuild() + "級");
                } else if (!type.instance().isEnoughBuild(city.getResource())) {
                    canBuild = false;
                    ToastController.instance().print("建造-物資不足 " + type.instance().showBuildCost());
                } else {
                    canBuild = true;
                }
                //當建築物不能建造時不可拖曳
                currentButton.setCanDragging(canBuild);
            }

            //建造階段
            if (canBuild) {
                //判斷是否進入可建造區
                for (int i = 0; i < buildingArea.lengthY(); i++) {
                    for (int j = 0; j < buildingArea.lengthX(i); j++) {
                        //將重疊區域回傳給buildingOption
                        Rect greenRect = currentButton.overlapObject(buildingArea.get(i, j));
                        currentButton.setGreenRect(greenRect);
                        if (currentButton.getGreenRect() != null) {
                            //若有蓋好的建築物，與所有不會飛的物件碰撞 redRects給currentButton 一個
                            ArrayList<Rect> redRects = new ArrayList<>();
                            for (BuildingType value : values()) {
                                for (int k = 0; k < value.list().size(); k++) {
                                    Rect tmpRect = value.list().get(k).getBuilding().overlapRect(currentButton.getGreenRect());
                                    if (tmpRect != null) {
                                        redRects.add(tmpRect);
                                    }
                                }
                            }
                            for (Zombie zombie : zombieKingdom.getZombieTroop().getLandTroop()) {
                                Rect tmpRect =zombie.overlapRect(currentButton.getGreenRect());
                                if (tmpRect != null) {
                                    redRects.add(tmpRect);
                                }
                            }
                            for (Citizen citizen : city.getCitizens().getAllCitizens()) {
                                Rect tmpRect =citizen.overlapRect(currentButton.getGreenRect());
                                if (tmpRect != null) {
                                    redRects.add(tmpRect);
                                }
                            }
                            for (ArmySoldier armySoldier : city.getMilitary().getArmy()) {
                                Rect tmpRect =armySoldier.overlapRect(currentButton.getGreenRect());
                                if (tmpRect != null) {
                                    redRects.add(tmpRect);
                                }
                            }

                            if (!redRects.isEmpty()) {
                                currentButton.setRedRects(redRects.toArray(new Rect[redRects.size()]));
                            }else{
                                currentButton.setRedRects(null);
                            }

                                        //滑鼠放開時，判斷滑鼠放開的上一偵是否在建造區中
                                        if (currentButton.isReleased && preDragging && buildingArea.get(i, j).isOnBuildGrid() && currentButton.getCanBuild()) {//


                                            //建造房子
                                            city.build(type, currentMouseX - BUILDING_WIDTH / 2, currentMouseY - BUILDING_HEIGHT / 2);
                                            AudioResourceController.getInstance().play(new Path().sound().building());
                                            ToastController.instance().print("建造-" + type.instance().getName() + "成功");

                                        }
                                        //判斷是否蓋在建築區上
                                        buildingArea.get(i, j).setOnBuildGrid(buildingArea.get(i, j).isCover(currentButton));
                                    }

                                }
                            }

            }
            //滑鼠放開瞬間 判斷上一偵是否是拖曳且不再區域內
            if (currentButton.isReleased && (isPreAllNonBuildGrid || !currentButton.getCanBuild()) && preDragging) {//
                ToastController.instance().print("建造-此處不能蓋房子");
            }


            if (currentButton.getGreenRect() != null && currentButton.getRedRects() != null) {
                for (int k = 0; k < currentButton.getRedRects().length; k++) {
                    if (currentButton.getGreenRect().overlap(currentButton.getRedRects()[k])) {
                        preCanBuild = false;
                        break;
                    } else {
                        preCanBuild = true;
                    }
                }
            }
            preDragging = currentButton.isDragging();
            isPreAllNonBuildGrid = buildingArea.isAllNonOnBuildGrid();
        }


        //升級
        if (currentBuildNode != null) {
            //選取要升級的種類
            BuildingType type = BuildingType.getBuildingTypeByInt(currentBuildNode.getBuilding().getId());
            if ((currentBuildNode.getBuilding().getAllUpdateIconsCan())) {
                if (City.getTechLevel() < type.instance().getTechLevelNeedUpgrade()) {
                    ToastController.instance().print("升級-科技等級不足");

                } else if (!type.instance().isEnoughUpgrade(city.getResource())) {
                    ToastController.instance().print("升級-物資不足");

                } else if (city.canUpgradeBuilding(type)) {

                    switch (type) {
                        case LAB: {
                            if (city.isUpgradingTech(currentBuildNode)) {
                                ToastController.instance().print("升級-科技已在升級中，請等待此次升級結束");
                            } else {
                                city.upgradeTechLevel();
                                ToastController.instance().print("升級-科技開始升級");
                            }
                            break;
                        }
                        case ARSENAL: {
                            //士兵已在升級中，請等待此次升級結束
                            if (currentBuildNode.getCan(0)) {
                                if (city.isUpgradingSoldier()) {
                                    ToastController.instance().print("士兵已在升級中，請等待此次升級結束");
                                } else {
                                    city.upgradeSoldier();
                                    ToastController.instance().print("士兵升級中");
                                }
                            }
                            if (currentBuildNode.getCan(1)) {
                                if (city.isUpgradingPlane()) {
                                    ToastController.instance().print("飛機已在升級中");
                                } else {
                                    city.upgradePlane();
                                    ToastController.instance().print("飛機升級中");
                                }

                            }
                            break;
                        }
                        default: {
                            if (currentBuildNode.getBuilding().getIcons().get(0).getPressed()) {
                                city.upgrade(currentBuildNode);
                                ToastController.instance().print("安排升級中");
                            }
                        }
                    }
                }
                //限制按鈕按一次 就變成false;
                currentBuildNode.getBuilding().shutAllUpdateIconCan();
            }
        }


        // 框選Box狀態on
        if (canUseBoxSelection) {

            // 更新box的狀態
            boxSelection.update();

            // 把框到的市民 加入到 tmpControlHumans
            List<Human> tmpControlHumans = city.getBoxSelectionObjs(boxSelection.getBox());

            // 如果新的框選有人(一個也可以) => 把框選的List換過去
            if (!tmpControlHumans.isEmpty()) {
                controlHumans = new ArrayList<>(tmpControlHumans);
            }
        }

        // 如果 現在有操控單位(單選) && 有設定要前往的目標
        if (currentObj != null && hasSetTarget) {

            if (currentObj instanceof Human) {

                currentObj.setTarget(targetX, targetY);

                if (currentObj instanceof Citizen) {
                    AudioResourceController.getInstance().play(new Path().sound().readyToWork());
                } else if (currentObj instanceof ArmySoldier) {
                    AudioResourceController.getInstance().play(new Path().sound().soldierWhat4());
                } else {
                    AudioResourceController.getInstance().play(new Path().sound().airForceYes());
                }
            }

            // reset boolean
            hasSetTarget = false;
        }

        // 如果存有當前框選的所有物件的陣列 有東西
        if (!controlHumans.isEmpty() && hasSetTarget) {

            // 計算有幾個人到Target了
            int count = 0;


            // 是否在前往採集的路上
            boolean isGoingToCollect = false;

            /*
            這邊是為了解決一個狀況:
            因為我進去採集資源隱形 並不是 真的不見了, 只是沒有印出來, 還是會有碰撞
            所以如果採資源也要停下來要後找地方散開
            散開之後沒碰到資源堆,那就會出現沒有和資源物件 collision 的狀況 => 沒辦法觸發採集流程
            所以我先判斷一下目的地是不是資源堆這樣.
            */
            for (int i = 0; i < resourceSystem.size(); i++) {

                // 如果目的地確定是在 資源堆裡面 => 我確定我要派他去採集
                if (targetX > resourceSystem.get(i).painter().left() && targetX < resourceSystem.get(i).painter().right() && targetY > resourceSystem.get(i).painter().top() && targetY < resourceSystem.get(i).painter().bottom()) {
                    isGoingToCollect = true;
                    break;
                }
            }

            /*
            我從目前框選的人類中 共同set一個Target
            */
            for (Human human : controlHumans) {

                /*
                 1.如果count != 0 表示已經有人在那個位置了 要散開
                 2.但是如果他要去的目標是個資源堆 && 他是一個村民 那不能散開
                 */
                if (count != 0 && !(isGoingToCollect && human instanceof Citizen)) {
                    if (Global.random(0, 2) == 1) {
                        targetX += 74;
                    } else {
                        targetY += 74;
                    }
                }

                // 這個人前往目的地
                human.setTarget(targetX, targetY);

                if(human instanceof Citizen) {
                    AudioResourceController.getInstance().play(new Path().sound().readyToWork());
                } else if(human instanceof ArmySoldier){
                    AudioResourceController.getInstance().play(new Path().sound().soldierWhat4());
                } else {
                    AudioResourceController.getInstance().play(new Path().sound().airForceYes());
                }

                // 一旦有人到目的地 就++
                count++;
            }

            // reset boolean
            hasSetTarget = false;
        }

        // 走路狀態
        ArrayList<Human> humans = new ArrayList<>();
        humans.addAll(city.getCitizens().getAllCitizens());
        humans.addAll(city.getMilitary().getArmy());

        for(Human human : humans){
            boolean get = false;
            for (BuildingType value : values()) {
                for (int j = 0; j < value.list().size() && get==false; j++) {
                    Building building = value.list().get(j).getBuilding();

                    if(human.getBlockedDir()==null && human.isCollision(building)){
                        get = true;
                        human.setBlockingObject(building);
                        if(human instanceof Citizen && BuildingType.BASE.list().get(0).getBuilding() instanceof Base && human.isCollision(BuildingType.BASE.list().get(0).getBuilding())){
                            Citizen citizen = (Citizen) human;
                            if (building.isCovering(citizen.targetX(), citizen.targetY()) && citizen.getResourceNum() != 0) {

                                // 呼叫city的方法去實際放資源
                                city.gainResource(citizen.getResourceNum(), citizen.getResourceType());

                                // reset該位村民的 身上資源狀態
                                citizen.setResourceNum();
                                citizen.setResourceType();

                                // 返回採集點
                                citizen.setTarget(citizen.getResourceTargetX(), citizen.getResourceTargetY());
                            }
                        }
                        else if (building.isCovering(human.targetX(), human.targetY())) {
                            human.stop();
                        }

                        switch (human.getWalkingDir()) {
                            case RIGHT: {
                                if (human.touchLeftOf(building)) {
                                    human.translateX(-1 * (human.painter().right() - building.painter().left()));
                                    human.setBlockedDir(Direction.RIGHT);
                                }
                                break;
                            }
                            case LEFT: {
                                if (human.touchRightOf(building)) {
                                    human.translateX(building.painter().right() - human.painter().left());
                                    human.setBlockedDir(Direction.LEFT);
                                }
                                break;
                            }
                            case UP: {

                                if (human.touchBottomOf(building)) {
                                    human.translateY(building.painter().bottom() - human.painter().top());
                                    human.setBlockedDir(Direction.UP);
                                }
                                break;
                            }
                            case DOWN: {
                                if (human.touchTopOf(building)) {
                                    human.translateY(-1 * (human.painter().bottom() - building.painter().top()));
                                    human.setBlockedDir(Direction.DOWN);
                                }
                                break;
                            }
                        }
                    }
                    else if (human.getBlockedDir() != null && !human.touches(human.getBlockingObject())) {
                        switch (human.getBlockedDir()) {
                            case LEFT: {
                                if (!human.touchRightOf(human.getBlockingObject())) {
                                    human.setNoBlockedDir();
                                }
                                break;
                            }
                            case RIGHT: {
                                if (!human.touchLeftOf(human.getBlockingObject())) {
                                    human.setNoBlockedDir();
                                }
                                break;
                            }
                            case UP: {
                                if (!human.touchBottomOf(human.getBlockingObject())) {
                                    human.setNoBlockedDir();
                                }
                                break;
                            }
                            case DOWN: {
                                if (!human.touchTopOf(human.getBlockingObject())) {
                                    human.setNoBlockedDir();
                                }
                                break;
                            }
                            default:{
                                System.out.println("Default");
                                break;
                            }
                        }
                        human.setBlockingObject(null);
                    } else if (human instanceof Citizen){
                        Citizen citizen = (Citizen) human;

                        // 遍尋一次 資源List
                        for (int i = 0; i < resourceSystem.size(); i++) {

                            // 先把資源堆記錄下來 不用每次都get一次
                            ResourceObj resourceObj = resourceSystem.get(i);

                            //如果和資源碰撞 && 我的目的地就是在資源裡面 && 村民看的到的話(沒寫這個條件 他會重複計算)
                            if (citizen.isCollision(resourceObj) && citizen.isTargetInObj(resourceObj) && citizen.getVisible()) {
                                get = true;

                                // 把資源的位置記起來
                                citizen.setResourceTargetX(resourceObj.painter().centerX());
                                citizen.setResourceTargetY(resourceObj.painter().centerY());

                                //開始採集

                                if (resourceObj.getResourceTypeStr().equals("WOOD")) { // 如果採集的是木頭

                                    // 資源堆被採集了(目前單次最大能採多少木頭數量)
                                    resourceObj.beCollected(Citizen.getMaxCarryWood());
                                } else if (resourceObj.getResourceTypeStr().equals("STEEL")) { // 如果採集的是鋼鐵

                                    // 資源堆被採集了(目前單次最大能採多少鋼鐵數量)
                                    resourceObj.beCollected(Citizen.getMaxCarrySteel());
                                }

                                // 如果資源採乾了
                                if (resourceObj.getTotalNum() <= 0) {

                                    // 移除他
                                    resourceSystem.remove(i);

                                    //因為是ArrayList 移除後 下一個會馬上接上來
                                    i--;
                                }

                                // 村民開始採集
                                citizen.collecting(building.painter().centerX(), building.painter().centerY(), resourceObj.getResourceTypeStr());
                            }
                        }
                    }
                }
                if(get){
                    break;
                }
            }
            // 下面的switch 是碰到邊界的狀況
            switch (human.getWalkingDir()) {

                // 如果右邊碰到了邊界
                case RIGHT: {
                    if (human.touchRight()) {

                        // 把人物移動回來 與邊界切齊
                        human.translateX(-1 * (human.painter().right() - SCREEN_WIDTH));

                        // 人物停止移動
                        human.stop();
                    }
                    break;
                }

                // 如果左邊碰到了邊界
                case LEFT: {
                    if (human.touchLeft()) {

                        // 把人物移動回來 與邊界切齊
                        human.translateX(-1 * human.painter().left());

                        // 人物停止移動
                        human.stop();
                    }
                    break;
                }

                // 如果上面碰到了邊界
                case UP: {
                    if (human.touchTop()) {

                        // 把人物移動回來 與邊界切齊
                        human.translateY(-1 * human.painter().top());

                        // 人物停止移動
                        human.stop();
                    }
                    break;
                }

                // 如果下面碰到了邊界
                case DOWN: {
                    if (human.touchBottom()) {
                        // 把人物移動回來 與邊界切齊
                        human.translateY(-1 * (human.painter().bottom() - SCREEN_HEIGHT));

                        // 人物停止移動
                        human.stop();
                    }
                    break;
                }
            }
        }

/*
        for (BuildingType value : values()) {
            for (int j = 0; j < value.list().size(); j++) {
                Building building = value.list().get(j).getBuilding();

                for (Human human : humans){

                    //如果人物走到與建築重疊了，將其拉回剛好接觸但不重疊的位置並且讓人物知道這個方向被擋住了，換個方向
                    if (human.isCollision(building)) {
                        // 如果村民 是 採集完成的狀態
                        if(human instanceof Citizen && BuildingType.BASE.list().get(0).getBuilding() instanceof Base && human.isCollision(BuildingType.BASE.list().get(0).getBuilding())){
                            Citizen citizen = (Citizen) human;
                            if (citizen.getResourceNum() != 0) {

                                // 呼叫city的方法去實際放資源
                                city.gainResource(citizen.getResourceNum(), citizen.getResourceType());

                                // reset該位村民的 身上資源狀態
                                citizen.setResourceNum();
                                citizen.setResourceType();

                                // 返回採集點
                                citizen.setTarget(citizen.getResourceTargetX(), citizen.getResourceTargetY());
                            }
                        }
                        else if (building.isCovering(human.targetX(), human.targetY())) {
                            human.stop();
                        }

                        switch (human.getWalkingDir()) {
                            case RIGHT: {
                                if (human.touchLeftOf(building)) {
                                    human.translateX(-1 * (human.painter().right() - building.painter().left()));
                                    human.setBlockedDir(Direction.RIGHT);
                                }
                                break;
                            }
                            case LEFT: {
                                if (human.touchRightOf(building)) {
                                    human.translateX(building.painter().right() - human.painter().left());
                                    human.setBlockedDir(Direction.LEFT);
                                }
                                break;
                            }
                            case UP: {

                                if (human.touchBottomOf(building)) {
                                    human.translateY(building.painter().bottom() - human.painter().top());
                                    human.setBlockedDir(Direction.UP);
                                }
                                break;
                            }
                            case DOWN: {
                                if (human.touchTopOf(building)) {
                                    human.translateY(-1 * (human.painter().bottom() - building.painter().top()));
                                    human.setBlockedDir(Direction.DOWN);
                                }
                                break;
                            }
                        }
                    } else if (human.getBlockedDir() != null) {
                        switch (human.getBlockedDir()) {
                            case LEFT: {
                                if (!human.touchRightOf(building)) {
                                    human.setNoBlockedDir();
                                }
                                break;
                            }
                            case RIGHT: {
                                if (!human.touchLeftOf(building)) {
                                    human.setNoBlockedDir();
                                }
                                break;
                            }
                            case UP: {
                                if (!human.touchBottomOf(building)) {
                                    human.setNoBlockedDir();
                                }
                                break;
                            }
                            case DOWN: {
                                if (!human.touchTopOf(building)) {
                                    human.setNoBlockedDir();
                                }
                                break;
                            }
                        }
                    } else if (human instanceof Citizen){
                        Citizen citizen = (Citizen) human;

                        // 遍尋一次 資源List
                        for (int i = 0; i < resourceSystem.size(); i++) {

                            // 先把資源堆記錄下來 不用每次都get一次
                            ResourceObj resourceObj = resourceSystem.get(i);

                            //如果和資源碰撞 && 我的目的地就是在資源裡面 && 村民看的到的話(沒寫這個條件 他會重複計算)
                            if (citizen.isCollision(resourceObj) && citizen.isTargetInObj(resourceObj) && citizen.getVisible()) {

                                // 把資源的位置記起來
                                citizen.setResourceTargetX(resourceObj.painter().centerX());
                                citizen.setResourceTargetY(resourceObj.painter().centerY());

                                //開始採集

                                if (resourceObj.getResourceTypeStr().equals("WOOD")) { // 如果採集的是木頭

                                    // 資源堆被採集了(目前單次最大能採多少木頭數量)
                                    resourceObj.beCollected(Citizen.getMaxCarryWood());
                                } else if (resourceObj.getResourceTypeStr().equals("STEEL")) { // 如果採集的是鋼鐵

                                    // 資源堆被採集了(目前單次最大能採多少鋼鐵數量)
                                    resourceObj.beCollected(Citizen.getMaxCarrySteel());
                                }

                                // 如果資源採乾了
                                if (resourceObj.getTotalNum() <= 0) {

                                    // 移除他
                                    resourceSystem.remove(i);

                                    //因為是ArrayList 移除後 下一個會馬上接上來
                                    i--;
                                }

                                // 村民開始採集
                                citizen.collecting(building.painter().centerX(), building.painter().centerY(), resourceObj.getResourceTypeStr());
                            }
                        }
                    }

                    // 下面的switch 是碰到邊界的狀況
                    switch (human.getWalkingDir()) {

                        // 如果右邊碰到了邊界
                        case RIGHT: {
                            if (human.touchRight()) {

                                // 把人物移動回來 與邊界切齊
                                human.translateX(-1 * (human.painter().right() - SCREEN_WIDTH));

                                // 人物停止移動
                                human.stop();
                            }
                            break;
                        }

                        // 如果左邊碰到了邊界
                        case LEFT: {
                            if (human.touchLeft()) {

                                // 把人物移動回來 與邊界切齊
                                human.translateX(-1 * human.painter().left());

                                // 人物停止移動
                                human.stop();
                            }
                            break;
                        }

                        // 如果上面碰到了邊界
                        case UP: {
                            if (human.touchTop()) {

                                // 把人物移動回來 與邊界切齊
                                human.translateY(-1 * human.painter().top());

                                // 人物停止移動
                                human.stop();
                            }
                            break;
                        }

                        // 如果下面碰到了邊界
                        case DOWN: {
                            if (human.touchBottom()) {
                                // 把人物移動回來 與邊界切齊
                                human.translateY(-1 * (human.painter().bottom() - SCREEN_HEIGHT));

                                // 人物停止移動
                                human.stop();
                            }
                            break;
                        }
                    }
                }
>>>>>>> 51107359ef59bd2bb135757459288dad8aaebca5
            }
        }


 */

//        Building allBuildings = BuildingType.BASE.instance();

        //TODO: Del
//        for (ArmySoldier armySoldier : city.getMilitary().getArmy()){
//            if (armySoldier.getAttackTarget() == null) {
//                armySoldier.detect(zombieNormal);
//            }
//
//            if (armySoldier.getAttackTarget() != null) {
//                if (armySoldier.isCollision(armySoldier.getAttackTarget())) {
//                    Enemy enemy = (Enemy) armySoldier.getAttackTarget();
//                    enemy.stop();
//                    armySoldier.stop();
//
//                    if (enemy.getFightEffect() == null) {
//                        enemy.setFightEffect(new FightEffect(enemy.painter().left(), enemy.painter().top()));
//                        enemy.getAttacked(armySoldier.getValue());
//                        System.out.println("RRRRR");
//                        System.out.println(enemy.getHp());
//                    } else {
//                        if (enemy.getFightEffect().isDue()) {
//                            enemy.setFightEffect(null);
//                            if (!enemy.isAlive()) {
//                                armySoldier.setAttackTargetToNull();
//                            }
//                        }
//                    }
//                }
//            }
//        }


        //士兵打殭屍
        for (ArmySoldier armySoldier : city.getMilitary().getArmy()) {
            if(armySoldier.getAttackTarget()!=null && !((Creature) armySoldier.getAttackTarget()).isAlive()){
                armySoldier.setAttackTargetToNull();
            }

            if (armySoldier.getAttackTarget() == null) {
                for (Zombie zombie : zombieKingdom.getZombieTroop().getLandTroop()) {

                    armySoldier.detect(zombie);
                    if (armySoldier.getAttackTarget() != null) {
                        break;
                    }
                }
            }
            if (armySoldier.getAttackTarget() != null) {
                if (armySoldier.isCollision(armySoldier.getAttackTarget())) {
                    Enemy enemy = (Enemy) armySoldier.getAttackTarget();
                    enemy.stop();
                    armySoldier.stop();

                    if (enemy.getFightEffect() == null) {
                        enemy.setFightEffect(new FightEffect(enemy.painter().left(), enemy.painter().top()));
                        enemy.getAttacked(armySoldier.getValue());
                    } else {
                        if (enemy.getFightEffect().isDue()) {
                            enemy.setFightEffect(null);
                            if (!enemy.isAlive()) {
                                armySoldier.setAttackTargetToNull();
                            }
                        }
                    }
                }
                else{
                    armySoldier.setMoveStatus(Animator.State.WALK);
                }
            }
//            System.out.println("first");
//            break;
        }
        //飛行士兵打殭屍
        for (AirForceSoldier airForceSoldier : city.getMilitary().getAirForce()) {
            if (airForceSoldier.getAttackTarget() == null) {
                ArrayList<Zombie> allZombie = new ArrayList<>();
                allZombie.addAll(zombieKingdom.getZombieTroop().getAirTroop());
                allZombie.addAll(zombieKingdom.getZombieTroop().getLandTroop());
//                for (Zombie zombie : zombieKingdom.getZombieTroop().getAirTroop()) {
//                    airForceSoldier.detect(zombie);
//                    if (airForceSoldier.getAttackTarget() != null) {
//                        break;
//                    }
//                }
//
//                for (Zombie zombie : zombieKingdom.getZombieTroop().getLandTroop()){
//                    airForceSoldier.detect(zombie);
//                    if (airForceSoldier.getAttackTarget() != null) {
//                        break;
//                    }
//                }
                for (Zombie zombie : allZombie){
                    airForceSoldier.detect(zombie);
                    if (airForceSoldier.getAttackTarget() != null) {
                        break;
                    }
                }
            }
            if (airForceSoldier.getAttackTarget() != null) {
                if (airForceSoldier.isCollision(airForceSoldier.getAttackTarget())) {
                    Enemy enemy = (Enemy) airForceSoldier.getAttackTarget();
                    enemy.stop();
                    airForceSoldier.stop();

                    if (enemy.getFightEffect() == null) {
                        enemy.setFightEffect(new FightEffect(enemy.painter().centerX(), enemy.painter().centerY()));
                        enemy.getAttacked(airForceSoldier.getValue());
                    } else {
                        if (enemy.getFightEffect().isDue()) {
                            enemy.setFightEffect(null);
                            if (!enemy.isAlive()) {
                                airForceSoldier.setAttackTargetToNull();
                            }
                        }
                    }
                }
                else{
                    airForceSoldier.setMoveStatus(Animator.State.WALK);
                }
            }
        }



        //殭屍偵測所有遊戲物件
        for (Zombie zombie : zombieKingdom.getZombieTroop().getLandTroop()) {

//            System.out.println("殭屍ID:"+zombie);

            if (!zombie.isAlive()) {
                continue;
            }

            if (zombie.getAttackTarget() == null) {
                for (Citizen citizen : city.getCitizens().getAllCitizens()) {
                    if (zombie.getAttackTarget() == null) {
                        zombie.detect(citizen);

                    } else {
                        break;
                    }
                }

                for (ArmySoldier armySoldier : city.getMilitary().getArmy()) {
                    if (zombie.getAttackTarget() == null) {
                        zombie.detect(armySoldier);
//                        System.out.println("殭屍鎖定陸軍:"+zombie);

                    } else {
                        break;
                    }
                }

                for (BuildingType value : values()) {
                    if (zombie.getAttackTarget() == null) {
                        for (int i = 0; i < value.list().size(); i++) {
                            if (zombie.getAttackTarget() == null) {

                                Building building = value.list().get(i).getBuilding();
                                zombie.detect(building);
                            } else {
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                }
            }


            if (zombie.getAttackTarget() != null) {
                if (zombie.isCollision(zombie.getAttackTarget())) {
                    zombie.stop();
                    if (zombie.getAttackTarget() instanceof Creature) {
                        ((Creature) zombie.getAttackTarget()).stop();
                    }

                    GameObject gameObject = zombie.getAttackTarget();
                    if (gameObject.getFightEffect() == null) {
                        gameObject.setFightEffect(new FightEffect(gameObject.painter().centerX(), gameObject.painter().centerY()));
                    } else {
                        if (gameObject.getFightEffect().isDue()) {
                            gameObject.setFightEffect(null);
                        }
                    }

                    if (zombie.getAttackTarget() instanceof Building) {

                        Building building = (Building) zombie.getAttackTarget();
                        building.getDamage(zombie.getValue());
                        if (building.getCurrentHp() <= 0) {
                            zombie.setAttackTargetToNull();
                        }


                    } else if (zombie.getAttackTarget() instanceof Human) {
                        Human human = (Human) zombie.getAttackTarget();
                        //human.stop();
                        human.getAttacked(zombie.getValue());
                        if (!human.isAlive()) {
                            zombie.setAttackTargetToNull();
                        }
                    }
                }
                else{
                    zombie.setMoveStatus(Animator.State.WALK);
                }
            }
            else{
                zombie.setMoveStatus(Animator.State.WALK);
            }
        }




        /*
        if (zombie.isCollision(base)) {
            switch (zombie.getWalkingDir()) {
                case LEFT: {
                    if (zombie.touchRightOf(base)) {
                        zombie.translateX(base.painter().right() - zombie.painter().left());
                    }
                    break;
                }
                case RIGHT: {
                    if (zombie.touchLeftOf(base)) {
                        zombie.translateX(zombie.painter().right() - base.painter().left());
                    }
                    break;
                }
                case UP: {
                    if (zombie.touchBottomOf(base)) {
                        zombie.translateY(base.painter().bottom() - zombie.painter().top());
                    }
                    break;
                }
                case DOWN: {
                    if (zombie.touchTopOf(base)) {
                        zombie.translateY(zombie.painter().bottom() - base.painter().top());
                    }
                    break;
                }
            }
            zombie.stop();
        }
        }


         */

        //TODO: del
//        zombieNormal.update();
//        if (zombieNormal.isCollision(base)) {
//            switch (zombieNormal.getWalkingDir()) {
//                case LEFT: {
//                    if (zombieNormal.touchRightOf(base)) {
//                        zombieNormal.translateX(base.painter().right() - zombieNormal.painter().left());
//                    }
//                    break;
//                }
//                case RIGHT: {
//                    if (zombieNormal.touchLeftOf(base)) {
//                        zombieNormal.translateX(zombieNormal.painter().right() - base.painter().left());
//                    }
//                    break;
//                }
//                case UP: {
//                    if (zombieNormal.touchBottomOf(base)) {
//                        zombieNormal.translateY(base.painter().bottom() - zombieNormal.painter().top());
//                    }
//                    break;
//                }
//                case DOWN: {
//                    if (zombieNormal.touchTopOf(base)) {
//                        zombieNormal.translateY(zombieNormal.painter().bottom() - base.painter().top());
//                    }
//                    break;
//                }
//            }
//            zombieNormal.stop();
//        }
//            }
//        }


        // 更新迷霧狀況
        // 先看看有沒有村民碰到了 迷霧
        for (Human human : city.getCitizens().getAllCitizens()) {
            fogOfWar.update(human);
        }

        for (Human human : city.getMilitary().getArmy()) {
            fogOfWar.update(human);
        }

        for (Human human : city.getMilitary().getAirForce()) {
            fogOfWar.update(human);
        }

        if (currentObj != null) {
            currentObj = (currentObj.isAlive()) ? currentObj : null;
        }

        for (int i = 0; i < controlHumans.size(); i++) {
            Human human = controlHumans.get(i);
            if (!human.isAlive()) {
                controlHumans.remove(i);
                i--;
            }
        }

        // 時間自然流動
        if (gameDelay.count()) {
            thisRoundTimePass = 1;
            city.doCityWorkAndTimePass(thisRoundTimePass);
        }
        //20分鐘後結束 或主堡死去 或巫妖王死去
        if (!city.isAlive() || StatusBar.instance().getTime() > 20 * 60) {//

            boolean isWin = city.isAlive();

            SceneController.getInstance().change(new EndScene(startTime, isWin));
        }
    }


    @Override
    public CommandSolver.MouseCommandListener mouseListener() {

        return (e, state, trigTime) -> {

            // 如果滑鼠沒有任何state 直接return
            if (state == null) {
                return;
            }
            // 選單控制
            buildingOption.mouseTrig(e, state, trigTime);

            // 紀錄當下的滑鼠位置
            currentMouseX = e.getX();
            currentMouseY = e.getY();

            //HintDialog.instance().mouseTrig(e, state, trigTime);

            city.mouseTrig(e, state, trigTime);
            //如果現在沒有框選
            if (!canUseBoxSelection) {


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


                    // 把座標丟給citizens 讓他 判斷有沒有村民 符合條件 (BUTTON1 : 左鍵)
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        //單選 按鈕
                        currentButton = buildingOption.getCurrentButton(e.getX(), e.getY());

                        currentBuildNode = city.getCuurentBuildingNode(e.getX(), e.getY());

                        // 單選 就不能 框選 (擇一)
                        controlHumans.clear();

                        // 現在控製的Obj 換成這個 (現在先檢查所有村民而已)
                        currentObj = city.getSingleObjectByXY(currentMouseX, currentMouseY);


                        if (currentObj != null) {

                            if (currentObj instanceof Citizen) {
                                if (Math.random() < 0.5) {

                                    AudioResourceController.getInstance().play(new Path().sound().what());
                                } else {
                                    AudioResourceController.getInstance().play(new Path().sound().what2());
                                }
                            } else if (currentObj instanceof ArmySoldier) {
                                AudioResourceController.getInstance().play(new Path().sound().soldierWhat3());
                            } else {
                                AudioResourceController.getInstance().play(new Path().sound().airForceWhat());
                            }
                        }
                    }


                    // 什麼時候開啟框選模式?? => 當你沒點到一個可控單位時
                    if (canCommand(e.getX(), e.getY())) {
                        canUseBoxSelection = true;
                    }

                    // 如我現在有能操控的單位 (單選[GameObj] 或 框選[Only Human])
                    // 按下右鍵 => 設定要前往的(x,y) [BUTTON3 => 右鍵]
                    if ((currentObj != null || !controlHumans.isEmpty()) && e.getButton() == MouseEvent.BUTTON3) {
                        targetX = e.getX();
                        targetY = e.getY();
                        hasSetTarget = true;
                    }

                    break;
                }

                case CLICKED: {


                    // 輔助取座標用(沒實際作用 完成可刪除)[BUTTON2 => 中鍵]
                    if (e.getButton() == MouseEvent.BUTTON2) {
                        System.out.println("X: " + e.getX());
                        System.out.println("Y: " + e.getY());
                    }

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

        // 按下ESC => 釋放所有操控單位
        if (commandCode == ESC) {
            hasSetTarget = false;
            currentObj = null;
            controlHumans.clear();
        }

        // 按下SPACE => 鏡頭中心回到主堡身上
        if (commandCode == SPACE) {

            city.resetObjectXY();

            tarmacArr.resetObjectXY();

            resourceSystem.resetObjectXY();

            buildingArea.buildingAreaResetPosition();

            fogOfWar.resetObjectXY();

            zombieKingdom.resetObjectXY();

            for (Effect effect : effects) {
                effect.resetObjectXY();
            }

            Global.resetSumOfCameraMove();
        }
    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {

    }

    @Override
    public void keyTyped(char c, long trigTime) {

        // 按下s的同時 所有操控單位停止移動
        if (c == 's' || c == 'S') {
            if (currentObj != null && currentObj instanceof Human) {
                ((Human) currentObj).stop();
            }

            if (controlHumans != null) {
                for (Human human : controlHumans) {
                    human.stop();
                }
            }
        }
    }

    //生物一 是否視生物二為攻擊目標
    public Creature attackTarget(Creature creature1, Creature creature2) {

        //已有鎖定目標
        if (creature1.getAttackTarget() == null) {
            creature1.detect(creature2);
            if (creature1.getAttackTarget() == null) {
                return creature1;
            } else {
                return creature1;
            }
        }
        return creature1;
    }

    public void attackEachOther(Creature creature1) {
        if (creature1.getAttackTarget() == null) {
            return;
        }
        if (creature1.isCollision(creature1.getAttackTarget())) {
            Creature enemy;
            if (creature1.getAttackTarget() instanceof Creature) {
                enemy = (Creature) creature1.getAttackTarget();
            } else {
                return;
            }

            enemy.stop();
            creature1.stop();

//            if (enemy.getFightEffect() == null) {
//                enemy.setFightEffect(new FightEffect(creature1.painter().centerX(), creature1.painter().centerY()));
            enemy.getAttacked(creature1.getValue());
            if (!enemy.isAlive()) {
                creature1.setAttackTargetToNull();
            }
//                if (creature1.getFightEffect().isDue()) {
//                    enemy.setFightEffect(null);
        }
    }
}

