package company.gameobj.creature.human;

import company.Global;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.Delay;
import company.gametest9th.utils.HumanAnimator;
import company.gametest9th.utils.Path;

import java.awt.*;

public class Citizen extends Human {   //市民

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

    private HumanAnimator animator;


    /**
     * 建構子 預設市民數值為1 , 不能打架 , 初始設定為閒人
     */
    public Citizen(int x, int y) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, CITIZEN_INITIAL_VALUE, CITIZEN_INITIAL_SPEED, new Path().img().actors().Actor2(), FLY_ABILITY.CANNOT_FLY,HUMAN_TYPE.CITIZEN, Animator.State.STAND);

        // 預設人物出生方向朝下
        setWalkingDir(Global.Direction.DOWN);

        toolDelay = new Delay(30);

        // 村民的圖片
        setCharacterType(0);

        // 動畫初始化
        animator = new HumanAnimator(getCharacterType(), getMoveStatus());
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

}
