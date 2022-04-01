package company.gameobj.buildings;


import company.Global;
import company.gametest9th.utils.Path;

import java.awt.*;

public class Arsenal extends Building {
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
    public Arsenal(int x, int y) {
        super(x, y);

        init();
        getIcons().add(new UpGradeIcon(x+ 3*(Global.BUILDING_ICON_WIDTH*getIcons().size()-1)/2,y,"升級士兵"));
        getIcons().add(new UpGradeIcon(x+ 3*(Global.BUILDING_ICON_WIDTH*getIcons().size()-1)/2,y,"升級飛機"));
        }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

    }

    public Arsenal() {
        init();
    }

    @Override
    public String toString() {
        return "兵工廠:可以升級士兵";
    }

    public String buildingDetail(int level){
        return "";
    }

    //初始化
    @Override
    protected void init() {
        setId(6)
                .setName("兵工廠")
                .setBuildTime(3)
                .setUpgradeTime(48)
                .setLevelC(0)
                .setTechLevelNeedBuild(2)
                .setTechLevelNeedUpgrade(2)
                .setHp(30)
                .setWoodCostCreate(30)
                .setSteelCostCreate(10)
                .setGasCostCreate(0)
                .setWoodCostLevelUpC(70)
                .setSteelCostLevelUpC(40)
                .setGasCostLevelup(0)
                .setImgPath(new Path().img().building().Arsenal());
        imgInit();
    }
}
