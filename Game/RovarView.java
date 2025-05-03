package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import prototipus_9.Rovar;
import prototipus_9.Tektonrész;

public class RovarView implements EntityView {
    private Rovar modell;
    private int x, y;

    public RovarView(Rovar modell, int x, int y) {
        this.modell = modell;
        this.x = x * 38 + 19;
        this.y = y * 38 + 19;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        int[] xPoints = { x, x - 10, x + 10 };
        int[] yPoints = { y - 10, y + 10, y + 10 };
        g.fillPolygon(xPoints, yPoints, 3);
    }
}