package company.gameobj.creature.zombies;

import company.gameobj.creature.Creature;

public abstract class Zombie extends Creature {
    /**
     * 殭屍的建構子
     *
     * @param value 該殭屍的預設攻擊力
     */
    //TODO: allow each type of zombie to set their own speed

    private static final int SPEED = 4;
    public Zombie(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight, int value, String img, FLY_ABILITY flyAbility) {
        super(x, y, painterWidth, painterHeight, colliderWidth, colliderHeight, value, SPEED, img, flyAbility);
    }

    /**
     * 殭屍數量成長
     *
     * @param round 現在第幾輪
     * @return 當前此類殭屍的數量
     */
    public abstract int currentRoundCount(int round);

    @Override
    public void walk(){

    }
}
