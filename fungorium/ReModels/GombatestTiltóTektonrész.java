package fungorium.ReModels;

public class GombatestTiltóTektonrész extends Tektonrész {
    public GombatestTiltóTektonrész() {}
    public GombatestTiltóTektonrész(Tektonrész t) {
        super(t);
    }

    public boolean entitásHozzáadás(Entitás entitás)
    {
        if (entitás instanceof Gombatest)
        {
            return false;
        }
        entitások.add(entitás);
        return true;
    }
}
