package fungorium;

import fungorium.Menu.MainMenuView;
import javax.swing.UIManager;


public class Main {
    public static void main(String[] args) {
        // Linux moment
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        // Az alkalmazás indításakor hozzuk létre a főmenüt
        /*JFrame frame = new JFrame("Fungorium");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new GamePanel());
                
        frame.setSize(825, 825);
        frame.setVisible(true);
        */
        MainMenuView mainMenu = new MainMenuView("Fungorium");
        mainMenu.setDefaultCloseOperation(MainMenuView.EXIT_ON_CLOSE);
        mainMenu.setSize(825, 825);
        mainMenu.setVisible(true);
    }
}