package com.company.gameObj;

import com.company.controllers.SceneController;
import com.company.gametest9th.utils.Path;

import java.awt.*;

public class BatmanDart extends GameObject{

    private Image img;

    public BatmanDart(int x, int y, int width, int height) {
        super(x, y, width, height);
        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().batmanDart());
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, painter().left(), painter().top(), painter().width(), painter().height(), null);
    }

    @Override
    public void update() {

    }
}
