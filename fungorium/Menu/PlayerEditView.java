package fungorium.Menu;

import fungorium.ReControllers.PlayerManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PlayerEditView extends JPanel {

    public PlayerEditView(MenuController controller, PlayerManager manager) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Fejléc
        addHeaderPanel();
        // Mezők panel
        addFieldsPanel(manager);
        // Vissza gomb
        addBackButton(controller, manager);
    }

    private void addHeaderPanel() {
        // fejléc egy sor, két oszlop
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
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
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(gombaszLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(50, 10)));
        headerPanel.add(rovaraszLabel);
        headerPanel.add(Box.createHorizontalGlue());

        add(headerPanel, BorderLayout.NORTH);
    }

    private void addFieldsPanel(PlayerManager manager) {
        // belső panel header+input fieldekhez
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));

        // Két oszlop inicializálása
        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
        leftColumn.setAlignmentY(TOP_ALIGNMENT);

        JPanel rightColumn = new JPanel();
        rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
        rightColumn.setAlignmentY(TOP_ALIGNMENT);

        // Bal oszlopba a címsor
        JLabel editLabel = new JLabel("Nevek szerkesztése:");
        editLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        editLabel.setAlignmentX(CENTER_ALIGNMENT);
        leftColumn.add(editLabel);
        leftColumn.add(Box.createRigidArea(new Dimension(0, 10)));

        // Igazítás a két oszlop között közelebb
        centerPanel.add(Box.createHorizontalGlue());
        centerPanel.add(leftColumn);
        centerPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        centerPanel.add(rightColumn);
        centerPanel.add(Box.createHorizontalGlue());

        // Helykihagyás a jobb oszlop tetején, hogy szinkronban legyenek
        rightColumn.add(Box.createRigidArea(new Dimension(0, editLabel.getPreferredSize().height + 8)));

        for (int i = 0; i < 4; i++) {
            JTextField leftField = createField(manager, i);
            leftField.setAlignmentX(CENTER_ALIGNMENT);
            leftColumn.add(leftField);
            if (i < 3) leftColumn.add(Box.createRigidArea(new Dimension(0, 10)));

            JTextField rightField = createField(manager, i + 4);
            rightField.setAlignmentX(CENTER_ALIGNMENT);
            rightColumn.add(rightField);
            if (i < 3) rightColumn.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        add(centerPanel, BorderLayout.CENTER);
    }

    private JTextField createField(PlayerManager manager, int index) {
        String defaultName = manager.getName(index);
        JTextField field = new JTextField(defaultName);
        
        // méretek beállítása
        field.setPreferredSize(new Dimension(150, 40));
        field.setMaximumSize(new Dimension(150, 40));
        field.setMinimumSize(new Dimension(150, 40));

        // input mezők betűstílusa, háttere
        field.setFont(new Font("SansSerif", Font.ITALIC, 20));
        field.setBackground(Color.BLACK);
        field.setForeground(Color.WHITE);
        // Elhelyezkedés
        field.setHorizontalAlignment(SwingConstants.CENTER);
        // Border
        field.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

        // Nevek átállítása az input mezőbe beírtakra
        final int idx = index;
        field.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { manager.setName(idx, field.getText()); }
            public void removeUpdate(DocumentEvent e) { manager.setName(idx, field.getText()); }
            public void changedUpdate(DocumentEvent e) { manager.setName(idx, field.getText()); }
        });

        return field;
    }

    private void addBackButton(MenuController controller, PlayerManager manager) {
        // Vissza gomb hozzáadása, panel közepén
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backBtn = new JButton("Vissza");

        // Méretek és stílus pl. háttér, betű
        backBtn.setPreferredSize(new Dimension(120, 40));
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 18));
        backBtn.setBackground(Color.BLACK);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);

        // Border beállítások
        backBtn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        backBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);

        // kerekített szélek
        backBtn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                g2.setColor(c.getForeground());
                g2.setFont(c.getFont());

                // Szöveg középre
                String text = ((JButton) c).getText();
                int stringWidth = g2.getFontMetrics().stringWidth(text);
                int stringHeight = g2.getFontMetrics().getAscent();
                g2.drawString(text, (c.getWidth() - stringWidth) / 2, (c.getHeight() + stringHeight) / 2 - 3);

                g2.dispose();
            }
        });

        // Ha rákattintanak a Vissza gombra, neveket elmenti és Főmenübe ugrik
        backBtn.addActionListener(e -> {
            savePlayerNames(manager);
            controller.showMainMenu();
        });

        // panelhez hozzáadás
        bottomPanel.add(backBtn);
        bottomPanel.setAlignmentX(CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20)); // Kis térköz a mezők és gomb között
        add(bottomPanel);
    }

    private void savePlayerNames(PlayerManager manager) {
        // Az összes név elmentése
        for (int i = 0; i < 8; i++) {
            manager.setName(i, manager.getName(i)); // Az aktuális szöveget mentjük el
        }
    }
}