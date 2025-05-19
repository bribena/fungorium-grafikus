package fungorium.ReModels;

import java.util.Map;
import java.util.HashMap;


/** Csak egy gombafajhoz tartozó fonalat tartalmazható tektonrész.
 * Ha van rajta másik fonal, azt eltávolítja. Ez kicsit random, hogy melyik.
 */
public class EgyfonalasTektonrész extends Tektonrész {
    private static Map<Integer, Gombafaj> tektonFonalFaj = new HashMap<>();

    public EgyfonalasTektonrész(Fungorium fungorium) {
        super(fungorium);
    }

    public EgyfonalasTektonrész(Tektonrész tr) {
        super(tr);
        
        Gombafaj faj = tektonFonalFaj.get(getTektonID());
        if (faj == null) {
            for (Entitás e : entitások) {
                if (e instanceof Gombafonal) {
                    faj = ((Gombafonal)e).getFaj();
                    tektonFonalFaj.put(getTektonID(), faj);
                    break;
                }
            }
        }

        for (int i = 0; i < entitások.size(); ++i) {
            Entitás e = entitások.get(i);
            if (e instanceof Gombafonal && ((Gombafonal)e).getFaj() != faj) {
                entitások.remove(i);
                --i;
            }
        }
    }

    @Override
    public boolean entitásHozzáadás(Gombafonal gf) {
        Gombafaj faj = tektonFonalFaj.get(getTektonID());
        if (faj != null) {
            if (faj != gf.getFaj()) {
                return false;
            }
            return super.entitásHozzáadás(gf);
        }
        tektonFonalFaj.put(getTektonID(), gf.getFaj());
        return super.entitásHozzáadás(gf);
    }
}
