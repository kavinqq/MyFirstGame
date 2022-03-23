package company.gameobj.buildings;

import company.gametest9th.utils.Path;

public class AirplaneMill extends Building {

    public AirplaneMill(int x, int y) {
        super(x, y, new Path().img().building().AirplanemIll());
    }

    public AirplaneMill() {
        super();
    }
}
