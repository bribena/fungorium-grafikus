package fungorium.Controllers.GombászListeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fungorium.Controllers.GameController;
import fungorium.Models.Entitás;
import fungorium.Models.Fungorium;
import fungorium.Models.Gombafaj;
import fungorium.Models.Gombafonal;
import fungorium.Models.Gombász;
import fungorium.Models.Játékos;
import fungorium.Models.Tektonrész;
import fungorium.Views.FungoriumView;
import fungorium.Views.GombafonalView;
import fungorium.Views.TektonrészView;

public class GombászFonalNövesztésKeyAdapter extends KeyAdapter {
    private GameController controller;

    public GombászFonalNövesztésKeyAdapter(GameController controller) {
        this.controller = controller;
    }

    private void gombafonalNövesztés(Gombafaj kezeltFaj, int irány) {
        TektonrészView selected = controller.getSelectedTektonrészView();
        Tektonrész selectedTektonrész = selected.getTektonrész();
        FungoriumView fungoriumView = controller.getFungoriumView();
        Fungorium fungorium = controller.getJáték().getFungorium();

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
                return;
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
