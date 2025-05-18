package fungorium.ReModels;

public class Spóra implements Entitás {
    private Gombafaj faj;
    private int spóraSzám;
    private int[] coords;
    
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

    public int[] getCoords()
    {
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
}
