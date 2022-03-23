package company.gameobj.buildings;

public class SteelMill extends Building{
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
    public SteelMill(int x, int y) {

        super(x, y);
                setId(5)
                .setName("煉鋼廠")
                .setBuildTime(1)
                .setUpgradeTime(30)
                .setTechLevelNeedUpgrade(2)
                .setLevel(0)
                .setTechLevel(1)
                .setHp(10)
                .setWoodCostCreate(15)
                .setSteelCostCreate(5)
                .setGasCostCreate(0)
                .setWoodCostLevelUp(30)
                .setSteelCostLevelUp(15)
                .setGasCostLevelup(0);
    }
    /**
     * 生產的飛機數
     * @return 生產的飛機數(含升級)
     */
    public int produceAirPlane(){
        return getLevel()+1;
    }

    @Override
    public String toString(){
        return "飛機工廠:每3小時花費5瓦斯產生1台戰鬥機(每等級可以使工作量+1)";
    }

    @Override
    public String buildingDetail(int level){
        return "飛機工廠：每3小時花費" + super.getLevel() *5 + "瓦斯生產" + (super.getLevel()+1) + "台戰鬥機";
    }
}
