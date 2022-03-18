package company.gameObj;

import company.controllers.SceneController;
import company.gametest9th.utils.Path;

import java.awt.*;

public class Foundation extends  GameObject{

    private Image img;

    public Foundation(int x, int y, int width, int height) {
        super(x, y, width, height);

        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().foundation());
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, collider().left(), collider().top(), collider().width(), collider().height(), null);
    }

    @Override
    public void update() {

    }
}
