package fungorium.ReControllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class FungoriumMouseAdapter extends MouseAdapter {
    private GameController controller;

    public FungoriumMouseAdapter(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        controller.selectByWindowPoint(e.getPoint());
    }
}
