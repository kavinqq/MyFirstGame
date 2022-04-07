package company.gameobj.creature.enemy.zombies;

import company.Global;
import company.gameobj.GameObject;
import company.gameobj.buildings.Building;
import company.gameobj.creature.Creature;
import company.gameobj.creature.enemy.Enemy;
import company.gameobj.creature.human.Human;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.Vector;
import company.gametest9th.utils.ZombieAnimator;

import java.awt.*;

public abstract class Zombie extends Enemy {

    //TODO: 設定各個殭屍角色的圖片
    public enum ZOMBIE_TYPE{
        ZOMBIE_BIG(3),
        ZOMBIE_FLYING(0),
        ZOMBIE_FLYING_BIG(4),
        ZOMBIE_KING(7),
        ZOMBIE_NORMAL(1),
        ZOMBIE_TYPE_I(2),
        ZOMBIE_TYPE_II(5),
        ZOMBIE_WITCH(6);

        private int value;
        ZOMBIE_TYPE(int value){
            this.value = value;
        }

        int getValue(){
            return this.value;
        }
    }

    private ZOMBIE_TYPE zombieType;
    /**
     * 殭屍的建構子
     *
     * @param value 該殭屍的預設攻擊力
     */
    //TODO: allow each type of zombie to set their own speed

    private static final int SPEED = 2;
    public Zombie(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight, int value, String img, FLY_ABILITY flyAbility, ZOMBIE_TYPE zombieType) {
        //TODO: set targetX and targetY to correct parameter
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, value, SPEED, img, flyAbility, Animator.State.WALK);
        setMaxHp(50);
        this.zombieType = zombieType;
        this.setAnimator(new ZombieAnimator(this.zombieType.getValue(), Animator.State.WALK));
    }

    public Zombie(int painterWidth, int painterHeight, int colliderWidth, int colliderHeight, int value, String img, FLY_ABILITY flyAbility, ZOMBIE_TYPE zombieType) {
        //TODO: set targetX and targetY to correct parameter
        super((int)Global.getRandom().vx(), (int) Global.getRandom().vy(), painterWidth, painterHeight, colliderWidth, colliderHeight, value, SPEED, img, flyAbility, Animator.State.WALK);
        setMaxHp(50);
        this.zombieType = zombieType;
        this.setAnimator(new ZombieAnimator(this.zombieType.getValue(), Animator.State.WALK));
    }




    /**
     * 殭屍數量成長
     *
     * @param round 現在第幾輪
     * @return 當前此類殭屍的數量
     */
    public abstract int currentRoundCount(int round);

    @Override
    public void update() {

        // 如果我的MoveStatue 是 walk
        switch (getMoveStatus()){
            case STAND:{
                getAnimator().setState(Animator.State.STAND);
                break;
            }
            case WALK:{
                getAnimator().setState(Animator.State.WALK);
                // 行走
                walk();
                break;
            }
        }

        // 動畫更新[換圖片]
        getAnimator().update();

        if(this.getFightEffect()!=null && this.getFightEffect().isDue()){
            this.setFightEffect(null);
        }
        if(getAttackTarget() != null) {
            setTargetXY(getAttackTarget().painter().centerX(),getAttackTarget().painter().centerY());
        }
    }


    public void detect(Human human){
        if(this.detectRange().overlap(human.painter())){
            this.setAttackTarget(human);
        }
    }

    public void detect(Building building){
        if(this.detectRange().overlap(building.painter())){
            this.setAttackTarget(building);
        }
    }
}
