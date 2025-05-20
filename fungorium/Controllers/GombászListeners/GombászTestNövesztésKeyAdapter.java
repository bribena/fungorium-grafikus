package fungorium.Controllers.GombászListeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fungorium.Controllers.GameController;
import fungorium.Models.Entitás;
import fungorium.Models.Gombafaj;
import fungorium.Models.Gombafonal;
import fungorium.Models.Gombatest;
import fungorium.Models.Gombász;
import fungorium.Models.Játékos;
import fungorium.Models.Tektonrész;
import fungorium.Views.FungoriumView;
import fungorium.Views.GombafonalView;
import fungorium.Views.GombatestView;
import fungorium.Views.TektonrészView;

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

        Gombafonal a = new Gombafonal(kezeltFaj);
        selectedTektonrész.entitásHozzáadás(a);
        a.setKapcsolódóTest(gt);
        selected.add(new GombafonalView(a));

        Tektonrész[] szomszédok = controller.getJáték().getFungorium().getTektonrészSzomszédok(selectedTektonrész);
        FungoriumView fungoriumView = controller.getFungoriumView();

        for (int i = 0; i < 4; ++i) {
            if (szomszédok[i].getTektonID() != -1) {
                Gombafonal f = new Gombafonal(kezeltFaj);
                szomszédok[i].entitásHozzáadás(f);
                a.összekapcsolás(f, i);
                fungoriumView.getTektonrészView(szomszédok[i]).add(new GombafonalView(f));
            }
        }

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
