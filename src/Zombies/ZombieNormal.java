package Zombies;

public class ZombieNormal extends Zombie {
    /**
     * 這種殭屍的預設攻擊力
     */
    public ZombieNormal(){
        super(5);
    }

    /**
     * @param round 這是第幾波的進攻
     * @return 回傳這波 殭屍 的總攻擊力
     */

    @Override
    public int getAttack(double round){
        return (int)(round * 3 * super.getAttack());
    }
}
