package creature.Zombies;

import company.gametest9th.utils.Path;

public class ZombieNormal extends Zombie {
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieNormal() {
        super(5, new Path().img().zombies().zombieNormal(), FLY_ABILITY.CANNOT_FLY);
    }


    @Override
    public int currentRoundCount(int round) {
        return round * 3;
    }
}
