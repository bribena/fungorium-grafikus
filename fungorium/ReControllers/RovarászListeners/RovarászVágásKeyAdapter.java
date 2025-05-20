package fungorium.ReControllers.RovarászListeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fungorium.ReControllers.GameController;
import fungorium.ReModels.Entitás;
import fungorium.ReModels.Játékos;
import fungorium.ReModels.Rovar;
import fungorium.ReModels.Rovarfaj;
import fungorium.ReModels.Rovarász;
import fungorium.ReModels.Tektonrész;
import fungorium.ReViews.TektonrészView;

public class RovarászVágásKeyAdapter extends KeyAdapter {
    private GameController controller;

    public RovarászVágásKeyAdapter(GameController controller) {
        this.controller = controller;
    }

    private void fonalVágás(Rovarfaj faj) {
        TektonrészView selected = controller.getSelectedTektonrészView();
        Tektonrész selectedTektonrész = selected.getTektonrész();

        for (Entitás e : selectedTektonrész.getEntitások()) {
            if (!(e instanceof Rovar)) {
                continue;
            }
            Rovar r = (Rovar)e;
            if (r.getFaj() != faj) {
                continue;
            }

            controller.toggleSecondarySelect();
            return;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Játékos j = controller.getJáték().getAktuálisJátékos();

        if (controller.getSelectedTektonrészView() == null || !(j instanceof Rovarász) || controller.getJáték().kezdő()
            || controller.getJáték().vége() || e.getKeyCode() != KeyEvent.VK_V) {
            return;
        }

        Rovarász r = (Rovarász)j;

        System.out.println("V lenyomva");
        fonalVágás(r.getKezeltFaj());
    }
}
