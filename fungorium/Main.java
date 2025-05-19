package fungorium;

import fungorium.Menu.MenuController;
import javax.swing.JFrame;


public class Main {
    public static void main(String[] args) {
        // Az alkalmazás indításakor hozzuk létre a főmenüt
        JFrame frame = new JFrame("Fungorium");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        MenuController menuController = new MenuController(frame);
        menuController.showMainMenu();

        //frame.add(new GamePanel());
        //frame.add(new GombafonalView(new TöbbfonalasTektonrész()));
        //frame.pack();

        frame.setSize(825, 825);
        frame.setVisible(true);
    }
}