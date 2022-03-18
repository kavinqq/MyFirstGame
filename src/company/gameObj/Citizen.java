package company.gameObj;

import company.Global;
import company.controllers.SceneController;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.HumanAnimator;
import company.gametest9th.utils.Path;

import javax.swing.plaf.nimbus.State;
import java.awt.*;

public class Citizen extends GameObject{

    private Image img;

    private Global.Direction dir;
    private int type;
    private Animator.State state;

    private HumanAnimator animator;

    public Citizen(int x, int y, int type, Animator.State state) {
        super(x, y, 64, 64);

        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().actors().Actor2());

        dir = Global.Direction.DOWN;

        this.type = type;

        this.state = state;

        animator = new HumanAnimator(type, state);
    }

    @Override
    public void paintComponent(Graphics g) {
        animator.paint(dir, painter().left(), painter().top(), painter().right(), painter().bottom(), g);
    }

    @Override
    public void update() {
        animator.update();
    }

    public void changeDir(int dirNum){
        if(dirNum == Global.UP){
            dir = Global.Direction.UP;
            painter().translateY(-4);
            collider().translateY(-4);
        }

        if(dirNum == Global.DOWN){
            dir = Global.Direction.DOWN;
            painter().translateY(4);
            collider().translateY(4);
        }

        if(dirNum == Global.LEFT){
            dir = Global.Direction.LEFT;
            painter().translateX(-4);
            collider().translateX(-4);
        }

        if(dirNum == Global.RIGHT){
            dir = Global.Direction.RIGHT;
            painter().translateX(4);
            collider().translateX(4);
        }

    }
}
