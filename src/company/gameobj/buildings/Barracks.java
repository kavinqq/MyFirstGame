package company.gameobj.buildings;

import company.gametest9th.utils.Path;

public class Barracks extends Building{
    public Barracks(int x, int y) {
        super(x, y, new Path().img().building().Barracks());
    }

    public Barracks() {
        super();
    }
}
