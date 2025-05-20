package fungorium.Views;

import java.awt.Graphics;

import fungorium.Models.Entitás;
import fungorium.Models.Gombatest;

import java.awt.Color;

public class GombatestView extends EntitásView {
    private Gombatest test;

    public GombatestView(Gombatest test) {
        this.test = test;
    }

    @Override
    public boolean isValid() {
        return test != null && test.érvényesE();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (test.getFaj()) {
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
        
        g.setColor(Color.BLACK);
        g.drawOval((getWidth() - 20) / 2, (getHeight() - 20) / 2, 20, 20);
    }

    @Override
    public boolean contains(Entitás e) {
        return test.equals(e);
    }
}
