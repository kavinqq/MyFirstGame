package company.gameobj;

import company.Global;
import company.gametest9th.utils.GameKernel;

import java.awt.*;
import java.util.Arrays;

public abstract class GameObject implements GameKernel.GameInterface {

    private boolean isVisible; // 能否看見這個物件

    private int originalX; // 原本的鏡頭X
    private int originalY; //

    private Rect detectRange;
    private Rect painter;
    private Rect tmpDetectRange;


    public GameObject(int x, int y, int width, int height) {

        isVisible = true;

        detectRange = new Rect(x, y, width, height);
        painter = new Rect(x, y, width, height);

        originalX = x;
        originalY = y;
    }

    public GameObject(int x, int y, int width, int height, int x2, int y2, int width2, int height2) {

        isVisible = true;

        detectRange = new Rect(x, y, width, height);
        painter = new Rect(x2, y2, width2, height2);

        painter.setCenter(detectRange.centerX(), detectRange.centerY());

        originalX = x;
        originalY = y;
    }

    public GameObject(int x, int y, int painterWidth, int painterHeight, int colliderWidth, int colliderHeight) {

        isVisible = true;

        painter = new Rect(x, y, painterWidth, painterHeight);
        painter.setCenter(x, y);

        detectRange = new Rect(x, y, colliderWidth, colliderHeight);
        detectRange.setCenter(x, y);

        originalX = x;
        originalY = y;
    }

    public GameObject(Rect rect) {

        isVisible = true;

        painter = rect.clone();
        detectRange = rect.clone();

        originalX = rect.left();
        originalY = rect.top();
    }

    public GameObject(Rect rect, Rect rect2) {

        painter = rect.clone();
        painter.setCenter(detectRange.centerX(), detectRange.centerY());

        detectRange = rect2.clone();

        originalX = rect.left();
        originalY = rect.top();
    }
    //設置中心點
    public void setCenter(int x, int y) {
        painter.setCenter(x, y);
        detectRange.setCenter(x, y);
    }

    public final void setPainterStartFromTopLeft(int x, int y) {
        detectRange.moveToPoint(x, y);
        painter.moveToPoint(x, y);
    }

    public final void moveToCenterPoint(int x, int y) {
        detectRange.moveToCenterPoint(x, y);
        painter.moveToCenterPoint(x, y);
    }

