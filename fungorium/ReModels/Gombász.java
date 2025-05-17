package fungorium.ReModels;


public class Gombász extends Játékos {
    private Gombafaj kezeltFaj;

    public Gombász(Gombafaj faj, String n) {
        kezeltFaj = faj;
        name = n;
    }

    public Gombafaj getKezeltFaj() {
        return kezeltFaj;
    }
}