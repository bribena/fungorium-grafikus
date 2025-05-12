package fungorium.ReViews;

import java.awt.Color;
import java.awt.Graphics;

import fungorium.ReModels.Gombafaj;
import fungorium.ReModels.Gombafonal;
import fungorium.ReModels.Tektonrész;

public class GombafonalView extends EntitásView {
    private Gombafonal fonal;

    public GombafonalView(Tektonrész tr, Gombafaj faj) {
        super(tr);
        fonal = new Gombafonal(faj);
        tr.getEntitások().add(fonal);
    }

    @Override
    public boolean isValid() {
        // return fonal.érvényesE();
        return fonal == null ? true : fonal.érvényesE();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (fonal.getFaj()) {
            case Amanita:
                g.setColor(Color.YELLOW);
                break;
            case Boletus:
                g.setColor(new Color(0, 187, 231));
                break;
            case Coprinus:
                g.setColor(new Color(150, 187, 174));
                break;
            case Deconica:
                g.setColor(Color.PINK);
                break;
        }

        int c = 0;
        for (int i = 0; i < 4; ++i) {
            if (fonal.getSzomszédosFonalak()[i] != null) {
                c++;
            }
        }
        if (c < 2) {
            g.fillRect(getWidth() / 2 - 2, getHeight() / 2 - 2, 5, 5);
        }

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
