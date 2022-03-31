package company.gameobj.background.component;

import company.controllers.SceneController;
import company.gameobj.Rect;
import company.gameobj.message.HintDialog;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.GameKernel;
import company.gametest9th.utils.Path;
import static company.gameobj.BuildingController.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static company.Global.*;

public class BuildingOption implements GameKernel.GameInterface, CommandSolver.MouseCommandListener {

    private ArrayList<BuildingButton> buildingButtons ;

    private boolean isMouseOnButtons;

    Image[] imgs;

    Image foundation_img;

    //為靜態類別，可取得Building相關訊息
    BuildingType type;

    private BuildingButton currentButton;

    private int pressCount;

    public BuildingOption() {
        buildingButtons= new ArrayList(BuildingTypeNum);//建立所有建築物按鈕

        imgs = new Image[BuildingTypeNum];

        //新增按鈕元件、給予按鈕圖片
        for (int i = 0; i < BuildingTypeNum; i++) {
            type = BuildingType.getBuildingTypeByInt(i+1);
            int tmpId=type.instance().getId();
            String tmpPath=type.instance().getImgPath();

            buildingButtons.add(new BuildingButton(BUILDING_OPTION_X +DIV_GAP_X, BUILDING_OPTION_Y + DIV_GAP_Y + (FOUNDATION_HEIGHT + OPTION_GAP_Y) * i,tmpId));
            buildingButtons.get(i).setImg(SceneController.getInstance().imageController().tryGetImage(tmpPath));
        }

        //新增地基圖片
        foundation_img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().buttonFoundation());

    }


    public BuildingButton getCurrentButton(){
        return currentButton;
    }

    //是否所有Button都false
    public boolean checkMouseOnButtons(){
        isMouseOnButtons =false;
        for (int i=0;i<BuildingTypeNum;i++){
            isMouseOnButtons |= buildingButtons.get(i).isMoveOnButton();
        }
        return isMouseOnButtons;
    }


    //取得
    public BuildingButton get(int index){
        return buildingButtons.get(index);
    }

    //總長度
    public int length(){
        return buildingButtons.size();
    }


    @Override
    public void paint(Graphics g) {
        //基地為背景先畫
        for (int i = 0; i < BuildingTypeNum; i++) {
            //畫基地
            g.drawImage(foundation_img, BUILDING_OPTION_X, BUILDING_OPTION_Y + (FOUNDATION_HEIGHT + OPTION_GAP_Y) * i
                    , FOUNDATION_WIDTH, FOUNDATION_HEIGHT, null);
        }
        //建築物後畫
        for (int i = 0; i < BuildingTypeNum; i++) {
            //畫按鈕
            if(currentButton==null){
                buildingButtons.get(i).paint(g);
            }else if(i!=currentButton.getId()-1){
               buildingButtons.get(i).paint(g);
            }
        }
    }


    @Override
    public void update() {
        //移出時不要有文字
        if(!checkMouseOnButtons()){
            HintDialog.instance().setHintMessage("");
        }

        //印出update ，取得當前按鈕
        for (int i = 0; i < BuildingTypeNum; i++) {
            buildingButtons.get(i).update();
            if(buildingButtons.get(i).getId()==BuildingButton.currentId){ //if(buildingButtons.get(i).isShuffle()){
                currentButton=buildingButtons.get(i);
            }
        }
    }


    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        //呼叫所有建築物按鈕
        for (int i = 0; i < BuildingTypeNum; i++) {
            //呼叫按鈕的滑鼠功能(傳入現在的滑鼠監聽)
            buildingButtons.get(i).mouseTrig(e, state, trigTime);
        }
    }
}
