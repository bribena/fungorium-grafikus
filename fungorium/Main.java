package fungorium;

import fungorium.Menu.MainMenuView;
import fungorium.Views.GamePanel;

import javax.swing.JFrame;
import javax.swing.UIManager;


public class Main {
    public static void main(String[] args) {
        // Linux moment
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        // Az alkalmazás indításakor hozzuk létre a főmenüt
        JFrame frame = new JFrame("Fungorium");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new GamePanel());
                
        frame.setSize(825, 825);
        frame.setVisible(true);
    }
}