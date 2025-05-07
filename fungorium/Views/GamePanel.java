package fungorium.Views;

import java.awt.Graphics;
import java.awt.event.*;
import java.util.List;

import javax.swing.JPanel;

import fungorium.Controllers.GameController;

public class GamePanel extends JPanel {
    private List<EntityView> views;
    private PalyaView palyaView;
    private GameController controller;

    private static final int TILE_SIZE = 38;
    
    public void setController(GameController controller) {
        this.controller = controller;
    }

    public GamePanel(List<EntityView> views, PalyaView palyaView) {
        this.views = views;
        this.palyaView = palyaView;

        setFocusable(true);
        requestFocusInWindow();

        // Egér- és billentyűkezelő
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / TILE_SIZE;
                int y = e.getY() / TILE_SIZE;
                controller.handleClick(x, y);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.handleKey(e.getKeyCode());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (palyaView != null)
            palyaView.drawPalya(g);

        for (EntityView view : views) {
            view.draw(g);
        }
    }
}