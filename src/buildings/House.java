package buildings;

import main.City;
import main.MyCity;

public class House extends Building {
    public House() {
        super(1, "房屋", 0, 1, 30, 0, 2, false, 10,
                10, 0, 30, 15,false,0,0);
    }

    /**
     * 增加的市民數
     * @return 增加的市民數
     */
    public int produceCitizen() {
        return getLevel();
    }

    @Override
    public String toString() {
        return "房屋:每24小時消耗1木頭產生1位市民(每房屋等級+2人)";
    }
}
