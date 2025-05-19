package fungorium.ReModels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Gombafonal implements Entitás {
    /**
     * A szoki.
     * [0]: fel
     * [1]: jobbra
     * [2]: le
     * [3]: balra
     */
    private Gombafonal[] kapcsolódóFonalak = new Gombafonal[4];
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

    public void szakít(int irany) {
        Gombafonal szakitando = kapcsolódóFonalak[irany];

        if (szakitando != null) {
            kapcsolódóFonalak[irany] = null;
            szakitando.kapcsolódóFonalak[(irany + 2) % 4] = null;
        }
    }

    public void addTest(Gombatest test) {
        this.test = test;
    } 

    public Gombafaj getFaj() {
        return faj;
    }

    @Override
    public boolean érvényesE() {
        return szakadt >= 2;
    }

    public void specBeállítás(boolean spec) {
        specFrissítés = spec;
    }

    private Set<Gombafonal> getÖsszesKapcsolódó(Set<Gombafonal> eddigiek) {
        if (eddigiek == null) {
            eddigiek = new HashSet<>();
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

        return érvényesE();
    }

    public boolean gombafonalatNöveszt(Tektonrész honnan, Tektonrész hova, Fungorium fungorium)
    {
        Gombatest t = getÉrvényesNövesztőGombatest();
        if (t == null || !honnan.tartalmaz(this)) {
            return false;
        }

        int[] honnanKoor = fungorium.getTektonrészKoordináta(honnan);
        int[] hovaKoor = fungorium.getTektonrészKoordináta(hova);
        int[] d = new int[] {hovaKoor[0] - honnanKoor[0], hovaKoor[1] - honnanKoor[1]};
        // Nem szomszédos
        if ((d[1] == 0 && (d[0] < -1 || d[0] > 1)) || (d[0] == 0 && (d[1] < -1 || d[1] > 1))) {
            return false;
        }

        for (Entitás e : hova.getEntitások()) {
            if (e instanceof Gombafonal && ((Gombafonal)e).faj == faj) {
                összekapcsolás((Gombafonal)e, );
            }
        }

        Gombafonal fonal = new Gombafonal(faj);
        if (!hova.entitásHozzáadás(fonal)) {
            return false;
        }

        if (hova.entitásHozzáadás(fonal)) // a tektonresz eldonti, hogy lehetseges-e a novesztes
        {
            int[] coords = fungorium.getTektonrészKoordináta(hova);
            int x = coords[0];
            int y = coords[1];

            int[][] szomszedokCoords = {{x + 1, y}, {x - 1, y}, {x, y + 1}, {x, y - 1}};

            for (int i = 0; i < szomszedokCoords.length; i++)
            {
                int szomszedX = szomszedokCoords[i][0];
                int szomszedY = szomszedokCoords[i][1];
                Tektonrész tekton = fungorium.getTektonrész(szomszedX, szomszedY);
                if (tekton != null)
                {
                    Gombafonal kapcsolodoFonal = tekton.getKapcsolodoFonal(faj);
                    if (kapcsolodoFonal != null)
                    {
                        if (szomszedX - x == 0)
                        {
                            if(szomszedY - y == 1)
                            {
                                fonal.setKapcsolodoFonal(2, kapcsolodoFonal);
                                kapcsolodoFonal.setKapcsolodoFonal(0, fonal);
                            }
                            else if(szomszedY - y == -1)
                            {
                                fonal.setKapcsolodoFonal(0, kapcsolodoFonal);
                                kapcsolodoFonal.setKapcsolodoFonal(2, fonal);
                            }
                        }
                        else if(szomszedY - y == 0)
                        {
                            if (szomszedX - x == 1)
                            {
                                fonal.setKapcsolodoFonal(1, kapcsolodoFonal);
                                kapcsolodoFonal.setKapcsolodoFonal(3, fonal);
                            }
                            else if (szomszedX - x == -1)
                            {
                                fonal.setKapcsolodoFonal(3, kapcsolodoFonal);
                                kapcsolodoFonal.setKapcsolodoFonal(1, fonal);
                            }
                        }
                    }
                }
            }

            return fonal;
        }
        else
        {
            return null;
        }
    }

    public boolean gombatestetNöveszt(Tektonrész aholVan, Fungorium fungorium) {
        if (!aholVan.vanSpóra()) {
            return false;
        }

        List<Entitás> entitások = aholVan.getEntitások();

        for (int i = 0; i < entitások.size(); i++) {
            if (entitások.get(i) instanceof Spóra) {
                Spóra spóra = (Spóra) entitások.get(i);
                if (spóra.getFaj() == faj && spóra.getSpóraSzám() > 5) {
                    Gombatest test = new Gombatest();
                    if (aholVan.entitásHozzáadás(test)) {
                        gombaTestek.add(test);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public void setKapcsolodoFonal(int irany, Gombafonal fonal)
    {
        if (irany < 0 || irany > 3)
        {
            return;
        }

        kapcsolódóFonalak[irany] = fonal;
    }

    public Gombafonal[] getKapcsolódóFonalak() {
        return kapcsolódóFonalak;
    }
}
