package company.gameobj.buildings;

import company.gametest9th.utils.Path;
import oldMain.Resource;

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
    public SteelMill() {
        init();
    }

    public SteelMill(int x,int y) {
        super(x,y);
        init();
    }
    //初始化
    protected void init(){
        setId(5)
                .setName("煉鋼廠")
                .setBuildTime(1)
                .setUpgradeTime(5)
                .setTechLevelNeedBuild(1)
                .setTechLevelNeedUpgrade(2)
                .setLevelC(0)
                .setTechLevel(1)
                .setHp(10)
                .setWoodCostCreate(15)
                .setSteelCostCreate(5)
                .setGasCostCreate(0)
                .setWoodCostLevelUpC(30)
                .setSteelCostLevelUpC(15)
                .setGasCostLevelup(0)
                .setImgPath(new Path().img().building().SteelMill());
        imgInit();
    }
    /**
     * 生產鋼鐵
     */
    public int steelSpeed(){
        return getLevel()+1;
    }

    @Override
    public String toString() {
        return "煉鋼廠:每小時採集量+1(每房屋等級+2)";
    }

    public String buildingDetail(int level) {
        return "煉鋼廠：市民每小時鋼鐵採集量提升至" + (Resource.DEFAULT_STEEL_SPEED + 1 + super.getLevel()) + "單位鋼鐵";
    }

}
