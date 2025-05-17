package fungorium.ReModels;

public class Rovarász extends Játékos {
    private Rovarfaj kezeltFaj;

    public Rovarász(Rovarfaj faj, String n) {
        kezeltFaj = faj;
        name = n;
    }

    public Rovarfaj getKezeltFaj() {
        return kezeltFaj;
    }
}
