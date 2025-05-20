package fungorium.Menu;

import java.awt.*;
import javax.swing.*;

import fungorium.Menu.MenuComponents.MainMenuButton;
import fungorium.Views.GamePanel;

public class MainMenuView extends JFrame {
    private GamePanel gamePanel = new GamePanel();
    public MainMenuView(String title) {
        super(title);
        // elhelyezkedés, méret beállítása
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Menü felirat
        JLabel titleLabel = new JLabel("Menü", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Gombméret
        Dimension buttonSize = new Dimension(170, 40);

        // Játékosok gomb
        JButton playerBtn = new MainMenuButton("Játékosok", buttonSize);
        PlayerEditView pew = new PlayerEditView(this, gamePanel.getController().getJáték());
        add(pew);
        pew.setVisible(false);
        playerBtn.addActionListener(e -> {
            setContentPane(pew);
            pew.setVisible(true);
        });

        // Játék kezdése gomb
        JButton startBtn = new MainMenuButton("Játék kezdése", buttonSize);
        add(gamePanel);
        gamePanel.setVisible(false);
        startBtn.addActionListener(e -> {
            setContentPane(gamePanel);
            gamePanel.setVisible(true);
        });

        // Hozzáadás (tetejéről indulva)
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(playerBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(startBtn);
    }
}
