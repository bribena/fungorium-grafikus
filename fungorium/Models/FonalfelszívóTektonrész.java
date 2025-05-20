package fungorium.Models;

// Egy speciális Tektonrész, amely "felszívja" a benne lévő gombafonalakat
public class FonalfelszívóTektonrész extends Tektonrész {

    public FonalfelszívóTektonrész() {
    }

    public FonalfelszívóTektonrész(Tektonrész t) {
        super(t);
    }

    // Frissítéskor minden gombafonalat "speciális" állapotba kapcsol
    @Override
    public void frissítés() {
        // Végigmegy az összes entitáson
        for (Entitás e : entitások) {
            // Ha az entitás Gombafonal, akkor beállítja annak speciális állapotát true-ra
            ((Gombafonal)e).specBeállítás(true);
        }

        // Meghívja az ősosztály frissítési logikáját is
        super.frissítés();
    }
}
