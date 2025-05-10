package fungorium.ReViews;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import fungorium.ReControllers.GameController;
import fungorium.ReModels.Fungorium;

public class GamePanel extends JPanel {
    public GameController controller;

    public GamePanel() {
        Fungorium f = new Fungorium();

        controller = new GameController(f);
        add(new FungoriumView(f));

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == ' ') {
                    f.törés();
                    repaint();
                    System.out.println("Törés");
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
