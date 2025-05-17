package fungorium.ReControllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fungorium.ReModels.Rovarász;

class FungoriumRovarászKeyAdapter extends KeyAdapter {
    private GameController controller;

    public FungoriumRovarászKeyAdapter(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (controller.getSelectedTektonrészView() == null || !(controller.getPlayerManager().getAktuálisJátékos() instanceof Rovarász)) {
            return;
        }
        
        switch (e.getKeyCode()) {
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
