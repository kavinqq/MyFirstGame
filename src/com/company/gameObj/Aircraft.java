package com.company.gameObj;

import com.company.Global;
import com.company.controllers.ImageResourceController;
import com.company.controllers.SceneController;
import com.company.gameObj.GameObject;
import com.company.gametest9th.utils.CommandSolver;
import com.company.gametest9th.utils.GameKernel;
import com.company.gametest9th.utils.Path;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Aircraft extends GameObject implements GameKernel.GameInterface, CommandSolver.MouseCommandListener {

    public Aircraft(int x, int y) {
        super(x, y, 55, 55, x, y, 50, 50);
        dir = Global.Direction.RIGHT;
        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().actors().aircraft());
    }

    private Image img;
    private Global.Direction dir;

    public void move() {
        if (dir == Global.Direction.RIGHT && !touchRight()) {
            translateX(4);
        } else if (dir == Global.Direction.LEFT && !touchLeft()) {
            translateX(-4);
        }
    }

    public void changeDir(int x) {
        if (painter().left() > x) {
            dir = Global.Direction.LEFT;
        } else {
            dir = Global.Direction.RIGHT;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, painter().left(), painter().top(), painter().width(), painter().height(), null);
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        if (state == CommandSolver.MouseState.MOVED) {
            changeDir(e.getX());
        }
    }
}
