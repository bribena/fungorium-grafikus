package fungorium.ReModels;

public class Rovar implements Entitás {
    private Rovarfaj faj;

    public Rovar(Rovarfaj faj) {
        this.faj = faj;
    }

    public Rovarfaj getFaj() {
        return faj;
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
