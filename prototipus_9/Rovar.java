package prototipus_9;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rovar implements Entitás {
    private Rovarfaj faj;
    private int[] hatások;

    private Random random = new Random();

    public boolean frissítés()
    {
        for(int i = 0; i < hatások.length; i++){
            hatások[i]--;
            return true;
        }
        return false;
    }

    public void spóraFeldolgozás(int mennyi) {
        //a paraméterként megadott mennyiségű spórát feldolgozza, tehát hatást rak a rovarra

        List<Integer> sorsolhato = new ArrayList<>(); // ez ahhoz kell, hogy az elso 4 garantaltan kulonbozo legyen

        for (int i = 0; i < 4; i++)
        {
            sorsolhato.add(i);
        }

        for (int i = 0; i < mennyi; i++)
        {
            if (i < 4)
            {
                int roll = random.nextInt(sorsolhato.size()); // elvileg mukodik
                
                hatások[sorsolhato.get(roll-1)] += 1; // idk mennyivel kene novelni
                sorsolhato.remove(roll); // kivesszuk a listabol hogy a tobbibol sorsoljon a kovetkezo ciklusban
            }
            else
            {
                int roll = random.nextInt(4); // itt mar full random
                hatások[roll-1] += 1; // idk mennyivel kene novelni
            }
        }
    }

    public boolean hatásaAlatt(int idx){
        return hatások[idx] > 0;
    }

    public boolean hatásLejárt(){
        for(int i = 0; i < hatások.length; i++){
            if(hatásaAlatt(i)){
                return false;
            }
        }
        return true;
    }

    public boolean fonalatVág(Tektonrész t, Gombafonal f){
        //a paraméterként kapott tektonrészen lévő,
        // szintén paraméterként kapott fonalat végja el,
        // vagyis a fonalnak nem lesznek szomszédai

        return true;
    }

    public boolean visszahelyez(Tektonrész t){
        //a paraméterként kapott tektonrészre helyezi vissza a rovart
        return t.entitásHozzáadás(this);
    }

    public void eszik(Tektonrész t){

    }

    public void mozog(Tektonrész t1, Tektonrész t2){
        //a paraméterként kapott első tektonrészről a második tektonrészre mozgatja a rovart
        t1.entitásTörlés(this);
        t2.entitásHozzáadás(this);
    }

    public boolean gyenge(){
        return hatások[2] > 0;
    }

}
