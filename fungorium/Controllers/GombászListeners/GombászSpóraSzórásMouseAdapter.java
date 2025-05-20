package fungorium.Controllers.GombászListeners;

import fungorium.Controllers.GameController;
import fungorium.Models.Entitás;
import fungorium.Models.Fungorium;
import fungorium.Models.Gombafaj;
import fungorium.Models.Gombatest;
import fungorium.Models.Gombász;
import fungorium.Models.Játékos;
import fungorium.Models.Tektonrész;
import fungorium.Views.FungoriumView;
import fungorium.Views.SpóraView;
import fungorium.Views.TektonrészView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GombászSpóraSzórásMouseAdapter extends MouseAdapter {
    private GameController controller;

    public GombászSpóraSzórásMouseAdapter(GameController controller) {
        this.controller = controller;
    }

    private void spóraSzórás(Gombafaj kezeltFaj) {
        Tektonrész honnan = controller.getSelectedTektonrészView().getTektonrész();
        Tektonrész hova = controller.getSecondarySelectedTektonrészView().getTektonrész();
        Fungorium fungorium = controller.getJáték().getFungorium();
        FungoriumView fungoriumView = controller.getFungoriumView();

        for (Entitás ent : honnan.getEntitások()) {
            if (!(ent instanceof Gombatest)) {
                System.out.println("Nincs source gombatest");
                continue;
            }
            Gombatest t = (Gombatest)ent;

            if (t.getFaj() != kezeltFaj) {
                System.out.println("Nem a kezelt faj");
                continue;
            }
            if (!t.spórátSzór(honnan, hova, fungorium)) {
                System.out.println("Nem sikerult a szoras");
                controller.update();
                return;
            }

            Tektonrész[][] targets = fungorium.getSpóraTektonrészSzomszédok(hova);

            for (int i = 0; i < 3; ++i) {
                for (int l = 0; l < targets[i].length; ++l) {
                    TektonrészView v = fungoriumView.getTektonrészView(targets[i][l]);

                    if (v == null) {
                        continue;
                    }

                    v.add(
                        new SpóraView(targets[i][l].getEgyezőFajúSpóra(kezeltFaj))
                    );
                }
            }
            controller.update();
            controller.toggleSecondarySelect();
            return;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Játékos j = controller.getJáték().getAktuálisJátékos();
        if (!controller.getSecondarySelect() || !(j instanceof Gombász) || controller.getJáték().vége() || controller.getJáték().kezdő()) {
            return;
        }
        
        Gombász g = (Gombász)j;
        
        spóraSzórás(g.getKezeltFaj());
    }
}
