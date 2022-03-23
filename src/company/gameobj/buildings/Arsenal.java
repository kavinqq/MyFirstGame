package company.gameobj.buildings;


import company.gametest9th.utils.Path;

public class Arsenal extends Building {
    public Arsenal(int x, int y) {
        super(x, y, new Path().img().building().Arsenal());
    }

    public Arsenal() {
        super();
    }
}
