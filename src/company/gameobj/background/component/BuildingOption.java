package company.gameobj.background.component;

import company.Global;
import company.controllers.SceneController;
import company.gameobj.Rect;
import company.gameobj.message.HintDialog;
import company.gameobj.message.MultiIHintDialog;
import company.gametest9th.utils.CommandSolver;
import company.gametest9th.utils.GameKernel;
import company.gametest9th.utils.Path;

import static company.gameobj.BuildingController.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static company.Global.*;

public class BuildingOption implements GameKernel.GameInterface, CommandSolver.MouseCommandListener {

    private ArrayList<BuildingButton> buildingButtons;

    private boolean isMouseOnButtons;

    //所有按鈕圖片
    Image[] imgs;
    //地基圖片
    Image foundation_img;
    //建築物選單背景
    Image buildingOption_Img;

    //為靜態類別，可取得Building相關訊息
    BuildingType type;

    private int pressCount;

    //建築物選單與地基差值X
    private int optionBgFoundationGapX;

    //建築物地基位置
    private int foundationX;

    //建築物按鈕x位置
    private int buttonX;

    private BuildingButton currentMoveOnButton;


    public BuildingOption() {
        //建築物選單與地基左邊間距
        optionBgFoundationGapX = (BUILDING_OPTION_WIDTH - FOUNDATION_WIDTH) / 2;

        foundationX = BUILDING_OPTION_X + optionBgFoundationGapX;

        buttonX = BUILDING_OPTION_X + (BUILDING_OPTION_WIDTH - BUILDING_WIDTH) / 2;

        buildingButtons = new ArrayList(BuildingTypeNum);//建立所有建築物按鈕

        imgs = new Image[BuildingTypeNum];

        //新增按鈕元件、給予按鈕圖片
        for (int i = 0; i < BuildingTypeNum; i++) {
            type = BuildingType.getBuildingTypeByInt(i + 1);
            int tmpId = type.instance().getId();
            String tmpPath = type.instance().getImgPath();

            buildingButtons.add(new BuildingButton(buttonX, BUILDING_OPTION_Y + FB_DIV_GAP_Y + (FOUNDATION_HEIGHT + OPTION_GAP_Y) * i, tmpId));
            buildingButtons.get(i).setImg(SceneController.getInstance().imageController().tryGetImage(tmpPath));
        }

        //新增地基圖片
        foundation_img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().buttonFoundation());
        //建築物選單圖片
        buildingOption_Img = SceneController.getInstance().imageController().tryGetImage(new Path().img().background().buildingOptionBg());
    }

    //取得
    public BuildingButton get(int index) {
        return buildingButtons.get(index);
    }

    //總長度
    public int length() {
        return buildingButtons.size();
    }

    //取得現在位置按鈕
    public BuildingButton getCurrentButton(int x, int y) {
        for (int i = 0; i < buildingButtons.size(); i++) {
            if (buildingButtons.get(i).isEntered(x, y)) {

                return buildingButtons.get(i);
            }
        }
        return null;
    }

    //取得目前移動時滑鼠下方的按鈕
    private BuildingButton getCurrentMoveOnButton(CommandSolver.MouseState state,int x,int y){
        if(state == CommandSolver.MouseState.MOVED){
            for (int i = 0; i < BuildingTypeNum; i++) {
                //取得目前指到的按鈕
                if (buildingButtons.get(i).isEntered(x, y)) {
                    return buildingButtons.get(i);
                }
            }
        }

        return null;
    }

    @Override
    public void paint(Graphics g) {
        //選單背景
        g.drawImage(buildingOption_Img, BUILDING_OPTION_X, BUILDING_OPTION_Y, BUILDING_OPTION_WIDTH, BUILDING_OPTION_HEIGHT, null);

        //基地為背景先畫
        for (int i = 0; i < BuildingTypeNum; i++) {
            //畫基地
            g.drawImage(foundation_img, foundationX, BUILDING_OPTION_Y + (FOUNDATION_HEIGHT + OPTION_GAP_Y) * i
                    , FOUNDATION_WIDTH, FOUNDATION_HEIGHT, null);

        }
        //建築物後畫
        for (int i = 0; i < BuildingTypeNum; i++) {
            //畫按鈕
            buildingButtons.get(i).paint(g);

        }
        if (currentMoveOnButton != null) {
            currentMoveOnButton.getMultiIHintDialog().paint(g);
        }
    }


    @Override
    public void update() {
        for (int i = 0; i < BuildingTypeNum; i++) {
            buildingButtons.get(i).update();
        }
    }


    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        currentMoveOnButton=getCurrentMoveOnButton(state,e.getX(),e.getY());

        //呼叫所有建築物按鈕
        for (int i = 0; i < BuildingTypeNum; i++) {
            //呼叫按鈕的滑鼠功能(傳入現在的滑鼠監聽)
            buildingButtons.get(i).mouseTrig(e, state, trigTime);
        }
    }
}
