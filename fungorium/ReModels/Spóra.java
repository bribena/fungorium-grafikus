package fungorium.ReModels;

public class Spóra implements Entitás {
    private Gombafaj faj;
    private int spóraSzám;
    private int[] coords;
    private boolean megevett;

    public Spóra(Gombafaj faj, int spóraSzám, int[] coords) {
        this.faj = faj;
        this.spóraSzám = spóraSzám;
        this.coords = coords;
    }

    public Gombafaj getFaj() {
        return faj;
    }

    public int getSpóraSzám() {
        return spóraSzám;
    }

    public void hozzáad(Spóra spóra) {
        spóraSzám += spóra.spóraSzám;
    }

    public int[] getCoords() {
        return coords;
    }

    @Override
    public boolean érvényesE() {
        return true;
    }

    @Override
    public boolean frissítés() {
        return false;
    }

    public boolean isMegevett() {
        return megevett;
    }

    public void megette() {
        this.megevett = true;
    }
}
