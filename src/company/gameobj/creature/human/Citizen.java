package company.gameobj.creature.human;

import company.Global;

import company.controllers.AudioResourceController;
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

    private HumanAnimator animator;

    // 這次採到的資源類別 && 數量
    private Resource resourceType;
    private int resourceNum;

    // 從哪個資源堆採的 紀錄下XY
    private int resourceTargetX;
    private int resourceTargetY;

    // 主堡XY
    private int baseX;
    private int baseY;


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

        if (Global.CAMERA_MOVE_VX != 0 || Global.CAMERA_MOVE_VY != 0) {

        }


        // 隱形時間到
        if (visibleDelay.count()) {

            // 現形
            setVisible(true);

            // 回去放資源
            setTarget(baseX, baseY);
        }

        // 如果我的MoveStatue 是 walk
        if (getMoveStatus() == Animator.State.WALK) {

            // 改成行走動畫
            getAnimator().setState(Animator.State.WALK);

            // 行走
            walk();
        } else {

            // 改成站立動畫
            getAnimator().setState(Animator.State.STAND);
        }

        // 動畫更新[換圖片]
        getAnimator().update();

//        //TODO
//        if (workStatus == WORK_STATUS.FREE) {
//            //randomWalk();
//        } else {
//            if (toolDelay.count()) {
//                //wave tool();
//            }
//        }
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
    public void collecting(GameObject gameObject, int baseX, int baseY, int resourceNum, Resource resourceType) {

        // 把base的位置一起傳進來 記起來
        this.baseX = baseX;
        this.baseY = baseY;


        // 開始計時 (消失1秒)
        visibleDelay.play();

        // 消失ing
        setVisible(false);

        // 這一次的採集種類 && 量
        this.resourceNum = resourceNum;
        this.resourceType = resourceType;

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
    public void cameraMove(){

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
        baseX -= getSumOfCameraMoveX();
        baseY -= getSumOfCameraMoveY();

        resourceTargetX -= getSumOfCameraMoveX();
        resourceTargetY -= getSumOfCameraMoveY();

        // ↓ Human的reset 會把 sum清空 要最後執行
        super.resetObjectXY();
    }

}
