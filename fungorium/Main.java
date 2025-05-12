package fungorium;

import javax.swing.JFrame;

import fungorium.ReViews.GamePanel;

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