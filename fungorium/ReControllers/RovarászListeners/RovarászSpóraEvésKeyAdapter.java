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
import fungorium.ReViews.RovarView;
import fungorium.ReViews.TektonrészView;

public class RovarászSpóraEvésKeyAdapter extends KeyAdapter {
    private GameController controller;

    public RovarászSpóraEvésKeyAdapter(GameController controller) {
        this.controller = controller;
    }

    private void spóraEvés(Rovarfaj faj) {
        TektonrészView selected = controller.getSelectedTektonrészView();
        Tektonrész selectedTektonrész = selected.getTektonrész();

        for (Entitás e : selectedTektonrész.getEntitások()) {
            if (!(e instanceof Rovar)) {
                continue;
            }
            Rovar r = (Rovar)e;
            if (r.getFaj() != faj || !r.spóraEvés(selectedTektonrész)) {
                continue;
            }

            Entitás e2 = selectedTektonrész.getEntitások().get(selectedTektonrész.getEntitások().size() - 1);
            if (!(e instanceof Rovar)) {
                return;
            }
            
            Rovar r2 = (Rovar)e2;
            if (r2 == r) {
                return;
            }
            selected.add(new RovarView(r2));
            controller.update();
            return;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Játékos j = controller.getJáték().getAktuálisJátékos();

        if (controller.getSelectedTektonrészView() == null || !(j instanceof Rovarász) || controller.getJáték().kezdő()
            || controller.getJáték().vége() || e.getKeyCode() != KeyEvent.VK_E) {
            return;
        }

        Rovarász r = (Rovarász)j;

        System.out.println("E");
        spóraEvés(r.getKezeltFaj());
    }
}
