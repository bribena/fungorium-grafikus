package fungorium.ReModels;

import java.util.ArrayList;
import java.util.List;

public class Rovarász extends Játékos {
    private Rovarfaj kezeltFaj;
    private List<Spóra> megevettSpórák = new ArrayList<>();

    public Rovarász(Rovarfaj faj, String n) {
        kezeltFaj = faj;
        name = n;
    }

    public Rovarfaj getKezeltFaj() {
        return kezeltFaj;
    }

    public void addMegevettSpóra(Spóra spora) {
        megevettSpórák.add(spora);
    }

    public List<Spóra> getMegevettSpórák() {
        return megevettSpórák;
    }
}
