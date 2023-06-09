package creature.Zombies;

import creature.Creature;

public abstract class Zombie extends Creature {
    /**
     * 殭屍的建構子
     * @param value 該殭屍的預設攻擊力
     */
    public Zombie(int value, String img, FLY_ABILITY flyAbility){
        super(value, img, flyAbility);
    }

    /**
     * 殭屍數量成長
     * @param round 現在第幾輪
     * @return 當前此類殭屍的數量
     */
    public abstract int currentRoundCount(int round);



}
