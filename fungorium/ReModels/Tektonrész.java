package fungorium.ReModels;

import java.util.List;
import java.util.ArrayList;

public abstract class Tektonrész {
    private int tektonID = -1;
    private boolean[] tektonSzéleE = {false, false, false, false};
    protected List<Entitás> entitások = new ArrayList<>();

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
    public boolean[] getTektonSzéleE() {
        return tektonSzéleE;
    }

    public List<Entitás> getEntitások() {
        return entitások;
    }

    public boolean vanGomba() {
        for (int i = 0; i < entitások.size(); i++) {
            if (entitások.get(i) instanceof Gombatest)
            {
                return true;
            }
        }
        return false;
    }

    public boolean vanSpóra() {
        for (int i = 0; i < entitások.size(); i++) {
            if (entitások.get(i) instanceof Spóra)
            {
                return true;
            }
        }
        return false;
    }

    public boolean entitásHozzáadás(Entitás entitás) {
        for (int i = 0; i < entitások.size(); i++) {
            if (vanGomba() && (entitás instanceof Gombatest)) {
                return false;
            }
        }
        entitások.add(entitás);
        return true;
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

    public List<Gombafonal> getGombafonalak() {
        List<Gombafonal> fonalak = new ArrayList<>();
        for (int i = 0; i < entitások.size(); i++) {
            if (entitások.get(i) instanceof Gombafonal) {
                fonalak.add((Gombafonal)entitások.get(i));
            }
        }
        return fonalak;
    }

    public void frissítés() {
        for (int i = 0; i < entitások.size(); i++) {
            entitások.get(i).frissítés();
        }
    }
}
