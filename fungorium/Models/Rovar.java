package fungorium.Models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

public class Rovar implements Entitás {
    private static Map<Rovarfaj, Integer> tápértékek = new HashMap<>();
    static {
        for (Rovarfaj f : Rovarfaj.values()) {
            tápértékek.put(f, 0);
        }
    }
    public static int getTápérték(Rovarfaj faj) {
        return tápértékek.get(faj);
    }

    private static Random random = new Random();

    private Rovarfaj faj;
    /**
     * [0]: Lassítás
     * [1]: Gyorsítás
     * [2]: Bénulás
     * [3]: Gyengeség
     * nincs 5., de az az osztódás lenne
     */
    private int[] hatások = new int[4]; 
    private boolean él = true;
    private boolean mozgatott = true;

    public Rovar(Rovarfaj faj) {
        this.faj = faj;
    }

    public Rovarfaj getFaj() {
        return faj;
    }

    public void pusztul() {
        él = false;
    }

    @Override
    public boolean érvényesE() {
        return él;
    }

    public void mozgatottFelülírás(boolean új) {
        mozgatott = új;
    }
    public boolean mozgatott() {
        return mozgatott || bénult();
    }

    @Override
    public boolean frissítés() {
        for (int i = 0; i < hatások.length; ++i) {
            if (hatások[i] > 0) {
                hatások[i]--;
            }
        }
        mozgatott = lassú();
        return érvényesE();
    }

    public boolean spóraEvés(Tektonrész holVan) {
        if (bénult() || mozgatott) {
            return false;
        }
        int spóraszám = 0;
        for (int i = 0; i < holVan.getEntitások().size(); ++i) {
            Entitás e = holVan.getEntitások().get(i);
            if (e instanceof Spóra) {
                Spóra s = (Spóra)e;
                spóraszám += s.getSpóraSzám();
                s.felszív();
                --i;
            }
        }

        HashSet<Integer> idx = new HashSet<>();
        for (int i = random.nextInt(5); idx.size() < spóraszám && idx.size() < 5; ) {
            idx.add(i);
        }
        int tápérték = 0;
        for (Integer i : idx) {
            if (i == 0) {
                tápérték += 2;
                hatások[i] = 2;
            }
            else if (i == 1) {
                tápérték += 1;
                hatások[i] = 1;
            }
            else if (i == 2) {
                tápérték += 3;
                hatások[i] = 1;
            }
            else if (i == 3) {
                tápérték += 3;
                hatások[i] = 3;
            }
            else {
                tápérték += 1;
                holVan.entitásHozzáadás(new Rovar(faj));
            }
            spóraszám--;
        }
        tápérték += spóraszám;
        tápértékek.put(faj, tápértékek.get(faj) + tápérték);

        return true;
    }

    public boolean lassú() {
        return hatások[0] > 0;
    }
    public boolean gyors() {
        return hatások[1] > 0;
    }
    public boolean bénult() {
        return hatások[2] > 0;
    }
    public boolean gyenge() {
        return hatások[3] > 0;
    }

    public boolean fonalatVág(Tektonrész holVan, Tektonrész hol, Fungorium fungorium) {
        if (bénult() || gyenge() || mozgatott) {
            return false;
        }

        int irány = 0;
        Tektonrész[] szomszédok = fungorium.getTektonrészSzomszédok(holVan);
        for (irány = 0; irány < 4; ++irány) {
            if (szomszédok[irány] == hol) {
                break;
            }
        }

        if (irány == 4 || !holVan.getTektonSzéleE()[irány] || !hol.getTektonSzéleE()[(irány + 2) % 4]) {
            return false;
        }

        boolean talált = false;
        for (Entitás e : holVan.getEntitások()) {
            if (e instanceof Gombafonal) {
                talált = true;
                Gombafonal gf = (Gombafonal)e;
                if (gf.getKapcsolódóFonalak()[irány] != null) {
                    gf.szakít(irány);
                }
            }
        }
        mozgatott = true;
        return talált;
    }

    public boolean mozog(Tektonrész honnan, int irány, Fungorium fungorium) {
        if (mozgatott) {
            System.out.println("Mar mozgott ez a rovar");
            return false;
        }

        Tektonrész[] szomszédok = fungorium.getTektonrészSzomszédok(honnan);
        Tektonrész hova = szomszédok[irány];

        if (hova.entitásHozzáadás(this)) {
            honnan.entitásTörlés(this);
            if (gyors() && fungorium.getTektonrészSzomszédok(hova)[irány].entitásHozzáadás(this)) {
                hova.entitásTörlés(this);
            }
            mozgatott = true;
            return true;
        }
        System.out.println("Nem sikerult a tektonhoz hozzaadni a rovart");
        return false;
    }
}
