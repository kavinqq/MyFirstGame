package company.gameobj.background.component;

import company.controllers.SceneController;
import company.gameobj.Rect;
import company.gameobj.background.HintDialog;
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

    private int currentIdByButton;

    private Rect mouseRect;

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
        foundation_img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().foundation());
    }


    public int getCurrentIdByButton(){
        return currentIdByButton;
    }

    //是否所有Button都false
    public boolean checkMouseOnButtons(){
        isMouseOnButtons =false;
        for (int i=0;i<BuildingTypeNum;i++){
            isMouseOnButtons |=buildingButtons.get(i).isMoveOnButton();
        }
        return isMouseOnButtons;
    }


    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < BuildingTypeNum; i++) {
            //畫基地
            g.drawImage(foundation_img, BUILDING_OPTION_X, BUILDING_OPTION_Y  + (FOUNDATION_HEIGHT + OPTION_GAP_Y) * i
                    , FOUNDATION_WIDTH, FOUNDATION_HEIGHT, null);
            //畫按鈕
            buildingButtons.get(i).paint(g);
        }
    }


    @Override
    public void update() {
        for (int i = 0; i < BuildingTypeNum; i++) {
            if(buildingButtons.get(i).isPressed()){
                currentIdByButton=buildingButtons.get(i).getId();
                //System.out.println(currentIdByButton);
                buildingButtons.get(i).setPressed(false);

                break;
            }
            if(buildingButtons.get(i).isDragged()){
                mouseRect=buildingButtons.get(i).getRecordRect();
                break;
            }

        }
    }


    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {

        if(checkMouseOnButtons()){
            HintDialog.instance().setHintMessage("");
        }
        //呼叫所有建築物按鈕

        for (int i = 0; i < BuildingTypeNum; i++) {
            //得到建築物Id
            type = BuildingType.getBuildingTypeByInt(buildingButtons.get(i).getId());

            //currentId=buildingButtons[i];

            //改變按鈕的內容
            buildingButtons.get(i).bni=(bb)-> {

                HintDialog.instance().setHintMessage(type.instance().getName());
            };
            //呼叫按鈕的滑鼠功能(傳入現在的滑鼠監聽)
            buildingButtons.get(i).mouseTrig(e, state, trigTime);
        }
    }

    //取得
    public BuildingButton get(int index){
        return buildingButtons.get(index);
    }

    //總長度
    public int length(){
        return buildingButtons.size();
    }

    public void setCurrentIdByButton(int id) {
        currentIdByButton=id;
    }

    public Rect getMouseRect(){
        return mouseRect;
    }

    public void clearMouseRect() {
        mouseRect=null;
    }
}
