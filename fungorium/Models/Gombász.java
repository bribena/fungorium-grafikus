package fungorium.Models;


public class Gombász extends Játékos {
    private Gombafaj kezeltFaj;

    public Gombász(Gombafaj faj, String name) {
        kezeltFaj = faj;
        setName(name);
    }

    public Gombafaj getKezeltFaj() {
        return kezeltFaj;
    }
}