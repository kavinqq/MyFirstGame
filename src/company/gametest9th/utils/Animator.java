package company.gametest9th.utils;

import company.Global;

import java.awt.*;

/**
 *  Animator 的 抽象父類別
 */
abstract public class Animator {

    public enum State {

        STAND(new int[]{1}, 0),
        WALK(new int[]{0,1,2,1}, 30);

        private int[] arr;
        private int speed;

        State(int[] arr, int speed){
            this.arr = arr;
            this.speed = speed;
        }

        public int[] getArr(){
            return arr;
        }
    }


    private Image img;
    private Delay delay;
    private int[] walk;
    private Global.Direction dir;
    private int type;

    public Animator(){}

    abstract public void paint(Global.Direction dir, int left, int top, int right, int bottom, Graphics g);

    abstract public void update();
}
