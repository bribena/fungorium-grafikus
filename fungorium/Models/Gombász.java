package fungorium.Models;

public class Gombász extends Játékos {
    // A gombász által kezelt gombafaj
    private Gombafaj kezeltFaj;

    // Konstruktor: beállítja a kezelt gombafajt és a játékos nevét
    public Gombász(Gombafaj faj, String name) {
        kezeltFaj = faj;       // A gombász által irányított faj eltárolása
        setName(name);         // A játékos nevének beállítása az ősosztály metódusával
    }

    // Visszaadja, hogy melyik gombafajt kezeli ez a gombász
    public Gombafaj getKezeltFaj() {
        return kezeltFaj;
    }
}
