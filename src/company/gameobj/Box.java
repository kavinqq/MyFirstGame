package company.gameobj;

import company.Global;
import company.controllers.SceneController;
import company.gametest9th.utils.Path;
import static company.Global.*;

import java.awt.*;

public class Box extends GameObject {

    private Image img;  // Box圖片
    private int startX; // 起點X
    private int startY; // 起點Y
    private int endX;   // 終點X
    private int endY;   // 終點Y

    public Box() {

        super(0, 0, 0, 0);// 預設畫不出來 都是0

        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().box());// 載入圖片
    }


    /**
     * 設定起點
     * @param startX 起點X
     * @param startY 起點Y
     */

    public void setStart(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
    }


    /**
     * 這定終點
     * @param endX 終點X
     * @param endY 終點Y
     */

    public void setEnd(int endX, int endY) {

        endX = Math.max(endX, 0);
        endX = Math.min(endX, WINDOW_WIDTH - BUILDING_OPTION_WIDTH);

        endY = Math.max(endY, STATUS_BAR_HEIGHT);
        endY = Math.min(endY, WINDOW_HEIGHT);

        this.endX = endX;
        this.endY = endY;
    }




    @Override
    public void paintComponent(Graphics g) {

        // 畫出框選的框框
        g.drawImage(img, startX, startY, endX, endY, 0, 0, img.getWidth(null), img.getHeight(null), null);
    }

    @Override
    public void update() {

        //因為碰撞體都是從左上往右下畫,所以 起點X,Y 一定都是比較小的那個
        int topLeftX = Math.min(startX, endX);
        int topLeftY = Math.min(startY, endY);

        //設定左上角那個點
        setPainterStartFromTopLeft(topLeftX, topLeftY);

        //painter 的長寬
        painter().scale(Math.abs(endX - startX), Math.abs(endY - startY));
    }
}
