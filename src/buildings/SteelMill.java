package buildings;

import main.Resource;

/**
 * 煉鋼廠
 */
public class SteelMill extends Building {
    /**
     * 父類建構子
     * id 建築物ID  (1.房屋 2.研究所 3.軍營 4.伐木場 5.煉鋼廠 6.兵工廠)
     * name 建築物名稱
     * buildStart 建築物開始時間
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
    public SteelMill() {
        super(5, "煉鋼場",  1, 30, 0, 1, false, 10,
                15, 5, 30, 15,false,0,0);
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

    public String buildingDetail(int level){
        return "市民每小時鋼鐵採集量提升至" + (Resource.DEFAULT_STEEL_SPEED + 1 + super.getLevel()) + "單位鋼鐵";

    }
}
