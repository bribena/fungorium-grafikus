package fungorium.ReModels;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rovar implements Entitás {
    private Rovarfaj faj;
    private int[] hatások;
    private Tektonrész tektonresz;

    private Random random = new Random();

    public Rovar(Rovarfaj faj) {
        this.faj = faj;
    }

    public Rovarfaj getFaj() {
        return faj;
    }

    @Override
    public boolean érvényesE() {
        return true;
    }

    @Override
    public boolean frissítés() {
        return false;
    }

    public void spóraFeldolgozás(int mennyi) {
        List<Integer> sorsolhato = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            sorsolhato.add(i);
        }

        for (int i = 0; i < mennyi; i++) {
            if (i < 4) {
                int roll = random.nextInt(sorsolhato.size());

                hatások[sorsolhato.get(roll - 1)] += 1;
                sorsolhato.remove(roll);
            } else {
                int roll = random.nextInt(4);
                hatások[roll - 1] += 1;
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

        for (int i = 0; i < 4; i++)
        {
            f.szakad(i);
        }

        return true;
    }

    public boolean visszahelyez(Tektonrész t) {
        boolean sikeres = t.entitásHozzáadás(this);
        if (sikeres) {
            tektonresz = t;
        }
        return sikeres;
    }

    public void eszik(Tektonrész t) {

    }

    public void mozog(Tektonrész t1, Tektonrész t2) {
        t1.entitásTörlés(this);
        t2.entitásHozzáadás(this);
        tektonresz = t2;
    }

    public boolean isGyenge() {
        return hatások[2] > 0;
    }
}
