package buildings;

public class Lab extends Building {
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
    public Lab(){
        super(2,"研究所",0,3,24,0,1,false,30,
                10,5,50,20,false,0,0);

    }

    @Override
    public String toString() {
        return "研究所:蓋了才能升級科技和房屋";
    }
}