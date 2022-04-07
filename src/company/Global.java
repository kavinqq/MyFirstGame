package company;

import company.gametest9th.utils.Vector;

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
    public static final boolean IS_DEBUG = false;

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
    public static final int SCREEN_X = 0;
    public static final int SCREEN_Y = 0;
    public static final int SCREEN_WIDTH = WINDOW_WIDTH - 8 - 8;
    public static final int SCREEN_HEIGHT = WINDOW_HEIGHT - 31 - 8;

    //地圖大小
    public static final int MAP_LEFT = (WINDOW_WIDTH - 8 - 8) * -1;
    public static final int MAP_RIGHT = (WINDOW_WIDTH - 8 - 8) * 2;
    public static final int MAP_TOP = (WINDOW_WIDTH - 8 - 8) * -1;
    public static final int MAP_BOTTOM = (WINDOW_WIDTH - 8 - 8) * 2;

    //基座大小
    public static final int FOUNDATION_WIDTH = 118;
    public static final int FOUNDATION_HEIGHT = 116;

    //選單大小
    public static final int BUILDING_OPTION_WIDTH = 150;
    public static final int BUILDING_OPTION_HEIGHT = WINDOW_HEIGHT - STATUS_BAR_HEIGHT;
    public static final int OPTION_GAP_Y = 1;
    public static final int OPTION_GAP_X = (BUILDING_OPTION_WIDTH - FOUNDATION_WIDTH) / 2;
    public static final int BUILDING_OPTION_X = SCREEN_WIDTH - BUILDING_OPTION_WIDTH;
    public static final int BUILDING_OPTION_Y = STATUS_BAR_HEIGHT;

    //領地大小
    public static final int LAND_WIDTH = 1600;
    public static final int LAND_HEIGHT = 840;
    public static final int LAND_X = (SCREEN_WIDTH - LAND_WIDTH - BUILDING_OPTION_WIDTH) / 2;
    public static final int LAND_Y = SCREEN_HEIGHT / 2 - LAND_HEIGHT / 2;

    //建築物大小
    public static final int BUILDING_WIDTH = 96;
    public static final int BUILDING_HEIGHT = 96;

    //按鈕基座與建築物差
    public static final int FB_DIV_GAP_X = (FOUNDATION_WIDTH - BUILDING_WIDTH) / 2;
    public static final int FB_DIV_GAP_Y = (FOUNDATION_HEIGHT - BUILDING_HEIGHT) / 2;


    //建築區起始位置
    public static final int BUILDING_AREA_X = BUILDING_WIDTH;
    public static final int BUILDING_AREA_Y = BUILDING_HEIGHT;
    public static final int BUILDING_AREA_DISTANCE_X = BUILDING_WIDTH * 5 / 2;
    public static final int BUILDING_AREA_DISTANCE_Y = BUILDING_HEIGHT * 5 / 2;

    public static final int BUILDING_GRID_WIDTH = BUILDING_WIDTH * 3 / 2;
    public static final int BUILDING_GRID_HEIGHT = BUILDING_WIDTH * 3 / 2;

    public final static int BUILDING_AREA_NUMY = 4;
    public final static int BUILDING_AREA_NUMX = 7;

    // StatusBar Icon相關設定
    public static final int ICON_START_X = 2;
    public static final int ICON_START_Y = 14;
    public static final int ICON_WIDTH = 60;
    public static final int ICON_HEIGHT = 50;
    public static final int ICON_GAP = 300;

    // 建築
    public final static int BuildingTypeNum = 8;

    // 建築Icon
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
    public static final int CAMERA_SPEED = 20;

    // 鏡頭移動量 X 跟 Y
    public static int CAMERA_MOVE_VX = 0;
    public static int CAMERA_MOVE_VY = 0;

    // 鏡頭移動總量 X 跟 Y
    public static int SUM_OF_CAMERA_MOVE_VX = 0;
    public static int SUM_OF_CAMERA_MOVE_VY = 0;

    //文字大小
    public static int FONT_SIZE = 23;

    //血量的高度
    public static int HP_HEIGHT = 10;

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

        // 邊界緩衝量 讓他提前一點碰到邊界
        int BOUND_BUFFERED = 100;


        CAMERA_MOVE_VX = vx; // 這次的鏡頭X移動量
        SUM_OF_CAMERA_MOVE_VX += vx;// 目前為止的鏡頭X移動量[尚未按下Space reset前]

        if (SUM_OF_CAMERA_MOVE_VX * -1 <= (MAP_LEFT + BOUND_BUFFERED)) { // 如果鏡頭向左移動量 已經到了左邊界 無法前進
            SUM_OF_CAMERA_MOVE_VX -= vx;// 把加的移動量 減回來
            CAMERA_MOVE_VX = 0;// 這次X移動量 = 0
        } else if (SUM_OF_CAMERA_MOVE_VX * -1 >= (MAP_RIGHT - (SCREEN_WIDTH - BUILDING_OPTION_WIDTH) - BOUND_BUFFERED)) { // 如果鏡頭向右移動量 已經到了右邊界 無法前進
            SUM_OF_CAMERA_MOVE_VX -= vx;// 把加的移動量 減回來
            CAMERA_MOVE_VX = 0;// 這次X移動量 = 0
        }
    }

    /**
     * 設定 目前鏡頭移動的 Y
     *
     * @param vy 鏡頭移動的 Y值
     */

    public static void setCameraMoveVY(int vy) {

        // 邊界緩衝量 讓他提前一點碰到邊界
        int BOUND_BUFFERED = 100;

        CAMERA_MOVE_VY = vy; // 這次的鏡頭Y移動量
        SUM_OF_CAMERA_MOVE_VY += vy;// 目前為止的鏡頭Y移動量[尚未按下Space reset前]

        if (SUM_OF_CAMERA_MOVE_VY * -1 <= (MAP_TOP + BOUND_BUFFERED)) {// 如果鏡頭向上移動量 已經到了上邊界 無法前進

            SUM_OF_CAMERA_MOVE_VY -= vy; // 把加的移動量 減回來
            CAMERA_MOVE_VY = 0;// 這次Y移動量 = 0
        } else if (SUM_OF_CAMERA_MOVE_VY * -1 >= (MAP_BOTTOM - SCREEN_HEIGHT - BOUND_BUFFERED)) {// 如果鏡頭向下移動量 已經到下邊界 無法前進(向下移動量極限 = 整當地圖的下邊界 - 視窗下邊界)

            SUM_OF_CAMERA_MOVE_VY -= vy;// 把加的移動量 減回來
            CAMERA_MOVE_VY = 0;// 這次Y移動量 = 0
        }
    }

    /**
     * 重置目前鏡頭的移動量總和
     */
    public static void resetSumOfCameraMove() {
        SUM_OF_CAMERA_MOVE_VX = 0;
        SUM_OF_CAMERA_MOVE_VY = 0;
    }

    public static Vector getRandom() {
        Vector v;
        do {
            v = new Vector(Global.random(Global.MAP_LEFT, Global.MAP_RIGHT), Global.random(MAP_TOP, MAP_BOTTOM));
        }
        while (0 < v.vx() && v.vx() < SCREEN_WIDTH || 0 < v.vy() && v.vy() < SCREEN_HEIGHT);
        return v;

    }
}
