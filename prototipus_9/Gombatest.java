package prototipus_9;

public class Gombatest implements Entitás {
    private boolean passzív = false;
    private boolean fejlődött = false;
    private boolean kezdő;
    private int spóraSzórásVárakozásIdő = 0;
    private int spóraSzórásLehetőség = 3; // nem konkret ertek
    private Gombafaj faj;

    public Gombatest(boolean elsoTest)
    {
        kezdő = elsoTest;
    }

    @Override
    public boolean frissítés() {
        if (!passzív && spóraSzórásVárakozásIdő > 0)
        {
            spóraSzórásVárakozásIdő -= 1;
            return true;
        }
        return false;
    }

    public Gombafaj getFaj()
    {
        return faj;
    }
    
    public boolean fejlődik()
    {
        if (fejlődött)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isKezdő()
    {
        return kezdő;
    }

    public boolean spórátSzór(Tektonrész t, Fungorium f)
    {
        throw new UnsupportedOperationException("Unimplemented method 'spórátSzór'");
    }
}
