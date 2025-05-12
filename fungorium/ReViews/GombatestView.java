package fungorium.ReViews;

import java.awt.Graphics;
import java.awt.Color;

import fungorium.ReModels.Gombatest;
import fungorium.ReModels.Gombafaj;
import fungorium.ReModels.Tektonrész;

public class GombatestView extends EntitásView {
    private Gombatest t;

    public GombatestView(Tektonrész tr, Gombafaj faj) {
        super(tr);
        t = new Gombatest(faj);
        tr.getEntitások().add(t);
    }

    @Override
    public boolean isValid() {
        return t == null ? true : t.érvényesE();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (t.getFaj()) {
            case Amanita:
                g.setColor(new Color(255, 225, 67));
                break;
            case Boletus:
                g.setColor(Color.BLUE);
                break;
            case Coprinus:
                g.setColor(Color.DARK_GRAY);
                break;
            case Deconica:
                g.setColor(Color.MAGENTA);
                break;
        }

        g.fillOval((getWidth() - 20) / 2, (getHeight() - 20) / 2, 20, 20);
    }
}
