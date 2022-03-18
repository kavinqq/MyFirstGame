package company.scene;

import company.controllers.SceneController;
import company.gameObj.Citizen;
import company.gameObj.Foundation;
import company.gameObj.Road;
import company.gameObj.building.Base;
import company.gameObj.building.RockFactory;
import company.gametest9th.utils.Animator;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.Path;

import java.awt.*;

import static company.Global.*;

/**
 * 遊戲主場景
 */
public class MainScene extends Scene {

    private Image img;

    private RockFactory rockFactory;
    private Base base;
    private Citizen citizen;
    private Foundation[][] foundation=new Foundation[BUILDING_AMOUNT_X][BUILDING_AMOUNT_Y];
    private Road road;




    @Override
    public void sceneBegin() {
        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().background());
        for (int i = 0; i < BUILDING_AMOUNT_X; i++) {
//                if(i==2){
//                i++;
//                continue;
//            }
            for (int j = 0; j < BUILDING_AMOUNT_Y; j++) {
                foundation[i][j] = new Foundation(
                        FOUNDATION_WIDTH+ LAND_X + i * FOUNDATION_WIDTH * 2,
                        FOUNDATION_HEIGHT + LAND_Y + j * FOUNDATION_HEIGHT * 2,
                        FOUNDATION_WIDTH,
                        FOUNDATION_HEIGHT );
            }

        }

        base = new Base(SCREEN_X / 2 - (BUILDING_WIDTH + 120), SCREEN_Y /2 - (BUILDING_HEIGHT), BUILDING_WIDTH + 100, BUILDING_HEIGHT + 100);

        rockFactory = new RockFactory(LAND_X, LAND_Y, BUILDING_WIDTH, BUILDING_HEIGHT);

        citizen = new Citizen(200,250,2, Animator.State.STAND);
    }

    @Override
    public void sceneEnd() {
    }

    @Override
    public void paint(Graphics g) {
        // 背景
        g.drawImage(img, 0, 0, SCREEN_X, SCREEN_Y, null);

        //狀態攔
        g.drawRect(STATUS_BAR_X, STATUS_BAR_Y, STATUS_BAR_WEIGHT, STATUS_BAR_HEIGHT);
        for (int i = 0; i < BUILDING_AMOUNT_X; i++) {
            for (int j = 0; j < BUILDING_AMOUNT_Y; j++) {
                foundation[i][j].paint(g);
            }
        }

        // 主堡
        base.paint(g);

        //建築物選單
        g.drawRect(BUILDING_OPTION_X, BUILDING_OPTION_Y,BUILDING_OPTION_WIDTH,BUILDING_OPTION_HEIGHT);

        //採石場
        rockFactory.paint(g);

        g.setColor(Color.black);
        g.drawRect(LAND_X, LAND_Y, LAND_WIDTH, LAND_HEIGHT);

        //test人物
        citizen.paint(g);
    }

    @Override
    public void update() {

        citizen.update();
    }



    private boolean canCatchRockFactory;
    @Override
    public CommandSolver.MouseCommandListener mouseListener() {
        return (e, state, trigTime) -> {
            if(state==CommandSolver.MouseState.MOVED){
                canCatchRockFactory = rockFactory.isClicked(e.getX(),e.getY());
            }
            if (state == CommandSolver.MouseState.DRAGGED) {
                if (canCatchRockFactory) {
                    rockFactory.mouseTrig(e, state, trigTime);
                }
            }
            if(state==CommandSolver.MouseState.RELEASED){
                canCatchRockFactory =false;
            }
        };
    }


    @Override
    public CommandSolver.KeyListener keyListener() {
        return null;
    }

}
