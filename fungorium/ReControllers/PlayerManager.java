package fungorium.ReControllers;

import fungorium.ReModels.Entitás;
import fungorium.ReModels.Fungorium;
import fungorium.ReModels.Gombafaj;
import fungorium.ReModels.Gombász;
import fungorium.ReModels.Játékos;
import fungorium.ReModels.Rovarfaj;
import fungorium.ReModels.Rovarász;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private List<Játékos> jatekosok = new ArrayList<>();
    private Fungorium fungorium = new Fungorium();
    private int aktuálisJátékos;

    public PlayerManager() {
        // Példaként minden játékos ugyanazt kapja
        for (int i = 0; i < 4; i++) {
            jatekosok.add(new Gombász(Gombafaj.values()[i], "Gombász"+(i+1)));
        }

        for (int i = 0; i < 4; i++) {
            jatekosok.add(new Rovarász(Rovarfaj.values()[i], "Rovarász"+(i+1)));
        }

        aktuálisJátékos = 0;
    }

    public Játékos getAktuálisJátékos() {
        return jatekosok.get(aktuálisJátékos);
    }

    public int getAktuálisJátékosIndex() {
        return aktuálisJátékos;
    }

    public void következőJátékos()
    {
        aktuálisJátékos = (aktuálisJátékos + 1) % 8;
    }

    public Fungorium getFungorium() {
        return fungorium;
    }

    public List<Játékos> getJatekosok()
    {
        return jatekosok;
    }

    public boolean isGombafajhozTartozo(int jatekosIndex, Entitás e) {
    	return false;
        //return e instanceof Gombatest g && g.getFaj() == gombafajok.get(jatekosIndex);
    }

    public boolean isRovarhozTartozo(int jatekosIndex, Entitás e) {
		return false;
        //return e instanceof Rovar r && r.getFaj() == rovarfajok.get(jatekosIndex);
    }

    // Név beállítása index alapján (0-3: gombász, 4-7: rovarász)
    public void setName(int index, String name) {
        if (index < 0 || index >= 8) throw new IndexOutOfBoundsException("Érvénytelen index");
        jatekosok.get(index).setName(name);
    }

    // Összes név lekérése egy tömbben (gombászok + rovarászok)
    public String[] getNames() {
        String[] all = new String[8];

        for(int i = 0; i < jatekosok.size(); i++)
        {
            all[i] = jatekosok.get(i).getName();
        }
        return all;
    }

    // Egy adott név lekérése index alapján
    public String getName(int index) {
        if (index < 0 || index >= 8) throw new IndexOutOfBoundsException("Érvénytelen index");
        return jatekosok.get(index).getName();
    }
}
