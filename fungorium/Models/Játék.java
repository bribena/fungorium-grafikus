package fungorium.Models;

import java.util.ArrayList;
import java.util.List;

public class Játék {
    private int kör = 0;
    private int aktuálisJátékos = 0;
    private boolean utókör = false;
    private boolean utókörVége = false;
    private List<Játékos> játékosok = new ArrayList<>();
    private Fungorium fungorium = new Fungorium();

    public Játék() {
        for (int i = 0; i < 4; i++) {
            játékosok.add(new Gombász(Gombafaj.values()[i], "Gombász"+(i+1)));
        }
        for (int i = 0; i < 4; i++) {
            játékosok.add(new Rovarász(Rovarfaj.values()[i], "Rovarász"+(i+1)));
        }
    }

    public Fungorium getFungorium() {
        return fungorium;
    }

    public void léptet() {
        if (vége()) {
            return;
        }

        aktuálisJátékos++;
        if (aktuálisJátékos <= játékosok.size()) {
            if (!utókör) {
                fungorium.lassúRovarokMozgathatása();
            }

            utókörVége = utókör;
            utókör = true;
        }
        aktuálisJátékos = 0;

        if (utókörVége) {
            fungorium.körtLéptet(kör);
            kör++;
            utókör = false;
            utókörVége = false;
        }
        if (utókör) {
            while (aktuálisJátékos < játékosok.size()) {
                if (getAktuálisJátékos() instanceof Rovarász) {
                    Rovarász r = (Rovarász)getAktuálisJátékos();
                    if (fungorium.vanMozgathatóEntitás(r.getKezeltFaj())) {
                        break;
                    }
                }
                aktuálisJátékos++;
            }
            if (aktuálisJátékos == játékosok.size()) {
                léptet();
            }
        }
    }
    
    public void automataLéptetés() {
        Játékos j = getAktuálisJátékos();
        if (j instanceof Gombász && !fungorium.vanMozgathatóEntitás(((Gombász)j).getKezeltFaj()) ||
            j instanceof Rovarász && !fungorium.vanMozgathatóEntitás(((Rovarász)j).getKezeltFaj())) {
            léptet();
        }
    }

    public Játékos getAktuálisJátékos() {
        if (aktuálisJátékos > játékosok.size()) {
            return játékosok.get(játékosok.size() - 1);
        }
        return játékosok.get(aktuálisJátékos);
    }

    public List<Játékos> getJátékosok() {
        return játékosok;
    } 

    public boolean vége() {
        return kör == 25;
    }
    public boolean kezdő() {
        return kör == 0;
    }
}
