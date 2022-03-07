package human;

/**
 * @author Lillian
 * @Date 2022/3/7
 * @Description
 */
public abstract class Soldier extends Human{
    public enum MILITARY_TYPE {
        ARMY, AIR_FORCE;
    }
    private MILITARY_TYPE militaryType;
    public Soldier(){
        this.setType(Human.TYPE.SOLDIER);
    }
    public void setMilitaryType(MILITARY_TYPE militaryType){
        this.militaryType = militaryType;
    }
}
