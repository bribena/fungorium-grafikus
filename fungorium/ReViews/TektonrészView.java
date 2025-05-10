package fungorium.ReViews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.FlowLayout;

import javax.swing.JLayeredPane;
import javax.swing.BorderFactory;

import fungorium.ReModels.*;

public class TektonrészView extends JLayeredPane {
    public static final int TILE_SIZE = 38;
    
    public Fungorium fungorium;
    public int x, y;

    public TektonrészView(Fungorium f, int x, int y) {
        fungorium = f;
        this.x = x;
        this.y = y;

        // if (x == 0 && y == 0) {
            add(new GombatestView(f.getTektonrész(x, y)));
            add(new GombafonalView(f.getTektonrész(x, y)));
        // }
        setOpaque(true);
        setVisible(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(TILE_SIZE, TILE_SIZE);
    }

    private int bToI(boolean b) {
        return b ? 1 : 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Tektonrész tr = fungorium.getTektonrész(x, y);
        
        // Alap rajzolása
        if (tr instanceof TöbbfonalasTektonrész) {
            setBackground(Color.GREEN);
        }
        else if (tr instanceof EgyfonalasTektonrész) {
            setBackground(Color.CYAN);
        }
        else if (tr instanceof ÉletbentartóTektonrész) {
            setBackground(Color.PINK);
        }
        else if (tr instanceof FonalfelszívóTektonrész) {
            setBackground(Color.ORANGE);
        }
        else if (tr instanceof GombatestTiltóTektonrész) {
            setBackground(Color.RED);
        }

        // Szél rajzolása
        boolean[] szel = tr.getTektonSzéleE();
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(bToI(!szel[0]), bToI(!szel[3]), bToI(!szel[2]), bToI(!szel[1]), Color.BLACK),
            BorderFactory.createMatteBorder(bToI(szel[0]), bToI(szel[3]), bToI(szel[2]), bToI(szel[1]), Color.WHITE)
        ));
    }
}
