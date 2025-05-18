package fungorium.ReControllers;

import fungorium.ReModels.*;
import fungorium.ReViews.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class FungoriumRovarászKeyAdapter extends KeyAdapter {
    private GameController controller;
    private GameStateManager gameState;

    public FungoriumRovarászKeyAdapter(GameController controller, GameStateManager gameState) {
        this.controller = controller;
        this.gameState = gameState;
    }

    public void helyezRovart() {
        // kiválasztott tektonrész lekérdezése + ha nincs kijelölt tektonrész, return
        TektonrészView selectedView = controller.getSelectedTektonrészView();
        if (selectedView == null) return;

        Tektonrész hova = selectedView.getTektonrész();

        // Csak akkor lehet rovart lehelyezni, ha van már gomba
        if (!hova.vanGomba()) return;

        // ha nem rovarász a soron következő játékos, return
        if (!(controller.getPlayerManager().getAktuálisJátékos() instanceof Rovarász)) return;

        Rovarász player = (Rovarász) controller.getPlayerManager().getAktuálisJátékos();
        Rovarfaj faj = player.getKezeltFaj();
        if (faj == null) return;

        Rovar rovar = new Rovar(faj);
        hova.entitásHozzáadás(rovar);

        // View frissítése
        RovarView rovarView = new RovarView(hova, rovar);
        selectedView.add(rovarView);
        selectedView.revalidate();
        selectedView.repaint();

        // TODO Maybe külön logikában vagy már van is csak nem néztem meg
        // játékosok és kör léptetése, ha kell
        int prevPlayerIndex = controller.getPlayerManager().getAktuálisJátékosIndex();
        // ha utolsó játékos lépett, kövi kör jön
        if (prevPlayerIndex == 7) {
            gameState.kovetkezoKor();
        } else{
            // amúgy kövi játékos
            controller.getPlayerManager().következőJátékos();
        }
        controller.getGamePanel().updateStatusLabel();
    }

    private void mozgatRovart(int irány) {
        TektonrészView selectedView = controller.getSelectedTektonrészView();
        if (selectedView == null) return;

        // aktuális/kiválasztott tektonrész
        Tektonrész jelenlegi = selectedView.getTektonrész();

        // Játékos csak a saját rovarát tudja mozgatni
        if (!(controller.getPlayerManager().getAktuálisJátékos() instanceof Rovarász)) return;
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
        if (rovar == null /*|| rovar.isBena()*/) return;

        // kijelölt tektonrész koordinátája
        int[] coord = controller.getPlayerManager().getFungorium().getTektonrészKoordináta(jelenlegi);
        // koordináta alapán szomszédok keresése
        Tektonrész[] szomszédok = controller.getPlayerManager().getFungorium().getTektonrészSzomszédok(coord[0], coord[1]);
        // irány alapján megmondjuk, melyik szomszédra kell menni
        Tektonrész cél = szomszédok[irány];
        // ha nincs fonal a cél tektonrészen, nem tud oda mozogni
        // TODO: bénaság ellenőrzése
        if (!cél.vanFonal()) return;

        // Mozgatás
        rovar.mozog(jelenlegi, cél);
        System.out.println("Rovar mozog.");

        int prevPlayerIndex = controller.getPlayerManager().getAktuálisJátékosIndex();
        // View frissítés
        FungoriumView fungoriumView = controller.getGamePanel().getFungoriumView();

        // 1. régi View-ból kivesszük
        // TODO

        // 2. új View-ba betesszük
        // TODO

        if (prevPlayerIndex == 7) {
            gameState.kovetkezoKor();
        } else{
            controller.getPlayerManager().következőJátékos();
        }

        // Kirajzolás frissítése
        // TODO
        controller.getGamePanel().updateStatusLabel();
    }

    private void spóraEvés(){

    }

    private void fonalVágás(){

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // ha nincs kijelölve Tektonrész vagy a soron következő játékos nem rovarász, return
        if (controller.getSelectedTektonrészView() == null || !(controller.getPlayerManager().getAktuálisJátékos() instanceof Rovarász)) {
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
                break;
            case KeyEvent.VK_E:
                // E billentyű lenyomására rovar egyen spórát
                System.out.println("E");
                break;
            // adott nyíl billentyű lenyomása után abba az irányba mozogjon
            case KeyEvent.VK_UP:
                System.out.println("up");
                mozgatRovart(0);
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("down");
                mozgatRovart(1);
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("right");
                mozgatRovart(2);
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("left");
                mozgatRovart(3);
                break;
        }
    }
}
