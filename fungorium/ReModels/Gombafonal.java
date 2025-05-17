package fungorium.ReModels;

import java.util.ArrayList;
import java.util.List;

public class Gombafonal implements Entitás {
    private Gombafonal[] kapcsolódóFonalak = new Gombafonal[4];
    private List<Gombatest> gombaTestek = new ArrayList<>();
    private Gombafaj faj;
    private int szakadt = 0;

    public Gombafonal(Gombafaj faj) {
        this.faj = faj;
    }

    public Gombafaj getFaj() {
        return faj;
    }

    @Override
    public boolean érvényesE() {
        return true;
    }

    public boolean kapcsolódikGombatesthez()
    {
        return gombaTestek.size() > 0;
    }

    public boolean addTest(Gombatest test)
    {
        if (!gombaTestek.contains(test))
        {
             gombaTestek.add(test);
             return true;
        }
        else
        {
            return false;
        }
    }

    public boolean removeTest(Gombatest test)
    {
        if (gombaTestek.contains(test))
        {
             gombaTestek.remove(test);
             return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean frissítés() {
        if (!kapcsolódikGombatesthez())
        {
            szakadt += 1;
            if (szakadt > 2)
            {
                return true;
            }
        }
        else
        {
            szakadt = 0;
        }

        return false;
    }

    public void szakad(int irany)
    {
        Gombafonal szakitando = kapcsolódóFonalak[irany];

        if (szakitando != null)
        {
            kapcsolódóFonalak[irany] = null;

            irany -= 2;
            if (irany < 0)
            {
                irany += 4;
            }

            szakitando.szakad(irany);
        }
    }

    public boolean gombafonalatNöveszt(Tektonrész honnan, Tektonrész hova, Fungorium fungorium)
    {
        if (!kapcsolódikGombatesthez())
        {
            return false;
        }

        int[] honnanKoor = fungorium.getTektonrészKoordináta(honnan);
        int[] hovaKoor = fungorium.getTektonrészKoordináta(hova);

        List<Gombafonal> fonalak = hova.getGombafonalak();
        if (!fonalak.isEmpty()) {
            for (Gombafonal fonal : fonalak) {
                if (fonal.faj == faj) {
                    if (honnanKoor[0] - 1 == hovaKoor[0]) {
                        // Balra új
                        kapcsolódóFonalak[3] = fonal;
                        fonal.kapcsolódóFonalak[1] = this;
                    }
                    else if (honnanKoor[0] + 1 == hovaKoor[0]) {
                        // Jobbra új
                        kapcsolódóFonalak[1] = fonal;
                        fonal.kapcsolódóFonalak[3] = this;
                    }
                    else if (honnanKoor[1] - 1 == hovaKoor[1]) {
                        // Fel új
                        kapcsolódóFonalak[0] = fonal;
                        fonal.kapcsolódóFonalak[2] = this;
                    }
                    else if (honnanKoor[1] + 1 == hovaKoor[1]) {
                        // Le új
                        kapcsolódóFonalak[2] = fonal;
                        fonal.kapcsolódóFonalak[0] = this;
                    }
                    else {
                        // Nem szomszéd
                        return false;
                    }

                    return true;
                }
            }
        }

        Gombafonal fonal = new Gombafonal(faj);

        for (int i = 0; i < gombaTestek.size(); i++)
        {
            fonal.gombaTestek.add(gombaTestek.get(i)); // ugyanazokhoz a gombatestekhez fog kapcsolodni az uj fonal is
        }

        if (hova.entitásHozzáadás(fonal)) // a tektonresz eldonti, hogy lehetseges-e a novesztes
        {
            if (honnanKoor[0] - 1 == hovaKoor[0]) {
                // Balra új
                kapcsolódóFonalak[3] = fonal;
                fonal.kapcsolódóFonalak[1] = this;
            }
            else if (honnanKoor[0] + 1 == hovaKoor[0]) {
                // Jobbra új
                kapcsolódóFonalak[1] = fonal;
                fonal.kapcsolódóFonalak[3] = this;
            }
            else if (honnanKoor[1] - 1 == hovaKoor[1]) {
                // Fel új
                kapcsolódóFonalak[0] = fonal;
                fonal.kapcsolódóFonalak[2] = this;
            }
            else if (honnanKoor[1] + 1 == hovaKoor[1]) {
                // Le új
                kapcsolódóFonalak[2] = fonal;
                fonal.kapcsolódóFonalak[0] = this;
            }
            else {
                // Nem szomszéd
                return false;
            }

            return true;
        }
        else
        {
            return false;
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

    public Gombafonal[] getKapcsolódóFonalak() {
        return kapcsolódóFonalak;
    }
}
