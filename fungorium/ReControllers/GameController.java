package fungorium.ReControllers;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyAdapter;
import java.awt.Point;

import fungorium.ReViews.FungoriumView;
import fungorium.ReViews.TektonrészView;
import fungorium.ReModels.Játék;

public class GameController {
    private Játék kezelő;
    private FungoriumView view;
    private TektonrészView selectedTektonrész;

    public GameController(Játék kezelő, FungoriumView view) {
        this.kezelő = kezelő;
        this.view = view;
    }

    public Játék getJáték() {
        return kezelő;
    }

    public void selectByWindowPoint(Point p) {
        if (selectedTektonrész != null) {
            selectedTektonrész.toggleSelected();
        }

        selectedTektonrész = (TektonrészView)view.getComponentAt(p);

        if (selectedTektonrész != null) {
            selectedTektonrész.toggleSelected();
        } 
    }

    public void selectByTectonCoordinates(int x, int y) {
        if (x < 0 || x >= kezelő.getFungorium().getWidth() || y < 0 || y >= kezelő.getFungorium().getHeight()) {
            return;
        }

        for (Component c : view.getComponents()) {
            if (c instanceof TektonrészView) {
                TektonrészView v = (TektonrészView)c;
                if (v.x == x && v.y == y) {
                    selectedTektonrész.toggleSelected();
                    selectedTektonrész = v;
                    selectedTektonrész.toggleSelected();
                    return;
                }
            }
        }
    }

    public void deselect() {
        selectedTektonrész.toggleSelected();
        selectedTektonrész = null;
    }

    public TektonrészView getSelectedTektonrészView() {
        return selectedTektonrész;
    }

    private FungoriumMouseAdapter mouseAdapter = new FungoriumMouseAdapter(this);

    public MouseAdapter getFungoriumMouseAdapter() {
        return mouseAdapter;
    }

    private FungoriumGombászKeyAdapter gombászKeyAdapter = new FungoriumGombászKeyAdapter(this);

    public KeyAdapter getGombászKeyAdapter() {
        return gombászKeyAdapter;
    }

    private FungoriumRovarászKeyAdapter rovarászKeyAdapter = new FungoriumRovarászKeyAdapter(this);

    public KeyAdapter getRovarászKeyAdapter() {
        return rovarászKeyAdapter;
    }
}
