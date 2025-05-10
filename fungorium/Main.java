package fungorium;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;

import fungorium.ReModels.TöbbfonalasTektonrész;
import fungorium.ReViews.GamePanel;
import fungorium.ReViews.GombafonalView;


public class Main {
    
    public static class Fucker extends JLayeredPane {
        public Fucker() {
        }
    }
    public static void main(String[] args) {
        // Az alkalmazás indításakor hozzuk létre a főmenüt
        JFrame frame = new JFrame("Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // frame.add(new GamePanel());
        // frame.add(new GombafonalView(new TöbbfonalasTektonrész()));
        Fucker fuckYou = new Fucker();
        JComponent comp1 = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.RED);
                g.drawLine(100, 0, 0, 100);
            }
        };
        JComponent comp2 = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.drawLine(0, 0, 100, 100);
            }
        };
        comp1.setBounds(0, 0, 100, 100);
        comp2.setBounds(0,0,100,100);
        fuckYou.add(comp1, 0);
        fuckYou.add(comp2, 1);

        frame.add(fuckYou);
        // frame.pack();

        frame.setSize(825, 825);
        frame.setVisible(true);
    }
}