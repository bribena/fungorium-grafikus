package fungorium.ReControllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fungorium.ReModels.Entitás;
import fungorium.ReModels.Fungorium;
import fungorium.ReModels.Gombatest;
import fungorium.ReModels.Gombász;
import fungorium.ReModels.Játékos;
import fungorium.ReModels.Tektonrész;
import fungorium.ReViews.FungoriumView;
import fungorium.ReViews.SpóraView;
import fungorium.ReViews.TektonrészView;

public class GombászSpóraSzórásMouseAdapter extends MouseAdapter {
    private GameController controller;

    public GombászSpóraSzórásMouseAdapter(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Játékos j = controller.getJáték().getAktuálisJátékos();
        if (controller.getSecondarySelect() && j instanceof Gombász) {
            Gombász g = (Gombász)j;
            Tektonrész honnan = controller.getSelectedTektonrészView().getTektonrész();
            Tektonrész hova = controller.getSecondarySelectedTektonrészView().getTektonrész();
            Fungorium fungorium = controller.getJáték().getFungorium();
            FungoriumView fungoriumView = controller.getFungoriumView();
            for (Entitás ent : honnan.getEntitások()) {
                if (ent instanceof Gombatest) {
                    Gombatest t = (Gombatest)ent;
                    if (t.getFaj() == g.getKezeltFaj() && t.spórátSzór(honnan, hova, fungorium)) {
                        Tektonrész[][] targets = fungorium.getSpóraTektonrészSzomszédok(hova);

                        for (int i = 0; i < 3; ++i) {
                            for (int l = 0; l < targets[i].length; ++i) {
                                TektonrészView v = fungoriumView.getTektonrészView(targets[i][l]);
                                if (v != null) {
                                    v.add(
                                        new SpóraView(targets[i][l].getEgyezőFajúSpóra(g.getKezeltFaj()))
                                    );
                                }
                            }
                        }

                        controller.toggleSecondarySelect();
                    }
                }
            }
        }
    }
}
