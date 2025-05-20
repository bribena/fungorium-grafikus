package fungorium.Controllers.RovarászListeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fungorium.Controllers.GameController;
import fungorium.Models.Entitás;
import fungorium.Models.Fungorium;
import fungorium.Models.Játékos;
import fungorium.Models.Rovar;
import fungorium.Models.Rovarfaj;
import fungorium.Models.Rovarász;
import fungorium.Models.Tektonrész;

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
