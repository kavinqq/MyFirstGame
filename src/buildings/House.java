package buildings;


public class House extends Building {
    public House() {
        super(1, "房屋", 0, 1, 30, 0, 2, 2, 10, 10,
                10, 0, 30, false, 15, false, 0, 0);
    }

    /**
     * 增加的市民數
     *
     * @return 增加的市民數
     */
    public int produceCitizen() {
        return getLevel();
    }

    @Override
    public String toString() {
        return "房屋)";
    }

    @Override
    public String buildingDetail() {
        return String.format("每24小時消耗%d木頭產生%d位市民", 1, getLevel() * 2 - 1);
    }

    @Override
    public String buildingUpgradeDetail() {
        return String.format("每24小時消耗%d木頭產生%d位市民", 1, (getLevel()+1) * 2 - 1);
    }
}
