package fungorium.Controllers.GombászListeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fungorium.Controllers.GameController;
import fungorium.Models.Gombász;
import fungorium.Models.Játékos;

public class GombászKörVégeKeyAdapter extends KeyAdapter {
    private GameController controller;
    public GombászKörVégeKeyAdapter(GameController controller) {
        this.controller = controller;
    }

    

    @Override
    public void keyReleased(KeyEvent e) {
        Játékos j = controller.getJáték().getAktuálisJátékos();
        if (!controller.getSecondarySelect() || !(j instanceof Gombász) || controller.getJáték().vége() || controller.getJáték().kezdő()
            || e.getKeyCode() != KeyEvent.VK_A) {
            return;
        }
        
    }
}
