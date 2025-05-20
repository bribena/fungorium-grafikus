package fungorium.ReControllers.GombászListeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fungorium.ReControllers.GameController;
import fungorium.ReModels.Entitás;
import fungorium.ReModels.Fungorium;
import fungorium.ReModels.Gombafaj;
import fungorium.ReModels.Gombafonal;
import fungorium.ReModels.Gombász;
import fungorium.ReModels.Játékos;
import fungorium.ReModels.Tektonrész;
import fungorium.ReViews.FungoriumView;
import fungorium.ReViews.GombafonalView;
import fungorium.ReViews.TektonrészView;

public class GombászFonalNövesztésKeyAdapter extends KeyAdapter {
    private GameController controller;

    public GombászFonalNövesztésKeyAdapter(GameController controller) {
        this.controller = controller;
    }

    private void gombafonalNövesztés(Gombafaj kezeltFaj, int irány) {
        TektonrészView selected = controller.getSelectedTektonrészView();
        Fungorium fungorium = controller.getJáték().getFungorium();
        FungoriumView fungoriumView = controller.getFungoriumView();

        Tektonrész selectedTektonrész = selected.getTektonrész();
        for (Entitás e : selectedTektonrész.getEntitások()) {
            if (!(e instanceof Gombafonal)) {
                continue;
            }

            Gombafonal f = (Gombafonal)e;

            if (f.getFaj() != kezeltFaj || !f.gombafonalatNöveszt(selectedTektonrész, irány, fungorium)) {
                continue;
            }

            Gombafonal uj = f.getKapcsolódóFonalak()[irány];
            Tektonrész köviTektonrész = fungorium.getTektonrészSzomszédok(selectedTektonrész)[irány];

            fungoriumView.getTektonrészView(köviTektonrész).add(new GombafonalView(uj));
            controller.update();

            if (uj.getKapcsolódóFonalak()[irány] == null) {
                continue;
            }

            Tektonrész köviKöviTektonrész = fungorium.getTektonrészSzomszédok(selectedTektonrész)[irány];
            fungoriumView.getTektonrészView(köviKöviTektonrész)
                .add(new GombafonalView(uj.getKapcsolódóFonalak()[irány]));

            controller.update();
            return;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Játékos j = controller.getJáték().getAktuálisJátékos();
        int irány = -1;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> irány = 0;
            case KeyEvent.VK_RIGHT -> irány = 1;
            case KeyEvent.VK_DOWN -> irány = 2;
            case KeyEvent.VK_LEFT -> irány = 3;
            default -> irány = -1;
        }

        if (controller.getSelectedTektonrészView() == null || !(j instanceof Gombász) 
            || controller.getJáték().kezdő() || controller.getJáték().vége() || irány == -1) {
            return;
        }
        
        Gombász g = (Gombász)j;

        System.out.println("Nyíl (" + irány + ") lenyomva");
        gombafonalNövesztés(g.getKezeltFaj(), irány);
    }
}
