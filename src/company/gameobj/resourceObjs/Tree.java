package company.gameobj.resourceObjs;

import company.gametest9th.utils.Path;

/**
 * 樹木資源堆
 */
public class Tree extends ResourceObj {

    public Tree(int x, int y) {

        // 設定初始位置 && 寬高
        super(x, y, 128,128);

        // 設定樹木資源堆圖片
        setImage(new Path().img().objs().tree());

        // 樹木堆預設有1000個樹木資源
        setTotalNum(1000);

        // 資源類別為木頭
        setResourceType(ResourceType.WOOD);
    }
}
