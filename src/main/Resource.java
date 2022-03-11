package main;

/**
 * 控管所有物資 並不是單一物資屬性  因此用static
 */
public class Resource {
    /**
     * 目前總共有多少木材和鋼鐵
     */
    private static int totalWood;
    private static int totalSteel;
    private static int totalGas;
    /**
     * 目前 木材和鋼鐵 的採集效率 續升級直接改此  (預設 市民可採 (3 木/時) 或 (1 鐵/時) )
     */
    public static int DEFAULT_WOOD_SPEED = 3;
    public static int DEFAULT_STEEL_SPEED = 1;

    /**
     * Resource建構子 用來把 一開始的 木/鋼鐵 資源歸0
     */
    public Resource() {
        totalSteel = 0;
        totalWood = 0;
        totalGas = 0;
    }

    /**
     * 增加多少木頭
     *
     * @param woodNewNum 新增的木頭量
     */
    public void addWood(int woodNewNum) {
        totalWood += woodNewNum;
    }

    /**
     * 增加多少鋼鐵
     *
     * @param steelNewNum 新增的鋼鐵量
     */
    public void addSteel(int steelNewNum) {   //"採集"鐵   每次放數各數為 採鐵效率數量  <由市民使用此動作>
        totalSteel += steelNewNum;
    }

    /**
     * 增加多少瓦斯
     *
     * @param gasNewNum 新增的瓦斯量
     */
    public void addGas(int gasNewNum) {   //"採集"鐵   每次放數各數為 採鐵效率數量  <由市民使用此動作>
        totalGas += gasNewNum;
    }

    /**
     * 建築物 建造/升級 消耗多少木頭
     *
     * @param woodQuantity 木頭數量
     */
    public void takeWood(int woodQuantity) {
        totalWood -= woodQuantity;
        if (totalGas <= 0) {
            totalWood = 0;
        }
    }

    /**
     * 建築物 建造/升級 消耗多少鋼鐵
     *
     * @param steelQuantity 鋼鐵數量
     */
    public void takeSteel(int steelQuantity) {
        totalSteel -= steelQuantity;
        if (totalSteel <= 0) {
            totalSteel = 0;
        }
    }

    /**
     * 建築物 建造/升級 消耗多少瓦斯
     *
     * @param gasQuantity 瓦斯數量
     */
    public void takeGas(int gasQuantity) {
        totalGas -= gasQuantity;
        if (totalGas <= 0) {
            totalGas = 0;
        }
    }

    /**
     * 取得目前木材總數
     *
     * @return 目前 木材 總數
     */
    public int getTotalWood() {
        return totalWood;
    }

    /**
     * 取得目前 鐵 總數
     *
     * @return 目前 鐵 總數
     */
    public int getTotalSteel() {
        return totalSteel;
    }

    /**
     * 取得目前 瓦斯 總數
     *
     * @return 目前 瓦斯 總數
     */
    public int getTotalGas() {
        return totalGas;
    }

    /**
     * 升級採木效率 每次 呼叫升級 +1
     */
    public void upgradeWoodSpeed(int speedChange) {
        DEFAULT_WOOD_SPEED += speedChange;
    }

    /**
     * 升級採鐵效率 每次 呼叫升級 +1
     */
    public void upgradeSteelSpeed(int speedChange) {
        DEFAULT_STEEL_SPEED += speedChange;
    }


}


