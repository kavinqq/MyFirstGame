package company.gameobj.creature.enemy.zombies;

import company.gametest9th.utils.Path;

import java.awt.*;

public class ZombieBig extends Zombie {
    private static final int painterWidth = 64;
    private static final int painterHeight = 64;
    private static final int colliderWidth = 64;
    private static final int colliderHeight = 64;
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieBig(int x, int y){
        super(x,y, painterWidth, painterHeight, colliderWidth, colliderHeight,7, new Path().img().zombies().zombieBig(), FLY_ABILITY.CANNOT_FLY, ZOMBIE_TYPE.ZOMBIE_BIG);
    }

    public ZombieBig(){
        super(painterWidth, painterHeight, colliderWidth, colliderHeight,7, new Path().img().zombies().zombieBig(), FLY_ABILITY.CANNOT_FLY, ZOMBIE_TYPE.ZOMBIE_BIG);
    }


    @Override
    public int currentRoundCount(int round) {
        return (round*5)/12;
    }


    @Override
    public void update() {

    }
}