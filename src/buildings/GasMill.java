package buildings;

/**
 * @author 瓦斯場
 * @Date 2022/3/7
 * @Description
 */
public class GasMill extends Building{
    /**
     * 父類建構子
     * id 建築物ID  (1.房屋 2.研究所 3.軍營 4.伐木場 5.煉鋼廠 6.兵工廠 7.瓦斯場 8.飛機工場)
     * name 建築物名稱
     * buildTime 建築物持續時間
     * upgradeTime 建築物升級時間
     * level 建築物等級 預設-1  建好 0 升級過 1~2,147,483,647
     * techLevelNeed 需要文明等級
     * readyToUpgrade 建築物是否在建築，建築中 -> true
     * hp 建築物血量
     * woodCostCreate 創建所需要的木頭量
     * steelCostCreate 創建所需要的鋼鐵量
     * woodCostLevelUp 升級所需要的木頭量
     * steelCostLevelUp 升級所需要的鋼鐵量
     * isWorking 建築物是否在運轉
     * gasCostCreate 創建所需要的瓦斯量
     * gasCostLevelUp 升級所需要的瓦斯量
     */
    public GasMill() {
        super(7, "瓦斯場",  1, 30, 0, 2, 3, 20,
                15, 5, 40, 20,0,0);
    }

    /**
     * 生產的瓦斯量
     * @return 瓦斯場整體產生的瓦斯量(含升級效果)
     */
    public int getProduceGasNum(){
        return (getLevel())+5;
    }

    @Override
    public String toString(){
        return "瓦斯廠:每小時生產5瓦斯(每房屋等級+1)";
    }

    public String buildingDetail(int level){
        return String.format("瓦斯廠：每小時生產%d瓦斯",getLevel()+5);
    }



}
