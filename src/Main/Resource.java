package Main;

/**
 * 控管所有物資 並不是單一物資屬性  因此用static
 *
 */
public class Resource {
    /**
     * 目前總共有多少木材和鋼鐵
     */
    private static int totalWood;
    private static int totalSteel;
    /**
     * 目前 木材和鋼鐵 的採集效率 續升級直接改此  (預設 市民可採 (3 木/時) 或 (1 鐵/時) )
     */
    private static int woodSpeed = 3;
    private static int steelSpeed = 1;

    /**
     * Resource建構子 用來把 一開始的 木/鋼鐵 資源歸0
     */
    public Resource(){
        totalSteel = 0;
        totalWood = 0;
    }

    /**
     * 每採集一次 增加多少木頭
     */
    public void addWood(){  //"採集"木材  每次放入各數為 採木效率數量  <由市民使用此動作>
        totalWood += woodSpeed;
    }

    /**
     * 每採集一次 增加多少鋼鐵
     */
    public void addSteel(){   //"採集"鐵   每次放數各數為 採鐵效率數量  <由市民使用此動作>
        totalSteel += steelSpeed;
    }

    /**
     * 建築物 建造/升級 消耗多少木頭
     * @param woodQuantity 木頭數量
     */
    public void takeWood(int woodQuantity){
        if(totalWood >= woodQuantity){
            totalWood -= woodQuantity;
        }
    }

    /**
     *  建築物 建造/升級 消耗多少鋼鐵
     * @param steelQuantity 鋼鐵數量
     */
    public void takeSteel(int steelQuantity){
        if(totalSteel >= steelQuantity){
            totalSteel -= steelQuantity;
        }
    }

    /**
     * 取得目前木材總數
     * @return 目前 木材 總數
     */
    public int getTotalWood(){
        return totalWood;
    }

    /**
     * 取得目前 鐵 總數
     * @return 目前 鐵 總數
     */
    public int getTotalSteel(){
        return totalSteel;
    }

    /**
     * 升級採木效率 每次 呼叫升級 +1
     */
    public void upgradeWoodSpeed(int speedChange){
        woodSpeed += speedChange;
    }

    /**
     * 升級採鐵效率 每次 呼叫升級 +1
     */
    public void upgradeSteelSpeed(int speedChange){
        steelSpeed += speedChange;
    }

}


