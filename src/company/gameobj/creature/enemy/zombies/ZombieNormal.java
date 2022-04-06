package company.gameobj.creature.enemy.zombies;

import company.gametest9th.utils.Path;

import java.awt.*;

public class ZombieNormal extends Zombie {
    private static final int painterWidth = 50;
    private static final int painterHeight = 50;
    private static final int colliderWidth = 50;
    private static final int colliderHeight = 50;

    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieNormal(int x, int y) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, 5, new Path().img().zombies().zombieNormal(), FLY_ABILITY.CANNOT_FLY, ZOMBIE_TYPE.ZOMBIE_NORMAL);
    }

    public ZombieNormal() {
        super( painterWidth, painterHeight, colliderWidth, colliderHeight, 5, new Path().img().zombies().zombieNormal(), FLY_ABILITY.CANNOT_FLY, ZOMBIE_TYPE.ZOMBIE_NORMAL);
    }


    @Override
    public int currentRoundCount(int round) {
        return round * 3;
    }

}
