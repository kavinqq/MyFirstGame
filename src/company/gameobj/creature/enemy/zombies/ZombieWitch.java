package company.gameobj.creature.enemy.zombies;

import company.gametest9th.utils.Path;

import java.awt.*;

public class ZombieWitch extends Zombie {
    private static final int painterWidth = 50;
    private static final int painterHeight = 50;
    private static final int colliderWidth = 50;
    private static final int colliderHeight = 50;

    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieWitch(int x, int y) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, 25, new Path().img().zombies().zombieWitch(), FLY_ABILITY.CANNOT_FLY, ZOMBIE_TYPE.ZOMBIE_WITCH);
    }

    public ZombieWitch() {
        super(-1000, -1000, painterWidth, painterHeight, colliderWidth, colliderHeight, 25, new Path().img().zombies().zombieWitch(), FLY_ABILITY.CANNOT_FLY, ZOMBIE_TYPE.ZOMBIE_WITCH);
    }


    @Override
    public int currentRoundCount(int round) {
        return round / 10;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.getImg(), this.painter().left(), this.painter().top(), null);
    }

    @Override
    public void update() {

    }
}
