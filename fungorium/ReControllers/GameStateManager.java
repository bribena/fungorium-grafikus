package fungorium.ReControllers;

import fungorium.Menu.MenuController;

public class GameStateManager {
    private PlayerManager playerManager;
    private MenuController menuController;

    /**
     * Konstruktor.
     */
    public GameStateManager(PlayerManager manager, MenuController controller) {
        playerManager = manager;
        menuController = controller;
    }

    private JatekFazis aktualisFazis = JatekFazis.GOMBA_HELYEZES;
    private int korokSzama = 0;

    /**
     * Visszaadja az aktuális fázist.
     */
    public JatekFazis getFazis() {
        return aktualisFazis;
    }

    /**
     * Fázisok közötti léptetést végzi el.
     */
    public void leptetFazist() {
        switch (aktualisFazis) {
            case GOMBA_HELYEZES:
                aktualisFazis = JatekFazis.ROVAR_HELYEZES;
                break;
            case ROVAR_HELYEZES:
                aktualisFazis = JatekFazis.KOROK;
                break;
            case KOROK:
                aktualisFazis = JatekFazis.VEGE;
                menuController.showVictoryScreen();
                break;
            default:
                break;
        }
    }

    /**
     * Körök számának és játékos léptetése .
     */
    public void kovetkezoKor() {
        korokSzama++;
        if (korokSzama >= 24) {
            aktualisFazis = JatekFazis.VEGE;
            menuController.showVictoryScreen();
        } else {
            playerManager.következőJátékos();
        }
        // --- Törés és nézet frissítés ---
        // Feltételezve, hogy elérhető a Fungorium példánya
        playerManager.getFungorium().körtLéptet(korokSzama);
    }

    /**
     * Visszaadja az eddigi körök számát.
     */
    public int getKorokSzama() {
        return korokSzama;
    }

    /**
     * Ellenőrzi, hogy vége van-e a játéknak.
     */
    public boolean isVege() {
        return aktualisFazis == JatekFazis.VEGE;
    }
}
