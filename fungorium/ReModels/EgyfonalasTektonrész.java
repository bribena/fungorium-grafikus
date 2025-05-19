package fungorium.ReModels;

public class EgyfonalasTektonrész extends Tektonrész {
    public EgyfonalasTektonrész() {}

    public EgyfonalasTektonrész(Tektonrész t) {
        super(t);
    }

    public boolean entitásHozzáadás(Entitás entitás)
    {
        if(vanGombatest() && entitás.getClass() == Gombatest.class)
        {
            return false;
        }
        if (getGombafonalak().size() > 0 && entitás.getClass() == Gombafonal.class)
        {
            return false;
        }
        entitások.add(entitás);
        return true;
    }
}
