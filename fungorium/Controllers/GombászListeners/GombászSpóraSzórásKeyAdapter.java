package fungorium.Controllers.GombászListeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fungorium.Controllers.GameController;
import fungorium.Models.Entitás;
import fungorium.Models.Gombafaj;
import fungorium.Models.Gombatest;
import fungorium.Models.Gombász;
import fungorium.Models.Játékos;
import fungorium.Models.Tektonrész;
import fungorium.Views.TektonrészView;

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
            || e.getKeyCode() != KeyEvent.VK_S) {
            return;
        }

        Gombász g = (Gombász)j;

        System.out.println("S lenyomva");
        spóraSzórás(g.getKezeltFaj());
    }
}
