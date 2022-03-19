package creature.human;

import company.gametest9th.utils.Path;

/**
 * @author Lillian
 * @Date 2022/3/7
 * @Description
 */
public class AirForceSoldier extends Soldier{
    public AirForceSoldier(int level){
        super(2 + 2*level, new Path().img().humans().armySoldier() ,FLY_ABILITY.CAN_FLY, SOLDIER_TYPE.AIR_FORCE);
    }
}
