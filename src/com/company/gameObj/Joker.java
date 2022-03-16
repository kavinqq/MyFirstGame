package com.company.gameObj;

import com.company.Global;
import com.company.controllers.SceneController;
import com.company.gametest9th.utils.Delay;
import com.company.gametest9th.utils.Path;

import java.awt.*;

public class Joker extends GameObject{

    public enum Status{
        Normal,
        Invincible
    }

    private int jokerSpeed;

    private Image img;

    private Status status;

    private Delay delay;

    public Joker(int x, int y, int width, int height) {
        super(x, y, width, height);

        jokerSpeed = 4;

        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().actors().joker());

        status = Status.Normal;

        delay = new Delay(120);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, painter().left(), painter().top(), painter().width(), painter().height(), null);
        if(status == Status.Invincible){
            g.setColor(Color.yellow);
            g.drawRect(collider().left() - collider().width() / 2,
                    collider().top() - collider().height() / 2,
                    collider().width() + 50,
                    collider().height() + 50);
        }
    }

    @Override
    public void update() {

    }

    public void update(int gameTime, int jokerDestination){
        if(gameTime > 0 && gameTime % 10 == 0){
            status = Status.Invincible;
            delay.play();
        }

        if(delay.count()){
            status = Status.Normal;
        }

        if(collider().left() < jokerDestination) {
            translateX(jokerSpeed);
        }
        if(collider().left() > jokerDestination) {
            translateX(jokerSpeed * -1);
        }

    }

    public void move(int num){
        translateX(num);
    }

    public Status getStatus(){
        return status;
    }
}
