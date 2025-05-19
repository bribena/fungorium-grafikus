package fungorium.ReControllers;

import fungorium.ReModels.*;
import fungorium.ReViews.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * N: Növesztés
 * F: Fejlesztés
 * S: Spóraszórás mód toggle
 * Nyilak: Fonalnövesztés
 */
public class FungoriumGombászKeyAdapter extends KeyAdapter {
    private GameController controller;
    

    public FungoriumGombászKeyAdapter(GameController controller) {
        this.controller = controller;
    }

    private void gombatestNövesztés(Gombafaj kezeltFaj) {
        TektonrészView selected = controller.getSelectedTektonrészView();
        Tektonrész selectedTektonrész = selected.getTektonrész();
        for (Entitás e : selectedTektonrész.getEntitások()) {
            if (e instanceof Gombafonal) {
                Gombafonal f = (Gombafonal)e;
                if (f.getFaj() == kezeltFaj && f.gombatestetNöveszt(selectedTektonrész, controller.getJáték().getFungorium())) {
                    selected.add(new GombatestView(f.getKapcsolódóTest()));
                }
            }
        }
    }

    private void gombatestFejlesztés(Gombafaj kezeltFaj) {
        TektonrészView selected = controller.getSelectedTektonrészView();
        Tektonrész selectedTektonrész = selected.getTektonrész();
        for (Entitás e : selectedTektonrész.getEntitások()) {
            if (e instanceof Gombatest) {
                Gombatest t = (Gombatest)e;
                if (t.getFaj() == kezeltFaj) {
                    t.fejleszt();
                }
            }
        }
    }

    private void gombafonalNövesztés(Gombafaj kezeltFaj, int irány) {
        TektonrészView selected = controller.getSelectedTektonrészView();
        Fungorium fungorium = controller.getJáték().getFungorium();
        FungoriumView fungoriumView = controller.getFungoriumView();


        Tektonrész selectedTektonrész = selected.getTektonrész();
        for (Entitás e : selectedTektonrész.getEntitások()) {
            if (e instanceof Gombafonal) {
                Gombafonal f = (Gombafonal)e;
                if (f.getFaj() == kezeltFaj && 
                    f.gombafonalatNöveszt(selectedTektonrész, irány, fungorium)) {
                    Gombafonal uj = f.getKapcsolódóFonalak()[irány];
                    Tektonrész köviTektonrész = fungorium.getTektonrészSzomszédok(selectedTektonrész)[irány];

                    fungoriumView.getTektonrészView(köviTektonrész).add(new GombafonalView(uj));

                    if (uj.getKapcsolódóFonalak()[irány] != null) {
                        Tektonrész köviKöviTektonrész = fungorium.getTektonrészSzomszédok(selectedTektonrész)[irány];
                        fungoriumView.getTektonrészView(köviKöviTektonrész)
                            .add(new GombafonalView(uj.getKapcsolódóFonalak()[irány]));
                    }

                }
            }
        }
    }

    private void spóraSzórás(Gombafaj kezeltFaj) {
        TektonrészView selected = controller.getSelectedTektonrészView();
        Tektonrész selectedTektonrész = selected.getTektonrész();
        for (Entitás e : selectedTektonrész.getEntitások()) {
            if (e instanceof Gombatest) {
                Gombatest t = (Gombatest)e;
                if (t.getFaj() == kezeltFaj) {
                    controller.toggleSecondarySelect();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Játékos j = controller.getJáték().getAktuálisJátékos();
        // ha nincs kijelölt tektonrész vagy nem gombász a játékos, return
        if (controller.getSelectedTektonrészView() == null || !(j instanceof Gombász)) {
            return;
        }
        Gombász g = (Gombász)j;

        if (controller.getJáték().kezdő()) {
            if (e.getKeyCode() == KeyEvent.VK_N) {
                System.out.println("N lenyomva");
                gombatestNövesztés(g.getKezeltFaj());
            }
        }
        else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_N:
                    // N billentyűre gombatestet növeszt
                    System.out.println("N lenyomva");
                    gombatestNövesztés(g.getKezeltFaj());
                    break;
                case KeyEvent.VK_F:
                    // F billentyűre gombatestet fejleszt
                    System.out.println("F lenyomva");
                    gombatestFejlesztés(g.getKezeltFaj());
                    break;
                case KeyEvent.VK_S:
                    // S billentyűre spórát szór
                    System.out.println("S lenyomva");
                    spóraSzórás(g.getKezeltFaj());
                    break;
                // nyíl billentyűknek megfelelően gombafonal növesztés
                case KeyEvent.VK_UP:
                    System.out.println("up");
                    gombafonalNövesztés(g.getKezeltFaj(), 0);
                    break;
                case KeyEvent.VK_DOWN:
                    System.out.println("down");
                    gombafonalNövesztés(g.getKezeltFaj(), 2);
                    break;
                case KeyEvent.VK_RIGHT:
                    System.out.println("right");
                    gombafonalNövesztés(g.getKezeltFaj(), 1);
                    break;
                case KeyEvent.VK_LEFT:
                    System.out.println("left");
                    gombafonalNövesztés(g.getKezeltFaj(), 3);
                    break;
            }
            controller.getJáték().automataLéptetés();
        }
        
        controller.getFungoriumView().revalidate();
    }
}
