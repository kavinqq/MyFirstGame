package company.gameobj.creature.human;

import company.gameobj.creature.Creature;

public abstract class Human extends Creature {
    public Human(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight, int value, String img, FLY_ABILITY flyAbility, HUMAN_TYPE humanType) {
        super(x,y,painterWidth, painterHeight, colliderWidth, colliderHeight, value, img, flyAbility);
        this.setType(humanType);
    }

    public enum HUMAN_TYPE {
        CITIZEN, SOLDIER;
    }

    private HUMAN_TYPE type;

    /**
     * 人物 的初始數值(也就是攻擊力)
     */
    private int value;
    /**
     *
     */
    private int upgradePoint;

    /**
     * 該人物 的初始數值(也就是攻擊力)
     * @param value 攻擊數值
     * @param type 讓各個subclass決定
     */

    /**
     * 升級該物件的等級 (每次升級 等級 + 1 && 數值 + 3)
     */


    /**
     * 獲取該人物物件的數值 (也就是每個人物的攻擊力)
     * @return 每個人物的攻擊力
     */
    public int getValue(){      //獲取目前數值
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public void setType(HUMAN_TYPE type){
        this.type = type;
    }

    public boolean isCitizen(){
        return (this.type== HUMAN_TYPE.CITIZEN);
    }

    public boolean isSoldier(){
        return (this.type == HUMAN_TYPE.SOLDIER);
    }

    public boolean isAlive(){
        return (this.value>0);
    }

    public void getAttacked(int value){
        this.setValue(this.getValue()-value);
    }
}

