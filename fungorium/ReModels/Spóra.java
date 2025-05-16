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

    @Override
    public boolean érvényesE() {
        return true;
    }

    @Override
    public boolean frissítés() {
        return false;
    }
}
