package company.gameObj;

import company.Global;
import company.controllers.SceneController;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.EventListener;

public class RockFactory extends  GameObject implements CommandSolver.MouseCommandListener {

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

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        if(state== CommandSolver.MouseState.DRAGGED){
            painter().offset(e.getX(),e.getY());
        }
    }

}
