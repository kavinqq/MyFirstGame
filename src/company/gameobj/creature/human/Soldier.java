package company.gameobj.creature.human;

import company.gameobj.GameObject;
import company.gameobj.creature.enemy.Enemy;
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

        super(x,y, painterWidth, painterHeight, colliderWidth, colliderHeight, value, speed, img, flyAbility);

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

    public void getDamaged(int value){
        setMaxHp(getHp()-value);

    }

    public boolean isDead(){
        if(getHp()<=0){
            return true;
        }
        return false;
    }

    public void detect(Enemy enemy){
        if(enemy==null){
            return;
        }
        if(enemy.isAlive() && this.detectRange().overlap(enemy.painter())){

            this.setAttackTarget(enemy);
        }
    }

    @Override
    public void update() {
        if(getAttackTarget() != null){
            setTarget(getAttackTarget().painter().centerX(),getAttackTarget().painter().centerY());
        }

        // 如果我的MoveStatue 是 walk
        if (getMoveStatus() == Animator.State.WALK) {
            walk();
        }

        // 動畫更新[換圖片]
        getAnimator().update();
    }
}
