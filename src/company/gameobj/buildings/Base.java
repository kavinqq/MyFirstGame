package company.gameobj.buildings;

import company.controllers.SceneController;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Base extends Building {
    public static final int BASE_WIDTH = 120;
    public static final int BASE_HEIGHT = 120;

    public Base(int x, int y) {
        super(x,y,BASE_WIDTH, BASE_HEIGHT, new Path().img().building().Base());
    }

//    public Base(int x, int y, int j, int k) {
//        super(x,y,BASE_WIDTH, BASE_HEIGHT, new Path().img().building().Base());
//    }
//

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
