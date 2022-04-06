package company.gameobj.resourceObjs;

import company.Global;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理所有的遊戲內的 視覺化資源堆
 */

public class ResourceSystem {

    // 初始樹木資源XY
    private static final int INIT_TREE_X = 350;
    private static final int INIT_TREE_Y = 350;

    // 初始鋼鐵資源XY
    private static final int INIT_STEEL_X = 1300;
    private static final int INIT_STEEL_Y = 400;

    // 每個資源的寬高
    private static final int RESOURCE_HEIGHT = 128;


    private float SPECIAL_RESOURCE_RATE = 0.02f;


    //存著所有資源堆的List
    private List<ResourceObj> resourceObjs;

    public ResourceSystem(){

        // 資源堆List初始化
        resourceObjs = new ArrayList<>();

        // 加入普通資源(固定位置)
        resourceObjs.add(new Tree(INIT_TREE_X,INIT_TREE_Y));
        resourceObjs.add(new Tree(INIT_TREE_X,INIT_TREE_Y + RESOURCE_HEIGHT));
        resourceObjs.add(new Steel(INIT_STEEL_X,INIT_STEEL_Y));
        resourceObjs.add(new Steel(INIT_STEEL_X,INIT_STEEL_Y + RESOURCE_HEIGHT));

        // 跑整張地圖XY => 去隨機生成特殊資源
        for(int i = Global.MAP_LEFT + RESOURCE_HEIGHT; i < Global.MAP_RIGHT; i +=  RESOURCE_HEIGHT){
            for(int j = Global.MAP_TOP + RESOURCE_HEIGHT; j < Global.MAP_BOTTOM; j += RESOURCE_HEIGHT){

                // 如果 (x,y) 在最初的鏡頭內 => 不生成特殊資源
                if((i > 0 && i < Global.SCREEN_WIDTH) && ( j > 0 && j < Global.SCREEN_HEIGHT)){
                    continue;
                }

                // 低機率生成
                if(Math.random() < SPECIAL_RESOURCE_RATE){

                    // 每次生成 一半機率是樹木 一半機率是鋼鐵
                    if(Math.random() < 0.5){
                        resourceObjs.add(new SpecialTree(i, j));
                    } else{
                        resourceObjs.add(new SpecialSteel(i, j));
                    }
                }
            }
        }

    }


    /**
     * 畫出遊戲內所有的資源堆
     *
     * @param g Graphics
     */
    public void paint(Graphics g){

        //畫出每一個資源堆
        for(ResourceObj resourceObj : resourceObjs){
            resourceObj.paint(g);
        }
    }

    /**
     * 所有資源堆跟著鏡頭移動
     */
    public void cameraMove(){

        for(ResourceObj resourceObj : resourceObjs){
            resourceObj.cameraMove();
        }
    }

    /**
     * 所有資源堆回到初始位置[他根本不會動 不用特別考慮額外狀況 直接回到出生位置就好]
     */
    public void resetObjectXY(){

        for(ResourceObj resourceObj : resourceObjs){
            resourceObj.resetObjectXY();
        }
    }

    /**
     * 回傳List大小
     *
     * @return 共有幾個資源堆
     */
    public int size(){
        return resourceObjs.size();
    }

    /**
     * 根據index獲得對應資源堆
     *
     * @return 對應的資源堆
     */
    public ResourceObj get(int index){
        return resourceObjs.get(index);
    }

    /**
     * 根據index刪除對應資源堆
     *
     * @param index 要刪除的索引
     */
    public void remove(int index){
        resourceObjs.remove(index);
    }

}
