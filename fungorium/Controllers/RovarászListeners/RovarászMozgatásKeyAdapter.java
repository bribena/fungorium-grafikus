package fungorium.Controllers.RovarászListeners;

import fungorium.Controllers.GameController;
import fungorium.Models.Entitás;
import fungorium.Models.Fungorium;
import fungorium.Models.Játékos;
import fungorium.Models.Rovar;
import fungorium.Models.Rovarfaj;
import fungorium.Models.Rovarász;
import fungorium.Models.Tektonrész;
import fungorium.Views.FungoriumView;
import fungorium.Views.RovarView;
import fungorium.Views.TektonrészView;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RovarászMozgatásKeyAdapter extends KeyAdapter {
    private GameController controller;

    public RovarászMozgatásKeyAdapter(GameController controller) {
        this.controller = controller;
    }

    private void rovarMozgatás(Rovarfaj faj, int irány) {
        TektonrészView selected = controller.getSelectedTektonrészView();
        Tektonrész selectedTektonrész = selected.getTektonrész();
        FungoriumView fungoriumView = controller.getFungoriumView();
        Fungorium fungorium = controller.getJáték().getFungorium();

        for (Entitás e : selectedTektonrész.getEntitások()) {
            if (!(e instanceof Rovar)) {
                continue;
            }

            Rovar r = (Rovar)e;
            if (r.getFaj() != faj || !r.mozog(selectedTektonrész, irány, fungorium)) {
                System.out.println("Nem sikerult a rovar mozgatas");
                continue;
            }

            Tektonrész sz = fungorium.getTektonrészSzomszédok(selectedTektonrész)[irány];
            if (sz.tartalmaz(r)) {
                selected.removeComponentContaining(r);
                fungoriumView.getTektonrészView(sz).add(new RovarView(r));
            }
            else {
                sz = fungorium.getTektonrészSzomszédok(sz)[irány];
                selected.removeComponentContaining(r);
                fungoriumView.getTektonrészView(sz).add(new RovarView(r));
            }

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

        if (controller.getSelectedTektonrészView() == null || !(j instanceof Rovarász) 
            || controller.getJáték().kezdő() || controller.getJáték().vége() || irány == -1) {
            return;
        }
        
        Rovarász r = (Rovarász)j;

        System.out.println("Nyíl (" + irány + ") lenyomva");
        rovarMozgatás(r.getKezeltFaj(), irány);
    }
}
