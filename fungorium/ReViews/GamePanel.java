package fungorium.ReViews;

import fungorium.ReControllers.*;
import fungorium.ReModels.Fungorium;

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
        FungoriumView fungoriumView = new FungoriumView(new Fungorium());
        this.controller = new GameController(fungoriumView);

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
        PlayerManager pm = controller.getPlayerManager();
        int index = pm.getAktuálisJátékosIndex(); // aki most jön
        String név = pm.getName(index);

        statusLabel.setText("Következő: " + név);
    }
}
