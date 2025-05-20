package fungorium.ReControllers.GombászListeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fungorium.ReControllers.GameController;
import fungorium.ReModels.Entitás;
import fungorium.ReModels.Gombafaj;
import fungorium.ReModels.Gombatest;
import fungorium.ReModels.Gombász;
import fungorium.ReModels.Játékos;
import fungorium.ReModels.Tektonrész;
import fungorium.ReViews.TektonrészView;

public class GombászSpóraSzórásKeyAdapter extends KeyAdapter {
    private GameController controller;

    public GombászSpóraSzórásKeyAdapter(GameController controller) {
        this.controller = controller;
    }

    private void spóraSzórás(Gombafaj kezeltFaj) {
        TektonrészView selected = controller.getSelectedTektonrészView();
        Tektonrész selectedTektonrész = selected.getTektonrész();
        for (Entitás e : selectedTektonrész.getEntitások()) {
            if (!(e instanceof Gombatest)) {
                continue;
            }

            Gombatest t = (Gombatest)e;

            if (t.getFaj() != kezeltFaj) {
                continue;
            }

            controller.toggleSecondarySelect();
            return;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Játékos j = controller.getJáték().getAktuálisJátékos();

         if (controller.getSelectedTektonrészView() == null || !(j instanceof Gombász) 
            || controller.getJáték().kezdő() || controller.getJáték().vége()
            || e.getKeyCode() != KeyEvent.VK_F) {
            return;
        }

        Gombász g = (Gombász)j;

        System.out.println("S lenyomva");
        spóraSzórás(g.getKezeltFaj());
    }
}
