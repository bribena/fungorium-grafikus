package fungorium.Controllers;

public class GameStateManager {
    public enum JatekFazis {
        GOMBA_HELYEZES,
        ROVAR_HELYEZES,
        KOROK,
        VEGE
    }

    private JatekFazis aktualisFazis = JatekFazis.GOMBA_HELYEZES;
    private int jatekosIndex = 0;
    private int korokSzama = 0;

    public JatekFazis getFazis() {
        return aktualisFazis;
    }

    public int getAktualisJatekosIndex() {
        return jatekosIndex;
    }

    public void kovetkezoJatekos() {
        jatekosIndex = (jatekosIndex + 1) % 4;
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
            kovetkezoJatekos();
        }
    }

    public int getKorokSzama() {
        return korokSzama;
    }

    public boolean isVege() {
        return aktualisFazis == JatekFazis.VEGE;
    }
}
