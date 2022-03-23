package company.gameobj;

import company.controllers.SceneController;
import company.gametest9th.utils.Path;

import java.awt.*;

public class Box extends GameObject {

    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private Image img;

    public Box() {

        super(0, 0, 0, 0);

        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().box());
    }

    @Override
    public void paintComponent(Graphics g) {
        // 畫出框選的框框
        g.drawImage(img, startX, startY, endX, endY, 0, 0, img.getWidth(null), img.getHeight(null), null);
    }

    @Override
    public void update() {

        //因為碰撞體都是從左上往右下畫,所以 起點X,Y 一錠都是比較小的那個
        int topLeftX = (startX < endX)? startX : endX;
        int topLeftY = (startY < endY)? startY : endY;

        //設定左上角那個點
        offset(topLeftX, topLeftY);

        painter().scaleX(Math.abs(endX - startX));
        painter().scaleY(Math.abs(endY - startY));


    }

    public void setStartXY(int x, int y) {
        startX = x;
        startY = y;
    }

    public void setEndXY(int x, int y) {
        endX = x;
        endY = y;
    }
}
