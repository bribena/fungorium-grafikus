package Game;

import java.awt.Color;
import java.awt.Graphics;
import prototipus_9.Gombatest;
import prototipus_9.Tektonrész;
import prototipus_9.Fungorium;

public class GombatestView implements EntityView {
    private Gombatest modell;
    private int x, y;

    public GombatestView(Gombatest modell, Fungorium fungorium, int x, int y) {
        this.modell = modell;
        this.x = x * 38 + 19;
        this.y = y * 38 + 19;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(139, 69, 19));
        g.fillOval(x - 10, y - 10, 20, 20);
    }
}
