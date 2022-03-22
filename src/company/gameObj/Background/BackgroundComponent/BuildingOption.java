package company.gameObj.Background.BackgroundComponent;

import company.Global;
import company.controllers.SceneController;
import company.gameObj.GameObject;
import company.gameObj.Road;
import company.gameObj.building.Base;
import company.gameObj.building.Building;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.GameKernel;
import company.gametest9th.utils.Path;
import oldbuildings.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import static company.Global.*;

public class BuildingOption implements GameKernel.GameInterface {
    private boolean canCatchBuilding;
    Building[] buildings=new Building[8];
    Image[] imgs=new Image[Global.BuildingTypeNum];
    Image foundation_img;
    public BuildingOption() {
        imgs[0]= SceneController.getInstance().imageController().tryGetImage(new Path().img().building().House());
        imgs[1]= SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Lab());
        imgs[2]= SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Sawmill());
        imgs[3]= SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Steelmill());
        imgs[4]= SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Barracks());
        imgs[5]= SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Gasmill());
        imgs[6]= SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Arsenal());
        imgs[7]= SceneController.getInstance().imageController().tryGetImage(new Path().img().building().AirplanemIll());


        buildings[0]=new Base(80,80,100,100);

        //將上方結合BuildingSystem
        for (int i = 0; i < Global.BuildingTypeNum; i++) {

        }
        foundation_img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().foundation());
    }

    @Override
    public void paint(Graphics g) {
        //paintComponent(g);
        for (int i = 0; i < 8; i++) {
            g.drawImage(foundation_img,BUILDING_OPTION_X+OPTION_GAP_X,BUILDING_OPTION_Y+(FOUNDATION_HEIGHT+OPTION_GAP_Y)*i
                    ,FOUNDATION_WIDTH,FOUNDATION_HEIGHT,null);
            g.drawImage(imgs[i], BUILDING_OPTION_X+OPTION_GAP_X, BUILDING_OPTION_Y+(FOUNDATION_HEIGHT+OPTION_GAP_Y)*i
                    ,BUILDING_WIDTH,BUILDING_HEIGHT,null);
        }


    }

    @Override
    public void update() {

    }

    //public abstract void paintComponent(Graphics g);
}
