package company.gameobj;

import company.Global;
import company.gametest9th.utils.GameKernel;

import java.awt.*;

public abstract class GameObject implements GameKernel.GameInterface {

    public GameObject(int x, int y, int width, int height) {
        detectRange = new Rect(x, y, width, height);
        painter = new Rect(x, y, width, height);
    }

    public GameObject(int x, int y, int width, int height, int x2, int y2, int width2, int height2) {
        detectRange = new Rect(x, y, width, height);
        painter = new Rect(x2, y2, width2, height2);
        painter.setCenter(detectRange.centerX(), detectRange.centerY());
    }

    public GameObject(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight){
        painter = new Rect(x,y,painterWidth, painterHeight);
        painter.setCenter(x,y);
        detectRange = new Rect(x,y, colliderWidth, colliderHeight);
        detectRange.setCenter(x,y);
    }

    public GameObject(Rect rect) {
        painter = rect.clone();
        detectRange = rect.clone();
    }

    public GameObject(Rect rect, Rect rect2) {
        painter = rect.clone();
        detectRange = rect2.clone();
        painter.setCenter(detectRange.centerX(), detectRange.centerY());
    }

    private Rect detectRange;
    private Rect painter;

    public final void offset(int x, int y) {
        detectRange.offset(x, y);
        painter.offset(x, y);
    }

    public final void centerOffset(int x, int y) {
        detectRange.centerOffset(x, y);
        painter.centerOffset(x, y);
    }

    public final void translate(int x, int y) {
        detectRange.translate(x, y);
        painter.translate(x, y);
    }

    public final void translateX(int x) {
        detectRange.translateX(x);
        painter.translateX(x);
    }

    public final void translateY(int y) {
        detectRange.translateY(y);
        painter.translateY(y);
    }

    public boolean isCollision(GameObject object) {
        //return collider.overlap(object.collider);
        return painter.overlap(object.painter);
    }

    public final boolean isEntered(int x, int y) {
        if (detectRange.left() < x && detectRange.right() > x && detectRange.top() < y && detectRange.bottom() > y) {
            return true;
        }
        return false;
    }

    public final Rect detectRange() {
        return detectRange;
    }

    public final Rect painter() {
        return painter;
    }

    public boolean touchTop() {
        return detectRange.top() <= 0;
    }

    public boolean touchBottom() {
        return detectRange.bottom() >= Global.SCREEN_Y;
    }

    public boolean touchLeft() {
        return detectRange.left() <= 0;
    }

    public boolean touchRight() {
        return detectRange.right() >= Global.SCREEN_X;
    }

    @Override
    public final void paint(Graphics g) {
        paintComponent(g);

        if (Global.IS_DEBUG) {
            g.setColor(Color.RED);
            detectRange.paint(g);
            g.setColor(Color.GREEN);
            painter.paint(g);
            g.setColor(Color.black);
        }
    }

    public abstract void paintComponent(Graphics g);//TODO: can we change this into non-abstract??
}
