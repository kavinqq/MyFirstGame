package company.gameobj.creature.enemy.zombies;

import company.Global;
import company.gametest9th.utils.GameKernel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 殭屍族群類
 */
public class ZombieKingdom implements GameKernel.GameInterface {

    private int zombieTime;
    private int attackRound;
    private ZombieTroop zombieTroop;

    /**
     * 攻擊頻率
     */
    private static final int attackRate = 1200;

    /**
     * 儲存殭屍數量的map
     */
    private final Map<Zombie, ArrayList<? extends Zombie>> zombies;

    /**
     * 各種殭屍
     */
    private final ZombieBig zombieBig = new ZombieBig();
    private final ZombieKing zombieKing = new ZombieKing();
    private final ZombieWitch zombieWitch = new ZombieWitch();
    private final ZombieNormal zombieNormal = new ZombieNormal();
    private final ZombieTypeI zombieTypeI = new ZombieTypeI();
    private final ZombieTypeII zombieTypeII = new ZombieTypeII();
    private final ZombieFlying zombieFlying = new ZombieFlying();
    private final ZombieFlyingBig zombieFlyingBig = new ZombieFlyingBig();

    public ZombieKingdom() {
        this.zombieTime = 0;
        this.attackRound = 1;
        zombies = new HashMap<>();
        this.zombies.put(zombieBig, new ArrayList<ZombieBig>());
        this.zombies.put(zombieKing, new ArrayList<ZombieKing>());
        this.zombies.put(zombieWitch, new ArrayList<ZombieWitch>());
        this.zombies.put(zombieNormal, new ArrayList<ZombieNormal>());
        this.zombies.put(zombieTypeI, new ArrayList<ZombieTypeI>());
        this.zombies.put(zombieTypeII, new ArrayList<ZombieTypeII>());
        this.zombies.put(zombieFlying, new ArrayList<ZombieFlying>());
        this.zombies.put(zombieFlyingBig, new ArrayList<ZombieFlyingBig>());
        this.zombieTroop = new ZombieTroop();
    }

    /**
     * 殭屍數量增長
     */
    public void troopLevelUp() {

        this.attackRound++;

        // 最多生成到第六波
        if(attackRound > Global.TOTAL_ZOMBIE_ROUND){
            return;
        }

        for (Map.Entry<Zombie, ArrayList<? extends Zombie>> entry : zombies.entrySet()) {
            Zombie currentType = entry.getKey();
            //該殭屍種類數量增加
            int currentNum = currentType.currentRoundCount(this.attackRound);
            //更新殭屍數量
            if (currentType instanceof ZombieBig) {
                ArrayList<ZombieBig> arr = new ArrayList<ZombieBig>();
                for (int i = 0; i < currentNum; i++) {
                    arr.add(new ZombieBig());
                }
                zombies.put(zombieBig, arr);
                //zombies.put(zombieBig, currentNum);
            } else if (currentType instanceof ZombieKing) {
                ArrayList<ZombieKing> arr = new ArrayList<ZombieKing>();
                for (int i = 0; i < currentNum; i++) {
                    arr.add(new ZombieKing());
                }
                zombies.put(zombieKing, arr);
                //zombies.put(zombieKing, currentNum);
            } else if (currentType instanceof ZombieWitch) {
                ArrayList<ZombieWitch> arr = new ArrayList<ZombieWitch>();
                for (int i = 0; i < currentNum; i++) {
                    arr.add(new ZombieWitch());
                }
                zombies.put(zombieWitch, arr);
                //zombies.put(zombieWitch, currentNum);
            } else if (currentType instanceof ZombieNormal) {
                ArrayList<ZombieNormal> arr = new ArrayList<ZombieNormal>();
                for (int i = 0; i < currentNum; i++) {
                    arr.add(new ZombieNormal());
                }
                zombies.put(zombieNormal, arr);

            } else if (currentType instanceof ZombieTypeI) {
                ArrayList<ZombieTypeI> arr = new ArrayList<ZombieTypeI>();
                for (int i = 0; i < currentNum; i++) {
                    arr.add(new ZombieTypeI());
                }
                zombies.put(zombieTypeI, arr);

            } else if (currentType instanceof ZombieTypeII) {
                ArrayList<ZombieTypeII> arr = new ArrayList<ZombieTypeII>();
                for (int i = 0; i < currentNum; i++) {
                    arr.add(new ZombieTypeII());
                }
                zombies.put(zombieTypeII, arr);

            } else if (currentType instanceof ZombieFlying) {
                ArrayList<ZombieFlying> arr = new ArrayList<ZombieFlying>();
                for (int i = 0; i < currentNum; i++) {
                    arr.add(new ZombieFlying());
                }
                zombies.put(zombieFlying, arr);

            } else if (currentType instanceof ZombieFlyingBig) {
                ArrayList<ZombieFlyingBig> arr = new ArrayList<ZombieFlyingBig>();
                for (int i = 0; i < currentNum; i++) {
                    arr.add(new ZombieFlyingBig());
                }
                zombies.put(zombieFlyingBig, arr);

            }
        }
    }

