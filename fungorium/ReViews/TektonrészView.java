package fungorium.ReViews;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLayeredPane;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

import fungorium.ReModels.*;

public class TektonrészView extends JLayeredPane {
    public static final int TILE_SIZE = 38;
    
    public Fungorium fungorium;
    public int x, y;
    // private JLabel debug = new JLabel("E", JLabel.CENTER);

    public TektonrészView(Fungorium f, int x, int y) {
        fungorium = f;
        this.x = x;
        this.y = y;

        // add(debug, 1);
        
        setOpaque(true);
        setVisible(true);
    }

    @Override
    public Component add(Component comp) {
        comp = super.add(comp);
        comp.setBounds(0, 0, TILE_SIZE, TILE_SIZE);
        return comp;
    }
    @Override
    public Component add(Component comp, int index) {
        comp = super.add(comp, index);
        comp.setBounds(0, 0, TILE_SIZE, TILE_SIZE);
        return comp;
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

        // debug.setText(fungorium.getTektonrész(x, y).getTektonID() + "");
    }
}
