package fungorium.ReViews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import fungorium.ReModels.Gombafonal;
import fungorium.ReModels.Tektonrész;

public class GombafonalView extends EntitásView {
    Gombafonal fonal;

    public GombafonalView(Tektonrész tr) {
        super(tr);
        fonal = new Gombafonal();
        tr.getEntitások().add(fonal);
        setOpaque(false);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(38, 38);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(0, 0, 38, 38);
    }
}
