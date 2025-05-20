package fungorium.Models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Gombafonal implements Entitás {
    private static HashMap<Gombafaj, Integer> gombatestSzám = new HashMap<>();

    static {
        for (Gombafaj f : Gombafaj.values()) {
            gombatestSzám.put(f, 0);
        }
    }
    public static int getTápérték(Gombafaj faj) {
        return gombatestSzám.get(faj);
    }

    /**
     * A szoki.
     * [0]: fel
     * [1]: jobbra
     * [2]: le
     * [3]: balra
     */
    private Gombafonal[] kapcsolódóFonalak = new Gombafonal[] { null, null, null, null };
    private Gombatest test = null;
    private Gombafaj faj;
    private int szakadt = 0;
    private boolean specFrissítés = false;

    public Gombafonal(Gombafaj faj) {
        this.faj = faj;
    }

    public void összekapcsolás(Gombafonal szomszéd, int irány) {
        kapcsolódóFonalak[irány] = szomszéd;
        szomszéd.kapcsolódóFonalak[(irány + 2) % 4] = this;
    }

    public void setKapcsolódóTest(Gombatest test) {
        this.test = test;
    }
    public Gombatest getKapcsolódóTest() {
        return test;
    }

    public void szakít(int irany) {
        Gombafonal szakitando = kapcsolódóFonalak[irany];

        if (szakitando != null) {
            kapcsolódóFonalak[irany] = null;
            szakitando.kapcsolódóFonalak[(irany + 2) % 4] = null;
        }
    }

    public Gombafaj getFaj() {
        return faj;
    }

    @Override
    public boolean érvényesE() {
        return szakadt < 2;
    }

    public void specBeállítás(boolean spec) {
        specFrissítés = spec;
    }

    private Set<Gombafonal> getÖsszesKapcsolódó(Set<Gombafonal> eddigiek) {
        if (eddigiek == null) {
            eddigiek = new HashSet<>();
        }
        if (!érvényesE()) {
            for (int i = 0; i < 4; ++i) {
                szakít(i);
            }
        }
        eddigiek.add(this);
        for (int i = 0; i < 4; ++i) {
            if (kapcsolódóFonalak[i] != null && !eddigiek.contains(kapcsolódóFonalak[i])) {
                kapcsolódóFonalak[i].getÖsszesKapcsolódó(eddigiek);
            }
        }
        return eddigiek;
    }

    private boolean kapcsolódikGombatesthez() {
        if (test != null && !test.érvényesE()) {
            test = null;
        }
        if (test != null) {
            return true;
        }

        Set<Gombafonal> kapcsolódók = getÖsszesKapcsolódó(null);
        for (Gombafonal f : kapcsolódók) {
            if (f.test != null && !f.test.érvényesE()) {
                f.test = null;
            }
            if (f.test != null) {
                return true;
            }
        }
        return false;
    }

    private Gombatest getÉrvényesNövesztőGombatest() {
        if (test != null && !test.érvényesE()) {
            test = null;
        }
        if (test != null && test.növeszthetFonalat()) {
            return test;
        }

        Set<Gombafonal> kapcsolódók = getÖsszesKapcsolódó(null);
        for (Gombafonal f : kapcsolódók) {
            if (f.test != null && !f.test.érvényesE()) {
                f.test = null;
            }
            if (f.test != null && f.test.növeszthetFonalat()) {
                return f.test;
            }
        }
        return null;
    }

    @Override
    public boolean frissítés() {
        if (!kapcsolódikGombatesthez() || specFrissítés) {
            szakadt++;
        }
        else {
            szakadt = 0;
        }
        if (!érvényesE()) {
            /*for (int i = 0; i < 4; ++i) {
                szakít(i);
            }*/
            return false;
        }
        return true;
    }

    public boolean gombafonalatNöveszt(Tektonrész honnan, int irány, Fungorium fungorium) {
        Gombatest t = getÉrvényesNövesztőGombatest();
        if (t == null || !honnan.tartalmaz(this)) {
            System.out.println("Nincs ervenyes gombatest");
            return false;
        }

        Tektonrész hova = fungorium.getTektonrészSzomszédok(honnan)[irány];
        if (hova.getTektonID() == -1) {
            System.out.println("Nincs ervenyes tekton ebben az iranyban");
            return false;
        }

        boolean spórás = false;
        for (Entitás e : hova.getEntitások()) {
            if (e instanceof Gombafonal && ((Gombafonal)e).faj == faj) {
                összekapcsolás((Gombafonal)e, irány);
                return true;
            }
            if (e instanceof Spóra && ((Spóra)e).getFaj() == faj) {
                spórás = true;
            }
        }

        Gombafonal fonal = new Gombafonal(faj);
        if (!hova.entitásHozzáadás(fonal)) {
            System.out.println("Nem sikerult hozzaadni a tektonhoz");
            return false;
        }

        t.fonalatNöveszt();
        összekapcsolás(fonal, irány);

        if (spórás) {
            Gombafonal fonal2 = new Gombafonal(faj);
            Tektonrész tr = fungorium.getTektonrészSzomszédok(hova)[irány];
            if (tr.getTektonID() != -1 && tr.entitásHozzáadás(fonal2)) {
                fonal.összekapcsolás(fonal2, irány);
            }
        }

        return true;
    }

    public boolean gombatestetNöveszt(Tektonrész aholVan, Fungorium fungorium) {
        Gombatest test = new Gombatest(faj);
        if (this.test != null || !aholVan.entitásHozzáadás(test) || !kapcsolódikGombatesthez()) {
            return false;
        }

        List<Spóra> spórák = fungorium.getTektonSpóraszám(aholVan.getTektonID(), faj);
        int c = 0;
        int i = 0;
        for (i = 0; i < spórák.size(); ++i) {
            c += spórák.get(i).getSpóraSzám();
            if (c >= 20) {
                break;
            }
        }
        Rovar bénult = null;
        for (Entitás e : aholVan.getEntitások()) {
            if (e instanceof Rovar && ((Rovar)e).bénult()) {
                bénult = (Rovar)e;
            }
        }
        if (c < 20 && bénult == null) {
            aholVan.entitásTörlés(test);
            return false;
        }
        if (bénult != null) {
            aholVan.entitásTörlés(bénult);
            bénult.pusztul();
        }
        else {
            for (int j = 0; j < spórák.size() && j < i; ++j) {
                c -= spórák.get(j).getSpóraSzám();
                spórák.get(j).felszív();
            }
            spórák.get(i).hozzáad(-c);
        }
        this.test = test;
        gombatestSzám.put(faj, gombatestSzám.get(faj) + 1);
        return true;
    }

    public Gombafonal[] getKapcsolódóFonalak() {
        return kapcsolódóFonalak;
    }
}
