package fungorium.ReModels;


public class Gombatest implements Entitás {
    private boolean passzív = false;
    private int spóraszórásVárakozásIdő = 0;
    private int spóraszórásLehetőség = 4;
    private boolean fejlődött = false;
    private boolean kezdő = false;
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
        if (!passzív && spóraszórásVárakozásIdő > 0) {
            spóraszórásVárakozásIdő -= 1;
        }
        return érvényesE();
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
        if (passzív) {
            return;
        }

        fejlődött = true;
        spóraszórásLehetőség += 3;
    }

    public void spórátSzór(Tektonrész forrás, Tektonrész cél, Fungorium fungorium) {
        if ((fejlődött && !fungorium.getMásodfokúTektonSzomszédosságok(forrás.getTektonID()).contains(cél.getTektonID())
            || (!fejlődött && !fungorium.getElsőfokúTektonSzomszédosságok(forrás.getTektonID()).contains(cél.getTektonID())))
            || spóraszórásVárakozásIdő > 0
            || (kezdő && spóraszórásLehetőség < 2)
            || passzív) {
            return;
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
    }
}
