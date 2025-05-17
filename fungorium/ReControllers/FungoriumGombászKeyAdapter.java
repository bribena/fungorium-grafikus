package fungorium.ReControllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fungorium.ReModels.Gombász;

class FungoriumGombászKeyAdapter extends KeyAdapter {
    private GameController controller;

    public FungoriumGombászKeyAdapter(GameController controller) {
        this.controller = controller;
    }

    private void gombatestNövesztés() {
        
    }

    private void gombatestFejlesztés() {

    }

    private void spóraSzórás() {

    }



    @Override
    public void keyReleased(KeyEvent e) {
        if (controller.getSelectedTektonrészView() == null || !(controller.getJáték().getAktuálisJátékos() instanceof Gombász)) {
            return;
        }
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_N:
                gombatestNövesztés();
                break;
            case KeyEvent.VK_F:
                gombatestFejlesztés();
                break;
            case KeyEvent.VK_S:
                spóraSzórás();
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
