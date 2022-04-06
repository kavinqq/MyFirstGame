package company.gameobj.resourceObjs;

import company.gametest9th.utils.Path;

public class SpecialTree extends ResourceObj{

    public SpecialTree(int x, int y) {

        // 設定初始位置 && 寬高
        super(x, y, 128,128);

        // 設定樹木資源堆圖片
        setImage(new Path().img().objs().specialTree());

        // 樹木堆預設有300個特別樹木資源
        setTotalNum(200);

        // 資源類別為特別木頭
        setResourceType(ResourceType.SPECIAL_TREE);
    }


}
