package company;

public class Global {

    /**
     * 角色行走方向
     */
    public enum Direction {
        UP(3),
        DOWN(0),
        LEFT(1),
        RIGHT(2);

        private int value;

        Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // 是否為Debug模式
    public static final boolean IS_DEBUG = true;

    // 每秒update幾次
    public static final int UPDATE_TIMES_PER_SEC = 60;
    public static final int NANOSECOUND_PER_UPDATE = 1000000000 / UPDATE_TIMES_PER_SEC;

    //  每秒paint幾次
    public static final int FRAME_LIMIT = 60;
    public static final int LIMIT_DELTA_TIME = 1000000000 / FRAME_LIMIT;

    //整個視窗大小
    public static final int WINDOW_WIDTH = 1920; //1920
    public static final int WINDOW_HEIGHT = 1080; //1080

    //狀態攔
    public static final int STATUS_BAR_X = 0;
    public static final int STATUS_BAR_Y = 0;
    public static final int STATUS_BAR_WIDTH = WINDOW_WIDTH;
    public static final int STATUS_BAR_HEIGHT = 80;

    //遊戲視窗大小

    public static final int SCREEN_X = WINDOW_WIDTH - 8 - 8;
    public static final int SCREEN_Y = WINDOW_HEIGHT - 31 - 8;

    //地圖大小

    public static final int MAP_LEFT = (WINDOW_WIDTH - 8 - 8) * -1;
    public static final int MAP_RIGHT = (WINDOW_WIDTH - 8 - 8) * 2;
    public static final int MAP_TOP = (WINDOW_WIDTH - 8 - 8) * -1;
    public static final int MAP_BOTTOM = (WINDOW_WIDTH - 8 - 8) * 2;

    //基座大小
    public static final int FOUNDATION_WIDTH = 118;
    public static final int FOUNDATION_HEIGHT = 117;

    //選單大小
    public static final int BUILDING_OPTION_WIDTH = 150;
    public static final int OPTION_GAP_Y = 5;
    public static final int OPTION_GAP_X = (BUILDING_OPTION_WIDTH - FOUNDATION_WIDTH) / 2;
    public static final int BUILDING_OPTION_HEIGHT = SCREEN_Y;
    public static final int BUILDING_OPTION_X = WINDOW_WIDTH - BUILDING_OPTION_WIDTH;
    public static final int BUILDING_OPTION_Y = STATUS_BAR_HEIGHT;

    //領地大小
    public static final int LAND_WIDTH = 1600;
    public static final int LAND_HEIGHT = 840;
    public static final int LAND_X = (SCREEN_X - LAND_WIDTH - BUILDING_OPTION_WIDTH) / 2;
    public static final int LAND_Y = SCREEN_Y / 2 - LAND_HEIGHT / 2;

    //建築物大小
    public static final int BUILDING_WIDTH = 96;
    public static final int BUILDING_HEIGHT = 96;

    //基座與建築物差
    public static final int DIV_GAP_X = (FOUNDATION_WIDTH - BUILDING_WIDTH) / 2;
    public static final int DIV_GAP_Y = (FOUNDATION_HEIGHT - BUILDING_HEIGHT) / 2;

    //Icon相關設定
    public static final int ICON_START_X = 2;
    public static final int ICON_START_Y = 14;
    public static final int ICON_WIDTH = 60;
    public static final int ICON_HEIGHT = 50;
    public static final int ICON_GAP = 300;

    //建築
    public final static int BuildingTypeNum = 8;

    //建築Icon

    public static final int BUILDING_ICON_WIDTH = 25;
    public static final int BUILDING_ICON_HEIGHT = 25;
    public static final int BUILDING_ICON_GAP_X = 20;


    // 鍵盤CommandCode列表
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    public static final int SPACE = 5;
    public static final int ESC = 6;

    // 鏡頭移動的速度
    public static final int CAMERA_SPEED = 8;

    // 鏡頭移動量 X 跟 Y
    public static int CAMERA_MOVE_VX = 0;
    public static int CAMERA_MOVE_VY = 0;

    // 鏡頭移動總量 X 跟 Y
    public static int SUM_OF_CAMERA_MOVE_VX = 0;
    public static int SUM_OF_CAMERA_MOVE_VY = 0;

    /**
     * 輸出範圍內的隨機數字
     *
     * @param min 最小值
     * @param max 最大值
     * @return 回傳限制範圍內的數字
     */

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static boolean canCommand(int x, int y) {

        return x > 0 && x < WINDOW_WIDTH - BUILDING_OPTION_WIDTH && y > STATUS_BAR_HEIGHT && y < WINDOW_HEIGHT - STATUS_BAR_HEIGHT;
    }

    /**
     * 設定 目前鏡頭移動的 X
     *
     * @param vx 鏡頭移動的 X值
     */

    public static void setCameraMoveVX(int vx) {
        CAMERA_MOVE_VX = vx;
        SUM_OF_CAMERA_MOVE_VX += vx;
    }

    /**
     * 設定 目前鏡頭移動的 Y
     *
     * @param vy 鏡頭移動的 Y值
     */

    public static void setCameraMoveVY(int vy) {
        CAMERA_MOVE_VY = vy;
        SUM_OF_CAMERA_MOVE_VY += vy;
    }

    public static void resetSumOfCameraMove(){
        SUM_OF_CAMERA_MOVE_VX = 0;
        SUM_OF_CAMERA_MOVE_VY = 0;
    }
}
