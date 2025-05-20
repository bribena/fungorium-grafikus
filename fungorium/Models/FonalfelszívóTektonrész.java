package fungorium.Models;

public class FonalfelszívóTektonrész extends Tektonrész {
    public FonalfelszívóTektonrész() {
    }
    public FonalfelszívóTektonrész(Tektonrész t) {
        super(t);
    }

    @Override
    public void frissítés() {
        for (Entitás e : entitások) {
            if (e instanceof Gombafonal) {
                ((Gombafonal)e).specBeállítás(true);
            }
        }
        super.frissítés();
    }
}