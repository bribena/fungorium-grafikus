package Game;

import java.awt.Color;
import java.awt.Graphics;
import prototipus_9.Fungorium;
import prototipus_9.Tektonrész;

public class PalyaView {
    private Fungorium fungorium;
    private static final int TILE_SIZE = 38;

    public PalyaView(Fungorium fungorium) {
        this.fungorium = fungorium;
    }

    public void drawPalya(Graphics g) {
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                Tektonrész resz = fungorium.getTektonrész(x, y);
                int px = x * TILE_SIZE;
                int py = y * TILE_SIZE;

                g.setColor(Color.GREEN);
                g.fillRect(px, py, TILE_SIZE, TILE_SIZE);

                boolean[] szel = resz.getTektonSzéleE();
                g.setColor(Color.BLACK);
                g.drawRect(px, py, TILE_SIZE, TILE_SIZE);

                if (szel[0]) {
                    g.setColor(Color.WHITE);
                    g.drawLine(px, py, px + TILE_SIZE, py);
                }
                if (szel[1]) {
                    g.setColor(Color.WHITE);
                    g.drawLine(px + TILE_SIZE, py, px + TILE_SIZE, py + TILE_SIZE);
                }
                if (szel[2]) {
                    g.setColor(Color.WHITE);
                    g.drawLine(px, py + TILE_SIZE, px + TILE_SIZE, py + TILE_SIZE);
                }
                if (szel[3]) {
                    g.setColor(Color.WHITE);
                    g.drawLine(px, py, px, py + TILE_SIZE);
                }
            }
        }
    }
}