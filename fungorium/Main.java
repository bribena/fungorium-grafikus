package fungorium;

import fungorium.Menu.MenuController;
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
        
        MenuController menuController = new MenuController(frame);
        menuController.showMainMenu();
        
        frame.setSize(825, 825);
        frame.setVisible(true);
    }
}