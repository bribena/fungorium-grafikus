package Menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Game.PlayerManager;

public class PlayerEditView extends JPanel {

    public PlayerEditView(MenuController controller, PlayerManager manager) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Fejléc
        addHeaderPanel();

        // Mezők panel
        addFieldsPanel(manager);

        // Vissza gomb
        addBackButton(controller, manager);
    }

    private void addHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(1, 2));
        JLabel gombaszLabel = new JLabel("Gombászok", SwingConstants.CENTER);
        JLabel rovaraszLabel = new JLabel("Rovarászok", SwingConstants.CENTER);
        gombaszLabel.setFont(new Font("Arial", Font.BOLD, 24));
        rovaraszLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(gombaszLabel);
        headerPanel.add(rovaraszLabel);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void addFieldsPanel(PlayerManager manager) {
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        JTextField[] fields = new JTextField[8];

        // Extra hely a cím alatt
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Mezők és alapértékek beállítása
        for (int i = 0; i < 4; i++) {
            JPanel rowPanel = createRowPanel(manager, i, fields);
            fieldsPanel.add(rowPanel);

            // Kisebb távolság a mezők között
            if (i < 3) fieldsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        add(fieldsPanel, BorderLayout.CENTER);
    }

    private JPanel createRowPanel(PlayerManager manager, int i, JTextField[] fields) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        rowPanel.setOpaque(false);

        for (int j = 0; j < 2; j++) {
            int index = i + j * 4;
            fields[index] = createField(manager, index);

            JPanel fieldContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            fieldContainer.setOpaque(false);
            fieldContainer.add(fields[index]);

            rowPanel.add(fieldContainer);
        }

        return rowPanel;
    }

    private JTextField createField(PlayerManager manager, int index) {
        String defaultName = manager.getName(index); // Alapnév lekérése a PlayerManager-ből
        JTextField field = new JTextField(defaultName); // Mező alapértelmezett értéke

        field.setBackground(Color.BLACK);
        field.setForeground(Color.WHITE);
        field.setHorizontalAlignment(SwingConstants.CENTER);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(100, 40));
        field.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        final int idx = index;
        field.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { manager.setName(idx, field.getText()); }
            public void removeUpdate(DocumentEvent e) { manager.setName(idx, field.getText()); }
            public void changedUpdate(DocumentEvent e) { manager.setName(idx, field.getText()); }
        });

        return field;
    }

    private void addBackButton(MenuController controller, PlayerManager manager) {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backBtn = new JButton("Vissza");
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 20));
        bottomPanel.add(backBtn);
        backBtn.addActionListener(e -> {
            // A "Vissza" gomb megnyomásakor elmentjük az összes nevet a PlayerManager-be
            savePlayerNames(manager);
            controller.showMainMenu(); // Vissza navigálás a főmenübe
        });
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void savePlayerNames(PlayerManager manager) {
        // Az összes név elmentése
        for (int i = 0; i < 8; i++) {
            manager.setName(i, manager.getName(i)); // Az aktuális szöveget mentjük el
        }
    }
}