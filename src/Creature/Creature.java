package Creature;

public abstract class Creature {
    public enum FLYABILITY{
        CAN_FLY, CANNOT_FLY;
    }
    private FLYABILITY flyability;

    public void setFlyability(FLYABILITY flyability) {
        this.flyability = flyability;
    }

    public boolean isFlyable(){
        return (this.flyability==FLYABILITY.CAN_FLY);
    }
}
