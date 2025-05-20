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

public class GombászTestFejlesztésKeyAdapter extends KeyAdapter {
    private GameController controller;

    public GombászTestFejlesztésKeyAdapter(GameController controller) {
        this.controller = controller;
    }

    private void gombatestFejlesztés(Gombafaj kezeltFaj) {
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
            t.fejleszt();
            controller.update();
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

        System.out.println("F lenyomva");
        gombatestFejlesztés(g.getKezeltFaj());
    }
}
