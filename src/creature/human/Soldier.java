package creature.human;

/**
 * @author Lillian
 * @Date 2022/3/7
 * @Description
 */
public abstract class Soldier extends Human{
    public enum SOLDIER_TYPE {
        ARMY, AIR_FORCE;
    }
    private SOLDIER_TYPE soldierType;
    public Soldier(int value, String img, FLY_ABILITY flyAbility, SOLDIER_TYPE soldierType){
        super(value, img, flyAbility, HUMAN_TYPE.SOLDIER);
        this.setType(HUMAN_TYPE.SOLDIER);
    }
    public void setSoldierType(SOLDIER_TYPE soldierType){
        this.soldierType = soldierType;
    }
    public boolean isArmy(){
        return (this.soldierType == SOLDIER_TYPE.ARMY);
    }
    public boolean isAirForce(){
        return (this.soldierType == SOLDIER_TYPE.AIR_FORCE);
    }
}
