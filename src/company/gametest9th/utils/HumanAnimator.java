package company.gametest9th.utils;

import company.Global;
import company.controllers.SceneController;

import java.awt.*;

public class HumanAnimator extends Animator{

    private Image img;
    private int type;
    private int walkCount;
    private Delay delay;
    private Global.Direction dir;
    private State state;

    public HumanAnimator(int type, State state) {

        this.type = type;

        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().actors().Actor2());

        walkCount = 0;

        this.state = state;

        delay = new Delay(15);
        delay.loop();

        dir = Global.Direction.DOWN;
    }

    @Override
    public void paint(Global.Direction dir, int left, int top, int right, int bottom, Graphics g) {

        g.drawImage(img, left, top, right, bottom,
                (type % 4) * 96 + state.getArr()[walkCount] * 32,
                (type / 4) * 128 + dir.getValue() * 32,
                (type % 4) * 96 + state.getArr()[walkCount] * 32 + 32,
                (type / 4) * 128 + dir.getValue() * 32 + 32,
                null);
    }


    @Override
    public void update(){
        delay.play();

        if(delay.count()){
            walkCount += 1;
            if(walkCount >= state.getArr().length){
                walkCount = 0;
            }
        }
    }

}
