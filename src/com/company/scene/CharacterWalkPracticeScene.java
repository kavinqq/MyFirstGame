package com.company.scene;

import com.company.Global;
import com.company.gameObj.Actor1;
import com.company.gametest9th.utils.CommandSolver;

import java.awt.*;

public class CharacterWalkPracticeScene extends Scene{

    Actor1 actor1;

    @Override
    public void sceneBegin() {
        actor1 = new Actor1(Global.SCREEN_X / 2, Global.SCREEN_Y / 2);
    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public void paint(Graphics g) {
        actor1.paint(g);
    }

    @Override
    public void update() {
        actor1.update();
    }

    @Override
    public CommandSolver.MouseCommandListener mouseListener() {
        return null;
    }

    @Override
    public CommandSolver.KeyListener keyListener() {
        return null;
    }
}
