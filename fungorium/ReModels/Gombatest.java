package fungorium.ReModels;


public class Gombatest implements Entitás {
    private boolean passzív = false;
    private int spóraszórásVárakozásIdő = 0;
    private int spóraszórásLehetőség = 4;
    private boolean fejlődött = false;
    private boolean kezdő = false;
    private boolean mozgatott = true;
    private Gombafaj faj;

    public Gombatest(Gombafaj faj) {
        this.faj = faj;
    }

    public Gombafaj getFaj() {
        return faj;
    }

    @Override
    public boolean érvényesE() {
        return spóraszórásLehetőség > 0;
    }

    @Override
    public boolean frissítés() {
        if (!passzív) {
            if (spóraszórásVárakozásIdő > 0) {
                spóraszórásVárakozásIdő -= 1;
            }
            mozgatott = false;
        }
        return érvényesE();
    }

    public boolean mozgatott() {
        return mozgatott;
    }
    public boolean növeszthetFonalat() {
        return !mozgatott && !passzív;
    }
    public void fonalatNöveszt() {
        mozgatott = true;
    }

    public void passzívBeállítás(boolean p) {
        passzív = p;
    }
    public boolean isPasszív() {
        return passzív;
    }

    public void setKezdő() {
        kezdő = true;
    }
    public boolean isKezdő() {
        return kezdő;
    }

    public boolean isFejlődött() {
        return fejlődött;
    }
    public void fejleszt() {
        if (mozgatott || passzív || spóraszórásVárakozásIdő > 0) {
            return;
        }

        fejlődött = true;
        spóraszórásLehetőség += 3;
        spóraszórásVárakozásIdő = 2;
        mozgatott = true;
    }

    public boolean spórátSzór(Tektonrész forrás, Tektonrész cél, Fungorium fungorium) {
        if ((fejlődött && !fungorium.getMásodfokúTektonSzomszédosságok(forrás.getTektonID()).contains(cél.getTektonID())
            || (!fejlődött && !fungorium.getElsőfokúTektonSzomszédosságok(forrás.getTektonID()).contains(cél.getTektonID())))
            || spóraszórásVárakozásIdő > 0
            || (kezdő && spóraszórásLehetőség < 2)
            || passzív
            || mozgatott) {
            return false;
        }

        Tektonrész[][] célok = fungorium.getSpóraTektonrészSzomszédok(cél);
        célok[0][0].entitásHozzáadás(new Spóra(faj, 5));
        for (Tektonrész tr : célok[1]) {
            tr.entitásHozzáadás(new Spóra(faj, 2));
        }
        for (Tektonrész tr : célok[2]) {
            tr.entitásHozzáadás(new Spóra(faj, 1));
        }
        spóraszórásVárakozásIdő = 2;
        spóraszórásLehetőség--;

        mozgatott = true;
        return true;
    }
}
