package company.scene;

import company.controllers.ImageResourceController;
import company.gametest9th.utils.*;

import java.awt.*;

/**
 * 場景的樣版
 */

public abstract class Scene {

    public abstract void sceneBegin();

    public abstract void sceneEnd();

    public abstract void paint(Graphics g);

    public abstract void update();

    public abstract CommandSolver.MouseCommandListener mouseListener();

    public abstract CommandSolver.KeyListener keyListener();
}
