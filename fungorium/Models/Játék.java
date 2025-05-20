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

        if (aktuálisJátékos >= játékosok.size()) {
            aktuálisJátékos = 0;
            kör++;
            fungorium.körtLéptet(kör);
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

    public String getWinnerGombasz() {
    int maxPont = -1;
    int győztesIndex = -1;

    for (int i = 0; i < 4; i++) {
        Gombafaj faj = ((Gombász) játékosok.get(i)).getKezeltFaj();
        int pont = Gombafonal.getGombatestSzám(faj);
        if (pont > maxPont) {
            maxPont = pont;
            győztesIndex = i;
        }
    }

    // Nincs ellenőrzés, simán visszaadjuk a győztes nevét
    return játékosok.get(győztesIndex).getName();
    }

    public String getWinnerRovarasz() {
        int maxPont = -1;
        int győztesIndex = -1;

        for (int i = 4; i < 8; i++) {
            Rovarfaj faj = ((Rovarász) játékosok.get(i)).getKezeltFaj();
            int pont = Rovar.getTápérték(faj);
            if (pont > maxPont) {
                maxPont = pont;
                győztesIndex = i;
            }
        }

        return játékosok.get(győztesIndex).getName();
    }
}
