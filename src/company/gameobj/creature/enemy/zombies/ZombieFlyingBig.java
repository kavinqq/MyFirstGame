package company.gameobj.creature.enemy.zombies;

import company.gametest9th.utils.Path;

import java.awt.*;

public class ZombieFlyingBig extends Zombie {
    private static final int painterWidth = 64;
    private static final int painterHeight = 64;
    private static final int colliderWidth = 256;
    private static final int colliderHeight = 256;

    public ZombieFlyingBig(int x, int y) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, 5, new Path().img().zombies().zombieFlyingBig(), FLY_ABILITY.CAN_FLY, ZOMBIE_TYPE.ZOMBIE_FLYING_BIG);
        setMaxHp(100);
    }

    public ZombieFlyingBig() {
        super(painterWidth, painterHeight, colliderWidth, colliderHeight, 5, new Path().img().zombies().zombieFlyingBig(), FLY_ABILITY.CAN_FLY, ZOMBIE_TYPE.ZOMBIE_FLYING_BIG);
        setMaxHp(400);
    }

    @Override
    public int currentRoundCount(int round) {
        if (round < 7) {
            return 0;
        }
        return (round - 7) / 2;
    }
}
