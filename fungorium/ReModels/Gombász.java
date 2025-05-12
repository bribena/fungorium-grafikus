package fungorium.ReModels;


public class Gombász extends Játékos {
    private Gombafaj kezeltFaj;

    public Gombász(Gombafaj faj) {
        kezeltFaj = faj;
    }

    public Gombafaj getKezeltFaj() {
        return kezeltFaj;
    }
}