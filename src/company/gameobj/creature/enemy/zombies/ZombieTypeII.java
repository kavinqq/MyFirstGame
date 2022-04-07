package company.gameobj.creature.enemy.zombies;

import company.gametest9th.utils.Path;

import java.awt.*;

public class ZombieTypeII extends Zombie {
    private static final int painterWidth = 64;
    private static final int painterHeight = 64;
    private static final int colliderWidth = 256;
    private static final int colliderHeight = 256;

    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieTypeII(int x, int y) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, 13, new Path().img().zombies().zombieTypeII(), FLY_ABILITY.CANNOT_FLY, ZOMBIE_TYPE.ZOMBIE_TYPE_II);
    }

    public ZombieTypeII() {
        super( painterWidth, painterHeight, colliderWidth, colliderHeight, 13, new Path().img().zombies().zombieTypeII(), FLY_ABILITY.CANNOT_FLY, ZOMBIE_TYPE.ZOMBIE_TYPE_II);
    }


    @Override
    public int currentRoundCount(int round) {
        return (round * 3) / 12;
    }
}
