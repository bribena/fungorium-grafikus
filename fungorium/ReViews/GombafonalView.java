package fungorium.ReViews;

import java.awt.Color;
import java.awt.Graphics;

import fungorium.ReModels.Gombafonal;
import fungorium.ReModels.Tektonrész;

public class GombafonalView extends EntitásView {
    Gombafonal fonal;

    public GombafonalView(Tektonrész tr) {
        super(tr);
        fonal = new Gombafonal();
        tr.getEntitások().add(fonal);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        g.fillRect(getWidth() / 2 - 2, getHeight() / 2 - 2, 5, 5);

        if (fonal.getSzomszédosFonalak()[0] != null) {
            g.fillRect(getWidth() / 2, 0, 1, getHeight() / 2);
        }
        if (fonal.getSzomszédosFonalak()[2] != null) {
            g.fillRect(getWidth() / 2, getHeight() / 2 + 1, 1, getHeight());
        }
        if (fonal.getSzomszédosFonalak()[3] != null) {
            g.fillRect(0, getHeight() / 2, getWidth() / 2, 1);
        }
        if (fonal.getSzomszédosFonalak()[1] != null) {
            g.fillRect(getWidth() / 2 + 1, getHeight() / 2, getWidth() / 2, 1);
        }
    }
}
