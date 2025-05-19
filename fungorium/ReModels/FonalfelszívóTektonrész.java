package fungorium.ReModels;

public class FonalfelszívóTektonrész extends Tektonrész {
    public FonalfelszívóTektonrész(Fungorium fungorium) {
        super(fungorium);
    }
    public FonalfelszívóTektonrész(Tektonrész t) {
        super(t);
        for (Entitás e : entitások) {
            if (e instanceof Gombafonal) {
                ((Gombafonal)e).specBeállítás(true);
            }
        }
    }
}