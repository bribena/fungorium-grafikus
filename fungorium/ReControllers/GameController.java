package fungorium.ReControllers;

import fungorium.ReViews.*;
import java.awt.Point;

public class GameController {
    private FungoriumView fungoriumView;
    private TektonrészView selectedTektonrész;

    /**
     * Konstrukor.
     */
    public GameController(FungoriumView view) {
        this.fungoriumView = view;
    }

    /**
     * A tektonrész kiválasztásának logikája kattintást nézve.
     */
    public void selectByWindowPoint(Point p) {
        if (selectedTektonrész != null) {
            selectedTektonrész.toggleSelected();
        }

        selectedTektonrész = (TektonrészView)fungoriumView.getComponentAt(p);

        if (selectedTektonrész != null) {
            selectedTektonrész.toggleSelected();
        }
    }

    /**
     * A tektonrész kiválasztásának logikája a kattintás helyén lévő tektonrészre.
     */
    public void selectByTectonCoordinates(int x, int y) {
        TektonrészView tw = fungoriumView.getTektonrészView(x, y);
        if (tw == null) {
            return;
        }
        selectedTektonrész.toggleSelected();
        selectedTektonrész = tw;
        selectedTektonrész.toggleSelected();
    }

    /**
     * A tektonrész már nem "aktív", ne legyen kiválasztva.
     */
    public void deselect() {
        selectedTektonrész.toggleSelected();
        selectedTektonrész = null;
    }

    /**
     * A kiválasztott tektonrész View-át adja vissza.
     * 
     * @return TektonrészView
     */
    public TektonrészView getSelectedTektonrészView() {
        return selectedTektonrész;
    }

    public FungoriumView getFungoriumView() {
        return fungoriumView;
    }
}
