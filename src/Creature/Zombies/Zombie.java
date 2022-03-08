package Creature.Zombies;

import Creature.Creature;

public abstract class Zombie extends Creature {
    /**
     * 由於目前 遊戲只有很單純的規定 殭屍 有攻擊力這件事 只設定這樣
     */
    private final int attack;

    /**
     * 殭屍的建構子
     * @param attack 該殭屍的預設攻擊力
     */
    public Zombie(int attack, FLYABILITY flyability){
        this.attack = attack;
        this.setFlyability(flyability);
    }

    /**
     * 得到該殭屍的攻擊力
     * @return 該殭屍的攻擊力
     */
    public int getAttack(){
        return attack;
    }

    /**
     * 殭屍數量成長
     * @param time 現在遊戲時間
     * @return 當前此類殭屍的數量
     */
    public abstract int currentTimeCount(int time);



}
