package fungorium.ReViews;

import fungorium.ReControllers.*;
import fungorium.ReControllers.GombászListeners.*;
import fungorium.ReControllers.RovarászListeners.*;
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

        fungoriumView.addMouseListener(new GombászSpóraSzórásMouseAdapter(controller));

        fungoriumView.addKeyListener(new GombászFonalNövesztésKeyAdapter(controller));
        fungoriumView.addKeyListener(new GombászSpóraSzórásKeyAdapter(controller));
        fungoriumView.addKeyListener(new GombászTestFejlesztésKeyAdapter(controller));
        fungoriumView.addKeyListener(new GombászTestNövesztésKeyAdapter(controller));

        fungoriumView.addMouseListener(new RovarászVágásMouseAdapter(controller));
        
        fungoriumView.addKeyListener(new RovarászElhelyezésKeyAdapter(controller));
        fungoriumView.addKeyListener(new RovarászMozgatásKeyAdapter(controller));
        fungoriumView.addKeyListener(new RovarászSpóraEvésKeyAdapter(controller));
        fungoriumView.addKeyListener(new RovarászVágásKeyAdapter(controller));

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
