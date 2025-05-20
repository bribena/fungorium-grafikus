package fungorium.Controllers.RovarászListeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fungorium.Controllers.GameController;
import fungorium.Models.Entitás;
import fungorium.Models.Játékos;
import fungorium.Models.Rovar;
import fungorium.Models.Rovarfaj;
import fungorium.Models.Rovarász;
import fungorium.Models.Tektonrész;
import fungorium.Views.RovarView;
import fungorium.Views.TektonrészView;

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
