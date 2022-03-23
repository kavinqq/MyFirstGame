package company.gameobj.background.component;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.buildings.Base;
import company.gameobj.buildings.Building;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.GameKernel;
import company.gametest9th.utils.Path;

import java.awt.*;
import java.awt.event.MouseEvent;

import static company.Global.*;

public class BuildingOption implements GameKernel.GameInterface, CommandSolver.MouseCommandListener {
    private boolean canCatchBuilding;

    private BuildingButton[] buildingButtons = new BuildingButton[BuildingTypeNum];

    Building[] buildings = new Building[BuildingTypeNum]; //以後放在buildingSystem

    Image[] imgs = new Image[BuildingTypeNum];

    Image foundation_img;

    public BuildingOption() {
        imgs[0] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().House());
        imgs[1] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Lab());
        imgs[2] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Sawmill());
        imgs[3] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Steelmill());
        imgs[4] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Barracks());
        imgs[5] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Gasmill());
        imgs[6] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Arsenal());
        imgs[7] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().AirplanemIll());

        //將上方結合BuildingSystem
        for (int i = 0; i < Global.BuildingTypeNum; i++) {

        }

        //新增按鈕元件、給予按鈕圖片
        for (int i = 0; i < BuildingTypeNum; i++) {
            buildingButtons[i] = new BuildingButton(BUILDING_OPTION_X + OPTION_GAP_X, BUILDING_OPTION_Y + OPTION_GAP_Y + (FOUNDATION_HEIGHT + OPTION_GAP_Y) * i);
            buildingButtons[i].setImg(imgs[i]);
        }


        //以後放在buildingSystem
        buildings[0] = new Base(80, 80, 100, 100);


        foundation_img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().foundation());
    }


    @Override
    public void paint(Graphics g) {
        //paintComponent(g);
        for (int i = 0; i < BuildingTypeNum; i++) {
            //畫基地
            g.drawImage(foundation_img, BUILDING_OPTION_X + OPTION_GAP_X, BUILDING_OPTION_Y + OPTION_GAP_Y + (FOUNDATION_HEIGHT + OPTION_GAP_Y) * i
                    , FOUNDATION_WIDTH, FOUNDATION_HEIGHT, null);

            //畫按鈕
            buildingButtons[i].paint(g);
        }
    }


    @Override
    public void update() {

    }


    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        if (state == CommandSolver.MouseState.ENTERED) {
            System.out.println("testEnter");
        }
        for (int i = 0; i < BuildingTypeNum; i++) {
            buildingButtons[i].mouseTrig(e, state, trigTime);
        }
    }

    //public abstract void paintComponent(Graphics g);
}
