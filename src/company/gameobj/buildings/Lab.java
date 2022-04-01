package company.gameobj.buildings;
import company.Global;
import company.gametest9th.utils.Path;
import oldMain.City;

import java.awt.*;

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
        init();
        getIcons().add(new UpGradeIcon(x+ 3*(Global.BUILDING_ICON_WIDTH*getIcons().size()-1)/2,y,"升級科技等級"));

    }



    public Lab() {
        init();
    }
    //初始化
    protected void init(){

        setId(2)
                .setName("研究所")
                .setBuildTime(3)
                .setUpgradeTime(24)
                .setLevelC(0)
                .setTechLevelNeedBuild(1)
                .setTechLevelNeedUpgrade(1)
                .setHp(30)
                .setWoodCostCreate(10)
                .setSteelCostCreate(5)
                .setGasCostCreate(0)
                .setWoodCostLevelUpC(50)
                .setSteelCostLevelUpC(20)
                .setGasCostLevelup(0)
                .setImgPath(new Path().img().building().Lab());
        imgInit();
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
    }

    @Override
    public void paintComponent(Graphics g) {
        if (getLevel()==0) { //畫出建造中的建築物 !isWorking() && !readyToUpgrade && !isUpgrading
            g.drawImage(getUnderConstructionImg(), painter().left(), painter().top(), painter().width(), painter().height(), null);
        //畫出升級中的建築物
        }else if (getIsTechUpgrading()) {
            g.drawImage(getUnderConstructionImg(), painter().left(), painter().top(), painter().width(), painter().height(), null);
            g.drawString("科技升級中", painter().left(), painter().top());
        } else { //畫出完成的建築物
            g.drawImage(getImg(), painter().left(), painter().top(), painter().width(), painter().height(), null);
            //畫出Icon
            if (isShowIcon()) {
                //畫出等級
                g.drawString("目前科技等級:" + City.getTechLevel(), painter().left(), painter().top());
                for (int i = 0; i < getIcons().size(); i++) {
                    getIcons().get(i).paint(g);
                }
            }
        }
    }
}
