package company.gameObj;

import company.Global;
import company.controllers.SceneController;
import company.gametest9th.utils.Path;

import java.awt.*;

public class MenuChoice extends GameObject{

    public enum Option{
        START,
        EXIT,
    }


    private String text;
    private Color fillColor;
    private Color hoverColor;
    private Option option;
    private Image imgStart;
    private Image imgExit;

    public MenuChoice(int x, int y, int width, int height,  String text, Color fillColor, Color hoverColor, Option option) {
        super(x, y, width, height);
        this.text = text;
        this.fillColor = fillColor;
        this.hoverColor = hoverColor;
        this.option = option;

        imgStart = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().start());
        imgExit = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().exit());
    }

    @Override
    public void paintComponent(Graphics g) {

        if(option == Option.START){
            g.drawImage(imgStart, collider().left(),  collider().top(), collider().width(), collider().height(),  null );
        }

        if(option == Option.EXIT){
            g.drawImage(imgExit, collider().left(),  collider().top(), collider().width(), collider().height(),  null );
        }
    }

    @Override
    public void update() {

    }

    public Option getOption(){
        return option;
    }
}
