package company.gameObj.creature.Zombies;

import company.gametest9th.utils.Path;

import java.awt.*;

/**
 * 飛行殭屍
 */
public class ZombieFlying extends Zombie {
    private static final int painterWidth = 50;
    private static final int painterHeight = 50;
    private static final int colliderWidth = 50;
    private static final int colliderHeight = 50;

    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieFlying(int x, int y) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, 2, new Path().img().zombies().zombieFlying(), FLY_ABILITY.CAN_FLY);
    }

    public ZombieFlying() {
        super(-1000, -1000, painterWidth, painterHeight, colliderWidth, colliderHeight, 2, new Path().img().zombies().zombieFlying(), FLY_ABILITY.CAN_FLY);
    }

    @Override
    public int currentRoundCount(int round) {
        if (round < 7) {
            return 0;
        }
        return round - 7;

    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.getImg(), this.painter().left(), this.painter().top(), null);
    }

    @Override
    public void update() {

    }
}
