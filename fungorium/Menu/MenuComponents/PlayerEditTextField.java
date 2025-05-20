package fungorium.Menu.MenuComponents;

import java.awt.*;
import javax.swing.*;
import fungorium.Models.Játékos;

public class PlayerEditTextField extends JTextField {
    public PlayerEditTextField(Játékos j) {
        super(j.getName());
        
        // méretek beállítása
        setPreferredSize(new Dimension(150, 40));
        setMaximumSize(new Dimension(150, 40));
        setMinimumSize(new Dimension(150, 40));

        // input mezők betűstílusa, háttere
        setFont(new Font("SansSerif", Font.ITALIC, 20));
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        // Elhelyezkedés
        setHorizontalAlignment(SwingConstants.CENTER);
        // Border
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

        getDocument().addDocumentListener(new PlayerEditDocumentListener(this, j));
    }
}
