package company.gameobj.resourceObjs;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理所有的遊戲內的 資源堆
 */

public class ResourceSystem {

    //存著所有資源堆的List
    private List<ResourceObj> resourceObjs;

    public ResourceSystem(){
        resourceObjs = new ArrayList<>();

        resourceObjs.add(new Tree(350,400));
        resourceObjs.add(new Steel(1200,400));
    }


    /**
     * 畫出遊戲內所有的資源堆
     * @param g Graphics
     */

    //TODO[可改進: 其實可以畫出看的到的就好 雖然不多, 迷霧做好再說] 2022/4/1 16:32

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
     * @return 共有幾個資源堆
     */
    public int size(){
        return resourceObjs.size();
    }

    /**
     * 根據index獲得對應資源堆
     * @return 對應的資源堆
     */
    public ResourceObj get(int index){
        return resourceObjs.get(index);
    }

    /**
     * 根據index刪除對應資源堆
     * @param index 要刪除的索引
     */
    public void remove(int index){
        resourceObjs.remove(index);
    }

}
