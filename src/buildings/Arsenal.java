package buildings;

/**
 * 兵工廠
 */
public class Arsenal extends Building {
    public enum UpgradeEvent{
        UPGRADE_SOLDIER,
        UPGRADE_AIRPLANE;
    }

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
     *
     * PS: 兵工廠的等級 → 決定士兵的攻擊力
     * 兵工廠升級  → 呼叫 Human類別 的 levelUp()方法
     * levelUp() → City類別的 第 347行 使用
     */
    public Arsenal(){
        super(6,"兵工廠",0,3,48,0,2,false,30,
                30,10,70,40,false,0,0);
    }

    @Override
    public String toString() {
        return "兵工廠:可以升級士兵";
    }

}
