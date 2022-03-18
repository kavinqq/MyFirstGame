package company.gameObj;

import company.Global;
import company.controllers.SceneController;
import company.gametest9th.utils.Path;

import java.awt.*;

public class RockFactory extends  GameObject{

    private Image img;

    public RockFactory(int x, int y, int width, int height) {
        super(x, y, width, height);
        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().RockFactory());
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, painter().left() , painter().top(),  painter().width(), painter().height(), null);
    }

    @Override
    public void update() {

    }
}
