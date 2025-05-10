package fungorium.ReModels;

public class Gombafonal implements Entitás {
    private Gombafonal[] kapcsolódóFonalak = new Gombafonal[4];

    @Override
    public boolean frissítés() {
        return false;
    }

    public Gombafonal[] getSzomszédosFonalak() {
        return kapcsolódóFonalak;
    }
    
}
