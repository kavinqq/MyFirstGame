package company.gameobj.buildings;

<<<<<<< HEAD
import company.gametest9th.utils.Path;

public class Lab extends Building{
    public Lab(int x, int y) {
        super(x, y, new Path().img().building().Lab());
    }

    public Lab(){
        super();
=======
import company.gameobj.GameObject;

public class Lab extends Building {
    /**
     * 父類建構子
     * id 建築物ID  (1.房屋 2.研究所 3.軍營 4.伐木場 5.煉鋼廠 6.兵工廠 7.瓦斯場 8.飛機工廠)
     * name 建築物名稱
     * buildTime 建築物持續時間
     * upgradeTime 建築物升級時間
     * level 建築物等級 預設-1  建好 0 升級過 1~2,147,483,647
     * techLevelNeed 需要文明等級
     * readyToUpgrade 建築物是否在建築，建築中 -> true
     * woodCostCreate 創建所需要的木頭量
     * steelCostCreate 創建所需要的鋼鐵量
     * woodCostLevelUp 升級所需要的木頭量
     * steelCostLevelUp 升級所需要的鋼鐵量
     */
    public Lab(int x, int y) {
        super(x, y);
        setId(2)
                .setName("研究所")
                .setBuildTime(3)
                .setUpgradeTime(24)
                .setLevelC(0)
                .setTechLevel(1)
                .setTechLevelNeedUpgrade(1)
                .setHp(30)
                .setWoodCostCreate(10)
                .setSteelCostCreate(5)
                .setGasCostCreate(0)
                .setWoodCostLevelUpC(50)
                .setSteelCostLevelUpC(20)
                .setGasCostLevelup(0);
    }



    public Lab() {
        super(90,90);
        setId(2)
                .setName("研究所")
                .setBuildTime(3)
                .setUpgradeTime(24)
                .setLevelC(0)
                .setTechLevel(1)
                .setTechLevelNeedUpgrade(1)
                .setHp(30)
                .setWoodCostCreate(10)
                .setSteelCostCreate(5)
                .setGasCostCreate(0)
                .setWoodCostLevelUpC(50)
                .setSteelCostLevelUpC(20)
                .setGasCostLevelup(0);
    }
    @Override
    public String toString() {
        return "研究所:蓋了才能升級科技和房屋";
    }

    public String buildingDetail(int level){
        return "";
    }

    /**
     * 升級後屬性更改(ex再升級需要的資源增加)
     * @param level 目前等級
     */
    public void levelUpTechResource(int level){
        switch (level){
            case 0:{
                super.setLevel(0);
            }
            case 1:{//一級時 需要升級的資源改變
                super.setWoodCostLevelUp(60);
                super.setSteelCostLevelUp(30);
                super.setGasCostLevelUp(10);
                super.setLevel(1);
                break;
            }
        }
>>>>>>> 407a217de5dd5b2a569fa9da541c7a9d1f420125
    }
}
