package company.gameobj.buildings;

import company.controllers.SceneController;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Base extends Building {
    private Image img;

    public Base(int x, int y, int width, int height) {

        super(x, y, width, height);
        //System.out.println(painter().left());
        this.img=SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Base());
    }


    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, painter().left(), painter().top(), painter().width(), painter().height(), null);

    }

    @Override
    public void update() {

    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        switch (state){
            case DRAGGED: {
                offset(e.getX(),e.getY());
                break;
            }
        }
    }
}
