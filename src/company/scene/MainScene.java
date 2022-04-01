package company.scene;

import company.Global;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

import static company.gameobj.BuildingController.*;

import company.gameobj.BuildingController;
import company.gameobj.Rect;
import company.gameobj.resourceObjs.Steel;
import company.gameobj.resourceObjs.Tree;
import company.gameobj.message.HintDialog;
import company.gameobj.message.ToastController;
import company.gametest9th.utils.*;
import company.gameobj.GameObject;
import company.gameobj.background.Background;
import company.gameobj.background.component.*;
import company.gameobj.creature.human.Citizen;
import company.gameobj.buildings.Base;
import company.gameobj.creature.human.Citizens;
import company.gameobj.creature.human.Human;

import oldMain.City;

import java.awt.*;

import static company.Global.*;
import static company.gameobj.BuildingController.BuildingType.values;


/**
 * 遊戲主場景
 */
public class MainScene extends Scene implements CommandSolver.KeyListener {

    private Base base;  // 主堡
    private Background background; // 背景
    private BuildingOption buildingOption; // 建築物選單

    private GameObject currentObj; // 當前操控的物件(單選)
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
    private List<GameObject> resources;

    @Override
    public void sceneBegin() {

        startTime = System.nanoTime();// 進入場景之後紀錄開始時間

        currentObj = null;// 當前操控的物件(單選)
        controlHumans = new ArrayList<>();// 設定: 只有人物可以多選

        //背景
        background = new Background(0, 0, SCREEN_X, SCREEN_Y);

        //建築物選單
        buildingOption = new BuildingOption();

        //可建築區
        buildingArea = new BuildingArea();

        //主堡
        //base先不刪下面有些東西與base連接
        base = new Base(20, 20);

        preCanBuild = true;
        // city本體
        city = new City();
        //city.build(BuildingType.BASE,SCREEN_X / 2 - Base.BASE_WIDTH, SCREEN_Y / 2 - Base.BASE_HEIGHT);

        // 測試: 預設有 ? 個 村民
        city.setCitizens(4);

        // 測試: 預設有 ? 個 士兵
        city.getMilitary().addArmy(1);
        city.getMilitary().addAirForce(1);

        //設定遊戲時間 120偵為遊戲一小時(oldMain的流動一小時)
        gameTimeSpeed = 120;
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
        resources = new ArrayList<>();
        resources.add(new Tree(350, 400));
        resources.add(new Steel(1200, 400));
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

        //城市
        city.paint(g);

        // 主堡
        base.paint(g);

        // 畫出每一個村民
//        city.getCitizens().paintAll(g);


        // 如果現在可以使用框選
        if (canUseBoxSelection) {

            // 畫出框選
            boxSelection.paint(g);
        }

        // 用血量條來判斷操控哪個人物
        if (currentObj != null && currentObj.getVisible()) {
            g.setColor(Color.RED);
            g.fillRect(currentObj.painter().left(), currentObj.painter().bottom() + 3, currentObj.painter().width(), 10);
            g.setColor(Color.black);
        }

        // 如果現在框選的遊戲物件列表有東西 而且 也沒有單一操控某個物件時
        if (!controlHumans.isEmpty()) {
            for (Human human : controlHumans) {

                // 如果該物件現在能看的到 才畫他
                if (human.getVisible()) {
                    g.setColor(Color.GREEN);
                    g.fillRect(human.painter().left(), human.painter().bottom() + 3, human.painter().width(), 10);
                    g.setColor(Color.black);
                }
            }
        }


        // 畫出可採集的資源
        for (GameObject resource : resources) {
            if (resource != null) {
                resource.paint(g);
            }
        }

        //畫出城市所有已建造建築物
        city.paint(g);

        // 狀態欄
        StatusBar.instance().paint(g);

        //畫當前按鈕
        if (buildingOption.getCurrentButton() != null) {
            buildingOption.getCurrentButton().paint(g);
        }

        //提示框
        //HintDialog.instance().paint(g);
        ToastController.instance().paint(g);
    }


