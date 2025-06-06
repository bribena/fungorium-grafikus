package fungorium.Controllers;

import fungorium.Models.Játék;
import fungorium.Views.*;
import java.awt.Point;

public class GameController {
    private FungoriumView fungoriumView;
    private Játék játék;
    private TektonrészView selectedTektonrész;
    private boolean secondarySelect = false;
    private TektonrészView secondarySelectedTektonrész;

    /**
     * Konstrukor.
     */
    public GameController(FungoriumView view, Játék játék) {
        this.fungoriumView = view;
        this.játék = játék;
    }

    public boolean getSecondarySelect() {
        return secondarySelect;
    }
    public void toggleSecondarySelect() {
        secondarySelect = !secondarySelect;
    }

    public Játék getJáték() {
        return játék;
    }

    /**
     * A tektonrész kiválasztásának logikája kattintást nézve.
     */
    public void selectByWindowPoint(Point p) {
        if (!secondarySelect) {
            if (selectedTektonrész != null) {
                selectedTektonrész.toggleSelected();
            }
    
            selectedTektonrész = (TektonrészView)fungoriumView.getComponentAt(p);
    
            if (selectedTektonrész != null) {
                selectedTektonrész.toggleSelected();
            }
        }
        else {
            secondarySelectedTektonrész = (TektonrészView)fungoriumView.getComponentAt(p);
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
        if (selectedTektonrész != null) {
            selectedTektonrész.toggleSelected();
            selectedTektonrész = null;
        }
        if (secondarySelectedTektonrész != null) {
            secondarySelectedTektonrész = null;
            secondarySelect = false;
        }
    }

    /**
     * A kiválasztott tektonrész View-át adja vissza.
     * 
     * @return TektonrészView
     */
    public TektonrészView getSelectedTektonrészView() {
        return selectedTektonrész;
    }

    public TektonrészView getSecondarySelectedTektonrészView() {
        return secondarySelectedTektonrész;
    }

    public FungoriumView getFungoriumView() {
        return fungoriumView;
    }

    public void update() {
        deselect();
        játék.automataLéptetés();
        fungoriumView.revalidate();
    }
}
