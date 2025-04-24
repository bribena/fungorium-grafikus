package prototipus_9;

public class Spóra implements Entitás {
    private int spóraSzám;
    private Gombafaj faj;

    public int spóraszámVisszaadása()
    {
        return spóraSzám;
    }

    public Gombafaj getFaj()
    {
        return faj;
    }

    @Override
    public boolean frissítés() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'frissítés'");
    }
}
