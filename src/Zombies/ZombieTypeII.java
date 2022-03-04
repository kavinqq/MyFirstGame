package Zombies;

public class ZombieTypeII extends Zombie{
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieTypeII(){
        super(13);
    }

    /**
     * @param round 這是第幾波的進攻
     * @return 回傳這波 II型變異體 的總攻擊力
     */
    @Override
    public int getAttack(double round){
        return (int)(round  / 10d * 3d) * super.getAttack();
    }
}
