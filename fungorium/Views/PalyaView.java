package fungorium.Views;

import java.awt.Graphics;

import fungorium.Models.Fungorium;

public class PalyaView {
    private Fungorium fungorium;

    public PalyaView(Fungorium fungorium) {
        this.fungorium = fungorium;
    }

    public void drawPalya(Graphics g) {
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                new TektonrészView(fungorium.getTektonrész(x, y), x, y).draw(g);
            }
        }
    }
}