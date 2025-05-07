package fungorium.Views;

import java.awt.Color;
import java.awt.Graphics;

import fungorium.Models.Gombafonal;

public class GombafonalView implements EntityView {
    private Gombafonal modell;
    private int startX, startY;
    private int dx, dy;

    public GombafonalView(Gombafonal modell, int x, int y, int dx, int dy) {
        this.modell = modell;
        this.startX = x * 38 + 19;
        this.startY = y * 38 + 19;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        int endX = startX + dx * 19;
        int endY = startY + dy * 19;
        g.drawLine(startX, startY, endX, endY);
    }
}