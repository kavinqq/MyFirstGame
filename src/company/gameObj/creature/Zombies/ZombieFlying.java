package company.gameObj.creature.Zombies;

import company.gametest9th.utils.Path;

/**
 * 飛行殭屍
 */
public class ZombieFlying extends Zombie {

    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieFlying() {
        super(2, new Path().img().zombies().zombieFlying(), FLY_ABILITY.CAN_FLY);
    }

    @Override
    public int currentRoundCount(int round) {
        if (round < 7) {
            return 0;
        }
        return round - 7;

    }
}
