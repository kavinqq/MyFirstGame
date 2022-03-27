package company.gameobj;

import java.awt.*;
import java.util.Arrays;

public class Rect {

    public Rect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    private int x;
    private int y;
    private int width;
    private int height;

    public final int centerX() {
        return x + width / 2;
    }

    public final int centerY() {
        return y + height / 2;
    }

    public final void setCenter(int x, int y) {
        this.x = x - width / 2;
        this.y = y - height / 2;
    }

    public final Rect moveToPoint(int x, int y) {
        //左上
        this.x=x;
        this.y=y;
        return this;
    }

    //方形涵蓋傳入的方形
    public final boolean cover(Rect object){
        if(right()> object.right() && left()<object.left() && top()<object.top() && bottom()>object.bottom()){
            return true;
        }
        return false;
    }

    //方形被涵蓋傳入的方形
    public final boolean covered(Rect object){
        if(right()< object.right() && left()>object.left() && top()<object.top() && bottom()>object.bottom()){
            return true;
        }
        return false;
    }

    public final Rect moveToCenterPoint(int x, int y) {
        //中心
        setCenter(x,y);
        return this;
    }

    public final Rect offset(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public final Rect translate(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public final Rect translateX(int x) {
        this.x += x;
        return this;
    }

    public final Rect translateY(int y) {
        this.y += y;
        return this;
    }

    public final Rect scale(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public final Rect scaleX(int width) {
        this.width = width;
        return this;
    }

    public final Rect scaleY(int height) {
        this.height = height;
        return this;
    }

    public final int left() {
        return x;
    }

    public final int right() {
        return x + width;
    }

    public final int top() {
        return y;
    }

    public final int bottom() {
        return y + height;
    }

    public final int width() {
        return this.width;
    }

    public final int height() {
        return this.height;
    }



    public final boolean overlap(Rect object) {
        if (right() <= object.left()) {
            return false;
        }
        if (left() >= object.right()) {
            return false;
        }
        if (top() >= object.bottom()) {
            return false;
        }
        if (bottom() <= object.top()) {
            return false;
        }
        return true;
    }

    //交疊正方形
    public final Rect overlapRect(Rect object) {
        if(overlap(object)){
            //已確認交疊情況下 排順序下 第2第3為交疊正方形位置
            int[] intsX={left(),object.left(),right(),object.right()};
            Arrays.sort(intsX);
            int[] intsY={left(),object.left(),right(),object.right()};
            Arrays.sort(intsY);
            return new Rect(intsX[1], intsY[1], intsX[2] - intsX[1], intsY[2] - intsY[1]) {
            };
        }
        return null;
    }

    public final void paint(Graphics g) {
        g.drawRect(x, y, width, height);
    }

    public final Rect clone() {
        return new Rect(left(), right(), width(), height());
    }

}
