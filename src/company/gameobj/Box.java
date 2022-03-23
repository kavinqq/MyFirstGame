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
        if(startX != -1 && startY != -1) {
            g.drawImage(img, startX, startY, endX, endY, 0, 0, img.getWidth(null), img.getHeight(null), null);
        }
    }

    @Override
    public void update() {

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
