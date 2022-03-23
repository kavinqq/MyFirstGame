package company.gameobj.buildings;

public class house extends Building{
    public house(int x, int y) {
        super(x, y);
                setId(1)
                .setName("房屋")
                .setBuildTime(1)
                .setUpgradeTime(30)
                .setLevel(0)
                .setTechLevel(1)
                .setTechLevelNeedUpgrade(2)
                .setHp(10)
                .setWoodCostCreate(10)
                .setSteelCostCreate(0)
                .setGasCostCreate(0)
                .setWoodCostLevelUp(30)
                .setSteelCostLevelUp(15)
                .setGasCostLevelup(0);
    }
    /**
     * 增加的市民數
     *
     * @return 增加的市民數
     */
    public int produceCitizen() {
        return (getLevel())*2 + 1;
    }

    @Override
    public String toString() {
        return "房子：每24小時消耗1木頭產生1位市民(每房屋等級+2人)";
    }

    @Override
    public String buildingDetail(int level) {
        return String.format("房子：每24小時消耗%d木頭產生%d位市民", 1, getLevel() * 2 - 1);
    }

}
