package fungorium.ReViews;

import fungorium.ReControllers.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    public GameController controller;
    private JLabel statusLabel;
    private FungoriumView fungoriumView;

    public GamePanel(FungoriumView fungoriumView, GameController controller) {
        this.fungoriumView = fungoriumView;
        this.controller = controller;
        setLayout(new BorderLayout()); 
        
        add(fungoriumView, BorderLayout.CENTER);

        // Status JLabel az alsó bal sarokba
        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setForeground(Color.BLACK);
        add(statusLabel, BorderLayout.SOUTH);

        updateStatusLabel();

        fungoriumView.addMouseListener(controller.getFungoriumMouseAdapter());
        fungoriumView.addKeyListener(controller.getGombászKeyAdapter());
        fungoriumView.addKeyListener(controller.getRovarászKeyAdapter());

        fungoriumView.setFocusable(true);
        fungoriumView.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public FungoriumView getFungoriumView() {
        return fungoriumView;
    }

    public void updateStatusLabel() {
        PlayerManager pm = controller.getPlayerManager();
        int index = pm.getAktuálisJátékosIndex(); // aki most jön
        String név = pm.getName(index);

        statusLabel.setText("Következő: " + név);
    }
}
