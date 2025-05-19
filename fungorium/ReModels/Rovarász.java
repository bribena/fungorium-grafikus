package fungorium.ReModels;

public class Rovarász extends Játékos {
    private Rovarfaj kezeltFaj;

    public Rovarász(Rovarfaj faj) {
        kezeltFaj = faj;
    }

    public Rovarfaj getKezeltFaj() {
        return kezeltFaj;
    }
}
