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
        TektonrészView selectedView = controller.getSelectedTektonrészView();
        if (selectedView == null) return;

        Tektonrész hova = selectedView.getTektonrész();

        // Csak akkor lehet rovart lehelyezni, ha van már gomba
        if (!hova.vanGomba()) return;

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

        int prevPlayerIndex = controller.getPlayerManager().getAktuálisJátékosIndex();

        if (prevPlayerIndex == 7) {
            gameState.kovetkezoKor();
        } else{
            controller.getPlayerManager().következőJátékos();
        }
        controller.getGamePanel().updateStatusLabel();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (controller.getSelectedTektonrészView() == null || !(controller.getPlayerManager().getAktuálisJátékos() instanceof Rovarász)) {
            return;
        }
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_L:
                System.out.println("L lenyomva");
                helyezRovart();
                break;
            case KeyEvent.VK_V:
                System.out.println("V");
                break;
            case KeyEvent.VK_E:
                System.out.println("E");
                break;
            case KeyEvent.VK_UP:
                System.out.println("up");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("down");
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("right");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("left");
                break;
        }
    }
}
