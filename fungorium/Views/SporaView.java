package fungorium.Views;

import java.awt.Color;
import java.awt.Graphics;

import fungorium.Models.Spóra;

public class SporaView implements EntityView {
    private Spóra modell;
    private int centerX;
    private int centerY;
    private static final int RADIUS = 10; // méret

    public SporaView(Spóra modell, int centerX, int centerY) {
        this.modell = modell;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(centerX - RADIUS, centerY - RADIUS, RADIUS * 2, RADIUS * 2);
    }
}