package company.gameobj.creature.enemy.zombies;

import company.gametest9th.utils.Path;

import java.awt.*;

public class ZombieFlyingBig extends Zombie {
    private static final int painterWidth = 50;
    private static final int painterHeight = 50;
    private static final int colliderWidth = 50;
    private static final int colliderHeight = 50;

    public ZombieFlyingBig(int x, int y) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, 4, new Path().img().zombies().zombieFlyingBig(), FLY_ABILITY.CAN_FLY, ZOMBIE_TYPE.ZOMBIE_FLYING_BIG);
    }

    public ZombieFlyingBig() {
        super(-1000, -1000, painterWidth, painterHeight, colliderWidth, colliderHeight, 2, new Path().img().zombies().zombieFlyingBig(), FLY_ABILITY.CAN_FLY, ZOMBIE_TYPE.ZOMBIE_FLYING_BIG);
    }

    @Override
    public int currentRoundCount(int round) {
        if (round < 7) {
            return 0;
        }
        return (round - 7) / 2;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.getImg(), this.painter().left(), this.painter().top(), null);
    }

    @Override
    public void update() {

    }
}
