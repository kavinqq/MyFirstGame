package creature.human;

import company.gametest9th.utils.Path;

public class ArmySoldier extends Soldier  {  //士兵
    /**
     * 建構子 預設士兵數值為2 ,等級為1
     */
    public ArmySoldier(int level){
        super(2 + 3*level, new Path().img().humans().armySoldier(), FLY_ABILITY.CANNOT_FLY, SOLDIER_TYPE.ARMY);
    }
}

