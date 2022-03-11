package creature.Zombies;

public class ZombieFlyingBigger extends Zombie implements Flyable {

    public ZombieFlyingBigger(){
        super(4, FLYABILITY.CAN_FLY);
    }


    @Override
    public int currentRoundCount(int round) {
        if(round<7){
            return 0;
        }
        return (round-7)/2;
    }
}
