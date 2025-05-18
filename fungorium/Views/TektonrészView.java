package fungorium.Views;

import java.awt.Color;
import java.awt.Graphics;

import fungorium.Models.EgyfonalasTektonrész;
import fungorium.Models.FonalfelszívóTektonrész;
import fungorium.Models.GombatestTiltóTektonrész;
import fungorium.Models.Tektonrész;
import fungorium.Models.TöbbfonalasTektonrész;
import fungorium.Models.ÉletbentartóTektonrész;

public class TektonrészView {
    private static final int TILE_SIZE = 38;
    private Tektonrész tr;
    private int x, y;

    public TektonrészView(Tektonrész tr, int x, int y) {
        this.tr = tr;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        int px = x * TILE_SIZE;
        int py = y * TILE_SIZE;

        // Alap rajzolása
        if (tr instanceof TöbbfonalasTektonrész) {
            g.setColor(Color.GREEN);
        } else if (tr instanceof EgyfonalasTektonrész) {
            g.setColor(Color.CYAN);
        } else if (tr instanceof ÉletbentartóTektonrész) {
            g.setColor(Color.PINK);
        } else if (tr instanceof FonalfelszívóTektonrész) {
            g.setColor(Color.ORANGE);
        } else if (tr instanceof GombatestTiltóTektonrész) {
            g.setColor(Color.RED);
        }
        g.fillRect(px, py, TILE_SIZE, TILE_SIZE);

        boolean[] szel = tr.getTektonSzéleE();
        g.setColor(Color.BLACK);

        g.drawRect(px, py, TILE_SIZE, TILE_SIZE);

        // Szél rajzolása
        g.setColor(Color.WHITE);

        if (szel[0]) {
            g.drawLine(px, py, px + TILE_SIZE, py);
        }
        if (szel[1]) {
            g.drawLine(px + TILE_SIZE, py, px + TILE_SIZE, py + TILE_SIZE);
        }
        if (szel[2]) {
            g.drawLine(px, py + TILE_SIZE, px + TILE_SIZE, py + TILE_SIZE);
        }
        if (szel[3]) {
            g.drawLine(px, py, px, py + TILE_SIZE);
        }
    }
}
