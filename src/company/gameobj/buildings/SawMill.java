package company.gameobj.buildings;


import company.Global;
import company.gametest9th.utils.Path;

import oldMain.Resource;


public class SawMill extends Building{

    public SawMill(int x, int y) {
        super(x, y);
        init();
        getIcons().add(new UpGradeIcon(x,y,"升級"+getName()));
    }

    public SawMill() {
        init();
    }
    /**
     * 生產的木頭量
     * @return
     */
    public int woodSpeed() {
        return (getLevel() + 1) * 2;
    }

    @Override
    public String toString() {
        return "伐木場:每小時採集量+1(每房屋等級+2)";
    }

    public int getSawSpeed(){
        return (2 + (super.getLevel()-1)*4);
    }

    public String buildingDetail(int level){
        return "伐木廠：市民每小時木材採集量提升至" + getSawSpeed() + "單位木材";
    }
    //初始化
    @Override
    protected void init() {
        setId(4)
                .setName("伐木場")
                .setBuildTime(2)
                .setUpgradeTime(30)
                .setLevelC(0)
                .setTechLevelNeedBuild(1)
                .setTechLevelNeedUpgrade(2)
                .setHp(10)
                .setWoodCostCreate(15)
                .setSteelCostCreate(0)
                .setGasCostCreate(0)
                .setWoodCostLevelUpC(30)
                .setSteelCostLevelUpC(15)
                .setGasCostLevelup(0)
                .setImgPath(new Path().img().building().SawMill());
        imgInit();
    }
}
