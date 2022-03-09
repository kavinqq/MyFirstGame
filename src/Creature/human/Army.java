package Creature.human;

public class Army extends Soldier  {  //士兵
    /**
     * 建構子 預設士兵數值為2 ,等級為1
     */
    public Army(){
        this.setValue(2);
        this.setSoldierType(SOLDIER_TYPE.ARMY);
        this.setFlyability(FLYABILITY.CANNOT_FLY);
    }
}

