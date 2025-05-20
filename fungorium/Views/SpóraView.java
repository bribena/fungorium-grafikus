package fungorium.Views;

import java.awt.Color;
import java.awt.Graphics;

import fungorium.Models.*;

public class SpóraView extends EntitásView {
    private Spóra spóra;

    public SpóraView(Spóra sp) {
        spóra = sp;
        setOpaque(false);
    }

    @Override
    public boolean isValid() {
        return spóra != null && spóra.érvényesE();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int alpha;
        if (spóra.getSpóraSzám() >= 10) {
            alpha = 0xFF;
        } else {
            alpha = 6 * spóra.getSpóraSzám() + 192;
        }

        switch (spóra.getFaj()) {
            case Amanita:
                g.setColor(new Color(0xf7, 0xf2, 0xcb, alpha));
                break;
            case Boletus:
                g.setColor(new Color(0xd6, 0xe8, 0xef, alpha));
                break;
            case Coprinus:
                g.setColor(new Color(0xf2, 0xec, 0xff, alpha));
                break;
            case Deconica:
                g.setColor(new Color(0xFF, 0xE4, 0XE3, alpha));
                break;
        }

        // RANDOM SZÁMOK GOOOOO
        g.fillOval((getWidth() - 16) / 4, (getHeight() - 16) / 4, 14, 14);
        g.fillOval((getWidth() - 12) / 4 + (getWidth()) / 2, (getHeight() - 12) / 4, 10, 10);
        g.fillOval((getWidth() - 12) / 4, (getHeight() - 12) / 4 + (getHeight()) / 2, 10, 10);
        g.fillOval((getWidth() - 8) / 4 + (getWidth()) / 2, (getHeight() - 8) / 4 + (getHeight()) / 2, 8, 8);

        g.setColor(Color.BLACK);
        g.drawOval((getWidth() - 16) / 4, (getHeight() - 16) / 4, 14, 14);
        g.drawOval((getWidth() - 12) / 4 + (getWidth()) / 2, (getHeight() - 12) / 4, 10, 10);
        g.drawOval((getWidth() - 12) / 4, (getHeight() - 12) / 4 + (getHeight()) / 2, 10, 10);
        g.drawOval((getWidth() - 8) / 4 + (getWidth()) / 2, (getHeight() - 8) / 4 + (getHeight()) / 2, 8, 8);
    }

    @Override
    public boolean contains(Entitás e) {
        return spóra.equals(e);
    }
}
