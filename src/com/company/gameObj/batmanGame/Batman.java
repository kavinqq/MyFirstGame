package com.company.gameObj.batmanGame;

import com.company.controllers.SceneController;
import com.company.gameObj.GameObject;
import com.company.gametest9th.utils.Path;

import java.awt.*;

public class Batman extends GameObject {
    public Batman(int x, int y) {
        super(x, y, 50, 50);
        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().actors().batman());
    }

    /**
     * 要畫的圖片
     */
    private Image img;

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, painter().left(), painter().top(), painter().width(), painter().height(), null);
    }

    @Override
    public void update() {

    }
}
