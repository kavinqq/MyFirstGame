package company.gameobj.buildings;

import company.gametest9th.utils.Path;

public class SawMill extends Building{

    public SawMill(int x, int y) {
        super(x, y, new Path().img().building().SawMill());
    }

    public SawMill(){
        super();
    }
}