    public final void offset(int x, int y) {
        detectRange.offset(x, y);
        painter.offset(x, y);
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

    public Rect overlapRect(Rect rect) {
        if (painter.overlap(rect)) {
            //已確認交疊情況下 排順序下 找到X、Y方向 第2第3為交疊正方形位置
            int[] intsX = {detectRange.left(), rect.left(), detectRange.right(), rect.right()};
            Arrays.sort(intsX);
            int[] intsY = {detectRange.top(), rect.top(), detectRange.bottom(), rect.bottom()};
            Arrays.sort(intsY);
            return new Rect(intsX[1], intsY[1], intsX[2] - intsX[1], intsY[2] - intsY[1]);
        }
        return null;
    }
    public Rect overlapObject(GameObject object) {
        if (painter.overlap(object.painter)) {
            //已確認交疊情況下 排順序下 找到X、Y方向 第2第3為交疊正方形位置
            int[] intsX = {detectRange.left(), object.detectRange.left(), detectRange.right(), object.detectRange.right()};
            Arrays.sort(intsX);
            int[] intsY = {detectRange.top(), object.detectRange.top(), detectRange.bottom(), object.detectRange.bottom()};
            Arrays.sort(intsY);
            return new Rect(intsX[1], intsY[1], intsX[2] - intsX[1], intsY[2] - intsY[1]);
        }
        return null;
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

    //物件是否涵蓋傳入的物件
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

    public final boolean isCovering(int x, int y) {
        return (this.painter().left() <= x && x <= this.painter().right() && this.painter().top() <= y && y <= this.painter().bottom());
    }

    public final Rect detectRange() {
        return detectRange;
    }

    public final Rect painter() {
        return painter;
    }

    //TODO: 行走邊界還沒搞定QQ 2022/04/02 19:20

    /**
     * 如果碰到視窗頂端
     *
     * @return true 碰到 false 沒碰到
     */

    public boolean touchTop() {
        return detectRange.top() <= Global.MAP_TOP;
    }

    /**
     * 如果碰到視窗底部
     *
     * @return true 碰到 false 沒碰到
     */

    public boolean touchBottom() {
        return detectRange.bottom() >= Global.MAP_BOTTOM;
    }

    /**
     * 如果碰到視窗左邊
     *
     * @return true 碰到 false 沒碰到
     */

    public boolean touchLeft() {
        return detectRange.left() <= Global.MAP_LEFT;
    }

    /**
     * 如果碰到視窗右邊
     *
     * @return true 碰到 false 沒碰到
     */

    public boolean touchRight() {

        if(Global.SUM_OF_CAMERA_MOVE_VX < 0){
            return detectRange.right() >= (Global.SUM_OF_CAMERA_MOVE_VX * -1) + Global.SCREEN_WIDTH - Global.BUILDING_OPTION_WIDTH;
        } else {
            return detectRange.right() >= Global.SCREEN_WIDTH - Global.BUILDING_OPTION_WIDTH;
        }
    }

    public void shutDetectRange() {
        tmpDetectRange = detectRange.clone();
        detectRange = new Rect(0, 0, 0, 0);
    }

    public void openDetectRange() {
        detectRange = tmpDetectRange.clone();
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
    public boolean touchLeftOf(GameObject gameObject) {
        return (this.painter.right() >= gameObject.painter().left() && this.painter.left() < gameObject.painter().left() && this.isHorizontalParallelTo(gameObject));
    }

    //檢查是否碰到某物件的右邊
    public boolean touchRightOf(GameObject gameObject) {
        return (this.painter.left() <= gameObject.painter().right() && this.painter.right() > gameObject.painter().right() && this.isHorizontalParallelTo(gameObject));
    }

    //檢查是否碰到某物件的上方
    public boolean touchTopOf(GameObject gameObject) {
        return (this.painter().bottom() >= gameObject.painter().top() && this.painter().top() < gameObject.painter().top() && this.isVerticalParallel(gameObject));
    }

    //檢查是否碰到某物件的下方
    public boolean touchBottomOf(GameObject gameObject) {
        return (this.painter().top() <= gameObject.painter.bottom() && this.painter().bottom() > gameObject.painter().bottom() && this.isVerticalParallel(gameObject));
    }

    //檢查是否在水平方向上有重疊
    public boolean isHorizontalParallelTo(GameObject gameObject) {
        return (this.painter().top() < gameObject.painter().bottom() && this.painter().bottom() > gameObject.painter().top());
    }

    //檢查是否在垂直方向上有重疊
    private boolean isVerticalParallel(GameObject gameObject) {
        return (this.painter().left() < gameObject.painter().right() && this.painter().right() > gameObject.painter().left());
    }


    /**
     * 設定能不能看見這個物件
     *
     * @param visible true => 能看見 / false => 不能看見
     */

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    /**
     * 獲得能否看見這個物件
     *
     * @return true => 能看見 / false => 不能看見
     */

    public boolean getVisible() {
        return isVisible;
    }

    /**
     * 取得要向量 && 移動
     */
    public void cameraMove() {

        translate(Global.CAMERA_MOVE_VX, Global.CAMERA_MOVE_VY);
    }

    /**
     * 重製視窗回初始位置
     */
    public void resetObjectXY() {

        detectRange.moveToPoint(originalX, originalY);
        painter.moveToPoint(originalX, originalY);

    }

    public void setBuildingOriginalX(int sumOfVX) {
        originalX -= sumOfVX;
    }

    public void setBuildingOriginalY(int sumOfVY) {
        originalY -= sumOfVY;
    }

}
