package company.gameobj.buildings;


import company.Global;
import company.gametest9th.utils.Path;

public class House extends Building {
    public static int makeTime=48;

    public House(int x, int y) {
        super(x, y);
        init();
        getIcons().add(new UpGradeIcon(getIcons().size(),"升級房屋"));
        getIcons().add(new WorkingIcon(getIcons().size(),"市民"));
    }

    public House() {
        init();
    }

    /**
     * 增加的市民數
     *
     * @return 增加的市民數
     */
    public int produceCitizen() {
        return (getLevel());
    }

    @Override
    public String toString() {
        return "自動定時產生市民";
    }//每24小時消耗1木頭產生1位市民(每房屋等級+2人)

    @Override
    public String buildingDetail(int level) {
        return String.format("房子：每24小時消耗%d木頭產生%d位市民", 1, getLevel() * 2 - 1);
    }
    //初始化
    @Override
    protected void init() {
        setId(1)
                .setName("房屋")
                .setBuildTime(5)
                .setUpgradeTime(3)
                .setLevelC(0)
                .setTechLevelNeedBuild(1)
                .setTechLevelNeedUpgrade(1)
                .setHp(150)
                .setWoodCostCreate(10)
                .setSteelCostCreate(0)
                .setGasCostCreate(0)
                .setWoodCostLevelUpC(30)
                .setSteelCostLevelUpC(15)
                .setGasCostLevelup(0)
                .setImgPath(new Path().img().building().House());
        imgInit();
    }
}
