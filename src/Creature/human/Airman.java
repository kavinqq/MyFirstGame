package Creature.human;

/**
 * @author Lillian
 * @Date 2022/3/7
 * @Description
 */
public class Airman extends Soldier{
    public Airman(){
        this.setValue(2);
        this.setSoldierType(SOLDIER_TYPE.AIR_FORCE);
        this.setFlyability(FLYABILITY.CAN_FLY);
    }
}
