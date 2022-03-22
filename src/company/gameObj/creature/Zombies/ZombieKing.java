package company.gameObj.creature.Zombies;

import company.gametest9th.utils.Path;

public class ZombieKing extends Zombie {
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieKing() {
        super(17, new Path().img().zombies().zombieKing(), FLY_ABILITY.CANNOT_FLY);
    }


    @Override
    public int currentRoundCount(int round) {
        return (round * 2 )/12 ;
    }
}
