package company.gameobj.buildings;

import oldMain.Resource;

public class SawMill extends Building{

    public SawMill(int x, int y) {
        super(x, y);
                setId(4)
                .setName("伐木場")
                .setBuildTime(1)
                .setUpgradeTime(30)
                .setLevel(0)
                .setTechLevel(1)
                .setTechLevelNeedUpgrade(2)
                .setHp(10)
                .setWoodCostCreate(15)
                .setSteelCostCreate(0)
                .setGasCostCreate(0)
                .setWoodCostLevelUp(30)
                .setSteelCostLevelUp(15)
                .setGasCostLevelup(0);
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
}
