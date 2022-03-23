package company.gameobj.creature.zombies;

import company.gametest9th.utils.Path;

import java.awt.*;

public class ZombieTypeII extends Zombie {
    private static final int painterWidth = 50;
    private static final int painterHeight = 50;
    private static final int colliderWidth = 50;
    private static final int colliderHeight = 50;

    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieTypeII(int x, int y) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, 13, new Path().img().zombies().zombieTypeII(), FLY_ABILITY.CANNOT_FLY);
    }

    public ZombieTypeII() {
        super(-1000, -1000, painterWidth, painterHeight, colliderWidth, colliderHeight, 13, new Path().img().zombies().zombieTypeII(), FLY_ABILITY.CANNOT_FLY);
    }


    @Override
    public int currentRoundCount(int round) {
        return (round * 3) / 12;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.getImg(), this.painter().left(), this.painter().top(), null);
    }

    @Override
    public void update() {

    }
}