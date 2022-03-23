package company.gameobj.creature.human;

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
    public Soldier(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight,int value, String img, FLY_ABILITY flyAbility, SOLDIER_TYPE soldierType){
        super(x,y, painterWidth, painterHeight, colliderWidth, colliderHeight, value, img, flyAbility, HUMAN_TYPE.SOLDIER);
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
