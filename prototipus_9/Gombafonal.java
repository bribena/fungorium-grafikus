package prototipus_9;

import java.util.ArrayList;
import java.util.List;

public class Gombafonal implements Entitás {
    private int szakadt = 0;
    /**
     * [0]: Fel
     * [1]: Jobbra
     * [2]: Le
     * [3]: Balra
     */
    private Gombafonal[] kapcsolodoFonalak = new Gombafonal[4];
    private List<Gombatest> gombaTestek = new ArrayList<>();
    private Gombafaj faj;

    public Gombafonal(Gombafonal forras)
    {
        faj = forras.getFaj();
    }

    @Override
    public boolean frissítés()
    {
        if (!kapcsolódikGombatesthez())
        {
            szakadt += 1;
            if (szakadt > 2) // random szam, erre majd valami konstant definialunk
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

    public boolean gombafonalatNöveszt(Tektonrész honnan, Tektonrész hova, Fungorium fungorium) // a honnan-t nem tudom hogyan alkalmazzam
    {
        if (!kapcsolódikGombatesthez()) // csak akkor noveszthet ha van kapcsolodo gombatest
        {
            return false;
        }

        int[] honnanKoor = fungorium.getTektonrészKoordináta(honnan);
        int[] hovaKoor = fungorium.getTektonrészKoordináta(hova);

        // Összenövesztés
        List<Gombafonal> fonalak = hova.getGombafonalak();
        if (!fonalak.isEmpty()) {
            for (Gombafonal fonal : fonalak) {
                if (fonal.faj == faj) {
                    if (honnanKoor[0] - 1 == hovaKoor[0]) {
                        // Balra új
                        kapcsolodoFonalak[3] = fonal;
                        fonal.kapcsolodoFonalak[1] = this;
                    }
                    else if (honnanKoor[0] + 1 == hovaKoor[0]) {
                        // Jobbra új
                        kapcsolodoFonalak[1] = fonal;
                        fonal.kapcsolodoFonalak[3] = this;
                    }
                    else if (honnanKoor[1] - 1 == hovaKoor[1]) {
                        // Fel új
                        kapcsolodoFonalak[0] = fonal;
                        fonal.kapcsolodoFonalak[2] = this;
                    }
                    else if (honnanKoor[1] + 1 == hovaKoor[1]) {
                        // Le új
                        kapcsolodoFonalak[2] = fonal;
                        fonal.kapcsolodoFonalak[0] = this;
                    }
                    else {
                        // Nem szomszéd
                        return false;
                    }

                    return true;
                }
            }
        }

        Gombafonal fonal = new Gombafonal(this);
        
        for (int i = 0; i < gombaTestek.size(); i++)
        {
            fonal.gombatestHozzáadása(gombaTestek.get(i)); // ugyanazokhoz a gombatestekhez fog kapcsolodni az uj fonal is
        }

        if (hova.entitásHozzáadás(fonal)) // a tektonresz eldonti, hogy lehetseges-e a novesztes
        {
            if (honnanKoor[0] - 1 == hovaKoor[0]) {
                // Balra új
                kapcsolodoFonalak[3] = fonal;
                fonal.kapcsolodoFonalak[1] = this;
            }
            else if (honnanKoor[0] + 1 == hovaKoor[0]) {
                // Jobbra új
                kapcsolodoFonalak[1] = fonal;
                fonal.kapcsolodoFonalak[3] = this;
            }
            else if (honnanKoor[1] - 1 == hovaKoor[1]) {
                // Fel új
                kapcsolodoFonalak[0] = fonal;
                fonal.kapcsolodoFonalak[2] = this;
            }
            else if (honnanKoor[1] + 1 == hovaKoor[1]) {
                // Le új
                kapcsolodoFonalak[2] = fonal;
                fonal.kapcsolodoFonalak[0] = this;
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

    public boolean gombatestetNöveszt(Tektonrész aholVan, Fungorium fungorium)
    {
        if (!aholVan.vanSpóra()) // ha nincs is spora akkor ki lehet returnolni
        {
            return false;
        }

        List<Entitás> entitások = aholVan.entitásokVisszaadása();

        for (int i = 0; i < entitások.size(); i++)
        {
            if (entitások.get(i).getClass() == Spóra.class)
            {
                Spóra spóra = (Spóra)entitások.get(i);
                if (spóra.getFaj() == faj && spóra.spóraszámVisszaadása() > 5) // ez majd valami valtozobol jojjon
                {
                    Gombatest test = new Gombatest(false); // majd valtoztatast igenyel a konstruktor
                    if (aholVan.entitásHozzáadás(test)) // a tektonrész eldönti hogy lehelyezhető-e
                    {
                        gombaTestek.add(test);
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }
        }

        return false;
    }

    public void gombatestHozzáadása(Gombatest t)
    {
        gombaTestek.add(t);
    }

    public boolean kapcsolódikGombatesthez()
    {
        return gombaTestek.size() > 0;
    }

    public Gombafonal[] getKapcsolodoFonalak()
    {
        return kapcsolodoFonalak;
    }

    public Gombafaj getFaj()
    {
        return faj;
    }
}
