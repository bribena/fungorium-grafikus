package fungorium.Menu;

import fungorium.Menu.MenuComponents.MainMenuButton;
import fungorium.Models.Játék;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VictoryScreenView extends JPanel {
    public VictoryScreenView(Játék játék, Runnable visszaFőmenü) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(CENTER_ALIGNMENT);

        // Felül: "Győztesek" cím
        JLabel headerLabel = new JLabel("Győztesek", JLabel.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        headerLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(headerLabel);

        add(Box.createRigidArea(new Dimension(0, 20))); // térköz a cím és a nevek között

        // Panel a két győztesnek vízszintesen egymás mellett
        JPanel winnersPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        winnersPanel.setAlignmentX(CENTER_ALIGNMENT);

        JLabel gombaszLabel = new JLabel(játék.getWinnerGombasz());
        gombaszLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        winnersPanel.add(gombaszLabel);

        JLabel rovaraszLabel = new JLabel(játék.getWinnerRovarasz());
        rovaraszLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        winnersPanel.add(rovaraszLabel);

        add(winnersPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);

        JButton backBtn = new MainMenuButton("Főmenü", new Dimension(120, 40));
        backBtn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                g2.setColor(c.getForeground());
                g2.setFont(c.getFont());

                String text = ((JButton) c).getText();
                int stringWidth = g2.getFontMetrics().stringWidth(text);
                int stringHeight = g2.getFontMetrics().getAscent();
                g2.drawString(text, (c.getWidth() - stringWidth) / 2, (c.getHeight() + stringHeight) / 2 - 3);

                g2.dispose();
            }
        });
        backBtn.addActionListener(e -> visszaFőmenü.run());

        buttonPanel.add(backBtn);
        add(buttonPanel);
    }
}