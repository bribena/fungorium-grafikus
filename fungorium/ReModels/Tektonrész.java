package fungorium.ReModels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public abstract class Tektonrész {
    /** tektonID - Gombatest pár, nem kell a fungoriumból kikeresni */
    private static Map<Integer, Gombatest> tektonGombatest = new HashMap<>();

    /** Azonos ID-jűek egy tektonon vannak */
    private int tektonID = -1;
    /** Szokásos sorrend:
     * [0]: Fel
     * [1]: Jobbra
     * [2]: Le
     * [3]: Balra
     */
    private boolean[] tektonSzéleE = {false, false, false, false};
    /** Entitásokat tárol (lol) */
    protected List<Entitás> entitások = new ArrayList<>();
    
    protected Tektonrész() {}
    /** Copy */
    protected Tektonrész(Tektonrész tr) {
        tektonID = tr.tektonID;
        tektonSzéleE = tr.tektonSzéleE;
        entitások = tr.entitások;

        for (Entitás e : entitások) {
            if (e instanceof Gombatest) {
                tektonGombatest.put(tektonID, (Gombatest)e);
                ((Gombatest)e).passzívBeállítás(false);
            }
            else if (e instanceof Gombafonal) {
                ((Gombafonal)e).specBeállítás(false);
            }
        }
    }

    public int getTektonID() {
        return tektonID;
    }
    public void setTektonID(int tektonID) {
        this.tektonID = tektonID;
    }
    
    public boolean[] getTektonSzéleE() {
        return tektonSzéleE;
    }

    public List<Entitás> getEntitások() {
        return entitások;
    }

    // Ha gombatest: tektononként csak 1
    public boolean entitásHozzáadás(Gombatest gt) {
        if (tektonGombatest.containsKey(tektonID)) {
            return false;
        }
        tektonGombatest.put(tektonID, gt);
        return entitások.add(gt);
    }

    // Ha gombafonal: nincs másik ugyanolyan fajú
    public boolean entitásHozzáadás(Gombafonal gf) {
        for (Entitás e : entitások) {
            if (e instanceof Gombafonal && ((Gombafonal)e).getFaj() == gf.getFaj()) {
                return false;
            }
        }
        return entitások.add(gf);
    }

    // Ha spóra: hozzáaad, ha van
    public boolean entitásHozzáadás(Spóra sp) {
        for (Entitás e : entitások) {
            if (e instanceof Spóra && ((Spóra)e).getFaj() == sp.getFaj()) {
                ((Spóra)e).hozzáad(sp);
                return true;
            }
        }
        return entitások.add(sp);
    }

    // Ha rovar: semmi
    public boolean entitásHozzáadás(Entitás entitás) {
        // Csak hogy ne legyen kikerülhető
        if (entitás instanceof Gombatest) {
            return entitásHozzáadás((Gombatest)entitás);
        }
        if (entitás instanceof Gombafonal) {
            return entitásHozzáadás((Gombafonal)entitás);
        }
        if (entitás instanceof Spóra) {
            return entitásHozzáadás((Spóra)entitás);
        }
        return entitások.add(entitás);
    }

    public boolean entitásTörlés(Entitás entitás){
        for (int i = 0; i < entitások.size(); i++) {
            if (entitás == entitások.get(i)) {
                entitások.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean tartalmaz(Entitás entitás) {
        return entitások.contains(entitás);
    }

    public void frissítés() {
        for (int i = 0; i < entitások.size(); i++) {
            if (!entitások.get(i).frissítés()) {
                entitások.remove(i);
                --i;
            }
        }
    }
}
