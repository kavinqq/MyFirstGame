package company.gameobj.buildings;

import company.gametest9th.utils.Path;

public class GasMill extends Building{
    public GasMill(int x, int y) {
        super(x, y, new Path().img().building().GasMill());
    }

    public GasMill() {
        super();
    }
}
