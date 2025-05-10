package fungorium.ReViews;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

import fungorium.ReModels.Tektonrész;

public class GombatestView extends EntitásView {
    public GombatestView(Tektonrész tr) {
        super(tr);
        setOpaque(false);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(38, 38);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.drawLine(38, 0, 0, 38);
    }
}
