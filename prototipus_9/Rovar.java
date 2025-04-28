package prototipus_9;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rovar implements Entitás {
    private Rovarfaj faj;
    private int[] hatások;
    private Tektonrész tektonresz;
    private Random random = new Random();

    public boolean frissítés() {
        for (int i = 0; i < hatások.length; i++) {
            hatások[i]--;
            return true;
        }
        return false;
    }

    public void spóraFeldolgozás(int mennyi) {
        // a paraméterként megadott mennyiségű spórát feldolgozza, tehát hatást rak a
        // rovarra

        List<Integer> sorsolhato = new ArrayList<>(); // ez ahhoz kell, hogy az elso 4 garantaltan kulonbozo legyen

        for (int i = 0; i < 4; i++) {
            sorsolhato.add(i);
        }

        for (int i = 0; i < mennyi; i++) {
            if (i < 4) {
                int roll = random.nextInt(sorsolhato.size()); // elvileg mukodik

                hatások[sorsolhato.get(roll - 1)] += 1; // idk mennyivel kene novelni
                sorsolhato.remove(roll); // kivesszuk a listabol hogy a tobbibol sorsoljon a kovetkezo ciklusban
            } else {
                int roll = random.nextInt(4); // itt mar full random
                hatások[roll - 1] += 1; // idk mennyivel kene novelni
            }
        }
    }

    public boolean hatásaAlatt(int idx) {
        return hatások[idx] > 0;
    }

    public boolean hatásLejárt() {
        for (int i = 0; i < hatások.length; i++) {
            if (hatásaAlatt(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isBena() {
        // a rovar bénult-e, vagyis a hatások tömb 0. indexe nagyobb-e 0-nál
        return hatásaAlatt(2);
    }

    public Tektonrész getTektonrész() {
        return tektonresz;
    }

    public void setTektonrész(Tektonrész t) {
        tektonresz = t;
    }

    public void setHatas(int idx, int ertek) {
        hatások[idx] = ertek;
    }

    public boolean fonalatVág(Tektonrész t, Gombafonal f) {
        // a paraméterként kapott tektonrészen lévő,
        // szintén paraméterként kapott fonalat végja el,
        // vagyis a fonalnak nem lesznek szomszédai

        return true;
    }

    /**
     * Visszahelyezi a rovart egy adott Tektonrészre, és frissíti a tektonresz
     * mutatót is.
     */
    public boolean visszahelyez(Tektonrész t) {
        boolean sikeres = t.entitásHozzáadás(this);
        if (sikeres) {
            tektonresz = t; // Ha sikeres volt, akkor frissítjük a helyét
        }
        return sikeres;
    }

    public void eszik(Tektonrész t) {

    }

    public void mozog(Tektonrész t1, Tektonrész t2) {
        // a paraméterként kapott első tektonrészről a második tektonrészre mozgatja a
        // rovart
        t1.entitásTörlés(this);
        t2.entitásHozzáadás(this);
        tektonresz = t2; // a tektonrész is frissül
    }

    public boolean gyenge() {
        return hatások[2] > 0;
    }

}
