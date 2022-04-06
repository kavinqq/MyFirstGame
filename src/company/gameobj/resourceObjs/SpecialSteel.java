package company.gameobj.resourceObjs;

import company.gametest9th.utils.Path;

public class SpecialSteel extends ResourceObj{

    public SpecialSteel(int x, int y) {

        // 設定初始位置 && 寬高
        super(x, y, 128,128);

        // 設定樹木資源堆圖片
        setImage(new Path().img().objs().specialSteel());

        // 樹木堆預設有200個特別鋼鐵資源
        setTotalNum(200);

        // 資源類別為特別鋼鐵
        setResourceType(ResourceType.SPECIAL_STEEL);
    }
}
