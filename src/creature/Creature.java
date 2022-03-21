package creature;

import company.controllers.SceneController;

import java.awt.*;

public abstract class Creature {
    public Creature(int value, String img, FLY_ABILITY flyAbility) {
        this.value = value;
        this.img = SceneController.getInstance().imageController().tryGetImage(img);
        this.setFlyAbility(flyAbility);
    }

    public enum Status{
        FIGHTING, WALKING;
    }


    public enum FLY_ABILITY {
        CAN_FLY, CANNOT_FLY;
    }
    /**
     * 生物 的初始數值(也就是攻擊力)
     */
    private int value;
    private FLY_ABILITY flyAbility;
    private Status status;
    private Image img;


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

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isAlive(){
        return (this.value>0);
    }

    public boolean isFlyable(){
        return (this.flyAbility== FLY_ABILITY.CAN_FLY);
    }

    public boolean isFighting(){
        return (this.status==Status.FIGHTING);
    }

}
