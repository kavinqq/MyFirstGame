package company.scene;

import company.Global;
import company.controllers.SceneController;
import company.gameObj.Aircraft;
import company.gameObj.CBoom;
import company.gameObj.CEnemy;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * 飛機場景
 */
public class MainScene extends Scene {

    private ArrayList<CEnemy> enemies;
    private Aircraft plane;
    private ArrayList<CBoom> bullets;

    private Image img;

    @Override
    public void sceneBegin() {
        enemies = new ArrayList<>();
        plane = new Aircraft(10, 400);
        bullets = new ArrayList<>();
    }

    @Override
    public void sceneEnd() {
//        enemies = null;
//        plane = null;
//        bullets = null;
        SceneController.getInstance().imageController().clear();
    }

    @Override
    public void paint(Graphics g) {
        plane.paint(g);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).paint(g);
        }
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
    }

    @Override
    public void update() {
        plane.update();
        if (Global.random(0, 14) == 0 && enemies.size() < 15) {
            enemies.add(new CEnemy(Global.random(0, 600), 0, Global.random(-5, 5)));
        }

        for (int i = 0; i < enemies.size(); i++) {
            if (!enemies.get(i).move()) {
                enemies.remove(i--);
                continue;
            }
            if (plane.isCollision(enemies.get(i))) {

                //SceneController.getInstance().change(new GameOverScene());
                continue;
            }
        }

        for (int i = 0; i < bullets.size(); i++) {
            CBoom bullet = bullets.get(i);
            bullet.update();
            if (bullet.getState() != CBoom.State.NORMAL) {
                if (bullet.getState() == CBoom.State.REMOVED) {
                    bullets.remove(i--);
                }
                continue;
            }
            if (bullet.touchTop()) {
                bullets.remove(i--);
                continue;
            }
            for (int j = 0; j < enemies.size(); j++) {

                if (bullet.isCollision(enemies.get(j))) {
                    bullet.setState(CBoom.State.BOOM);
                    enemies.remove(j);
                    break;
                }
            }
        }
    }

    @Override
    public CommandSolver.MouseCommandListener mouseListener() {
        return (e, state, trigTime) -> {
            plane.mouseTrig(e, state, trigTime);
            if (state == CommandSolver.MouseState.PRESSED) {
                bullets.add(new CBoom(plane.painter().left(), plane.painter().top()));
            }
        };
    }

    @Override
    public CommandSolver.KeyListener keyListener() {
        return null;
    }
}
