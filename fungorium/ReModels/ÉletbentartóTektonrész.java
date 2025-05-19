package fungorium.ReModels;

public class ÉletbentartóTektonrész extends Tektonrész {
    public ÉletbentartóTektonrész() {
    }
    public ÉletbentartóTektonrész(Tektonrész t) {
        super(t);
    }

    @Override
    public void frissítés() {
        for (int i = 0; i < entitások.size(); i++) {
            Entitás e = entitások.get(i);
            if (!(e instanceof Gombafonal) && !e.frissítés()) {
                entitások.remove(i);
                --i;
            }
        }
    }
}
