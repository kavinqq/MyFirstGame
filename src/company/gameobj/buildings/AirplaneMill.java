package company.gameobj.buildings;

import company.Global;
import company.gametest9th.utils.Path;

public class AirplaneMill extends Building {
    /**
     * 父類建構子
     * id 建築物ID  (1.房屋 2.研究所 3.軍營 4.伐木場 5.煉鋼廠 6.兵工廠 7.瓦斯場 8.飛機工場)
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
     *
     * PS: 兵工廠的等級 → 決定士兵的攻擊力
     * 兵工廠升級  → 呼叫 Human類別 的 levelUp()方法
     * levelUp() → City類別的 第 347行 使用
     */
    public AirplaneMill(int x, int y) {
        super(x, y);
        init();
        getIcons().add(new UpGradeIcon(getIcons().size(),"升級飛機"));
        getIcons().add(new WorkingIcon(getIcons().size(),"飛機"));
    }

    public AirplaneMill() {
        init();
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
        return "定時自動產生飛機";
    }//每3小時花費5瓦斯產生1台戰鬥機(每等級可以使工作量+1)

    @Override
    public String buildingDetail(int level){
        return "飛機工廠：每3小時花費" + super.getLevel() *5 + "瓦斯生產" + (super.getLevel()+1) + "台飛機";
    }
    //初始化
    @Override
    protected void init() {
        setId(8)
                .setName("飛機工廠")
                .setBuildTime(2)
                .setUpgradeTime(30)
                .setLevelC(0)
                .setTechLevelNeedBuild(2)
                .setTechLevelNeedUpgrade(2)
                .setHp(50)
                .setWoodCostCreate(15)
                .setSteelCostCreate(5)
                .setGasCostCreate(5)
                .setWoodCostLevelUpC(30)
                .setSteelCostLevelUpC(15)
                .setGasCostLevelup(5)
                .setImgPath(new Path().img().building().AirplanemIll());
        imgInit();
    }

}
