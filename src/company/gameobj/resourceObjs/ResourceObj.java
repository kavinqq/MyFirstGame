package company.gameobj.resourceObjs;

import company.controllers.SceneController;
import company.gameobj.GameObject;

import java.awt.*;

abstract public class ResourceObj extends GameObject {

    /**
     * 資源種類
     */
    public enum ResourceType {
        WOOD("WOOD"),
        STEEL("STEEL");

        private String resourceTypeStr;

        ResourceType(String resourceTypeStr) {
            this.resourceTypeStr = resourceTypeStr;
        }
    }

    //共有多少資源量
    private int totalNum;

    //該資源堆的圖片
    private Image image;

    private ResourceType resourceType;


    public ResourceObj(int x, int y, int width, int height) {
        super(x, y, width, height);

        totalNum = 0;
    }

    /**
     * 畫出資源堆
     *
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, painter().left(), painter().top(), null);
    }

    /**
     * 資源堆更新
     */
    @Override
    public void update() {

    }

    /**
     * 獲取現在共有多少資源
     *
     * @return 還有多少資源
     */

    public int getTotalNum() {
        return totalNum;
    }

    /**
     * 預設這一堆資源堆共有多少資源能採
     *
     * @param totalNum 共有多少資源能採
     */
    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }


    /**
     * 總共資源 = 總共資源 - 這次被採了多少
     *
     * @param num 這次被採了多少
     */

    public void beCollected(int num) {
        totalNum -= num;
    }

    /**
     * 設定該資源堆的圖片path
     *
     * @param path 圖片路徑
     */
    public void setImage(String path) {
        image = SceneController.getInstance().imageController().tryGetImage(path);
    }

    /**
     * 設定這堆資源堆的類別
     *
     * @param resourceType 資源類別
     */
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * 獲得這堆資源堆的類別字串資訊
     *
     * @return 這堆資源堆的類別(字串型態)
     */
    public String getResourceTypeStr() {
        return resourceType.resourceTypeStr;
    }
}
