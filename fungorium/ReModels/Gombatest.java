package fungorium.ReModels;

import fungorium.Models.Fungorium;
import fungorium.Models.Tektonrész;

public class Gombatest implements Entitás {
    private boolean passzív = false;
    private int spóraSzórásVárakozásIdő = 0;
    private boolean fejlődött = false;
    private boolean kezdő;
    private Gombafaj faj;
    public Gombatest(){};
    public Gombatest(Gombafaj faj) {
        this.faj = faj;
    }

    public Gombafaj getFaj() {
        return faj;
    }


    @Override
    public boolean érvényesE() {
        return true;
    }

    @Override
    public boolean frissítés() {
        if (!passzív && spóraSzórásVárakozásIdő > 0) {
            spóraSzórásVárakozásIdő -= 1;
            return true;
        }
        return false;
    }

    public boolean isKezdő() {
        return kezdő;
    }

    public boolean isFejlődött() {
        return fejlődött;
    }

    public void setFejlodott(boolean fejlodott) {
        this.fejlődött = fejlodott;
    }

    public boolean spórátSzór(Tektonrész t, Fungorium f) {
        throw new UnsupportedOperationException("Unimplemented method 'spórátSzór'");
    }
    
}
