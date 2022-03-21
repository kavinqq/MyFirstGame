package company.gameObj;

import company.Global;
import company.controllers.SceneController;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.HumanAnimator;
import company.gametest9th.utils.Path;

import java.awt.*;

public class Citizen extends GameObject{

    private Image img;

    private Global.Direction dir;
    private int type;
    private Animator.State state;

    private int speed;
    private int targetX;
    private int targetY;
    private boolean canMove;

    private HumanAnimator animator;

    public Citizen(int x, int y, int type, Animator.State state) {

        super(x, y, 64, 64);

        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().actors().Actor2());

        dir = Global.Direction.DOWN;

        this.type = type;

        this.state = state;

        speed = 4;

        animator = new HumanAnimator(type, state);

        targetX = painter().centerX();
        targetY = painter().centerY();

        canMove = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        animator.paint(dir, painter().left(), painter().top(), painter().right(), painter().bottom(), g);
    }

    @Override
    public void update() {
        animator.update();
    }

    public void keyToMove(int dirNum){

        if(dirNum == Global.UP){
            dir = Global.Direction.UP;
            painter().translateY(speed * -1);
            collider().translateY(speed * -1);
        }

        if(dirNum == Global.DOWN){
            dir = Global.Direction.DOWN;
            painter().translateY(speed);
            collider().translateY(speed);
        }

        if(dirNum == Global.LEFT){
            dir = Global.Direction.LEFT;
            painter().translateX(speed * -1);
            collider().translateX(speed * -1);
        }

        if(dirNum == Global.RIGHT){
            dir = Global.Direction.RIGHT;
            painter().translateX(speed);
            collider().translateX(speed);
        }

    }

    public void setTarget(int x, int y){
        System.out.println("SET READY!");

        targetX = x;
        targetY = y;

        canMove = true;
    }

    public void mouseToMove() {
        if(!canMove){
            return;
        }


        if(Math.abs(targetX - painter().centerX()) < speed){
            speed = Math.abs(targetX - painter().centerX());
        }

        if(targetX > painter().centerX()){
            dir = Global.Direction.RIGHT;

            painter().translateX(speed);
            collider().translateX(speed);
        }

        if(targetX < painter().centerX()){
            dir = Global.Direction.LEFT;

            painter().translateX(speed * -1);
            collider().translateX(speed * -1);
        }

        speed = 4;
        // 處理Y

        if(Math.abs(targetY - painter().centerY()) < speed){
            speed = Math.abs(targetY - painter().centerY());
        }

        if(targetY > painter().centerY()){
            dir = Global.Direction.DOWN;

            painter().translateY(speed);
            collider().translateY(speed);
        }

        if(targetY < painter().centerY()){
            dir = Global.Direction.UP;

            painter().translateY(speed * -1);
            collider().translateY(speed * -1);
        }

        speed = 4;

        if(targetX == painter().centerX() && targetY == painter().centerY()){
            canMove = false;
        }
    }

}
