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
    public static void main(String[] args) {
        // Az alkalmazás indításakor hozzuk létre a főmenüt
        JFrame frame = new JFrame("Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(new GamePanel());
        // frame.add(new GombafonalView(new TöbbfonalasTektonrész()));
        frame.pack();

        // frame.setSize(825, 825);
        frame.setVisible(true);
    }
}