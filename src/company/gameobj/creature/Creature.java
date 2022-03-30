package company.gameobj.creature;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.GameObject;
import company.gametest9th.utils.Animator;

import java.awt.*;

public abstract class Creature extends GameObject {

    public enum FIGHTING_STATUS {
        FIGHTING, NOT_FIGHTING;
    }


    public enum FLY_ABILITY {
        CAN_FLY, CANNOT_FLY;
    }

    /**
     * 生物 的初始數值(也就是攻擊力)
     */

    private int value;
    private int speed;
    private Image img;
    private FLY_ABILITY flyAbility;
    private FIGHTING_STATUS fightingStatus;
    private Animator.State moveStatus;
    private GameObject attackTarget;
    private int targetX;
    private int targetY;
    private boolean isAbleToGoRight = true;
    private boolean isAbleToGoLeft = true;
    private boolean isAbleToGoUp = true;
    private boolean isAbleToGoDown = true;

    private Global.Direction walkingDir;
    private Global.Direction blockedDir;


    public Creature(int x, int y, int targetX, int targetY, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight, int value, int speed, String img, FLY_ABILITY flyAbility, Animator.State moveStatus) {

        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight);

        this.targetX = targetX;
        this.targetY = targetY;

        this.value = value;

        this.speed = speed;


        this.img = SceneController.getInstance().imageController().tryGetImage(img);

        this.setFlyAbility(flyAbility);

        this.fightingStatus = FIGHTING_STATUS.NOT_FIGHTING;

        this.moveStatus = moveStatus;
    }


    /**
     * 獲取該人物物件的數值 (也就是每個人物的攻擊力)
     *
     * @return 每個人物的攻擊力
     */

    public int getValue() {      //獲取目前數值
        return value;
    }

    public void getAttacked(int value) {
        this.value -= value;
    }

    public void setFlyAbility(FLY_ABILITY flyAbility) {
        this.flyAbility = flyAbility;
    }

    public void setFightingStatus(FIGHTING_STATUS fightingStatus) {
        this.fightingStatus = fightingStatus;
    }

    public FIGHTING_STATUS getFightingStatus() {
        return fightingStatus;
    }

    public boolean isAlive() {
        return (this.value > 0);
    }


    public boolean isFlyable() {
        return (this.flyAbility == FLY_ABILITY.CAN_FLY);
    }


    public boolean isFighting() {
        return (this.fightingStatus == FIGHTING_STATUS.FIGHTING);
    }


    public void setAttackTarget(GameObject attackTarget) {
        this.attackTarget = attackTarget;
    }


    public GameObject getAttackTarget() {
        return attackTarget;
    }

    public boolean isHavingNoTarget() {
        return (this.attackTarget == null);
    }


    /**
     * 回傳這個生物的圖片
     * @return 該生物的圖片
     */

    public Image getImg() {
        return this.img;
    }

    /**
     * 設定行走狀態
     * @param walkingStatus 新的行走狀態
     */

    public void setMoveStatus(Animator.State walkingStatus) {
        this.moveStatus = walkingStatus;
    }

    /**
     * 回傳該生物目前的行走狀態 (站立 or 行走)
     * @return e
     */

    public Animator.State getMoveStatus() {
        return moveStatus;
    }

    /**
     * 設定目的地 x, y
     * @param x 目的地X
     * @param y 目的地Y
     */

    public void setTargetXY(int x, int y) {
        if (!this.isAt(x, y)) {
            setTargetX(x);
            setTargetY(y);
            if (x != painter().centerX() && y != painter().centerY()) {
                Global.Direction[] arr = new Global.Direction[2];
                arr[0] = (targetX() > painter().centerX()) ? Global.Direction.RIGHT : Global.Direction.LEFT;
                arr[1] = (targetY() > painter().centerY()) ? Global.Direction.DOWN : Global.Direction.UP;
                this.setWalkingDir(arr[Global.random(0, 1)]);
            }
//            if(this.getBlockedDir()==null){
//            }
//            else{
//            }
        }
    }

    /**
     * 單純設定 目的地X
     * @param targetX  目的地X
     */

    private void setTargetX(int targetX) {
        this.targetX = targetX;
    }


    /**
     * 單純設定目的地X
     * @param targetY 目的地X
     */

    private void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    /**
     * 取得目的地X
     * @return 目的地X
     */

    public int targetX() {
        return targetX;
    }

    /**
     * 取得目的地Y
     * @return 目的地Y
     */

    public int targetY() {
        return targetY;
    }

    public boolean isSeeing(GameObject gameObject) {
        return this.detectRange().overlap(gameObject.painter());
    }

    /**
     * 每個生物的行走 (各自實現)
     */

    public abstract void walk();

    /**
     * 每個生物的移動速度(每一幀)
     * @return 移動速度
     */

    public int speed() {
        return speed;
    }

    public boolean isAbleToGoRight() {
        return isAbleToGoRight;
    }

    public boolean isAbleToGoLeft() {
        return isAbleToGoLeft;
    }

    public boolean isAbleToGoUp() {
        return isAbleToGoUp;
    }

    public boolean isAbleToGoDown() {
        return isAbleToGoDown;
    }

    public void setAbleToGoRight(boolean ableToGoRight) {
        isAbleToGoRight = ableToGoRight;
    }

    public void setAbleToGoLeft(boolean ableToGoLeft) {
        isAbleToGoLeft = ableToGoLeft;
    }

    public void setAbleToGoUp(boolean ableToGoUp) {
        isAbleToGoUp = ableToGoUp;
    }

    public void setAbleToGoDown(boolean ableToGoDown) {
        isAbleToGoDown = ableToGoDown;
    }


    public void setWalkingDir(Global.Direction walkingDir) {
        this.walkingDir = walkingDir;
    }

    public Global.Direction getWalkingDir() {
        return walkingDir;
    }

    public void setBlockedDir(Global.Direction blockedDir) {
        this.blockedDir = blockedDir;
    }

    public void setNoBlockedDir() {
        this.blockedDir = null;
    }

    public Global.Direction getBlockedDir() {
        return blockedDir;
    }

    public boolean isAt(int x, int y) {
        return (this.painter().centerX() == x && this.painter().centerY() == y);
    }

    public boolean isAtTarget() {
        return (this.painter().centerX() == targetX && this.painter().centerY() == targetY);
    }

    // 目的地是否在該gameObj裡面
    public boolean isTargetInObj(GameObject gameObject){

        return targetX > gameObject.painter().left() && targetX < gameObject.painter().right() && targetY < gameObject.painter().bottom() && targetY > gameObject.painter().top();

    }

}
