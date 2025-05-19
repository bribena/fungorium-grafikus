package fungorium.ReControllers;

import fungorium.ReModels.*;
import fungorium.ReViews.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FungoriumRovarászKeyAdapter extends KeyAdapter {
    private GameController controller;
    private GameStateManager gameState;

    public FungoriumRovarászKeyAdapter(GameController controller, GameStateManager gameState) {
        this.controller = controller;
        this.gameState = gameState;
    }

    public void helyezRovart() {
        // kiválasztott tektonrész lekérdezése + ha nincs kijelölt tektonrész, return
        TektonrészView selectedView = controller.getSelectedTektonrészView();
        if (selectedView == null)
            return;

        Tektonrész hova = selectedView.getTektonrész();

        // Csak akkor lehet rovart lehelyezni, ha van már gomba
        if (!hova.vanGombatest())
            return;

        // ha nem rovarász a soron következő játékos, return
        if (!(controller.getPlayerManager().getAktuálisJátékos() instanceof Rovarász))
            return;

        Rovarász player = (Rovarász) controller.getPlayerManager().getAktuálisJátékos();
        Rovarfaj faj = player.getKezeltFaj();
        if (faj == null)
            return;

        Rovar rovar = new Rovar(faj);
        hova.entitásHozzáadás(rovar);

        // View frissítése
        RovarView rovarView = new RovarView(rovar);
        selectedView.add(rovarView);
        selectedView.revalidate();
        selectedView.repaint();

        // TODO Maybe külön logikában vagy már van is csak nem néztem meg
        // játékosok és kör léptetése, ha kell
        int prevPlayerIndex = controller.getPlayerManager().getAktuálisJátékosIndex();
        // ha utolsó játékos lépett, kövi kör jön
        if (prevPlayerIndex == 7) {
            gameState.kovetkezoKor();
        } else {
            // amúgy kövi játékos
            controller.getPlayerManager().következőJátékos();
        }
        controller.getGamePanel().updateStatusLabel();
    }

    private void mozgatRovart(int irány) {
        TektonrészView selectedView = controller.getSelectedTektonrészView();
        if (selectedView == null)
            return;

        Tektonrész jelenlegi = selectedView.getTektonrész();

        // Csak a saját rovarát mozgathatja a játékos
        if (!(controller.getPlayerManager().getAktuálisJátékos() instanceof Rovarász))
            return;
        Rovarász player = (Rovarász) controller.getPlayerManager().getAktuálisJátékos();

        // Rovar keresése
        Rovar rovar = null;
        for (Entitás e : jelenlegi.getEntitások()) {
            if (e instanceof Rovar && ((Rovar) e).getFaj() == player.getKezeltFaj()) {
                rovar = (Rovar) e;
                break;
            }
        }

        // ha nincs rovar vagy a rovaron bénító hatás van, nem tud mozogni
        if (rovar == null /* || rovar.isBena() */)
            return;

        // kijelölt tektonrész koordinátája
        int[] coord = controller.getPlayerManager().getFungorium().getTektonrészKoordináta(jelenlegi);
        Tektonrész[] szomszédok = controller.getPlayerManager().getFungorium().getTektonrészSzomszédok(coord[0],
                coord[1]);
        Tektonrész cél = szomszédok[irány];
        if (cél == null)
            return;

        // ha nincs fonal a cél tektonrészen, nem tud oda mozogni
        if (!cél.vanFonal())
            return;

        // Modell: rovar mozgatása
        rovar.mozog(jelenlegi, cél);

        // Nézet: RovarView áthelyezése a régi mezőről az újra
        RovarView rovarView = null;
        for (java.awt.Component comp : selectedView.getComponents()) {
            if (comp instanceof RovarView) {
                RovarView rv = (RovarView) comp;
                if (rv.getRovar() == rovar) {
                    rovarView = rv;
                    break;
                }
            }
        }
        if (rovarView != null) {
            selectedView.remove(rovarView);

            TektonrészView célView = controller.getGamePanel().getFungoriumView().getTektonrészView(cél);
            if (célView != null) {
                célView.add(rovarView);
                célView.revalidate();
                célView.repaint();
            }
        }

        // Mindkét érintett TektonrészView újrarajzolása
        selectedView.revalidate();
        selectedView.repaint();

        // Játékos/kör léptetése
        int prevPlayerIndex = controller.getPlayerManager().getAktuálisJátékosIndex();
        if (prevPlayerIndex == 7) {
            gameState.kovetkezoKor();
        } else {
            controller.getPlayerManager().következőJátékos();
        }
        controller.getGamePanel().updateStatusLabel();
    }

    private void spóraEvés() {
        TektonrészView selectedView = controller.getSelectedTektonrészView();
        if (selectedView == null)
            return;

        Tektonrész tektonrész = selectedView.getTektonrész();

        // Csak akkor, ha a soron következő játékos rovarász
        if (!(controller.getPlayerManager().getAktuálisJátékos() instanceof Rovarász))
            return;
        Rovarász player = (Rovarász) controller.getPlayerManager().getAktuálisJátékos();

        // Saját rovar keresése
        Rovar rovar = null;
        for (Entitás e : tektonrész.getEntitások()) {
            if (e instanceof Rovar && ((Rovar) e).getFaj() == player.getKezeltFaj()) {
                rovar = (Rovar) e;
                break;
            }
        }
        if (rovar == null)
            return;

        // Spóra keresése a mezőn
        Spóra spora = null;
        for (Entitás e : tektonrész.getEntitások()) {
            if (e instanceof Spóra && !((Spóra) e).isMegevett()) {
                spora = (Spóra) e;
                break;
            }
        }
        if (spora == null)
            return;

        // Modell: spóra megevése
        spora.megette();
        player.addMegevettSpóra(spora);

        // View: újrarajzolás (a View magától lekérdezi a modellből az állapotot)
        selectedView.revalidate();
        selectedView.repaint();

        // Játékos/kör léptetése
        int prevPlayerIndex = controller.getPlayerManager().getAktuálisJátékosIndex();
        if (prevPlayerIndex == 7) {
            gameState.kovetkezoKor();
        } else {
            controller.getPlayerManager().következőJátékos();
        }
        controller.getGamePanel().updateStatusLabel();
    }

    private void fonalVágás() {
        TektonrészView selectedView = controller.getSelectedTektonrészView();
        if (selectedView == null)
            return;

        Tektonrész tektonrész = selectedView.getTektonrész();

        // Csak akkor, ha a soron következő játékos rovarász
        if (!(controller.getPlayerManager().getAktuálisJátékos() instanceof Rovarász))
            return;
        Rovarász player = (Rovarász) controller.getPlayerManager().getAktuálisJátékos();

        // Saját rovar keresése
        Rovar rovar = null;
        for (Entitás e : tektonrész.getEntitások()) {
            if (e instanceof Rovar && ((Rovar) e).getFaj() == player.getKezeltFaj()) {
                rovar = (Rovar) e;
                break;
            }
        }
        if (rovar == null)
            return;

        // Fonal keresése a mezőn
        Gombafonal fonal = null;
        for (Entitás e : tektonrész.getEntitások()) {
            if (e instanceof Gombafonal) {
                fonal = (Gombafonal) e;
                break;
            }
        }
        if (fonal == null)
            return;

        // Modell: fonal eltávolítása
        tektonrész.entitásTörlés(fonal);

        // View: újrarajzolás (a View magától lekérdezi a modellből az állapotot)
        selectedView.revalidate();
        selectedView.repaint();

        // Játékos/kör léptetése
        int prevPlayerIndex = controller.getPlayerManager().getAktuálisJátékosIndex();
        if (prevPlayerIndex == 7) {
            gameState.kovetkezoKor();
        } else {
            controller.getPlayerManager().következőJátékos();
        }
        controller.getGamePanel().updateStatusLabel();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // ha nincs kijelölve Tektonrész vagy a soron következő játékos nem rovarász,
        // return
        if (controller.getSelectedTektonrészView() == null
                || !(controller.getPlayerManager().getAktuálisJátékos() instanceof Rovarász)) {
            return;
        }

        switch (e.getKeyCode()) {
            // L billenytyű lenyomására rovar lehelyezése
            case KeyEvent.VK_L:
                System.out.println("L lenyomva");
                helyezRovart();
                break;
            case KeyEvent.VK_V:
                // V billenytű lenyomására rovar vágja el a fonalat
                System.out.println("V");
                fonalVágás();
                break;
            case KeyEvent.VK_E:
                // E billentyű lenyomására rovar egyen spórát
                System.out.println("E");
                spóraEvés();
                break;
            // adott nyíl billentyű lenyomása után abba az irányba mozogjon
            case KeyEvent.VK_UP:
                System.out.println("up");
                mozgatRovart(0);
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("down");
                mozgatRovart(2);
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("right");
                mozgatRovart(1);
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("left");
                mozgatRovart(3);
                break;
        }
    }
}
