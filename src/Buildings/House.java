package Buildings;

import Main.City;
import Main.MyCity;

public class House extends Building {
    public House() {
        super(1, "房屋", 0, 1, 30, 0, 1, false, 10,
                10, 0, 30, 15);
    }

    /**
     * 增加的市民數
     * @return 增加的市民數
     */
    public int produceCitizen() {
        if(MyCity.getGameTime()%24==0){
            return getLevel();
        }
        return 0;
    }

}
