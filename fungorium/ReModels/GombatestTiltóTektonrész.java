package fungorium.ReModels;

import fungorium.Models.Gombatest;

public class GombatestTiltóTektonrész extends Tektonrész {
    public GombatestTiltóTektonrész() {}
    public GombatestTiltóTektonrész(Tektonrész t) {
        super(t);
    }

    public boolean entitásHozzáadás(Entitás entitás)
    {
        if (entitás.getClass() == Gombatest.class)
        {
            return false;
        }
        entitások.add(entitás);
        return true;
    }
}
