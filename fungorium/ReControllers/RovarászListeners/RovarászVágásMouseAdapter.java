package fungorium.ReControllers.RovarászListeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fungorium.ReControllers.GameController;
import fungorium.ReModels.Entitás;
import fungorium.ReModels.Fungorium;
import fungorium.ReModels.Játékos;
import fungorium.ReModels.Rovar;
import fungorium.ReModels.Rovarfaj;
import fungorium.ReModels.Rovarász;
import fungorium.ReModels.Tektonrész;

public class RovarászVágásMouseAdapter extends MouseAdapter {
    private GameController controller;

    public RovarászVágásMouseAdapter(GameController controller) {
        this.controller = controller;
    }

    private void fonalVágás(Rovarfaj faj) {
        Tektonrész holVan = controller.getSelectedTektonrészView().getTektonrész();
        Tektonrész hol = controller.getSecondarySelectedTektonrészView().getTektonrész();
        Fungorium fungorium = controller.getJáték().getFungorium();

        for (Entitás e : holVan.getEntitások()) {
            if (!(e instanceof Rovar)) {
                continue;
            }
            Rovar r = (Rovar)e;
            if (r.getFaj() != faj || r.fonalatVág(holVan, hol, fungorium)) {
                continue;
            }
            controller.update();
            controller.toggleSecondarySelect();
            return;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Játékos j = controller.getJáték().getAktuálisJátékos();
        if (!controller.getSecondarySelect() || !(j instanceof Rovarász) || controller.getJáték().vége() || controller.getJáték().kezdő()) {
            return;
        }
        
        Rovarász r = (Rovarász)j;
        
        fonalVágás(r.getKezeltFaj());
    }

}
