package company.gameObj.buildings;

import company.controllers.SceneController;
import company.gameObj.GameObject;
import company.gametest9th.utils.Path;

import java.awt.*;

public class Base extends GameObject {

    private Image img;

    public Base(int x, int y, int width, int height) {

        super(x, y, width, height);

        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Base());
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, painter().left(), painter().top(), painter().width(), painter().height(), null);
    }

    @Override
    public void update() {

    }
}
