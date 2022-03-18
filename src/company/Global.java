package company;

public class Global {



    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public static final boolean IS_DEBUG = false;

    public static final int UPDATE_TIMES_PER_SEC = 60;
    public static final int NANOSECOUND_PER_UPDATE = 1000000000 / UPDATE_TIMES_PER_SEC;

    public static final int FRAME_LIMIT = 60;
    public static final int LIMIT_DELTA_TIME = 1000000000 / FRAME_LIMIT;

    //整個視窗大小
    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;

    //狀態攔
    public static final int STATUS_BAR_X=0;
    public static final int STATUS_BAR_Y=0;
    public static final int STATUS_BAR_WEIGHT = WINDOW_WIDTH;
    public static final int STATUS_BAR_HEIGHT=60;

    //遊戲視窗大小
    public static final int SCREEN_X = WINDOW_WIDTH - 8 - 8;
    public static final int SCREEN_Y = WINDOW_HEIGHT - 31 - 8;

    //選單大小
    public static final int BUILDING_OPTION_WIDTH=240;
    public static final int BUILDING_OPTION_HEIGHT=SCREEN_Y;
    public static final int BUILDING_OPTION_X=WINDOW_WIDTH-BUILDING_OPTION_WIDTH;
    public static final int BUILDING_OPTION_Y=0;


    //領地大小
    public static final int LAND_WIDTH=1320;
    public static final int LAND_HEIGHT=840;
    public static final int LAND_X=(SCREEN_X-LAND_WIDTH-BUILDING_OPTION_WIDTH)/2;
    public static final int LAND_Y=SCREEN_Y/2-LAND_HEIGHT/2;

    //建築物大小
    public static final int BUILDING_WIDTH=110;
    public static final int BUILDING_HEIGHT=130;

    //基座大小
    public static final int FOUNDATION_WIDTH=120;
    public static final int FOUNDATION_HEIGHT=120;
    public static final int FOUNDATION_DISTANCE_X=120;
    public static final int FOUNDATION_DISTANCE_Y=120;


    //建築
    public final static int BUILDING_AMOUNT_X=LAND_WIDTH/FOUNDATION_WIDTH/2;
    public final static int BUILDING_AMOUNT_Y=LAND_HEIGHT/FOUNDATION_HEIGHT/2;

    /**
     * 操作指令
     */
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    public static final int SPACE = 5;

    /**
     * 輸出範圍內的隨機數字
     *
     * @param min
     * @param max
     * @return
     */
    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
