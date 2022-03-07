package human;

/**
 * @author Lillian
 * @Date 2022/3/7
 * @Description
 */
public class Airman extends Soldier{
    public Airman(){
        this.setValue(2);
        this.setIsAirForce();
        this.setMilitaryType(MILITARY_TYPE.AIR_FORCE);
    }
}
