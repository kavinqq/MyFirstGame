package company.gametest9th.utils;

public class FightEffect extends Effect{
    public FightEffect(int x, int y) {
        super(x-20, y, 200,200, new Delay(120), new Delay(20), new Path().img().effects().SwordShadow());
    }
}
