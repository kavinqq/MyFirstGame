package company.gameobj.resourceObjs;

import company.gametest9th.utils.Path;

public class Steel extends ResourceObj {

    public Steel(int x, int y) {

        super(x, y, 128,128);

        // 設定鋼鐵堆圖片
        setImage(new Path().img().objs().steel());

        // 預設總資源量500
        setTotalNum(500);

        // 設定資源類別為鋼鐵
        setResourceType(ResourceType.STEEL);
    }

}