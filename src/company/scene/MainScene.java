package company.scene;

import company.Global;
import company.controllers.SceneController;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

import static company.gameobj.BuildingController.*;

import company.gameobj.Rect;
import company.gameobj.Steel;
import company.gameobj.Tree;
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
import oldMain.OldMain;

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

    private Base base;  // 主堡
    private Background background; // 背景
    private BuildingOption buildingOption; // 建築物選單

    private GameObject currentObj; // 當前操控的物件
    private List<Human> controlHumans;

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

    //時間
    Delay delay;
    private int timeSpeed; //時間的速度
    int thisRoundTimePass; //要跳過的時間

    //與建築相關

    City city; //城市
    BuildingType type;
    private BuildingArea buildingArea;
    //private boolean preOnBuildArea[][];
    boolean[][] isOnBuildArea;
    // 提示詞
    private String message;

    // 時間
    private long startTime;
    private int nowTime;
    private String outputTimeStr;

    // 資源
    private List<GameObject> resources;
    private Delay collectionDelay;

    @Override
    public void sceneBegin() {

        startTime = System.nanoTime();// 進入場景之後紀錄開始時間

        city = new City();// city本體

        currentObj = null;// 當前操控的物件(單選)
        controlHumans = new ArrayList<Human>();

        //背景
        background = new Background(0, 0, SCREEN_X, SCREEN_Y);


        //建築物選單
        buildingOption = new BuildingOption();


        //測試:建築物
        buildingArea = new BuildingArea();

        isOnBuildArea = new boolean[buildingArea.lengthY()][buildingArea.lengthX()];

        base = new Base(SCREEN_X / 2 - Base.BASE_WIDTH, SCREEN_Y / 2 - Base.BASE_HEIGHT);


        // 測試: 預設有 ? 個 村民
        city.setCitizens(new Citizens(4));


        //時間速度
        timeSpeed = 120000; // 暫時訂一個超長的 => 不然很快就往生 麻煩
        delay = new Delay(timeSpeed);
        delay.loop();

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


        // 資源
        resources = new ArrayList<>();
        resources.add(new Tree(350, 400));
        resources.add(new Steel(1200, 400));

        // 採資源耗時1秒
        collectionDelay = new Delay(60);
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

        //提示框
        HintDialog.instance().paint(g);
        ToastController.instance().paint(g);

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
        city.getCitizens().paintAll(g);

        if (canUseBoxSelection) {
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
            resource.paint(g);
        }

        city.paint(g);
    }

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

        //判斷現在有無選取按鈕
        if (buildingOption.getCurrentButton() != null) {
            //判斷是否進入可建造區
            for (int i = 0; i < buildingArea.lengthY(); i++) {
                for (int j = 0; j < buildingArea.lengthX(); j++) {
                    //取得現在選取的按鈕 及 建築類型
                    BuildingButton currentButton = buildingOption.getCurrentButton();
                    type = BuildingType.getBuildingTypeByInt(currentButton.getId());

                    //將重疊區域回傳給buildingOption
                    Rect greenRect = currentButton.overlapObject(buildingArea.get(i, j));
                    buildingOption.setGreenRect(greenRect);

                    //完全在建造區中 且放開

//                    System.out.println("RELEASED:"+currentButton.isReleased);
                    if (isOnBuildArea[i][j] && currentButton.isReleased) {//
                        for (int k = 0; k < currentButton.getCountPressed(); k++) {
                            ToastController.instance().print(type.instance().getName() + "建造成功");
                            //建造房子
                            city.build(type, currentX - BUILDING_WIDTH / 2, currentY - BUILDING_HEIGHT / 2);

                        }
                    }
                    //判斷可否蓋在建築區上
                    isOnBuildArea[i][j] = buildingArea.get(i, j).isCover(currentButton);
                }
            }
        }


        //city.getBuildingsNum()
        //建造成功與否
        if (type != null) {
            type = BuildingType.getBuildingTypeByInt(buildingOption.getCurrentButton().getId());
            if (city.getBuildingNum() != city.MAX_CAN_BUILD && city.canBuildBuilding(type)) {
                //city.build(type,buildingOption.getCurrentButton().painter().left(),buildingOption.getCurrentButton().painter().top());
                //System.out.println(type.instance().getName() + "建造中");
            } else {
                if (city.getBuildingNum() == city.MAX_CAN_BUILD) {
                    ToastController.instance().print("a建築物已蓋滿");
                    //System.out.println("你的城市 經過多年風風雨雨 鐵與血的灌溉\n如今 從杳無人煙之地 成了 充斥著滿滿的高樓大廈 人車馬龍的繁華之地\n你的城市 已沒有地方可以建造新的建築了");
                }
                if (City.getTechLevel() < type.instance().getTechLevelNeedBuild()) {
                    ToastController.instance().print("b科技等級不足");
                    //System.out.println("科技等級不足");
                }
                if (!type.instance().isEnoughBuild(city.getResource())) {
                    ToastController.instance().print("c物資不足");
                    //System.out.println("物資不足");
                }
            }
        }

        // 時間自然流動
        if (delay.count()) {
            //city.showInfo();
            thisRoundTimePass = 1;
            city.doCityWorkAndTimePass(thisRoundTimePass);
        }


        // 框選Box狀態on
        if (canUseBoxSelection) {

            // 更新box的狀態
            boxSelection.update();

            // 把框到的市民 加入到 tmpControlHumans
            List<Human> tmpControlHumans = city.getCitizens().getBoxCitizens(boxSelection.getBox());

            if (!tmpControlHumans.isEmpty()) {
                controlHumans = new ArrayList<>(tmpControlHumans);
            }
        }

        // 如果 現在有操控單位 單選 && 有設定要前往的目標
        if (currentObj != null && hasSetTarget) {

            if (currentObj instanceof Human) {
                ((Human) currentObj).setTarget(mouseX, mouseY);
            }

            // reset boolean
            hasSetTarget = false;
        }

        // 如果存有當前框選的所有物件的陣列 有東西
        if (!controlHumans.isEmpty() && hasSetTarget) {
            int count = 0;

            boolean isGoingToCollect = false;


            /*
            這邊是為了解決一個狀況:
            因為我進去採集資源隱形 != 真的不見了, 只是沒有印出來
            所以如果採資源也要停下來後找地方散開
            那就會出現沒有和資源物件 collision 的狀況 => 沒辦法觸發採集流程
            所以我先判斷一下目的地是不是資源堆這樣.
            */
            for(GameObject resource: resources){
                if(mouseX > resource.painter().left() && mouseX < resource.painter().right() && mouseY > resource.painter().top() && mouseY < resource.painter().bottom()){
                    isGoingToCollect = true;
                    break;
                }
            }

            /*
            我從目前框選的人類中 共同set一個Target
            */
            for (Human human : controlHumans) {

                /*
                 1.如果count != 0 表示已經有人在那個位置了
                 2.如果他要去的目標是個資源堆 && 他是一個村民 那也不能散開
                 */
                if (count != 0 && !(isGoingToCollect && human instanceof Citizen)) {
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
        city.getCitizens().updateAll();

        // 走路狀態
        for (Citizen citizen : city.getCitizens().getAllCitizens()) {

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
                for (GameObject gameObject : resources) {

                    //如果和資源碰撞 && 我的目的地就是在資源裡面
                    if (citizen.isCollision(gameObject) && citizen.isTargetInObj(gameObject)) {

                        System.out.println("I am citizen num: " + citizen.myNum + " I am working!!");

                        // 把資源的位置記起來
                        citizen.setResourceTargetX(gameObject.painter().centerX());
                        citizen.setResourceTargetY(gameObject.painter().centerY());

                        // 如果村民現在看的到的話 => 開始採集
                        if (citizen.getVisible()) {
                            citizen.collecting(gameObject, base.painter().left() - 30, base.painter().top() + base.painter().height() / 2) ;
                        }
                    }
                }
            }

            // 下面的switch 是碰到邊界的狀況
            switch (citizen.getWalkingDir()) {
                case RIGHT: {
                    if (citizen.touchRight()) {
                        citizen.translateX(-1 * (citizen.painter().right() - SCREEN_X));
                        citizen.stop();
                    }
                    break;
                }
                case LEFT: {
                    if (citizen.touchLeft()) {
                        citizen.translateX(-1 * citizen.painter().left());
                        citizen.stop();
                    }
                    break;
                }

                case UP: {
                    if (citizen.touchTop()) {
                        citizen.translateY(-1 * citizen.painter().top());
                        citizen.stop();
                    }
                    break;
                }

                case DOWN: {
                    if (citizen.touchBottom()) {
                        citizen.translateY(-1 * (citizen.painter().bottom() - SCREEN_Y));
                        citizen.stop();
                    }
                    break;
                }
            }
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

                    // 如我現在有能操控的單位 (單選 或 框選)
                    // 按下右鍵 => 設定要前往的(x,y)
                    if ((currentObj != null || !controlHumans.isEmpty()) && e.getButton() == MouseEvent.BUTTON3) {
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
            controlHumans.clear();
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
