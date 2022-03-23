package company.gameobj;

import company.controllers.SceneController;
import company.gametest9th.utils.Path;

import java.awt.*;

public class Road extends GameObject{

    private Image img;

    public Road(int x, int y, int width, int height) {

        super(x, y, width, height);

        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().road());
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, painter().left(), painter().top(), painter().width(), painter().height(),null);
    }

    @Override
    public void update() {

    }
}
