package creature.human;

/**
 * @author Lillian
 * @Date 2022/3/7
 * @Description
 */
public class AirForceSoldier extends Soldier{
    public AirForceSoldier(int level){
        this.setValue(2 + 2*level);
        this.setSoldierType(SOLDIER_TYPE.AIR_FORCE);
        this.setFlyability(FLYABILITY.CAN_FLY);
    }
}
