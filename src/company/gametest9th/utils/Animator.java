package company.gametest9th.utils;

import company.Global;

import java.awt.*;

/**
 *  Animator 的 抽象父類別
 */
abstract public class Animator {
    /**
     * 把 走路/站立 狀態的整數陣列存在 enum之中
     */
    public enum State {

        STAND(new int[]{1}, 0),
        WALK(new int[]{0,1,2,1}, 15);

        private int[] arr;
        private int speed;

        State(int[] arr, int speed){
            this.arr = arr;
            this.speed = speed;
        }

        // 拿到 該狀態的 陣列
        public int[] getArr(){
            return arr;
        }

        // 拿到 該狀態的 delay速度
        public int getSpeed(){
            return speed;
        }
    }

    // 哪一張人物行走圖
    private Image img;
    // 哪一個人物
    private int type;
    // 站立 或 走路的 陣列 index => 用來換 左腳/右腳/站立 的圖片
    private int walkCount;
    // 延遲
    private Delay delay;
    // 方向
    private Global.Direction dir;
    // 人物狀態(站立/行走)
    private State state;

    public Animator(){}

    abstract public void setState(State state);

    abstract public void paint(Global.Direction dir, int left, int top, int right, int bottom, Graphics g);

    abstract public void update();
}
