package fungorium.ReControllers;

public class GameStateManager {
    private PlayerManager playerManager;

    public GameStateManager(PlayerManager manager)
    {
        playerManager = manager;
    }

    private JatekFazis aktualisFazis = JatekFazis.GOMBA_HELYEZES;
    private int korokSzama = 0;

    public JatekFazis getFazis() {
        return aktualisFazis;
    }

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
                break;
            default:
                break;
        }
    }

    public void kovetkezoKor() {
        korokSzama++;
        if (korokSzama >= 24) {
            aktualisFazis = JatekFazis.VEGE;
        } else {
            playerManager.következőJátékos();
        }
    }

    public int getKorokSzama() {
        return korokSzama;
    }

    public boolean isVege() {
        return aktualisFazis == JatekFazis.VEGE;
    }
}
