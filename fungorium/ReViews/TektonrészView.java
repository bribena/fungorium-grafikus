package fungorium.ReViews;

import java.util.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLayeredPane;
import javax.swing.BorderFactory;

import fungorium.ReModels.*;

public class TektonrészView extends JLayeredPane {
    public static final int TILE_SIZE = 38;
    
    private List<EntitásView> e = new ArrayList<>();

    public Fungorium fungorium;
    public int x, y;
    // private JLabel debug = new JLabel("E", JLabel.CENTER);

    public TektonrészView(Fungorium f, int x, int y) {
        fungorium = f;
        this.x = x;
        this.y = y;

        Gombafaj faj = Gombafaj.values()[new Random().nextInt(4)];
        add(new GombafonalView(f.getTektonrész(x, y), faj));

        // if (x > 0) {
        //     ((Gombafonal)f.getTektonrész(x, y).getEntitások().get(0)).getSzomszédosFonalak()[3] = ((Gombafonal)f.getTektonrész(x - 1, y).getEntitások().get(0));
        //     ((Gombafonal)f.getTektonrész(x - 1, y).getEntitások().get(0)).getSzomszédosFonalak()[1] = ((Gombafonal)f.getTektonrész(x, y).getEntitások().get(0));
        // }

        add(new GombatestView(f.getTektonrész(x, y), faj));

        add(new SpóraView(f.getTektonrész(x, y), faj));
        // add(debug, 1);

        setOpaque(true);
    }

    @Override
    public Component add(Component comp) {
        comp = super.add(comp);
        comp.setBounds(0, 0, TILE_SIZE, TILE_SIZE);
        return comp;
    }

    public Component add(EntitásView comp) {
        e.add(comp);

        if (comp instanceof GombafonalView) {
            setLayer(comp, 0);
        }
        else if (comp instanceof GombatestView) {
            setLayer(comp, 2);
        }
        else if (comp instanceof SpóraView) {
            setLayer(comp, 1);
        }
        else if (comp instanceof RovarView) {
            setLayer(comp, 3);
        }

        return add((Component)comp);
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
        
        for (int i = 0; i < e.size(); ++i) {
            if (e.get(i) != null && !e.get(i).isValid()) {
                e.remove(i);
                --i;
            }
        }

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
