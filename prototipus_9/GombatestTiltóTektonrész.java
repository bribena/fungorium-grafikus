package prototipus_9;

import java.util.List;

public class GombatestTiltóTektonrész extends Tektonrész {
    public boolean entitásHozzáadás(Entitás entitás)
    {   
        if (vanGomba())
        {
            return false;
        }
        
        List<Entitás> entitások = entitásokVisszaadása();
        entitások.add(entitás);
        return true;
    }
}
