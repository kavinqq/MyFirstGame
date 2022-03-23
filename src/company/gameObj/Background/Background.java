package company.gameObj.Background;

import company.controllers.SceneController;
import company.gameObj.GameObject;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.GameKernel;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;

import static company.Global.SCREEN_X;
import static company.Global.SCREEN_Y;

public class Background extends GameObject implements GameKernel.GameInterface,CommandSolver.MouseCommandListener  {

    private Image bg_img;

    public Background(int x, int y, int width, int height) {
        super(x, y, width, height);
        bg_img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().background());
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(bg_img, painter().left(), painter().top(), SCREEN_X, SCREEN_Y, null);
    }

    @Override
    public void update() {

    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
//        if(state== CommandSolver.MouseState.DRAGGED){
//            offset(e.getX(),e.getY());
//        }

    }

}