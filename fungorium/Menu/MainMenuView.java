package fungorium.Menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenuView extends JPanel {

    public MainMenuView(MenuController controller) {
        // elhelyezkedés, méret beállítása
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400, 300));

        // Menü felirat
        JLabel titleLabel = new JLabel("Menü", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Gombméret
        Dimension buttonSize = new Dimension(170, 40);

        // Játékosok gomb
        JButton playerBtn = new JButton("Játékosok");
        styleButton(playerBtn, buttonSize);
        playerBtn.addActionListener(e -> controller.showPlayerEdit());

        // Játék kezdése gomb
        JButton startBtn = new JButton("Játék kezdése");
        styleButton(startBtn, buttonSize);
        startBtn.addActionListener(e -> controller.startGame());

        // Hozzáadás (tetejéről indulva)
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(playerBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(startBtn);
    }

    private void styleButton(JButton button, Dimension size) {
        // Gomb háttere és betű színe, típusa
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        // Elhelyezkedés
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Méretek beállítása
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setMinimumSize(size);
        // ne legyen körvonala szövegnek
        button.setFocusPainted(false);
    }
}
