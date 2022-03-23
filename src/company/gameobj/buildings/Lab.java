package company.gameobj.buildings;

import company.gametest9th.utils.Path;

public class Lab extends Building{
    public Lab(int x, int y) {
        super(x, y, new Path().img().building().Lab());
    }

    public Lab(){
        super();
    }
}
