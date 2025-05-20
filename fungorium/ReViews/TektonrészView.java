package fungorium.ReViews;

import java.util.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

// import javax.swing.JLabel;

import javax.swing.JLayeredPane;
import javax.swing.BorderFactory;

import fungorium.ReModels.*;

public class TektonrészView extends JLayeredPane {
    private static final int TILE_SIZE = 38;

    private List<EntitásView> e = new ArrayList<>();

    private Fungorium fungorium;
    private int x, y;

    public int x() {
        return x;
    }
    public int y() {
        return y;
    }

    private boolean selected = false;

    // private JLabel label;

    public Tektonrész getTektonrész() {
        return fungorium.getTektonrész(x, y);
    }

    public TektonrészView(Fungorium f, int x, int y) {
        fungorium = f;
        this.x = x;
        this.y = y;

        // label = new JLabel(fungorium.getTektonrész(x, y).getTektonID() + "");
        // add(label);

        setOpaque(true);
    }

    public void toggleSelected() {
        selected = !selected;
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
        } else if (comp instanceof GombatestView) {
            setLayer(comp, 2);
        } else if (comp instanceof SpóraView) {
            setLayer(comp, 1);
        } else if (comp instanceof RovarView) {
            setLayer(comp, 3);
        }

        return add((Component) comp);
    }

    public void removeComponentContaining(Entitás e) {
        EntitásView ew = null;
        for (EntitásView v : this.e) {
            if (v.contains(e)) {
                ew = v;
                break;
            }
        }
        if (ew == null) {
            return;
        }
        this.e.remove(ew);
        remove(ew);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(TILE_SIZE, TILE_SIZE);
    }

    /** boolean-t int-té alakít */
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
        } else if (tr instanceof EgyfonalasTektonrész) {
            setBackground(Color.CYAN);
        } else if (tr instanceof ÉletbentartóTektonrész) {
            setBackground(Color.PINK);
        } else if (tr instanceof FonalfelszívóTektonrész) {
            setBackground(Color.ORANGE);
        } else if (tr instanceof GombatestTiltóTektonrész) {
            setBackground(Color.RED);
        }

        // Szél rajzolása
        if (!selected) {
            boolean[] szel = tr.getTektonSzéleE();
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(bToI(!szel[0]), bToI(!szel[3]), bToI(!szel[2]), bToI(!szel[1]),
                            Color.BLACK),
                    BorderFactory.createMatteBorder(bToI(szel[0]), bToI(szel[3]), bToI(szel[2]), bToI(szel[1]),
                            Color.WHITE)));
        } else {
            setBorder(BorderFactory.createLineBorder(Color.CYAN, 4));
        }

        // label.setText(fungorium.getTektonrész(x, y).getTektonID() + "");
    }
}
