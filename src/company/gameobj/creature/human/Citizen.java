package company.gameobj.creature.human;

import company.Global;

import company.gameobj.GameObject;
import company.gametest9th.utils.*;

import java.awt.*;

public class Citizen extends Human {   //市民

    // 控管村民身上資源的enum
    public enum Resource {

        // 瓦斯自動採 所以不用寫
        WOOD,
        STEEL;
    }

    public enum WORK_STATUS {
        FREE, WOOD, STEEL;
    }

    private WORK_STATUS workStatus;

    private static final int CITIZEN_INITIAL_VALUE = 1;
    private static final int CITIZEN_INITIAL_SPEED = 4;
    private static final int painterWidth = 64;
    private static final int painterHeight = 64;
    private static final int colliderWidth = 64;
    private static final int colliderHeight = 64;

    private Delay toolDelay;
    private Delay visibleDelay; // 隱形的秒數

    // 這次採到的資源類別 && 數量
    private Resource resourceType;
    private int resourceNum;

    // 從哪個資源堆採的 紀錄下XY
    private int resourceTargetX;
    private int resourceTargetY;

    // 主堡XY
    private int baseX;
    private int baseY;

    // 我現在一次最大能帶多少木頭 && 鋼鐵回來
    private static int maxCarryWood;
    private static int maxCarrySteel;

    // 特殊資源的加成
    private static final int specialResourcePlus = 5;


