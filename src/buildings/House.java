package buildings;


public class House extends Building {
    public House() {
        super(1, "房屋",  1, 30, 0, 1, false, 10,
                10, 0, 30, 15,false,0,0);
    }

    /**
     * 增加的市民數
     *
     * @return 增加的市民數
     */
    public int produceCitizen() {
        return getLevel()*2 + 1;
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
