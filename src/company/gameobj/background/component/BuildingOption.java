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

    private BuildingButton currentButton;

    private int buttonPressedCount;

    private Rect mouseRect;
    private Rect greenRect;
    private Image greenImg; //不可建造紅背景
    private Image redImg; //可建造綠背景
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
        //可建:綠背景
        greenImg =SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().green());
        //不可建:紅背景
        redImg =SceneController.getInstance().imageController().tryGetImage(new Path().img().objs().red());
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
        //最後畫
        if(currentButton!=null && currentButton.getId()-1>=0){
            if(currentButton.isDragging()){
                //畫拖曳中的按鈕
                Rect redRect=currentButton.painter();
                g.drawImage(redImg,redRect.left(),redRect.top(),redRect.width(),redRect.height(),null);
                if(greenRect!=null){
                    g.drawImage(greenImg,greenRect.left(),greenRect.top(),greenRect.width(),greenRect.height(),null);
                }
            }
            //畫被點擊的按鈕
            buildingButtons.get(currentButton.getId()-1).paint(g);
        }
    }


    @Override
    public void update() {
        if(!checkMouseOnButtons()){
            HintDialog.instance().setHintMessage("");
        }

        for (int i = 0; i < BuildingTypeNum; i++) {
            if(buildingButtons.get(i).isPressed()){
                currentButton=buildingButtons.get(i);

                buttonPressedCount++;
               // buildingButtons.get(i).setPressed(false); //有時release關不掉isPressed 有時間再修成interface
                break;
            }
            if(buildingButtons.get(i).isMoveOnButton()){
                type = BuildingType.getBuildingTypeByInt(buildingButtons.get(i).getId());
                HintDialog.instance().setHintMessage(type.instance().getName());
            }
            if(buildingButtons.get(i).isDragging()){
                mouseRect=buildingButtons.get(i).painter();
                break;
            }else{
                greenRect=null; //當放開時清除 綠框
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

    //取得點選次數
    public int getButtonPressedCount(){
        return buttonPressedCount;
    }

    //減少點選次數
    public void decButtonPressedCount(){
        buttonPressedCount--;
    }

    //取得
    public BuildingButton get(int index){
        return buildingButtons.get(index);
    }

    //總長度
    public int length(){
        return buildingButtons.size();
    }


    public void clearMouseRect() {
        mouseRect=null;
    }

    public void setGreenRect(Rect Rect) {
        if(Rect == null){
            return;
        }
        this.greenRect = Rect;
    }
}
