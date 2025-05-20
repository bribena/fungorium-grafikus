package fungorium.Models;

public class ÉletbentartóTektonrész extends Tektonrész {

    public ÉletbentartóTektonrész() {
    }

    public ÉletbentartóTektonrész(Tektonrész t) {
        super(t);
    }

    // Frissítés során végigmegy az entitások listáján
    @Override
    public void frissítés() {
        for (int i = 0; i < entitások.size(); i++) {
            Entitás e = entitások.get(i);

            // Ha az entitás nem Gombafonal és a frissítés() metódusa false-t ad vissza (nem életképes),
            // akkor eltávolítja a tektonrészből
            if (!(e instanceof Gombafonal) && !e.frissítés()) {
                entitások.remove(i);
                --i; // Lista méretének változása miatt az indexet vissza kell léptetni
            }
        }
    }
}
