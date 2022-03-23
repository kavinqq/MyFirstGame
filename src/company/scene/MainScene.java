package company.scene;

import company.Global;
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
public class MainScene extends Scene implements CommandSolver.KeyListener {

    private Image img;

    private RockFactory rockFactory;

    private Base base;

    private Citizen citizen;
    private Citizen currentCitizen;
    private boolean canControlCitizen;

    private boolean isBoxSelect;
    private int boxSelectStartX;
    private int boxSelectStartY;
    private int boxSelectEndX;
    private int boxSelectEndY;

    private Foundation[][] foundation = new Foundation[BUILDING_AMOUNT_X][BUILDING_AMOUNT_Y];

    private Road road;


    @Override
    public void sceneBegin() {
        img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().background());

        for (int i = 0; i < BUILDING_AMOUNT_X; i++) {

            for (int j = 0; j < BUILDING_AMOUNT_Y; j++) {
                foundation[i][j] = new Foundation(
                        FOUNDATION_WIDTH + LAND_X + i * FOUNDATION_WIDTH * 2,
                        FOUNDATION_HEIGHT + LAND_Y + j * FOUNDATION_HEIGHT * 2,
                        FOUNDATION_WIDTH,
                        FOUNDATION_HEIGHT);
            }

        }

        base = new Base(SCREEN_X / 2 - (BUILDING_WIDTH + 120), SCREEN_Y / 2 - (BUILDING_HEIGHT), BUILDING_WIDTH + 100, BUILDING_HEIGHT + 100);

        rockFactory = new RockFactory(LAND_X, LAND_Y, BUILDING_WIDTH, BUILDING_HEIGHT);

        citizen = new Citizen(200, 250, 7, Animator.State.WALK);

        currentCitizen = citizen;
        canControlCitizen = false;

        isBoxSelect = false;
        boxSelectStartX = 0;
        boxSelectStartY = 0;
        boxSelectEndX = 0;
        boxSelectEndY = 0;
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
        g.drawRect(BUILDING_OPTION_X, BUILDING_OPTION_Y, BUILDING_OPTION_WIDTH, BUILDING_OPTION_HEIGHT);

        //採石場
        rockFactory.paint(g);

        g.setColor(Color.black);
        g.drawRect(LAND_X, LAND_Y, LAND_WIDTH, LAND_HEIGHT);

        //test人物
        citizen.paint(g);

        if (isBoxSelect) {
            g.setColor(Color.PINK);
            g.fillRect(boxSelectStartX, boxSelectStartY, boxSelectEndX - boxSelectStartX, boxSelectEndY - boxSelectStartY);
            g.setColor(Color.black);
        }
    }

    @Override
    public void update() {
        // 更新 村民狀態
        citizen.update();

        currentCitizen.mouseToMove();
    }


    private boolean canCatchRockFactory;


    @Override
    public CommandSolver.MouseCommandListener mouseListener() {

        return (e, state, trigTime) -> {


            if (state == null) {
                return;
            }

            switch (state) {

                case CLICKED: {
//                    System.out.println("CLICKED");

                    if (e.getX() > currentCitizen.painter().left() && e.getX() < currentCitizen.painter().right()
                            && e.getY() > currentCitizen.painter().top() && e.getY() < currentCitizen.painter().bottom()) {

                        canControlCitizen = true;
                    }


                    break;
                }


                case MOVED: {

                    canCatchRockFactory = rockFactory.isClicked(e.getX(), e.getY());

                    break;
                }

                case DRAGGED: {
//                    System.out.println("DRAGGED");

                    if (canCatchRockFactory) {
                        rockFactory.mouseTrig(e, state, trigTime);
                    }

                    if (isBoxSelect) {
                        boxSelectEndX = e.getX();
                        boxSelectEndY = e.getY();
                    }

                    break;
                }

                case RELEASED: {
//                    System.out.println("RELEASED");

                    canCatchRockFactory = false;
                    isBoxSelect = false;
                    boxSelectEndX = 0;
                    boxSelectEndY = 0;
                    break;
                }

                case PRESSED: {
//                    System.out.println("PRESSED");

                    if (canControlCitizen) {
                        currentCitizen.setTarget(e.getX(), e.getY());
                    }

                    //
                    if (!(e.getX() > currentCitizen.painter().left() && e.getX() < currentCitizen.painter().right()
                            && e.getY() > currentCitizen.painter().top() && e.getY() < currentCitizen.painter().bottom())) {

                        isBoxSelect = true;
                        boxSelectStartX = e.getX();
                        boxSelectStartY = e.getY();
                    }


                    break;
                }

                case ENTERED: {
                    System.out.println("ENTER");
                    break;
                }

                case EXITED: {
                    System.out.println("EXIT");
                    break;
                }

                case WHEEL_MOVED: {
                    System.out.println("WHEEL_MOVED");
                    break;
                }
            }
        };
    }


    @Override
    public CommandSolver.KeyListener keyListener() {
        return this;
    }

    @Override
    public void keyPressed(int commandCode, long trigTime) {
        if (commandCode == Global.UP || commandCode == Global.DOWN || commandCode == Global.LEFT || commandCode == Global.RIGHT) {
            citizen.keyToMove(commandCode);
        }
    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {

    }

    @Override
    public void keyTyped(char c, long trigTime) {

    }
}
