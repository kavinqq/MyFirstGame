package company.gameobj.resourceObjs;

import company.gametest9th.utils.Path;

/**
 * 鋼鐵資源堆
 */
public class Steel extends ResourceObj {

    public Steel(int x, int y) {

        // 設定初始位置 && 寬高
        super(x, y, 128,128);

        // 設定鋼鐵堆圖片
        setImage(new Path().img().objs().steel());

        // 預設總資源量500
        setTotalNum(500);

        // 設定資源類別為鋼鐵
        setResourceType(ResourceType.STEEL);
    }

}