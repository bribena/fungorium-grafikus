package Game;

import java.util.HashMap;
import java.util.Map;

import prototipus_9.*;

public class PlayerManager {
    private String[] gombaszok = new String[4];
    private String[] rovaraszok = new String[4];
    
    private Map<Integer, Gombafaj> gombafajok = new HashMap<>();
    private Map<Integer, Rovarfaj> rovarfajok = new HashMap<>();

    public PlayerManager() {
        // Példaként minden játékos ugyanazt kapja
        for (int i = 0; i < 4; i++) {
            gombafajok.put(i, Gombafaj.Amanita);
            rovarfajok.put(i, Rovarfaj.Danae);
        }
        // Alapnevek beállítása
        gombaszok[0] = "Gombász1";
        gombaszok[1] = "Gombász2";
        gombaszok[2] = "Gombász3";
        gombaszok[3] = "Gombász4";
            
        rovaraszok[0] = "Rovarász1";
        rovaraszok[1] = "Rovarász2";
        rovaraszok[2] = "Rovarász3";
        rovaraszok[3] = "Rovarász4";
    }

    public boolean isGombafajhozTartozo(int jatekosIndex, Entitás e) {
    	return false;
        //return e instanceof Gombatest g && g.getFaj() == gombafajok.get(jatekosIndex);
    }

    public boolean isRovarhozTartozo(int jatekosIndex, Entitás e) {
		return false;
        //return e instanceof Rovar r && r.getFaj() == rovarfajok.get(jatekosIndex);
    }

    public Gombafaj getGombafaj(int index) {
        return gombafajok.get(index);
    }

    public Rovarfaj getRovarFaj(int index) {
        return rovarfajok.get(index);
    }

    // Kijelölés logika
    public void kijelolEntitast(Entitás e) {
        // Jelöljük ki az adott entitást, pl. állítsunk be belső státuszt (ha szükséges)
        //e.setKijelolt(true);
    }

    // Név beállítása index alapján (0-3: gombász, 4-7: rovarász)
    public void setName(int index, String name) {
        if (index < 0 || index >= 8) throw new IndexOutOfBoundsException("Érvénytelen index");
        if (index < 4) gombaszok[index] = name;
        else rovaraszok[index - 4] = name;
    }

    // Összes név lekérése egy tömbben (gombászok + rovarászok)
    public String[] getNames() {
        String[] all = new String[8];
        System.arraycopy(gombaszok, 0, all, 0, 4);
        System.arraycopy(rovaraszok, 0, all, 4, 4);
        return all;
    }

    // Egy adott név lekérése index alapján
    public String getName(int index) {
        if (index < 0 || index >= 8) throw new IndexOutOfBoundsException("Érvénytelen index");
        if (index < 4) return gombaszok[index];
        else return rovaraszok[index - 4];
    }

    // Gombászok külön lekérhetők
    public String[] getGombaszok() {
        return gombaszok.clone();
    }

    // Rovarászok külön lekérhetők
    public String[] getRovaraszok() {
        return rovaraszok.clone();
    }
}
