package company.gameobj.creature.human;

import company.Global;

import company.gameobj.GameObject;
import company.gameobj.Steel;
import company.gameobj.Tree;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.Delay;
import company.gametest9th.utils.HumanAnimator;
import company.gametest9th.utils.Path;

import java.awt.*;

public class Citizen extends Human {   //市民

    // 控管村民身上資源的enum
    public enum Resource {

        // 瓦斯自動採 所以不用寫
        WOOD(0),
        STEEL(0);

        private int num;

        Resource(int num){
            this.num = num;
        }

        // 設定村民現在身上有多少資源(不用 += 是因為 拿的量是有上限的 由伐木場升級)
        public void setNum(int num){
            this.num = num;
        }

        // 去主堡繳資源
        public int getNum(){
            int tmp = num;
            num = 0;
            return tmp;
        }
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
    private Delay visibleDelay;

    private HumanAnimator animator;

    private boolean hasCollected;
    private boolean isAutoCollecting;

    private Resource resourceType;
    private int resourceNum;

    private int resourceTargetX;
    private int resourceTargetY;

    public static int count = 0;
    public int myNum;

    private int baseX;
    private int baseY;


    /**
     * 建構子 預設市民數值為1 , 不能打架 , 初始設定為閒人
     */
    public Citizen(int x, int y) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, CITIZEN_INITIAL_VALUE, CITIZEN_INITIAL_SPEED, new Path().img().actors().Actor2(), FLY_ABILITY.CANNOT_FLY,HUMAN_TYPE.CITIZEN, Animator.State.STAND);

        // 預設人物出生方向朝下
        setWalkingDir(Global.Direction.DOWN);

        toolDelay = new Delay(30);

        // 村民的圖片
        setCharacterType(6);

        // 動畫初始化
        animator = new HumanAnimator(getCharacterType(), getMoveStatus());

        // 現在看不看的見村民
        setVisible(true);

        // 能不能看到人物的delay
        visibleDelay = new Delay(60);

        // 是否採集過了
        hasCollected = false;

        isAutoCollecting = false;

        // 目前身上的資源類別 && 數量
        resourceType = null;
        resourceNum = 0;

        // 上一個採集點
        resourceTargetX = 0;
        resourceTargetY = 0;

        System.out.println("我新增了一個村民!!");

        count++;
        myNum = count;
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
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {

        animator.paint(getWalkingDir(), painter().left(), painter().top(), painter().right(), painter().bottom(), g);
    }


    /**
     * 人物的每帧更新
     */
    @Override
    public void update() {

        if(visibleDelay.count()){
            setVisible(true);

            setTarget(baseX, baseY);
        }

        // 如果我的MoveStatue 是 walk
        if (getMoveStatus() == Animator.State.WALK) {

            // 改成行走動畫
            animator.setState(Animator.State.WALK);

            // 行走
            walk();
        } else {

            // 改成站立動畫
            animator.setState(Animator.State.STAND);
        }

        animator.update();

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
    public void collecting(GameObject gameObject, int baseX, int baseY){

        // 把base的位置一起傳進來 記起來
        this.baseX = baseX;
        this.baseY = baseY;


        // 開始計時 (消失1秒)
        visibleDelay.play();

        // 消失ing
        setVisible(false);

        // 如果我碰到樹木資源
        if(gameObject instanceof Tree){

            // 我身上有多少樹木 存在enum中
            Resource.WOOD.setNum(((Tree)gameObject).eachTimeGet());
            resourceType = Resource.WOOD;
            resourceNum = Resource.WOOD.getNum();


            // 標示為採集過後
            hasCollected = true;
        }

        // 如果我碰到鋼鐵資源
        if(gameObject instanceof Steel){

            // 我身上有多少鋼鐵 存在enum中
            Resource.STEEL.setNum(((Steel)gameObject).eachTimeGet());

            resourceType = Resource.STEEL;
            resourceNum = Resource.STEEL.getNum();

            // 標示為採集過後
            hasCollected = true;

            isAutoCollecting = true;
        }

    }

    /**
     * 是否是採集完成的狀態
     * @return 剛採集完成 true 反之 false
     */
    public boolean isHasCollected(){
        return hasCollected;
    }

    public int getResourceNum(){
        return resourceNum;
    }

    public Resource getResourceType(){
        return resourceType;
    }

    public void setResourceType(){
        resourceType = null;
    }

    public void setResourceNum(){
        resourceNum = 0;
    }

    public boolean isAutoCollecting(){
        return isAutoCollecting;
    }

    public void setResourceTargetX(int x){
        this.resourceTargetX = x;
    }

    public void setResourceTargetY(int y){
        this.resourceTargetY = y;
    }

    public int getResourceTargetX(){
        return resourceTargetX;
    }

    public int getResourceTargetY(){
        return resourceTargetY;
    }

}
