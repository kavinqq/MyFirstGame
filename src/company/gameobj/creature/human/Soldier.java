package company.gameobj.creature.human;

import company.gametest9th.utils.Animator;

/**
 * @author Lillian
 * @Date 2022/3/7
 * @Description
 */
public abstract class Soldier extends Human{
    public enum SOLDIER_TYPE {
        ARMY, AIR_FORCE;
    }

    public enum STATUS {
        WALKING, PATROLLING, FIGHTING;
    }

    private SOLDIER_TYPE soldierType;
    public Soldier(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight,int value, int speed, String img, FLY_ABILITY flyAbility, SOLDIER_TYPE soldierType){
        super(x,y, painterWidth, painterHeight, colliderWidth, colliderHeight, value, speed, img, flyAbility, Animator.State.STAND);
        this.setHumanType(HUMAN_TYPE.SOLDIER);
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