    /**
     * 計算僵屍的時間往前
     */
    public void timePass() {
        this.zombieTime++;
        if (this.zombieTime % attackRate == 0) {
            this.troopLevelUp();
            this.zombieTroop.update();
        }
    }


    @Override
    public void paint(Graphics g) {

        for (Zombie zombie : zombieTroop.getLandTroop()) {
            zombie.paint(g);
        }
        for (Zombie zombie : zombieTroop.getAirTroop()) {
            zombie.paint(g);
        }
    }

    public void cameraMove() {

        for (int i = 0; i < zombieTroop.getLandTroop().size(); i++) {
            zombieTroop.getLandTroop().get(i).cameraMove();
        }

        for (int i = 0; i < zombieTroop.getAirTroop().size(); i++) {
            zombieTroop.getAirTroop().get(i).cameraMove();
        }

    }

    public void resetObjectXY() {

        for (int i = 0; i < zombieTroop.getLandTroop().size(); i++) {
            zombieTroop.getLandTroop().get(i).resetObjectXY();
        }

        for (int i = 0; i < zombieTroop.getAirTroop().size(); i++) {
            zombieTroop.getAirTroop().get(i).resetObjectXY();
        }
    }

    @Override
    public void update() {

        for (int i = 0; i < zombieTroop.getLandTroop().size(); i++) {
            if (!zombieTroop.getLandTroop().get(i).isAlive()) {
                zombieTroop.getLandTroop().remove(i);
                i--;
            } else {
                zombieTroop.getLandTroop().get(i).update();
            }
        }
        for (int i = 0; i < zombieTroop.getAirTroop().size(); i++) {
            if (!zombieTroop.getAirTroop().get(i).isAlive()) {
                zombieTroop.getAirTroop().remove(i);
                i--;
            } else {
                zombieTroop.getAirTroop().get(i).update();
            }
        }

        this.zombieTime++;
        if (this.zombieTime % attackRate == 0) {
            this.troopLevelUp();
            this.zombieTroop.update();
        }
    }

    /**
     * 殭屍族群ga
     */
    public class ZombieTroop {
        /**
         * 地面攻擊力
         */
        private ArrayList<Zombie> landTroop;
        /**
         * 空中攻擊力
         */
        private ArrayList<Zombie> airTroop;

        public ZombieTroop() {

            landTroop = new ArrayList<Zombie>();

            airTroop = new ArrayList<Zombie>();

            update();

        }

        public void update() {
            Zombie zombieGenre;
            //TODO : delete comment
            for (Map.Entry<Zombie, ArrayList<? extends Zombie>> entry : zombies.entrySet()) {
                zombieGenre = entry.getKey();
                if (zombieGenre.isFlyable()) {
                    if (!entry.getValue().isEmpty()) {
                        airTroop.addAll(entry.getValue());
                    }
                } else {
                    if (!entry.getValue().isEmpty()) {
                        landTroop.addAll(entry.getValue());
                    }
                }
            }
        }

        public int getLandAttack() {
            return 0;
        }

        public int getAirAttack() {
            return 0;
        }

        public ArrayList<Zombie> getLandTroop() {
            return this.landTroop;
        }

        public ArrayList<Zombie> getAirTroop() {
            return this.airTroop;
        }

    }

    /**
     * 回傳殭屍族群
     *
     * @return 殭屍族群
     */
    public ZombieTroop getZombieTroop() {
        return this.zombieTroop;
    }

    /**
     * 回傳目前殭屍波數
     *
     * @return 目前殭屍波數
     */
    public int getAttackRound() {
        return attackRound;
    }


    /**
     * 是否清光殭屍了
     *
     * @return 清光了 => true  還有剩 => false
     */
    public boolean isClear(){
        return zombieTroop.getLandTroop().isEmpty() && zombieTroop.getAirTroop().isEmpty();
    }
}
