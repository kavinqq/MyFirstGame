package creature.Zombies;

import company.gametest9th.utils.Path;

public class ZombieFlyingBig extends Zombie {

    public ZombieFlyingBig() {
        super(4, new Path().img().zombies().zombieFlyingBig(), FLY_ABILITY.CAN_FLY);
    }

        @Override
    public int currentRoundCount(int round) {
        if(round<7){
            return 0;
        }
        return (round-7)/2;
    }
}
