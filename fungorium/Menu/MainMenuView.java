package fungorium.Menu;

import fungorium.Menu.MenuComponents.MainMenuButton;
import fungorium.Views.GamePanel;
import java.awt.*;
import javax.swing.*;

public class MainMenuView extends JFrame {
    private GamePanel gamePanel = new GamePanel();
    private JPanel mainMenuPanel;
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

        gamePanel = new GamePanel(() -> {
            // Játék vége -> győztes képernyő megjelenítése
            VictoryScreenView victoryScreen = new VictoryScreenView(gamePanel.getController().getJáték(), () -> {
                setContentPane(MainMenuView.this.getContentPane()); // vissza főmenübe
                revalidate();
                repaint();
            });
            setContentPane(victoryScreen);
            revalidate();
            repaint();
        });

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
            revalidate();
            repaint();
            gamePanel.setVisible(true);
            gamePanel.getFungoriumView().requestFocusInWindow();
        });

        /*// Hozzáadás (tetejéről indulva)
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(playerBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(startBtn);*/
        mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.Y_AXIS));
        getContentPane().setLayout(new BorderLayout()); // így középre tehető a mainMenuPanel
        getContentPane().add(mainMenuPanel, BorderLayout.CENTER);

        mainMenuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainMenuPanel.add(titleLabel);
        mainMenuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainMenuPanel.add(playerBtn);
        mainMenuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainMenuPanel.add(startBtn);
    }
}
