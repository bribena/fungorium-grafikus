package fungorium.ReViews;

import java.awt.Color;
// import java.awt.event.KeyAdapter;
// import java.awt.event.KeyEvent;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

import fungorium.ReModels.Fungorium;
import fungorium.ReModels.Tektonrész;

public class FungoriumView extends JPanel {
    private Fungorium fungorium;

    public FungoriumView(Fungorium f) {
        fungorium = f;

        setLayout(new GridLayout(f.getHeight(), f.getWidth()));

        for (int y = 0; y < fungorium.getHeight(); ++y) {
            for (int x = 0; x < fungorium.getWidth(); ++x) {
                add(new TektonrészView(f, x, y));
            }
        }

        // addMouseListener(new MouseAdapter() {
        // @Override
        // public void mouseClicked(MouseEvent e) {
        // TektonrészView tw = (TektonrészView)getComponentAt(e.getPoint());
        // System.out.println(tw.x + ", " + tw.y);
        // }
        // });

        // addKeyListener(new KeyAdapter() {
        // @Override
        // public void keyReleased(KeyEvent e) {
        // f.törés();
        // System.out.println("Törés");
        // }
        // });

        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Törésvonal kirajzolása
        Fungorium.Point a = fungorium.getTörésA();
        Fungorium.Point b = fungorium.getTörésB();
        if (a != null && b != null) {
            g.setColor(Color.WHITE);

            // Átváltás mező-koordinátából pixel-koordinátára
            int tileSize = getComponent(0).getWidth(); // vagy fix TILE_SIZE
            int x1 = (int) ((a.x + 0.5) * tileSize);
            int y1 = (int) ((a.y + 0.5) * tileSize);
            int x2 = (int) ((b.x + 0.5) * tileSize);
            int y2 = (int) ((b.y + 0.5) * tileSize);

            g.drawLine(x1, y1, x2, y2);
        }
    }

    public TektonrészView getTektonrészView(Tektonrész keresett) {
        for (int i = 0; i < getComponentCount(); ++i) {
            TektonrészView tv = (TektonrészView) getComponent(i);
            if (tv.getTektonrész() == keresett) {
                return tv;
            }
        }
        return null;
    }
}
