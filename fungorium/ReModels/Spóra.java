package fungorium.ReModels;

public class Spóra implements Entitás {
    private Gombafaj faj;
    private int spóraSzám;

    public Spóra(Gombafaj faj, int spóraSzám) {
        this.faj = faj;
        this.spóraSzám = spóraSzám;
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
    public void hozzáad(int spóraSzám) {
        this.spóraSzám += spóraSzám;
    }

    @Override
    public boolean érvényesE() {
        return spóraSzám > 0;
    }

    @Override
    public boolean frissítés() {
        return érvényesE();
    }
}
