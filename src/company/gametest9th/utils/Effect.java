package company.gametest9th.utils;

import company.controllers.AudioResourceController;
import company.controllers.SceneController;
import company.gameobj.GameObject;

import java.awt.*;

public abstract class Effect extends GameObject {
    private Delay totalDelay;
    private Delay delay;
    private Image img;
    private boolean show;

    public Effect(int x,int y, int width, int height,Delay totalDelay, Delay delay,String img){
        super(x,y,width,height);
        this.totalDelay = totalDelay;
        totalDelay.play();
        this.delay = delay;
        delay.loop();
        this.img = SceneController.getInstance().imageController().tryGetImage(img);
        this.show = true;
    }

    @Override
    public void paintComponent(Graphics g) {

        if(delay.count()){
            show = !show;
        }
        if(show){
            g.drawImage(img,this.painter().left(), this.painter().top(),null);

            AudioResourceController.getInstance().play(new Path().sound().fight());
        }
    }

    @Override
    public void update() {

    }

    public boolean isDue(){
        return this.totalDelay.count();
    }
}
