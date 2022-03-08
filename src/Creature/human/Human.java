package Creature.human;

import Creature.Creature;

public abstract class Human extends Creature {
    public enum TYPE {
        CITIZEN, SOLDIER;
    }
    /**
     * 用來辦別他是不是士兵
     */
    private boolean isArmy = false;
    /**
     * 用來辦別他是不是戰機飛行員
     */
    private boolean isAirForce = false;
    private TYPE type;

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
     * 拿取isArmy 判斷 他是否是士兵
     * @return 如果是 士兵 回傳 true 不是 回傳 false
     */
    public boolean isArmy(){
        return isArmy;
    }
    /**
     * 拿取isAirForce 判斷 他是否是空軍
     * @return 如果是 空軍 回傳 true 不是 回傳 false
     */
    public boolean isAirForce(){
        return isAirForce;
    }
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

    public void setType(TYPE type){
        this.type = type;
    }

    protected void setIsArmy(){
        this.isArmy = true;
    }

    protected void setIsAirForce(){
        this.isAirForce = true;
    }

    public boolean isCitizen(){
        return (this.type==TYPE.CITIZEN);
    }


}

