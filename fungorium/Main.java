package fungorium;

import fungorium.Menu.MenuController;
import fungorium.ReControllers.PlayerManager;
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
        
        PlayerManager playerManager = new PlayerManager();
        MenuController menuController = new MenuController(frame, playerManager);
        menuController.showMainMenu();
        
        frame.setSize(825, 825);
        frame.setVisible(true);
    }
}