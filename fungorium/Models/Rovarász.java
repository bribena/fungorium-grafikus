package fungorium.Models;

public class Rovarász extends Játékos {
    private Rovarfaj kezeltFaj;

    public Rovarász(Rovarfaj faj, String name) {
        kezeltFaj = faj;
        setName(name);
    }

    public Rovarfaj getKezeltFaj() {
        return kezeltFaj;
    }
}
