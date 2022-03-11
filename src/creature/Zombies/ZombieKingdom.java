package creature.Zombies;

import java.util.HashMap;
import java.util.Map;

/**
 * 殭屍族群類
 */
public class ZombieKingdom {

    private int zombieTime;
    private int attackRound;
    private ZombieTroop zombieTroop;

    /**
     * 攻擊頻率
     */
    private static final int attackRate = 16;
    /**
     * 儲存殭屍數量的map
     */
    private final Map<Zombie, Integer> zombies;
    /**
     * 各種殭屍
     */
    private final ZombieBigger zombieBigger = new ZombieBigger();
    private final ZombieKing zombieKing = new ZombieKing();
    private final ZombieLichKing zombieLichKing = new ZombieLichKing();
    private final ZombieNormal zombieNormal = new ZombieNormal();
    private final ZombieTypeI zombieTypeI = new ZombieTypeI();
    private final ZombieTypeII zombieTypeII = new ZombieTypeII();
    private final ZombieFlying zombieFlying = new ZombieFlying();
    private final ZombieFlyingBigger zombieFlyingBigger = new ZombieFlyingBigger();

    public ZombieKingdom() {
        this.zombieTime = 0;
        this.attackRound = 0;
        zombies = new HashMap<>();
        this.zombies.put(zombieBigger, 0);
        this.zombies.put(zombieKing, 0);
        this.zombies.put(zombieLichKing, 0);
        this.zombies.put(zombieNormal, 0);
        this.zombies.put(zombieTypeI, 0);
        this.zombies.put(zombieTypeII, 0);
        this.zombies.put(zombieFlying, 0);
        this.zombies.put(zombieFlyingBigger, 0);
    }

    /**
     * 殭屍數量增長
     */
    public void troopLevelUp() {
        this.attackRound++;
        for (Map.Entry<Zombie, Integer> entry : zombies.entrySet()) {
            Zombie currentType = entry.getKey();
            //該殭屍種類數量增加
            int currentNum = currentType.currentRoundCount(this.attackRound);
            //更新殭屍數量
            if (currentType instanceof ZombieBigger) {
                zombies.put(zombieBigger, currentNum);
            } else if (currentType instanceof ZombieKing) {
                zombies.put(zombieKing, currentNum);
            } else if (currentType instanceof ZombieLichKing) {
                zombies.put(zombieLichKing, currentNum);
            } else if (currentType instanceof ZombieNormal) {
                zombies.put(zombieNormal, currentNum);
            } else if (currentType instanceof ZombieTypeI) {
                zombies.put(zombieTypeI, currentNum);
            } else if (currentType instanceof ZombieTypeII) {
                zombies.put(zombieTypeII, currentNum);
            } else if (currentType instanceof ZombieFlying) {
                zombies.put(zombieFlying, currentNum);
            } else if (currentType instanceof ZombieFlyingBigger) {
                zombies.put(zombieFlyingBigger, currentNum);
            }
        }
        this.zombieTroop = new ZombieTroop();
    }

    /**
     * 是否要進攻了
     *
     * @return 是否要進攻了
     */
    public boolean isAttacking() {
        return (this.zombieTime % 16 == 0);
    }

    /**
     * 計算僵屍的時間往前
     */
    public void timePass() {
        this.zombieTime++;
        if (this.zombieTime % attackRate == 0) {
            this.troopLevelUp();
        }
    }

    /**
     * 殭屍族群
     */
    public class ZombieTroop {
        /**
         * 地面攻擊力
         */
        private int landAttack;
        /**
         * 空中攻擊力
         */
        private int airAttack;

        public ZombieTroop() {
            int number;
            this.landAttack = 0;
            this.airAttack = 0;
            Zombie zombieGenre;
            for (Map.Entry<Zombie, Integer> entry : zombies.entrySet()) {
                zombieGenre = entry.getKey();
                number = entry.getValue();
                if (zombieGenre.isFlyable()) {
                    this.airAttack += (number * zombieGenre.getAttack());
                } else {
                    this.landAttack += (number * zombieGenre.getAttack());
                }
            }
        }

        public int getLandAttack() {
            return this.landAttack;
        }

        public int getAirAttack() {
            return this.airAttack;
        }

        @Override
        public String toString() {
            return "空中攻擊力：" + airAttack +
                    ", 地面攻擊力：" + landAttack;
        }
    }

    /**
     * 回傳殭屍族群
     * @return 殭屍族群
     */
    public ZombieTroop getZombieTroop() {
        return this.zombieTroop;
    }
}
