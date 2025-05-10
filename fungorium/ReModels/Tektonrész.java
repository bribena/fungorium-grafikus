package fungorium.ReModels;

import java.util.List;
import java.util.ArrayList;

public abstract class Tektonrész {
    private int tektonID = -1;
    private boolean[] tektonSzéleE = {false, false, false, false};
    private List<Entitás> entitások = new ArrayList<>();

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
}
