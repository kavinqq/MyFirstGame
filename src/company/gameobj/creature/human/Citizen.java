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
    private static final int CITIZEN_INITIAL_SPEED= 4;
    private static final int painterWidth = 64;
    private static final int painterHeight = 64;
    private static final int colliderWidth = 64;
    private static final int colliderHeight = 64;
    private Delay toolDelay;

    private Animator.State state;
//    private boolean canMove;
//    private boolean hasMove;

    private HumanAnimator animator;



    /**
     * 建構子 預設市民數值為1 , 不能打架 , 初始設定為閒人
     */
    public Citizen(int x, int y) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, CITIZEN_INITIAL_VALUE, CITIZEN_INITIAL_SPEED, new Path().img().actors().Actor2(), FLY_ABILITY.CANNOT_FLY, HUMAN_TYPE.CITIZEN);

        // 預設人物出生方向朝下
        setWalkingDir(Global.Direction.DOWN);

        toolDelay = new Delay(30);

        // 人物自己記錄一下狀態
        this.state = Animator.State.STAND;

        // 村民的圖片
        setCharacterType(6);

        animator = new HumanAnimator(getCharacterType(), this.state);


//        canMove = false;
//        hasMove = false;
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

    @Override
    public void paintComponent(Graphics g) {
        //g.drawImage(getImg(), this.painter().left(), this.painter().top(), null);
        animator.paint(getWalkingDir(), painter().left(), painter().top(), painter().right(), painter().bottom(), g);
    }

    @Override
    public void update() {

        //mouseToMove();
        if(getMoveStatus()==Animator.State.WALK){
            walk();
        }
        animator.update();

        //TODO
        if (workStatus == WORK_STATUS.FREE) {
            //randomWalk();
        } else {
            if (toolDelay.count()) {
                //wave tool();
            }
        }
    }


    public void setTarget(int x, int y){

        // 設定目的地X Y
        if(!this.isAt(x,y)){
            this.setTargetXY(x,y);
            this.setMoveStatus(Animator.State.WALK);
        }
    }

    /**
     * 停止移動 目的地 直接 =  現在位置(就是直接讓他到目的地的意思)
     */
    @Override
    public void stop() {
        setTarget(painter().centerX(), painter().centerY());
    }

    public void mouseToMove() {
        // 如果現在不能移動 那下面都不用跑
//        if(!canMove){
//            return;
//        }

        // 確定能走了, 把狀態改為walk
        animator.setState(Animator.State.WALK);

        // 速度(一步的距離) = 初始速度
        int speed = speed();

        // 處理X
        // 如果當前X還沒走到目的地X
        if(targetX() != painter().centerX()){
            // 如果剩下的距離 < 一步 那麼 一步距離 = 剩下的距離
            if(Math.abs(targetX() - painter().centerX()) < speed()){
                speed = Math.abs(targetX() - painter().centerX());
            }

            // 如果 目的地在角色 右邊 往右走
            if(targetX() > painter().centerX()){
                setWalkingDir(Global.Direction.RIGHT);
                this.translateX(speed);
            }
            // 如果 目的地在角色 左邊 往左走
            if(targetX() < painter().centerX()){
                setWalkingDir(Global.Direction.LEFT);
                this.translateX(-1*speed);
            }
        }


        // 處理Y
        // 如果當前Y沒走到目的地Y
        if(targetY() != painter().centerY()) {

            // 如果剩下的距離 < 一步 那麼 一步距離 = 剩下的距離
            if(Math.abs(targetY() - painter().centerY()) < speed()){
                speed = Math.abs(targetY() - painter().centerY());
            }

            // 如果 目的地在角色 下面 往下走
            if(targetY() > painter().centerY()){// && !hasMove){
                setWalkingDir(Global.Direction.DOWN);
                this.translateY(speed);
                //hasMove = true;
            }

            // 如果 目的地在角色 上面 往上走
            if(targetY() < painter().centerY()){// && !hasMove){
                setWalkingDir(Global.Direction.UP);
                this.translateY(-1 * speed);
            }
        }

        // 走到了目的地 把能移動關起來
        if(targetX() == painter().centerX() && targetY() == painter().centerY()){
            animator.setState(Animator.State.STAND);
            //canMove = false;
        }
    }

}
