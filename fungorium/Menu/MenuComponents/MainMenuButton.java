package fungorium.Menu.MenuComponents;

import java.awt.*;
import javax.swing.*;

public class MainMenuButton extends JButton {
    public MainMenuButton(String text, Dimension size) {
        super(text);
        // Gomb háttere és betű színe, típusa
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.PLAIN, 18));
        // Elhelyezkedés
        setAlignmentX(Component.CENTER_ALIGNMENT);
        // Méretek beállítása
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        // ne legyen körvonala szövegnek
        setFocusPainted(false);
    }
}
