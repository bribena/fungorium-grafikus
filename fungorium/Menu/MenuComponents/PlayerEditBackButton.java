package fungorium.Menu.MenuComponents;

import java.awt.*;
import javax.swing.*;

public class PlayerEditBackButton extends JButton {
    public PlayerEditBackButton(String text) { 
        super(text);

        // Méretek és stílus pl. háttér, betű
        setPreferredSize(new Dimension(120, 40));
        setFont(new Font("SansSerif", Font.PLAIN, 18));
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setFocusPainted(false);

        // Border beállítások
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        setContentAreaFilled(false);
        setBorderPainted(false);

        // kerekített szélek
        setUI(new javax.swing.plaf.basic.BasicButtonUI() {
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
    }
}
