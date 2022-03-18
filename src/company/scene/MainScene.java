package company.scene;

import company.Global;
import company.controllers.SceneController;
import company.gameObj.Foundation;
import company.gameObj.Road;
import company.gameObj.RockFactory;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Delay;
import company.gametest9th.utils.GameKernel;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 遊戲主場景
 */
public class MainScene extends Scene {

    private Image img;

    private RockFactory rockFactory;
    private Foundation foundation;
    private Road road;

    @Override
    public void sceneBegin() {
        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().background());

        rockFactory = new RockFactory(370,220,110,130);

        foundation = new Foundation(370,220,120,120);

//        road = new Road()
    }

    @Override
    public void sceneEnd() {
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, Global.SCREEN_X, Global.SCREEN_Y, null);

        foundation.paint(g);
        rockFactory.paint(g);



        g.setColor(Color.black);
        g.drawRect(Global.SCREEN_X / 2 - 650, Global.SCREEN_Y / 2 - 350, 1300,700);


    }

    @Override
    public void update() {
    }
    private boolean isCatchrockFactory=false;
    @Override
    public CommandSolver.MouseCommandListener mouseListener() {
        return (e, state, trigTime) -> {
            if(state==CommandSolver.MouseState.MOVED){
                isCatchrockFactory=rockFactory.isClicked(e.getX(),e.getY());
            }
            if (state == CommandSolver.MouseState.DRAGGED) {
                if (isCatchrockFactory) {
                    rockFactory.mouseTrig(e, state, trigTime);
                }
            }
            if(state==CommandSolver.MouseState.RELEASED){
                isCatchrockFactory=false;
            }
        };
    }


    @Override
    public CommandSolver.KeyListener keyListener() {
        return null;
    }

}
