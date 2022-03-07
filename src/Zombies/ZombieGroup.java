package Zombies;

import java.util.HashMap;
import java.util.Map;

/**
 * 殭屍族群類
 */
public class ZombieGroup {

    private final Map<Zombie,Integer> zombies;
    private final ZombieBigger zombieBigger = new ZombieBigger();
    private final ZombieKing zombieKing = new ZombieKing();
    private final ZombieLichKing zombieLichKing = new ZombieLichKing();
    private final ZombieNormal zombieNormal = new ZombieNormal();
    private final ZombieTypeI zombieTypeI = new ZombieTypeI();
    private final ZombieTypeII zombieTypeII = new ZombieTypeII();
    private final ZombieFlying zombieFlying = new ZombieFlying();
    private final ZombieFlyingBigger zombieFlyingBigger = new ZombieFlyingBigger();

    public ZombieGroup() {
        zombies = new HashMap<>();
        this.zombies.put(zombieBigger,0);
        this.zombies.put(zombieKing,0);
        this.zombies.put(zombieLichKing,0);
        this.zombies.put(zombieNormal,0);
        this.zombies.put(zombieTypeI,0);
        this.zombies.put(zombieTypeII,0);
        this.zombies.put(zombieFlying,0);
        this.zombies.put(zombieFlyingBigger,0);
    }

    /**
     * 殭屍數量增長
     * @param gameTime 現在遊戲時間
     */
    public void groupGrowUp(int gameTime){
        for(Map.Entry<Zombie,Integer> entry : zombies.entrySet()){
            Zombie currentType = entry.getKey();
            //該殭屍種類數量增加
            int currentNum = currentType.currentTimeCount(gameTime);
            //更新殭屍數量
            if(currentType instanceof ZombieBigger){
                zombies.put(zombieBigger,currentNum);
            }else if(currentType instanceof ZombieKing){
                zombies.put(zombieKing,currentNum);
            }else if(currentType instanceof ZombieLichKing){
                zombies.put(zombieLichKing,currentNum);
            }else if(currentType instanceof ZombieNormal){
                zombies.put(zombieNormal,currentNum);
            }else if(currentType instanceof ZombieTypeI){
                zombies.put(zombieTypeI,currentNum);
            }else if(currentType instanceof ZombieTypeII){
                zombies.put(zombieTypeII,currentNum);
            }else if(currentType instanceof ZombieFlying){
                zombies.put(zombieFlying,currentNum);
            }else if(currentType instanceof ZombieFlyingBigger){
                zombies.put(zombieFlyingBigger,currentNum);
            }
        }
    }

//    /**
//     * 殭屍族群 map
//     * @return 殭屍族群map
//     */
//    public Map<Zombie, Integer> getZombies() {
//        return zombies;
//    }
}
