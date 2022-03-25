package company.gameobj.background.component;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.background.HintDialog;
import company.gameobj.buildings.Base;
import company.gameobj.buildings.Building;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.GameKernel;
import company.gametest9th.utils.Path;
import static company.gameobj.BuildingController.*;

import java.awt.*;
import java.awt.event.MouseEvent;

import static company.Global.*;

public class BuildingOption implements GameKernel.GameInterface, CommandSolver.MouseCommandListener {

    private BuildingButton[] buildingButtons ;

    private boolean canUseButton;

    Image[] imgs;

    Image foundation_img;

    //為靜態類別，可取得Building相關訊息
    BuildingType type;

    public BuildingOption() {

        buildingButtons= new BuildingButton[BuildingTypeNum];//建立所有建築物按鈕

        //將以下結合BuildingSystem
        imgs = new Image[BuildingTypeNum];
        imgs[0] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().House());
        imgs[1] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Lab());
        imgs[2] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().SawMill());
        imgs[3] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().SteelMill());
        imgs[4] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Barracks());
        imgs[5] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().GasMill());
        imgs[6] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().Arsenal());
        imgs[7] = SceneController.getInstance().imageController().tryGetImage(new Path().img().building().AirplanemIll());


        for (int i = 0; i < Global.BuildingTypeNum; i++) {

        }

        //新增按鈕元件、給予按鈕圖片
        for (int i = 0; i < BuildingTypeNum; i++) {
            buildingButtons[i] = new BuildingButton(BUILDING_OPTION_X +DIV_GAP_X, BUILDING_OPTION_Y + DIV_GAP_Y + (FOUNDATION_HEIGHT + OPTION_GAP_Y) * i);
            buildingButtons[i].setImg(imgs[i]);
            buildingButtons[i].setId(i+1);
        }

        //新增地基圖片
        foundation_img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().foundation());
    }


    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < BuildingTypeNum; i++) {
            //畫基地
            g.drawImage(foundation_img, BUILDING_OPTION_X, BUILDING_OPTION_Y  + (FOUNDATION_HEIGHT + OPTION_GAP_Y) * i
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
        canUseButton=false;
        //是否所有都false
        for (int i=0;i<BuildingTypeNum;i++){
            canUseButton=canUseButton|buildingButtons[i].isCanUseButton();
        }
        if(canUseButton){
            HintDialog.instance().setHintMessage("");
        }
        //呼叫所有建築物按鈕
        for (int i = 0; i < BuildingTypeNum; i++) {

            //得到建築物Id
            type = BuildingType.getBuildingTypeByInt(buildingButtons[i].getId());
            //改變按鈕的內容
            buildingButtons[i].bni=new BuildingButton.ButtonInterface() {
                @Override
                public int entered(BuildingButton bb) {
                    //System.out.println(type.instance().getName());
                    HintDialog.instance().setHintMessage(type.instance().getName());
                    return 0;
                }
            };


            //呼叫按鈕的滑鼠功能(傳入現在的滑鼠監聽)
            buildingButtons[i].mouseTrig(e, state, trigTime);
        }
    }
}
