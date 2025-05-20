package fungorium.Models;

import java.util.HashMap;
import java.util.Map;

/** Csak egy gombafajhoz tartozó fonalat tartalmazható tektonrész.
 * Ha van rajta másik fonal, azt eltávolítja. Ez kicsit random, hogy melyik.
 */
public class EgyfonalasTektonrész extends Tektonrész {
    // Minden tektonrész ID-hez eltárolja, hogy milyen gombafaj fonala lehet rajta
    private static Map<Integer, Gombafaj> tektonFonalFaj = new HashMap<>();

    public EgyfonalasTektonrész() {
    }

    public EgyfonalasTektonrész(Tektonrész tr) {
        super(tr);
        
        // Lekéri, hogy ehhez a tektonrészhez tartozik-e már gombafaj
        Gombafaj faj = tektonFonalFaj.get(getTektonID());
        
        // Ha még nincs eltárolt faj, akkor megkeresi az entitások között az első Gombafonal-t,
        // és annak faját eltárolja
        if (faj == null) {
            for (Entitás e : entitások) {
                if (e instanceof Gombafonal) {
                    faj = ((Gombafonal)e).getFaj();
                    tektonFonalFaj.put(getTektonID(), faj);
                    break;
                }
            }
        }

        // Végigmegy az entitásokon, és eltávolít minden olyan Gombafonal-t,
        // amely nem az előzőleg meghatározott fajhoz tartozik
        for (int i = 0; i < entitások.size(); ++i) {
            Entitás e = entitások.get(i);
            if (e instanceof Gombafonal && ((Gombafonal)e).getFaj() != faj) {
                entitások.remove(i);
                --i; // index visszaléptetése eltávolítás után
            }
        }
    }

    // Gombafonal hozzáadása a tektonrészhez
    @Override
    public boolean entitásHozzáadás(Gombafonal gf) {
        // Megnézi, hogy már van-e faj eltárolva ehhez a tektonrészhez
        Gombafaj faj = tektonFonalFaj.get(getTektonID());

        if (faj != null) {
            // Ha van, és a hozzáadni kívánt fonal más fajhoz tartozik, nem engedi hozzáadni
            if (faj != gf.getFaj()) {
                return false;
            }
            // Ha ugyanahhoz a fajhoz tartozik, meghívja az ősosztály metódusát
            return super.entitásHozzáadás(gf);
        }

        // Ha még nincs eltárolva faj, beállítja a most hozzáadott fonal faját
        tektonFonalFaj.put(getTektonID(), gf.getFaj());
        return super.entitásHozzáadás(gf);
    }
}
