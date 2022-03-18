package company.gameObj;

import company.Global;

import java.awt.*;

public class MenuChoice extends GameObject{

    public enum Option{
        START,
        EXIT,
    }


    private String text;
    private Color fillColor;
    private Color hoverColor;
    private Option option;

    public MenuChoice(int x, int y, int width, int height,  String text, Color fillColor, Color hoverColor, Option option) {
        super(x, y, width, height);
        this.text = text;
        this.fillColor = fillColor;
        this.hoverColor = hoverColor;
        this.option = option;
    }

    @Override
    public void paintComponent(Graphics g) {
        // 選項框
        g.setColor(Color.yellow);
        g.fillRect( collider().left(),  collider().top(), collider().width(), collider().height());

        // 顯示一下選項文字
        g.setColor(Color.PINK);
        g.setFont(new Font("楷體", Font.PLAIN, 20));
        g.drawString(text, collider().left() + 40, collider().top() + 30);
    }

    @Override
    public void update() {

    }

    public Option getOption(){
        return option;
    }
}
