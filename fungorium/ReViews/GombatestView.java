package fungorium.ReViews;

import java.awt.Graphics;
import java.awt.Color;

import fungorium.ReModels.Tektonrész;

public class GombatestView extends EntitásView {
    public GombatestView(Tektonrész tr) {
        super(tr);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(139, 69, 19));
        g.fillOval((getWidth() - 20) / 2, (getHeight() - 20) / 2, 20, 20);
    }
}
