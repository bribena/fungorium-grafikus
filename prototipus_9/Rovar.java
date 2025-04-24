package prototipus_9;

public class Rovar implements Entitás {
    private Rovarfaj faj;
    private int[] hatások;

    public void frissítés()
    {
        for(int i = 0; i < hatások.length; i++){
            hatások[i]--;
        }
    }

    public void spóraFeldolgozás(int mennyi) {
        //a paraméterként megadott mennyiségű spórát feldolgozza, tehát hatást rak a rovarra
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
    }

    public boolean gyenge(){
        return hatások[2] > 0;
    }

}
