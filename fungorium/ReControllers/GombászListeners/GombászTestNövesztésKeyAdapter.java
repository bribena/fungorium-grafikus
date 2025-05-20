package fungorium.ReControllers.GombászListeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fungorium.ReControllers.GameController;
import fungorium.ReModels.Entitás;
import fungorium.ReModels.Gombafaj;
import fungorium.ReModels.Gombafonal;
import fungorium.ReModels.Gombatest;
import fungorium.ReModels.Gombász;
import fungorium.ReModels.Játékos;
import fungorium.ReModels.Tektonrész;
import fungorium.ReViews.GombatestView;
import fungorium.ReViews.TektonrészView;

public class GombászTestNövesztésKeyAdapter extends KeyAdapter {
    private GameController controller;

    public GombászTestNövesztésKeyAdapter(GameController controller) {
        this.controller = controller;
    }

    private void gombatestElhelyezés(Gombafaj kezeltFaj) {
        TektonrészView selected = controller.getSelectedTektonrészView();
        Tektonrész selectedTektonrész = selected.getTektonrész();

        Gombatest gt = new Gombatest(kezeltFaj);

        if (!selectedTektonrész.entitásHozzáadás(gt)) {
            return;
        }
        
        selected.add(new GombatestView(gt));
        gt.setKezdő();
        controller.update();
    }

    private void gombatestNövesztés(Gombafaj kezeltFaj) {
        TektonrészView selected = controller.getSelectedTektonrészView();
        Tektonrész selectedTektonrész = selected.getTektonrész();
        for (Entitás e : selectedTektonrész.getEntitások()) {
            if (!(e instanceof Gombafonal)) {
                continue;
            }
            Gombafonal f = (Gombafonal)e;

            if (f.getFaj() != kezeltFaj || !f.gombatestetNöveszt(selectedTektonrész, controller.getJáték().getFungorium())) {
                continue;
            }
            
            selected.add(new GombatestView(f.getKapcsolódóTest()));
            controller.update();
            return;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Játékos j = controller.getJáték().getAktuálisJátékos();

        if (controller.getSelectedTektonrészView() == null || !(j instanceof Gombász) || controller.getJáték().vége() 
            || e.getKeyCode() != KeyEvent.VK_N) {
            return;
        }

        Gombász g = (Gombász)j;

        System.out.println("N lenyomva");
        if (controller.getJáték().kezdő()) {
            gombatestElhelyezés(g.getKezeltFaj());
        }
        else {
            gombatestNövesztés(g.getKezeltFaj());
        }
    }
}
