package fungorium.ReModels;

public class GombatestTiltóTektonrész extends Tektonrész {
    public GombatestTiltóTektonrész() {}
    public GombatestTiltóTektonrész(Tektonrész t) {
        super(t);
        for (Entitás e : entitások) {
            if (e instanceof Gombatest) {
                ((Gombatest)e).passzívBeállítás(true);
            }
        }
    }

    @Override
    public boolean entitásHozzáadás(Gombatest gt) {
        return false;
    }
}
