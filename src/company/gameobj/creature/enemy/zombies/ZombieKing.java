package company.gameobj.creature.enemy.zombies;

import company.gametest9th.utils.Path;

import java.awt.*;

public class ZombieKing extends Zombie {
    private static final int painterWidth = 128;
    private static final int painterHeight = 128;
    private static final int colliderWidth = 400;
    private static final int colliderHeight = 400;

    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieKing(int x, int y) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, 2, new Path().img().zombies().zombieKing(), FLY_ABILITY.CANNOT_FLY, ZOMBIE_TYPE.ZOMBIE_KING);
        setMaxHp(1000);
    }

    public ZombieKing() {
        super( painterWidth, painterHeight, colliderWidth, colliderHeight, 2, new Path().img().zombies().zombieKing(), FLY_ABILITY.CANNOT_FLY, ZOMBIE_TYPE.ZOMBIE_KING);
        setMaxHp(1000);
    }


    @Override
    public int currentRoundCount(int round) {
        return (round * 2) / 12;
    }
}
