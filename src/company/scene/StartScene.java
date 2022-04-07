package company.scene;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.MenuChoice;
import company.gameobj.creature.human.Human;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * 一開始的選單
 */

public class StartScene extends Scene {

    // 背景圖片
    private Image img;

    @Override
    public void sceneBegin() {
        // 載入背景圖
        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().gameStart());

    }

    @Override
    public void sceneEnd() {

        img = null;
    }


    @Override
    public void paint(Graphics g) {

        g.drawImage(img, 0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT, null);

    }

    @Override
    public void update() {

    }


    @Override
    public CommandSolver.KeyListener keyListener() {
        return null;
    }

    @Override
    public CommandSolver.MouseCommandListener mouseListener() {
        return (e, state, trigTime) -> {

            if (state == null) {
                return;
            }

            switch (state) {
                case PRESSED: {

                    // 開始畫面選單
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (e.getX() >= 731 && e.getX() <= 1132 && e.getY() >= 455 && e.getY() <= 552) {
                            SceneController.getInstance().change(new MainScene());
                        }

                        if (e.getX() >= 731 && e.getX() <= 1132 && e.getY() >= 614 && e.getY() <= 710) {
                            System.exit(0);
                        }
                    }
                }
            }
        };
    }
}
