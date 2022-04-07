package company.gameobj.creature.enemy.zombies;

import company.gametest9th.utils.Path;

import java.awt.*;

/**
 * 飛行殭屍
 */
public class ZombieFlying extends Zombie {
    private static final int painterWidth = 64;
    private static final int painterHeight = 64;
    private static final int colliderWidth = 256;
    private static final int colliderHeight = 256;

    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieFlying(int x, int y) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, 2, new Path().img().zombies().zombieFlying(), FLY_ABILITY.CAN_FLY, ZOMBIE_TYPE.ZOMBIE_FLYING);
    }

    public ZombieFlying() {
        super( painterWidth, painterHeight, colliderWidth, colliderHeight, 2, new Path().img().zombies().zombieFlying(), FLY_ABILITY.CAN_FLY, ZOMBIE_TYPE.ZOMBIE_FLYING);
    }

    @Override
    public int currentRoundCount(int round) {
        if (round < 7) {
            return 0;
        }
        return round - 7;

    }
}
