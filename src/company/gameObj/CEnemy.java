package company.gameObj;

import company.controllers.ImageResourceController;
import company.controllers.SceneController;
import company.gameObj.GameObject;
import company.gametest9th.utils.Delay;
import company.gametest9th.utils.GameKernel;
import company.gametest9th.utils.Path;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CEnemy extends GameObject implements GameKernel.GameInterface {
    private int d;
    private static Image img;

    public CEnemy(int x, int y, int d) {
        super(x, y, 50, 50);
        this.d = d;
        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().actors().enemy());
    }

    public boolean move() {
        translateY(4);
        translateX(d);
        return painter().top() <= 600;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, painter().left(), painter().top(), painter().width(), painter().height(), null);
    }

    @Override
    public void update() {

    }
}
