package com.company;

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

    //視窗大小
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int SCREEN_X = WINDOW_WIDTH - 8 - 8;
    public static final int SCREEN_Y = WINDOW_HEIGHT - 31 - 8;

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
