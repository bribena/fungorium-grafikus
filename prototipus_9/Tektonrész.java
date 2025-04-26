package prototipus_9;

import java.util.ArrayList;
import java.util.List;

public abstract class Tektonrész {
    protected int tektonID = -1;
    protected boolean[] tektonSzéleE = {false, false, false, false}; // tárolja, hogy melyik irányban van rés
    protected List<Entitás> entitások = new ArrayList<>(); // a Tektonrészen levő entitásokat tárolja

    protected Tektonrész() {}

    protected Tektonrész(Tektonrész tr) {
        tektonID = tr.tektonID;
        tektonSzéleE = tr.tektonSzéleE;
        entitások = tr.entitások;
    }

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

    // viasszaadja a saját magán levő fonalak listáját
    public List<Gombafonal> getGombafonalak()
    {
        List<Gombafonal> fonalak = new ArrayList<>();

        for (int i = 0; i < entitások.size(); i++)
        {
            if (entitások.get(i) instanceof Gombafonal)
            {
                fonalak.add((Gombafonal)entitások.get(i));
            }
        }

        return fonalak;
    }

    /**
     * Visszaadja, hogy melyik irányban van rés.
     * [0]: Felfelé,
     * [1]: Jobbra,
     * [2]: Lefelé,
     * [3]: Balra.
     * Bármelyik érték igaz, ha van ott rés.
     */
    public boolean[] getTektonSzéleE() {
        return tektonSzéleE;
    }
}
