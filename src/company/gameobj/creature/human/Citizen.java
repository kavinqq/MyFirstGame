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


    private int type;
    private Animator.State state;
    private boolean canMove;
    private boolean hasMove;

    private HumanAnimator animator;



    /**
     * 建構子 預設市民數值為1 , 不能打架 , 初始設定為閒人
     */
    public Citizen(int x, int y) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, CITIZEN_INITIAL_VALUE, CITIZEN_INITIAL_SPEED, new Path().img().actors().Actor2(), FLY_ABILITY.CANNOT_FLY, HUMAN_TYPE.CITIZEN);
        toolDelay = new Delay(30);
        animator = new HumanAnimator(type, state);
        canMove = false;
        hasMove = false;
    }
    public Citizen(int x, int y, Animator.State state) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, CITIZEN_INITIAL_VALUE, CITIZEN_INITIAL_SPEED, new Path().img().actors().Actor2(), FLY_ABILITY.CANNOT_FLY, HUMAN_TYPE.CITIZEN);
        setDir(Global.Direction.DOWN);
        toolDelay = new Delay(30);
        animator = new HumanAnimator(type, state);
        canMove = false;
        hasMove = false;
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
        animator.paint(getDir(), painter().left(), painter().top(), painter().right(), painter().bottom(), g);
    }

    @Override
    public void update() {
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

    public void keyToMove(int dirNum){

        if(dirNum == Global.UP){
            setDir(Global.Direction.UP);
            this.translateY(speed() * -1);
        }

        if(dirNum == Global.DOWN){
            setDir(Global.Direction.DOWN);
            this.translateY(speed());
        }

        if(dirNum == Global.LEFT){
            setDir(Global.Direction.LEFT);
            this.translateX(speed() * -1);
        }

        if(dirNum == Global.RIGHT){
            setDir(Global.Direction.RIGHT);
            this.translateX(speed());
        }

    }

    public void setTarget(int x, int y){
        System.out.println("SET READY!");

        this.setTargetXY(x,y);

        canMove = true;
    }

    public void mouseToMove() {

        hasMove = false;


        if(!canMove){
            return;
        }

        int speed = speed();
        if(Math.abs(targetX() - painter().centerX()) < speed()){
            speed = Math.abs(targetX() - painter().centerX());
        }

        if(targetX() > painter().centerX()){
            setDir(Global.Direction.RIGHT);
            this.translateX(speed);
            hasMove = true;
        }

        if(targetX() < painter().centerX() && !hasMove){
            setDir(Global.Direction.LEFT);
            this.translateX(-1*speed);
            hasMove = true;
        }

        speed = speed();

        // 處理Y

        if(Math.abs(targetY() - painter().centerY()) < speed()){
            speed = Math.abs(targetY() - painter().centerY());
        }

        if(targetY() > painter().centerY() && !hasMove){
            setDir(Global.Direction.DOWN);
            this.translateY(speed);
            hasMove = true;
        }

        if(targetY() < painter().centerY() &&!hasMove){
            setDir(Global.Direction.UP);
            this.translateY(-1 * speed);
            hasMove = true;
        }

        speed = speed();

        if(targetX() == painter().centerX() && targetY() == painter().centerY()){
            canMove = false;
        }

        hasMove = false;
    }

}
