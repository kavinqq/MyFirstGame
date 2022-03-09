package creature.human;

public class ArmySoldier extends Soldier  {  //士兵
    /**
     * 建構子 預設士兵數值為2 ,等級為1
     */
    public ArmySoldier(int level){
        this.setValue(2 + 3*level);
        this.setSoldierType(SOLDIER_TYPE.ARMY);
        this.setFlyability(FLYABILITY.CANNOT_FLY);
    }
}

