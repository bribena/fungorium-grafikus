package fungorium.ReViews;

import fungorium.ReControllers.*;
import fungorium.ReModels.Játék;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    public GameController controller;
    private JLabel statusLabel;

    public GamePanel() {
        Játék játék = new Játék();
        FungoriumView fungoriumView = new FungoriumView(játék.getFungorium());
        this.controller = new GameController(fungoriumView, játék);

        setLayout(new BorderLayout()); 
        
        add(fungoriumView, BorderLayout.CENTER);

        // Status JLabel az alsó bal sarokba
        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setForeground(Color.BLACK);
        add(statusLabel, BorderLayout.SOUTH);

        updateStatusLabel();

        fungoriumView.addMouseListener(new FungoriumSelectionMouseAdapter(controller));
        fungoriumView.addKeyListener(new FungoriumGombászKeyAdapter(controller));
        fungoriumView.addKeyListener(new FungoriumRovarászKeyAdapter(controller));

        fungoriumView.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void updateStatusLabel() {
        String név = controller.getJáték().getAktuálisJátékos().getName();

        statusLabel.setText("Aktuális játékos: " + név);
    }
}
