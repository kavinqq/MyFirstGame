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
    private Rect tmpDetectRange;

    public final void setPainterStartFromTopLeft(int x, int y) {
        detectRange.moveToPoint(x, y);
        painter.moveToPoint(x, y);
    }

    public final void moveToCenterPoint(int x, int y) {
        detectRange.moveToCenterPoint(x, y);
        painter.moveToCenterPoint(x, y);
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

    //物件是否涵蓋傳入的物件
    public boolean isCover(GameObject object) {
        //return collider.overlap(object.collider);
        return painter.cover(object.painter);
    }

    public boolean isCover(Rect rect) {
        //return collider.overlap(object.collider);
        return painter.cover(rect);
    }

    //物件是否被涵蓋傳入的物件
    public boolean isCovered(GameObject object) {
        //return collider.overlap(object.collider);
        return painter.covered(object.painter);
    }

    public final boolean isEntered(int x, int y) {
        return (detectRange.left() < x && detectRange.right() > x && detectRange.top() < y && detectRange.bottom() > y);
    }

    public final boolean isCovering(int x, int y){
        return (this.painter().left()<=x && x<=this.painter().right() && this.painter().top()<=y && y<=this.painter().bottom());
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

    public void shutDetectRange(){
        tmpDetectRange=detectRange.clone();
        detectRange=new Rect(0,0,0,0);
    }

    public void openDetectRange(){
        detectRange=tmpDetectRange.clone();
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

    //檢查是否碰到某物件的左邊
    public boolean touchLeftOf(GameObject gameObject){
        return (this.painter.right()>=gameObject.painter().left() && this.painter.left()<gameObject.painter().left() && this.isHorizontalParallelTo(gameObject));
    }
    //檢查是否碰到某物件的右邊
    public boolean touchRightOf(GameObject gameObject){
        return (this.painter.left()<=gameObject.painter().right() && this.painter.right()>gameObject.painter().right() && this.isHorizontalParallelTo(gameObject));
    }
    //檢查是否碰到某物件的上方
    public boolean touchTopOf(GameObject gameObject){
        return (this.painter().bottom()>=gameObject.painter().top() && this.painter().top()<gameObject.painter().top() && this.isVerticalParallel(gameObject));
    }
    //檢查是否碰到某物件的下方
    public boolean touchBottomOf(GameObject gameObject){
        return (this.painter().top()<=gameObject.painter.bottom() && this.painter().bottom()>gameObject.painter().bottom() && this.isVerticalParallel(gameObject));
    }

    //檢查是否在水平方向上有重疊
    public boolean isHorizontalParallelTo(GameObject gameObject){
        return (this.painter().top()<gameObject.painter().bottom() && this.painter().bottom()>gameObject.painter().top());
    }

    //檢查是否在垂直方向上有重疊
    private boolean isVerticalParallel(GameObject gameObject){
        return (this.painter().left()<gameObject.painter().right() && this.painter().right()>gameObject.painter().left());
    }
}
