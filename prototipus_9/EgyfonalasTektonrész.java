package prototipus_9;

import java.util.List;

public class EgyfonalasTektonrész extends Tektonrész {
    public EgyfonalasTektonrész() {}
    public EgyfonalasTektonrész(Tektonrész tr) {
        super(tr);
    }

    public boolean entitásHozzáadás(Entitás entitás)
    {
        if(vanGomba() && entitás.getClass() == Gombatest.class)
        {
            return false;
        }

        List<Entitás> entitások = entitásokVisszaadása();

        if (getGombafonalak().size() > 0 && entitás.getClass() == Gombafonal.class)
        {
            return false;
        }
        
        entitások.add(entitás);
        return true;
    }
}