    /**
     * 建構子 預設市民數值為1 , 不能打架 , 初始設定為閒人
     */
    public Citizen(int x, int y) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, CITIZEN_INITIAL_VALUE, CITIZEN_INITIAL_SPEED, new Path().img().actors().Actor2(), FLY_ABILITY.CANNOT_FLY, HUMAN_TYPE.CITIZEN, 6);

        // 預設人物出生方向朝下
        setWalkingDir(Global.Direction.DOWN);

        toolDelay = new Delay(30);

        // 村民的圖片
        setCharacterType(6);

        // 現在看不看的見村民
        setVisible(true);

        // 能不能看到人物的delay
        visibleDelay = new Delay(60);

        // 目前身上的資源類別 && 數量
        resourceType = null;
        resourceNum = 0;

        // 上一個採集點
        resourceTargetX = 0;
        resourceTargetY = 0;

        // 預設採集量
        maxCarryWood = 3;
        maxCarrySteel = 1;
    }

    /**
     * 派遣村民去採木頭
     */
    public void startToLog() {

        workStatus = WORK_STATUS.WOOD;
        this.toolDelay.loop();
    }

    /**
     * 派遣村民去採鐵
     */
    public void startToMine() {

        workStatus = WORK_STATUS.STEEL;
        this.toolDelay.loop();
    }

    /**
     * 設定 村民 = 閒人
     */
    public void setStateToFree() {

        workStatus = WORK_STATUS.FREE;
    }

    /**
     * 取得狀態
     *
     * @return 該人物的狀態
     */
    public WORK_STATUS getWorkStatus() {
        return workStatus;
    }

    public boolean isFree() {
        return (this.workStatus == WORK_STATUS.FREE);
    }


    /**
     * 人物的每帧繪畫
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {

        getAnimator().paint(getWalkingDir(), painter().left(), painter().top(), painter().right(), painter().bottom(), g);
    }


    /**
     * 人物的每帧更新
     */
    @Override
    public void update() {

        // 隱形時間到
        if (visibleDelay.count()) {

            // 現形
            setVisible(true);

            // 回去放資源
            setTarget(baseX, baseY);
        }

        // 如果我的MoveStatue 是 walk
        if (getMoveStatus() == Animator.State.WALK) {
            walk();
        }

        // 動畫更新[換圖片]
        getAnimator().update();
    }

    /**
     * 設定目的地
     *
     * @param x 目的地X
     * @param y 目的地Y
     */
    public void setTarget(int x, int y) {

        // 設定目的地X Y
        if (!this.isAt(x, y)) {

            this.setTargetXY(x, y);
            this.setMoveStatus(Animator.State.WALK);
        }
    }

    // 採集ING
    public void collecting(int baseX, int baseY, String resourceTypeStr) {

        // 把base的位置一起傳進來 記起來
        this.baseX = baseX;
        this.baseY = baseY;

        // 開始計時 (消失1秒)
        visibleDelay.play();

        // 消失ing
        setVisible(false);

        // 設定村民身上攜帶的資源類型(MainScene告知的)
        setCarryFromResourceTypeStr(resourceTypeStr);
    }

    /**
     * 根據傳進來的資源堆字串去設定 身上的資源種類和數量
     * @param resourceTypeStr 資源堆字串
     */
    private void setCarryFromResourceTypeStr(String resourceTypeStr){

        // 如果這次採集的是木頭
        if(resourceTypeStr.equals("WOOD")){
            resourceNum = maxCarryWood;
            resourceType = Resource.WOOD;
        }

        // 如果這次採集的是鋼鐵
        if(resourceTypeStr.equals("STEEL")){
            resourceNum = maxCarrySteel;
            resourceType = Resource.STEEL;
        }

        // 如果這次採集的是特殊木頭
        if(resourceTypeStr.equals("SPECIAL_WOOD")){
            // 那麼單次採集數量 直接五倍
            resourceNum = maxCarrySteel * specialResourcePlus;
            resourceType = Resource.WOOD;
        }

        // 如果這次採集的是特殊鋼鐵
        if(resourceTypeStr.equals("SPECIAL_STEEL")){
            // 那麼單次採集數量 直接五倍
            resourceNum = maxCarrySteel * specialResourcePlus;
            resourceType = Resource.STEEL;
        }
    }


    public int getResourceNum() {
        return resourceNum;
    }

    public Resource getResourceType() {
        return resourceType;
    }

    public void setResourceType() {
        resourceType = null;
    }

    public void setResourceNum() {
        resourceNum = 0;
    }

    public void setResourceTargetX(int x) {
        this.resourceTargetX = x;
    }

    public void setResourceTargetY(int y) {
        this.resourceTargetY = y;
    }

    public int getResourceTargetX() {
        return resourceTargetX;
    }

    public int getResourceTargetY() {
        return resourceTargetY;
    }


    /**
     * 鏡頭移動
     */

    @Override
    public void cameraMove() {

        // 因為村民有額外紀錄 XY 要另外改
        baseX += Global.CAMERA_MOVE_VX;
        baseY += Global.CAMERA_MOVE_VY;

        resourceTargetX += Global.CAMERA_MOVE_VX;
        resourceTargetY += Global.CAMERA_MOVE_VY;

        super.cameraMove();
    }

    /**
     * 重製視窗回初始位置
     */

    @Override
    public void resetObjectXY() {

        // 因為村民有額外紀錄 XY 要另外改
        baseX -= Global.SUM_OF_CAMERA_MOVE_VX;
        baseY -= Global.SUM_OF_CAMERA_MOVE_VY;

        resourceTargetX -= Global.SUM_OF_CAMERA_MOVE_VX;
        resourceTargetY -= Global.SUM_OF_CAMERA_MOVE_VY;


        super.resetObjectXY();
    }

    /**
     * 這邊設給韋辰的伐木場建造後 改進 伐木效率用 [設為static 可以直接Call Citizen. 不用透過很多層]
     *
     * @param num 所有煉鋼廠提升的總和
     */

    public static void setMaxCarryWood(int num) {
        maxCarryWood = oldMain.Resource.DEFAULT_WOOD_SPEED + num;
    }

    /**
     * 和上面一樣 改成鋼鐵版本
     *
     * @param num 所有煉鋼廠提升的總和
     */

    public static void setMaxCarrySteel(int num) {
        maxCarrySteel = oldMain.Resource.DEFAULT_STEEL_SPEED + num;
    }

    /**
     * 取得現在的伐木效率
     *
     * @return 現在的伐木效率
     */

    public static int getMaxCarryWood() {
        return maxCarryWood;
    }

    /**
     * 取得現在的採鋼效率
     *
     * @return 現在的採鋼效率
     */

    public static int getMaxCarrySteel() {
        return maxCarrySteel;
    }

}
