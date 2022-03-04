package Zombies;

public abstract class Zombie {
    /**
     * 由於目前 遊戲只有很單純的規定 殭屍 有攻擊力這件事 只設定這樣
     */
    private final int attack;

    /**
     * 殭屍的建構子
     * @param attack 該殭屍的預設攻擊力
     */
    public Zombie(int attack){
        this.attack = attack;
    }

    /**
     * 得到該殭屍的攻擊力
     * @return 該殭屍的攻擊力
     */
    public int getAttack(){
        return attack;
    }

    /**
     * 規定每一個子類別殭屍 要有一個回傳該殭屍攻擊力總和的方法 (該怎麼計算分別設定)
     * @param round 這是第幾波的進攻
     * @return 這個殭屍類別這一波的總攻擊
     */
    public abstract  int getAttack(double round);
}
