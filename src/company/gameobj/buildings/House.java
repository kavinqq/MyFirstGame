package company.gameobj.buildings;

import company.gametest9th.utils.Path;

public class House extends Building{

    public House(int x, int y){
        super(x, y, new Path().img().building().House());
    }
    public House() {
        super();
    }
}
