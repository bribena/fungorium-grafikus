package fungorium.ReViews;

import java.awt.Graphics;

import javax.swing.JPanel;

import fungorium.ReControllers.GameController;
import fungorium.ReModels.*;

public class GamePanel extends JPanel {
    public GameController controller;

    public GamePanel() {
        Játék k = new Játék();
        FungoriumView f = new FungoriumView(k.getFungorium());

        controller = new GameController(k, f);
        add(f);

        f.addMouseListener(controller.getFungoriumMouseAdapter());
        f.addKeyListener(controller.getGombászKeyAdapter());
        f.addKeyListener(controller.getRovarászKeyAdapter());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
