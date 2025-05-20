package fungorium.Views;

import java.awt.GridLayout;
import java.awt.Component;

import javax.swing.JPanel;

import fungorium.Models.Fungorium;
import fungorium.Models.Tektonrész;

//fungorium, azaz a játéktér megjelenítése
public class FungoriumView extends JPanel {
    //fungorium a modellből
    private Fungorium fungorium;

    //konstruktor, léterhoz egy alap pályát, összes tekton zökld, nincs rajtuk semmilyen entitás
    public FungoriumView(Fungorium f) {
        fungorium = f;

        setLayout(new GridLayout(f.getHeight(), f.getWidth()));

        for (int y = 0; y < fungorium.getHeight(); ++y) {
            for (int x = 0; x < fungorium.getWidth(); ++x) {
                add(new TektonrészView(f, x, y));
            }
        }

        setFocusable(true);
    }

    // public Fungorium getFungorium() {
    //     return fungorium;
    // }

    //egy adott (keresett) tektonrész nézetét visszaadja a fungoriumról
    public TektonrészView getTektonrészView(Tektonrész keresett) {
        for (int i = 0; i < getComponentCount(); ++i) {
            TektonrészView tv = (TektonrészView) getComponent(i);
            if (tv.getTektonrész() == keresett) {
                return tv;
            }
        }
        return null;
    }

    //egy adott (keresett) tektonrész nézetét visszaadja a fungoriumról a koordinátái alapján
    public TektonrészView getTektonrészView(int x, int y) {
        if (x < 0 || x >= fungorium.getWidth() || y < 0
                || y >= fungorium.getHeight()) {
            return null;
        }

        for (Component c : getComponents()) {
            if (c instanceof TektonrészView) {
                TektonrészView v = (TektonrészView) c;
                if (v.x() == x && v.y() == y) {
                    return v;
                }
            }
        }

        return null;
    }
}
