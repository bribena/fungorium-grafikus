package prototipus_9;

public class Gombatest implements Entitás {
    private boolean passzív;
    private boolean fejlődött;
    private boolean kezdő; //ha ez volt a Gombász által először elhelyezett Gombatest.
    private int spóraszórásVárakozásiIdő; //Mennyi idő van hátra, mielőtt újból szórhatna spórát
    private int spóraszórásLehetőség;
    private Gombafaj faj;


    public void frissítés()
    {
    }

    public boolean fejlődik(){
        // igaz értéket ad, ha fejlesztve lett a test, hamisat,
        // ha nem lehet fejleszteni vagy/mert már fejlesztve van
        return true;
    }
    public boolean spórátSzór (Tektonrész t, Fungorium f) {
        //a paraméterként megadott tekronrészről szór spórát.
        // Ha a test rajta fejlett, akkor szomszédos tektonokra és azok szomszédaira is szór,
        // amúgy csak a szomszédos tektonokra. True-t ad vissza, ha szórás megtörtént, false-ot, ha nem.
        return true;
    }
}
