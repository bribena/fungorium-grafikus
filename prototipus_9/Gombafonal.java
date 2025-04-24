package prototipus_9;

import java.util.ArrayList;
import java.util.List;

public class Gombafonal implements Entitás {
    private int szakadt = 0;
    private List<Gombafonal> kapcsolodoFonalak = new ArrayList<>();
    private List<Gombatest> gombaTestek = new ArrayList<>();
    private Gombafaj faj;

    public Gombafonal(Gombafonal forras)
    {
        faj = forras.getFaj();
        kapcsolodoFonalak.add(forras);
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

    public boolean gombafonalatNöveszt(Tektonrész honnan, Tektonrész hova) // a honnan-t nem tudom hogyan alkalmazzam
    {
        if (!kapcsolódikGombatesthez()) // csak akkor noveszthet ha van kapcsolodo gombatest
        {
            return false;
        }

        Gombafonal fonal = new Gombafonal(this);
        
        for (int i = 0; i < gombaTestek.size(); i++)
        {
            fonal.gombatestHozzáadása(gombaTestek.get(i)); // ugyanazokhoz a gombatestekhez fog kapcsolodni az uj fonal is
        }

        if (hova.entitásHozzáadás(fonal)) // a tektonresz eldonti, hogy lehetseges-e a novesztes
        {
            kapcsolodoFonalak.add(fonal);
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

    public void fonalÖsszenövesztés(Gombafonal masik)
    {
        kapcsolodoFonalak.add(masik); // feltetelezem hogy a masik-hoz mar hozza van kotve ez a fonal
    }

    public void gombatestHozzáadása(Gombatest t)
    {
        gombaTestek.add(t);
    }

    public boolean kapcsolódikGombatesthez()
    {
        return gombaTestek.size() > 0;
    }

    public List<Gombafonal> getKapcsolodoFonalak()
    {
        return kapcsolodoFonalak;
    }

    public Gombafaj getFaj()
    {
        return faj;
    }
}
