package fungorium.ReViews;

import java.awt.Color;
import java.awt.Graphics;

import fungorium.ReModels.Gombafonal;

public class GombafonalView extends EntitásView {
    private Gombafonal fonal;

    public GombafonalView(Gombafonal fonal) {
        this.fonal = fonal;
    }

    @Override
    public boolean isValid() {
        // return fonal.érvényesE();
        return fonal != null && fonal.érvényesE();
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
            if (fonal.getKapcsolódóFonalak()[i] != null) {
                c++;
            }
        }
        if (c < 2) {
            g.fillRect(getWidth() / 2 - 2, getHeight() / 2 - 2, 5, 5);
        }
        else {
            g.fillRect(getWidth() / 2 - 2, getHeight() / 2 - 2, 1, 1);
        }

        if (fonal.getKapcsolódóFonalak()[0] != null) {
            g.fillRect(getWidth() / 2, 0, 1, getHeight() / 2);
        }
        if (fonal.getKapcsolódóFonalak()[2] != null) {
            g.fillRect(getWidth() / 2, getHeight() / 2 + 1, 1, getHeight());
        }
        if (fonal.getKapcsolódóFonalak()[3] != null) {
            g.fillRect(0, getHeight() / 2, getWidth() / 2, 1);
        }
        if (fonal.getKapcsolódóFonalak()[1] != null) {
            g.fillRect(getWidth() / 2 + 1, getHeight() / 2, getWidth() / 2, 1);
        }
    }
}
