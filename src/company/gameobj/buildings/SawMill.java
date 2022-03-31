package company.gameobj.buildings;


import company.gametest9th.utils.Path;

import oldMain.Resource;


public class SawMill extends Building{

    public SawMill(int x, int y) {
        super(x, y);
        init();
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

    public String buildingDetail(int level){
        return "伐木廠：市民每小時木材採集量提升至" + (Resource.DEFAULT_WOOD_SPEED + 1 + super.getLevel()) + "單位木材";
    }
    //初始化
    @Override
    protected void init() {
        setId(4)
                .setName("伐木場")
                .setBuildTime(1)
                .setUpgradeTime(30)
                .setLevelC(0)
                .setTechLevel(1)
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
