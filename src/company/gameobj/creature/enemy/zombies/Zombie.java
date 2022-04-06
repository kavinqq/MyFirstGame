package company.gameobj.creature.enemy.zombies;

import company.Global;
import company.gameobj.creature.Creature;
import company.gameobj.creature.enemy.Enemy;
import company.gameobj.creature.human.Human;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.ZombieAnimator;

import java.awt.*;

public abstract class Zombie extends Enemy {

    //TODO: 設定各個殭屍角色的圖片
    public enum ZOMBIE_TYPE{
        ZOMBIE_BIG(2),
        ZOMBIE_FLYING(2),
        ZOMBIE_FLYING_BIG(2),
        ZOMBIE_KING(2),
        ZOMBIE_NORMAL(2),
        ZOMBIE_TYPE_I(2),
        ZOMBIE_TYPE_II(2),
        ZOMBIE_WITCH(2);

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

    private static final int SPEED = 4;
    public Zombie(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight, int value, String img, FLY_ABILITY flyAbility, ZOMBIE_TYPE zombieType) {
        //TODO: set targetX and targetY to correct parameter
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, value, SPEED, img, flyAbility, Animator.State.WALK);
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
    public void paintComponent(Graphics g) {
        this.getAnimator().paint(getWalkingDir(), painter().left(), painter().top(), painter().right(), painter().bottom(), g);
    }

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
    }


    public void isSeeing(Human human){
        if(this.detectRange().overlap(human.painter())){
            this.setAttackTarget(human);
        }
    }
}
