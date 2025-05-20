package fungorium.ReControllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FungoriumSelectionMouseAdapter extends MouseAdapter {
    private GameController controller;

    public FungoriumSelectionMouseAdapter(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        controller.selectByWindowPoint(e.getPoint());
    }
}
