package fungorium.ReModels;

public class Gombatest implements Entitás {
    private Gombafaj faj;
    
    public Gombatest(Gombafaj faj) {
        this.faj = faj;
    }

    public Gombafaj getFaj() {
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
