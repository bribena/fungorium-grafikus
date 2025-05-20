package fungorium.Controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KörVégeKeyAdapter extends KeyAdapter {
    private GameController controller;
    public KörVégeKeyAdapter(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (controller.getJáték().vége() || controller.getJáték().kezdő() || e.getKeyCode() != KeyEvent.VK_A) {
            return;
        }
        controller.getJáték().léptet();
        controller.update();
    }
}
