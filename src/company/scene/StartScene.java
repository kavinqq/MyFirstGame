package company.scene;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.MenuChoice;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * 一開始的選單
 */

public class StartScene extends Scene{

    // 背景圖片
    private Image img;

    // 選單設定值
    private final int MENU_WIDTH = 350;
    private final int MENU_HEIGHT = 450;

    // 選項設定值
    private final int CHOICE_WIDTH =  200;
    private final int CHOICE_HEIGHT = 50;
    private final int CHOICE_GAP = 50;
    private final int CHOICE_Y_DEFAULT = Global.SCREEN_HEIGHT / 2 - MENU_HEIGHT / 2;
    private final Color CHOICE_FILL_COLOR  = Color.PINK;
    private final Color CHOICE_HOVER_COLOR = Color.GREEN;

    // 選項
    private final ArrayList<MenuChoice> choices = new ArrayList<>();

    @Override
    public void sceneBegin() {
        // 載入背景圖
        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().grassBG());

        int choiceCnt = 1;

        choices.add(new MenuChoice(Global.SCREEN_WIDTH / 2 - CHOICE_WIDTH / 2,  CHOICE_Y_DEFAULT + (CHOICE_GAP + CHOICE_HEIGHT) * choiceCnt - CHOICE_HEIGHT + 100
                                    , CHOICE_WIDTH, CHOICE_HEIGHT, "開始遊戲", CHOICE_FILL_COLOR, CHOICE_HOVER_COLOR,MenuChoice.Option.START));
        choiceCnt++;

        choices.add(new MenuChoice(Global.SCREEN_WIDTH / 2 - CHOICE_WIDTH / 2,  CHOICE_Y_DEFAULT + (CHOICE_GAP + CHOICE_HEIGHT) * choiceCnt - CHOICE_HEIGHT + 100
                                    , CHOICE_WIDTH, CHOICE_HEIGHT, "離開遊戲", CHOICE_FILL_COLOR, CHOICE_HOVER_COLOR, MenuChoice.Option.EXIT));
        choiceCnt++;
    }

    @Override
    public void sceneEnd() {

    }


    @Override
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT, null);

//        // start menu
//        g.setColor(Color.blue);
//        g.fill3DRect(Global.SCREEN_X / 2 - MENU_WIDTH / 2, Global.SCREEN_Y / 2 - MENU_HEIGHT / 2, MENU_WIDTH, MENU_HEIGHT, false);

        //Choices
        for(MenuChoice choice: choices){
            choice.paint(g);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public CommandSolver.MouseCommandListener mouseListener() {

       return new CommandSolver.MouseCommandListener() {
           @Override
           public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

               // 滑鼠點擊
               if (state == CommandSolver.MouseState.CLICKED) {

                   // 尋訪一次choices ArrayList
                   for (MenuChoice choice : choices) {
                       System.out.println(choice.getOption());
                       // 如果 滑鼠的 x,y 在 某個選項裡面
                       if (e.getX() >= choice.detectRange().left() && e.getX() <= choice.detectRange().right()
                               && e.getY() >= choice.detectRange().top() && e.getY() <= choice.detectRange().bottom()) {
                           // 如果 該選項的 option值 為 START => 進入主畫面
                           if(choice.getOption() == MenuChoice.Option.START){
                               SceneController.getInstance().change(new MainScene());
                               break;
                           }

                           // 如果 該選項的 option值 為 EXIT => 離開遊戲
                           if(choice.getOption() == MenuChoice.Option.EXIT){
                               System.exit(0);
                               break;
                           }
                       }
                   }
               }
           }
       };
    }

    @Override
    public CommandSolver.KeyListener keyListener() {
        return null;
    }
}
