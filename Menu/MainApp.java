package Menu;

import javax.swing.JFrame;

public class MainApp {
    public static void main(String[] args) {
        // Az alkalmazás indításakor hozzuk létre a főmenüt
        JFrame frame = new JFrame("Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // PlayerManager és VictoryResult példányosítása
        PlayerManager playerManager = new PlayerManager();
        
        // Menü vezérlő létrehozása
        MenuController menuController = new MenuController(frame, playerManager);
        
        // Főmenü megjelenítése
        menuController.showMainMenu();
        
        // Teszt: közvetlenül a győzelmi képernyő megjelenítése
        //VictoryScreenView victoryScreen = new VictoryScreenView(menuController, victoryResult);
        //frame.setContentPane(victoryScreen);
        
        // A JFrame beállítása
        frame.setSize(825, 825);
        frame.setVisible(true);
    }
}