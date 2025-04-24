package prototipus_9;

import java.util.ArrayList;
import java.util.List;

public abstract class Tektonrész {
    private int tektonID = -1;
    private boolean[] TektonSzéleE; // tárolja, hogy melyik irányban van rés
    private List<Entitás> entitások = new ArrayList<>(); // a Tektonrészen levő entitásokat tárolja

    public int getTektonID() {
        return tektonID;
    }
    public void setTektonID(int tektonID) {
        this.tektonID = tektonID;
    }

    // hozzáadja a tektonrész entitásainak listájához a paraméterként kapott entitást, true-t ad vissza, ha sikeres volt, amúgy false-ot
    public boolean entitásHozzáadás(Entitás entitás)
    {
        for (int i = 0; i < entitások.size(); i++)
        {
            if(vanGomba() && entitás.getClass() == Gombatest.class)
            {
                return false;
            }
        }
        entitások.add(entitás);
        return true;
    }

    // törli az aktuális tektonrészről az entitások listájából a paraméterként kapott entitást
    public void entitásTörlés(Entitás entitás)
    {
        if (entitások.contains(entitás))
        {
            entitások.remove(entitás);
        }
    }

    // visszaadja a listában tárolt entitásokat
    public List<Entitás> entitásokVisszaadása()
    {
        return entitások;
    }

    // entitások frissítését kezeli. Alapértelmezés szerint mindegyik entitás frissítés függvényét meghívja
    public void frissítés()
    {
        for (int i = 0; i < entitások.size(); i++)
        {
            entitások.get(i).frissítés();
        }
    }

    // ha van gombatest az aktuális tektonrészen, true-t ad vissza, amúgy false-ot
    public boolean vanGomba()
    {
        for (int i = 0; i < entitások.size(); i++)
        {
            if(entitások.get(i).getClass() == Gombatest.class) // ez nem tudom mennyire szép
            {
                return true;
            }
        }

        return false;
    }

    // ha van spóra a tektonrészen, true-t ad vissza, amúgy false-ot
    public boolean vanSpóra()
    {
        for (int i = 0; i < entitások.size(); i++)
        {
            if(entitások.get(i).getClass() == Spóra.class) // ez nem tudom mennyire szép
            {
                return true;
            }
        }

        return false;
    }

    // viasszaadja a paraméterként kapott tektonrészen lévő fonalak listáját
    List<Entitás> getGombafonalak(Tektonrész t)
    {
        List<Entitás> fonalak = new ArrayList<>();

        for (int i = 0; i < entitások.size(); i++)
        {
            if (entitások.get(i).getClass() == Gombafonal.class)
            {
                fonalak.add(entitások.get(i));
            }
        }

        return fonalak;
    }
}
