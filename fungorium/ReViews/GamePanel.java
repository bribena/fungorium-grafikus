package fungorium.ReViews;

import java.awt.Graphics;

import javax.swing.JPanel;

import fungorium.ReControllers.GameController;
import fungorium.ReModels.*;

public class GamePanel extends JPanel {
    public GameController controller;

    public GamePanel() {
        JátékKezelő k = new JátékKezelő();

        controller = new GameController(k);
        add(new FungoriumView(k.getFungorium()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
