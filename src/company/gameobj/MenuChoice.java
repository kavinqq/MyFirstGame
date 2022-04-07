package company.gameobj;

import company.controllers.SceneController;
import company.gametest9th.utils.Path;

import java.awt.*;

public class MenuChoice extends GameObject{

    public enum Option{
        START,
        EXIT,
    }

    private Option option;
    private Image imgStart;
    private Image imgExit;

    public MenuChoice(int x, int y, int width, int height,Option option) {
        super(x, y, width, height);

        this.option = option;

        imgStart = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().start());
        imgExit = SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().exit());
    }

    @Override
    public void paintComponent(Graphics g) {

        if(option == Option.START){
            g.drawImage(imgStart, detectRange().left(),  detectRange().top(), detectRange().width(), detectRange().height(),  null );
        }

        if(option == Option.EXIT){
            g.drawImage(imgExit, detectRange().left(),  detectRange().top(), detectRange().width(), detectRange().height(),  null );
        }
    }

    @Override
    public void update() {

    }

    public Option getOption(){
        return option;
    }
}
