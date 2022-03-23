package company.gameobj.creature;

import company.controllers.SceneController;
import company.gameobj.GameObject;

import java.awt.*;

public abstract class Creature extends GameObject{
    public Creature(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight ,int value, String img, FLY_ABILITY flyAbility) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight);
        this.value = value;
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
    private Image img;
    private FLY_ABILITY flyAbility;
    private STATUS status;
    private GameObject attackTarget;


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

    public Image getImg(){
        return this.img;
    }
}
