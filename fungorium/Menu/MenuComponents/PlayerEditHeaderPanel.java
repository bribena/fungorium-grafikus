package fungorium.Menu.MenuComponents;

import java.awt.*;
import javax.swing.*;

public class PlayerEditHeaderPanel extends JPanel {
    public PlayerEditHeaderPanel() {
        // fejléc egy sor, két oszlop
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        // Két oszlop címei
        JLabel gombaszLabel = new JLabel("Gombászok");
        JLabel rovaraszLabel = new JLabel("Rovarászok");

        // Betűk beállításai
        gombaszLabel.setFont(new Font("Arial", Font.BOLD, 24));
        rovaraszLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Középre igazítás
        gombaszLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rovaraszLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Hozzáadás header-höz
        add(Box.createHorizontalGlue());
        add(gombaszLabel);
        add(Box.createRigidArea(new Dimension(50, 10)));
        add(rovaraszLabel);
        add(Box.createHorizontalGlue());
    }
}