    @Override
    public void update() {

        // 更新StatusBar 各項數據
        StatusBar.instance().setTimeString(startTime);
        StatusBar.instance().updateResource(city.getResource().getTotalWood(), city.getResource().getTotalSteel(), city.getResource().getTotalGas(), city.getTotalCitizen());

        // 優先處理鏡頭移動
        if ((currentMouseX >= SCREEN_X || currentMouseX <= 8) || (currentMouseY <= 0 || currentMouseY >= SCREEN_Y - 8)) {

            // 得到向量 滑鼠的位置(XY) - 螢幕中心(XY)
            Vector vector = new Vector((currentMouseX - SCREEN_X / 2) * -1, (currentMouseY - SCREEN_Y / 2) * -1);

            // 向量換算成 單位向量
            vector = vector.normalize();


            // 下面就是移動地圖上每一個物件

            base.cameraMove(vector);

            for (Human human : city.getCitizens().getAllCitizens()) {
                human.cameraMove(vector);
            }

            for (GameObject resource : resources) {
                resource.cameraMove(vector);
            }

            buildingArea.buildingAreaCameraMove(vector);
        }


        //建築物相關測試
        buildingOption.update();
        buildingArea.update();

        //City更新
        city.update();

        //判斷現在有無選取按鈕
        if (buildingOption.getCurrentButton() != null) {

            //取得現在選取的按鈕 及 建築類型
            BuildingButton currentButton = buildingOption.getCurrentButton();
            BuildingType type = BuildingType.getBuildingTypeByInt(currentButton.getId());
            //點選按鈕時 判斷可否蓋建築物
            if (currentButton.getCountPressed() >= 0 && currentButton.isPressed() && !currentButton.isDragging() && !currentButton.isReleased()) {
                currentButton.decCountPressed();
                if (city.getBuildingNum() >= city.MAX_CAN_BUILD) {
                    canBuild = false;
                    currentButton.setCanDragging(false);
                    ToastController.instance().print("建造-建築物已蓋滿 最大建築物量為" + city.MAX_CAN_BUILD);
                    //System.out.println("你的城市 經過多年風風雨雨 鐵與血的灌溉\n如今 從杳無人煙之地 成了 充斥著滿滿的高樓大廈 人車馬龍的繁華之地\n你的城市 已沒有地方可以建造新的建築了");
                } else if (City.getTechLevel() < type.instance().getTechLevelNeedBuild()) {
                    canBuild = false;
                    currentButton.setCanDragging(false);
                    ToastController.instance().print("建造-科技等級不足 科技等級需要" + type.instance().getTechLevelNeedBuild() + "級");
                } else if (!type.instance().isEnoughBuild(city.getResource())) {
                    canBuild = false;
                    currentButton.setCanDragging(false);
                    ToastController.instance().print("建造-物資不足 " + type.instance().showBuildCost());
                } else {
                    canBuild = true;
                    currentButton.setCanDragging(true);
                }
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
                            //與蓋好建築物比較回傳給currentButton
                            for (BuildingType value : values()) {
                                ArrayList<Rect> redRects = new ArrayList<>();
                                for (int k = 0; k < value.list().size(); k++) {
                                    Rect tmpRect = value.list().get(k).getBuilding().overlapRect(currentButton.getGreenRect());
                                    if (tmpRect != null) {
                                        redRects.add(tmpRect);
                                    }
                                }
                                if (redRects != null && redRects.size() >= 1) {
                                    currentButton.setRedRects(redRects.toArray(new Rect[redRects.size()]));
                                }
                            }
                        }


                        //滑鼠放開時，判斷滑鼠放開的上一偵是否在建造區中
                        if (currentButton.isReleased && buildingArea.get(i, j).isOnBuildGrid() && preCanBuild && !currentButton.isDragging()) {//


                            //建造房子
                            city.build(type, currentMouseX - BUILDING_WIDTH / 2, currentMouseY - BUILDING_HEIGHT / 2);
                            ToastController.instance().print("建造-" + type.instance().getName() + "成功");


                        }
                        //判斷是否蓋在建築區上
                        buildingArea.get(i, j).setOnBuildGrid(buildingArea.get(i, j).isCover(currentButton));
                    }

                }

            }
            //滑鼠放開瞬間 判斷上一偵是否是拖曳且不再區域內
            if (currentButton.isReleased && (isPreAllNonBuildGrid || !preCanBuild) && preDragging) {//|| canBuild
                ToastController.instance().print("建造-此處不能蓋房子");
            }


            if (currentButton.getGreenRect() != null && currentButton.getRedRects() != null) {
                for (int k = 0; k < currentButton.getRedRects().length; k++) {
                    if (!currentButton.getGreenRect().overlap(currentButton.getRedRects()[k])) {
                        preCanBuild = true;
                    } else {
                        preCanBuild = false;
                        break;
                    }
                }
            }
            preDragging = currentButton.isDragging();
            isPreAllNonBuildGrid = buildingArea.isAllNonOnBuildGrid();
        }

        //升級
        if (city.getCurrentBuildingNode() != null) {
            //選取要升級的種類
            BuildingController.BuildingNode selectBuilding = city.getCurrentBuildingNode();
            BuildingType type = BuildingType.getBuildingTypeByInt(selectBuilding.getBuilding().getId());
            //TODO:getCan(0)待修
            if ((selectBuilding.getBuilding().getIcons().size() == 2 && selectBuilding.getCan(0))) {


                if (City.getTechLevel() < type.instance().getTechLevelNeedUpgrade()) {
                    ToastController.instance().print("升級-科技等級不足");

                } else if (!type.instance().isEnoughUpgrade(city.getResource())) {
                    ToastController.instance().print("升級-物資不足");

                } else if (city.canUpgradeBuilding(type)) {

                    switch (type) {
                        case LAB: {
                            if (city.isUpgradingTech(selectBuilding)) {
                                ToastController.instance().print("升級-科技已在升級中，請等待此次升級結束");
                                //System.out.println("科技已在升級中，請等待此次升級結束");
                            } else {
                                city.upgradeTechLevel();
                                ToastController.instance().print("升級-科技開始升級");
                            }
                            break;
                        }
                        case ARSENAL: {
                            //士兵已在升級中，請等待此次升級結束
                            if (selectBuilding.getCan(0)) {
                                if (city.isUpgradingSoldier()) {
                                    ToastController.instance().print("士兵已在升級中，請等待此次升級結束");
                                } else {
                                    city.upgradeSoldier();
                                    ToastController.instance().print("士兵升級中");
                                }
                            }
                            if (selectBuilding.getCan(2)) {
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
                            if (selectBuilding.getBuilding().getIcons().get(0).getCan()) {
                                city.upgrade(selectBuilding);
                                ToastController.instance().print("安排升級中");
                            }
                        }
                    }
                }
                //限制按鈕按一次 就變成false;
                selectBuilding.getBuilding().getIcons().get(0).setCan(false);
                if (selectBuilding.getBuilding().getIcons().size() == 3) {
                    selectBuilding.getBuilding().getIcons().get(2).setCan(false);
                }
            }
        }


        // 框選Box狀態on
        if (canUseBoxSelection) {

            // 更新box的狀態
            boxSelection.update();

            // 把框到的市民 加入到 tmpControlHumans
            List<Human> tmpControlHumans = city.getCitizens().getBoxCitizens(boxSelection.getBox());

            // 如果新的框選有人(一個也可以) => 把框選的List換過去
            if (!tmpControlHumans.isEmpty()) {
                controlHumans = new ArrayList<>(tmpControlHumans);
            }
        }

        // 如果 現在有操控單位(單選) && 有設定要前往的目標
        if (currentObj != null && hasSetTarget) {

            /*
            為什麼currentObj要特別判斷 是否是Human?
            因為單選 可以是建築物 不一定會是個人
             */
            if (currentObj instanceof Human) {
                ((Human) currentObj).setTarget(targetX, targetY);
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
            因為我進去採集資源隱形 != 真的不見了, 只是沒有印出來
            所以如果採資源也要停下來後找地方散開
            那就會出現沒有和資源物件 collision 的狀況 => 沒辦法觸發採集流程
            所以我先判斷一下目的地是不是資源堆這樣.
            */
            for (GameObject resource : resources) {
                if (targetX > resource.painter().left() && targetX < resource.painter().right() && targetY > resource.painter().top() && targetY < resource.painter().bottom()) {
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

                // 一旦有人到目的地 就++
                count++;
            }

            // reset boolean
            hasSetTarget = false;
        }

        // 走路狀態
        for (Citizen citizen : city.getCitizens().getAllCitizens()) {

            /*
            閒吉 我這邊有多一些東西

            目前是:
            if() => 村民與base overlap (小疑問: 這個和 isCollision 有差嗎??)

            else if() => citizen.getBlockedDir() != null 村民有被擋住的方向

            (↓我新增的)
            else() => 其餘狀況 跑一次資源堆物件 [處理村民碰到採集資源的狀況]

            */

            //如果人物走到與建築重疊了，將其拉回剛好接觸但不重疊的位置並且讓人物知道這個方向被擋住了，換個方向
            if (citizen.painter().overlap(base.painter())) {

                // 如果村民 是 採集完成的狀態
                if (citizen.getResourceNum() != 0) {

                    // 呼叫city的方法去實際放資源
                    city.gainResource(citizen.getResourceNum(), citizen.getResourceType());

                    // reset該位村民的 身上資源狀態
                    citizen.setResourceNum();
                    citizen.setResourceType();

                    // 返回採集點
                    citizen.setTarget(citizen.getResourceTargetX(), citizen.getResourceTargetY());

                    continue;
                }

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
            } else if (citizen.getBlockedDir() != null) {
                switch (citizen.getBlockedDir()) {
                    case LEFT: {
                        if (!citizen.touchRightOf(base)) {
                            citizen.setNoBlockedDir();
                        }
                        break;
                    }
                    case RIGHT: {
                        if (!citizen.touchLeftOf(base)) {
                            citizen.setNoBlockedDir();
                        }
                        break;
                    }
                    case UP: {
                        if (!citizen.touchBottomOf(base)) {
                            citizen.setNoBlockedDir();
                        }
                        break;
                    }
                    case DOWN: {
                        if (!citizen.touchTopOf(base)) {
                            citizen.setNoBlockedDir();
                        }
                        break;
                    }
                }
            } else {
                // 遍尋一次 資源List
                for (int i = 0; i < resources.size(); i++) {

                    // 先把資源堆物件 取出來(不用每次都用get去跑一次拿他)
                    GameObject resource = resources.get(i);

                    //如果和資源碰撞 && 我的目的地就是在資源裡面 && 村民看的到的話(沒寫這個條件 他會重複計算)
                    if (citizen.isCollision(resource) && citizen.isTargetInObj(resource) && citizen.getVisible()) {

                        // 把資源的位置記起來
                        citizen.setResourceTargetX(resource.painter().centerX());
                        citizen.setResourceTargetY(resource.painter().centerY());

                        //開始採集
                        int resourceNum = 0;
                        Citizen.Resource resourceType = null;

                        // 幫村民判斷 採了什麼資源 && 一次能採的量
                        if (resource instanceof Tree) {

                            resourceNum = ((Tree) resource).eachTimeGet();
                            resourceType = Citizen.Resource.WOOD;

                            // 如果資源採乾了 移除他
                            if (resource != null && ((Tree) resource).getTotalNum() <= 0) {
                                resources.remove(i);
                            }
                        }

                        // 幫村民判斷 採了什麼資源 && 一次能採的量
                        if (resource instanceof Steel) {
                            resourceNum = ((Steel) resource).eachTimeGet();
                            resourceType = Citizen.Resource.STEEL;

                            // 如果資源採乾了 移除他
                            if (resource != null && ((Steel) resource).getTotalNum() <= 0) {

                                resources.remove(i);
                            }
                        }

                        // 村民開始採集
                        citizen.collecting(resource, base.painter().left() - 30, base.painter().top() + base.painter().height() / 2, resourceNum, resourceType);

                    }
                }
            }

            // 下面的switch 是碰到邊界的狀況
            switch (citizen.getWalkingDir()) {

                // 如果右邊碰到了邊界
                case RIGHT: {
                    if (citizen.touchRight()) {

                        // 把人物移動回來 與邊界切齊
                        citizen.translateX(-1 * (citizen.painter().right() - SCREEN_X));

                        // 人物停止移動
                        citizen.stop();
                    }
                    break;
                }

                // 如果左邊碰到了邊界
                case LEFT: {
                    if (citizen.touchLeft()) {

                        // 把人物移動回來 與邊界切齊
                        citizen.translateX(-1 * citizen.painter().left());

                        // 人物停止移動
                        citizen.stop();
                    }
                    break;
                }

                // 如果上面碰到了邊界
                case UP: {
                    if (citizen.touchTop()) {

                        // 把人物移動回來 與邊界切齊
                        citizen.translateY(-1 * citizen.painter().top());

                        // 人物停止移動
                        citizen.stop();
                    }
                    break;
                }

                // 如果下面碰到了邊界
                case DOWN: {
                    if (citizen.touchBottom()) {
                        // 把人物移動回來 與邊界切齊
                        citizen.translateY(-1 * (citizen.painter().bottom() - SCREEN_Y));

                        // 人物停止移動
                        citizen.stop();
                    }
                    break;
                }
            }
        }

        // 時間自然流動
        if (gameDelay.count()) {
            thisRoundTimePass = 1;
            city.doCityWorkAndTimePass(thisRoundTimePass);
        }

        if (!city.isAlive()) {
            StartScene startScene = new StartScene(); //還沒有結束畫面已此充當結束遊戲
            //SceneController.getInstance().change(startScene);
        }
    }


    @Override
    public CommandSolver.MouseCommandListener mouseListener() {

        return (e, state, trigTime) -> {

            // 如果滑鼠沒有任何state 直接return
            if (state == null) {
                return;
            }

            // 紀錄當下的滑鼠位置
            currentMouseX = e.getX();
            currentMouseY = e.getY();

            //HintDialog.instance().mouseTrig(e, state, trigTime);

            city.mouseTrig(e, state, trigTime);
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


                    // 把座標丟給citizens 讓他 判斷有沒有村民 符合條件 (BUTTON1 : 左鍵)
                    if (e.getButton() == MouseEvent.BUTTON1) {

                        // 單選 就不能 框選 (擇一)
                        controlHumans.clear();

                        // 現在控製的Obj 換成這個 (現在先檢查所有村民而已)
                        currentObj = city.getCitizens().getCitizen(e.getX(), e.getY());
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

        if (commandCode == SPACE) {
            base.resetObjectXY();
            for (Human human : city.getCitizens().getAllCitizens()) {
                human.resetObjectXY();
            }

            for (GameObject resource : resources) {
                resource.resetObjectXY();
            }

            buildingArea.buildingAreaResetPosition();
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
}
