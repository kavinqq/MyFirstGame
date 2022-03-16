package com.company.scene;

import com.company.controllers.ImageResourceController;
import com.company.gametest9th.utils.CommandSolver;

import java.awt.*;

public abstract class Scene {

    public abstract void sceneBegin();

    public abstract void sceneEnd();

    public abstract void paint(Graphics g);

    public abstract void update();

    public abstract CommandSolver.MouseCommandListener mouseListener();

    public abstract CommandSolver.KeyListener keyListener();
}
