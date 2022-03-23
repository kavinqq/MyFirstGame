package company.gameobj.creature;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.GameObject;

import java.awt.*;

public abstract class Creature extends GameObject{
    public Creature(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight ,int value, int speed, String img, FLY_ABILITY flyAbility) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight);
        this.value = value;
        this.speed = speed;
        this.img = SceneController.getInstance().imageController().tryGetImage(img);
        this.setFlyAbility(flyAbility);
        this.status = STATUS.STOPPING;
    }

    public enum STATUS {
        FIGHTING, WALKING, STOPPING;
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
    private STATUS status;
    private GameObject attackTarget;
    private int targetX = Global.SCREEN_X/2;
    private int targetY = Global.SCREEN_Y/2;


    private Global.Direction dir;


    /**
     * 獲取該人物物件的數值 (也就是每個人物的攻擊力)
     * @return 每個人物的攻擊力
     */
    public int getValue(){      //獲取目前數值
        return value;
    }

    public void getAttacked(int value){
        this.value-=value;
    }

    public void setFlyAbility(FLY_ABILITY flyAbility) {
        this.flyAbility = flyAbility;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public STATUS getStatus() {
        return status;
    }

    public boolean isAlive(){
        return (this.value>0);
    }

    public boolean isFlyable(){
        return (this.flyAbility== FLY_ABILITY.CAN_FLY);
    }

    public boolean isFighting(){
        return (this.status== STATUS.FIGHTING);
    }

    public void setAttackTarget(GameObject attackTarget) {
        this.attackTarget = attackTarget;
    }

    public GameObject getAttackTarget() {
        return attackTarget;
    }

    public boolean isHavingNoTarget(){
        return (this.attackTarget == null);
    }

    public Image getImg(){
        return this.img;
    }

    public void setTargetXY(int x, int y){
        setTargetX(x);
        setTargetY(y);
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public int targetX() {
        return targetX;
    }

    public int targetY() {
        return targetY;
    }

    public boolean isSeeing(GameObject gameObject){
        return this.detectRange().overlap(gameObject.painter());
    }

    public abstract void walk();

    public int speed() {
        return speed;
    }

    public Global.Direction getDir() {
        return dir;
    }

    public void setDir(Global.Direction dir) {
        this.dir = dir;
    }
}
