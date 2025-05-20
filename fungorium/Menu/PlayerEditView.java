package fungorium.Menu;

import java.awt.*;
import javax.swing.*;

import fungorium.Menu.MenuComponents.PlayerEditBackButton;
import fungorium.Menu.MenuComponents.PlayerEditCenterPanel;
import fungorium.Menu.MenuComponents.PlayerEditHeaderPanel;
import fungorium.Models.Játék;

public class PlayerEditView extends JPanel {
    private Container originalContentPane;
    public PlayerEditView(MainMenuView origin, Játék játék) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Fejléc
        add(new PlayerEditHeaderPanel(), BorderLayout.NORTH);        
        // Mezők panel
        add(new PlayerEditCenterPanel(játék), BorderLayout.CENTER);
        // Vissza gomb
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btn = new PlayerEditBackButton("Vissza");
        originalContentPane = origin.getContentPane();
        btn.addActionListener(e -> {
            origin.setContentPane(originalContentPane);
            setVisible(false);
        });
        bottomPanel.add(btn);
        bottomPanel.setAlignmentX(CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20)); // Kis térköz a mezők és gomb között
        add(bottomPanel);
    }
}