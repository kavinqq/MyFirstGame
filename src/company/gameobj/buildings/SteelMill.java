package company.gameobj.buildings;

import company.gametest9th.utils.Path;

public class SteelMill extends Building{

    public SteelMill(int x, int y) {
        super(x, y, new Path().img().building().SteelMill());
    }

    public SteelMill() {
        super();
    }
}
