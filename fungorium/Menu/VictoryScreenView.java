// package fungorium.Menu;

// import java.awt.Color;
// import java.awt.Dimension;
// import java.awt.Font;
// import java.awt.Graphics;
// import java.awt.Graphics2D;
// import java.awt.RenderingHints;
// import javax.swing.JButton;
// import javax.swing.JComponent;
// import javax.swing.JPanel;


// public class VictoryScreenView extends JPanel {
//     public VictoryScreenView(MenuController controller){
//         //add(new JLabel("Gombász nyertes: " + JátékKezelő.getWinnerGombasz()));
//         //add(new JLabel("Rovarász nyertes: " + JátékKezelő.getWinnerRovarasz()));

//         JButton backBtn = new JButton("Főmenü");
//         backBtn.setPreferredSize(new Dimension(120, 40));
//         backBtn.setFont(new Font("SansSerif", Font.PLAIN, 18));
//         backBtn.setBackground(Color.BLACK);
//         backBtn.setForeground(Color.WHITE);
//         backBtn.setFocusPainted(false);
        
//         // kerekített szélek
//         backBtn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
//             @Override
//             public void paint(Graphics g, JComponent c) {
//                 Graphics2D g2 = (Graphics2D) g.create();
//                 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                 g2.setColor(c.getBackground());
//                 g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
//                 g2.setColor(c.getForeground());
//                 g2.setFont(c.getFont());

//                 // Szöveg középre
//                 String text = ((JButton) c).getText();
//                 int stringWidth = g2.getFontMetrics().stringWidth(text);
//                 int stringHeight = g2.getFontMetrics().getAscent();
//                 g2.drawString(text, (c.getWidth() - stringWidth) / 2, (c.getHeight() + stringHeight) / 2 - 3);

//                 g2.dispose();
//             }
//         });

//         // Ha rákattintanak a Vissza gombra, Főmenübe ugrik
//         backBtn.addActionListener(e -> { controller.showMainMenu();});

//         add(backBtn);
//     }
// }
