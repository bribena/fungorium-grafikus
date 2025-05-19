package fungorium.ReViews;

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

        setFocusable(true);
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
