package company.gameObj.creature.Zombies;

import company.gametest9th.utils.Path;

public class ZombieBig extends Zombie {
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieBig(){
        super(7, new Path().img().zombies().zombieBig(), FLY_ABILITY.CANNOT_FLY);
    }


    @Override
    public int currentRoundCount(int round) {
        return (round*5)/12;
    }
}