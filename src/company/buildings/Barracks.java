package buildings;

/**
 * 軍營
 */
public class Barracks extends Building {
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
     */
    public Barracks() {
        super(3, "軍營",  2, 30, 0, 1, 2,30,
                20, 10, 30, 15,0,0);
    }

    /**
     * 增加的士兵數
     * @return 增加的士兵數
     */
    public int produceSoldier(){
        return getLevel()*2-1;

    }
    @Override
    public String toString() {
        return "軍營:每3小時消耗2木材2鋼鐵產生1位士兵(每房屋等級+2人)";
    }

    public String buildingDetail(int level){
        return "軍營：每3小時消耗2木材、2鋼鐵生產" + (super.getLevel() * 2 + 1) + "位士兵";
    }

}
