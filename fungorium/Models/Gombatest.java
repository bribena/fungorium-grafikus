package fungorium.Models;

public class Gombatest implements Entitás {

    // A gombatest jelenleg passzív-e
    private boolean passzív = false;

    // A spóraszórás előtt szükséges várakozási idő
    private int spóraszórásVárakozásIdő = 0;

    // Hány spóraszórási lehetősége van még a gombatestnek
    private int spóraszórásLehetőség = 4;

    // Fejlődött-e már a gombatest (pl. több spórát tud szórni)
    private boolean fejlődött = false;

    // Kezdő-e a gombatest
    private boolean kezdő = false;

    // Ebben a körben már végzett-e műveletet (pl. növesztett fonalat)
    private boolean mozgatott = true;

    // A gombatesthez tartozó gombafaj
    private Gombafaj faj;

    public Gombatest(Gombafaj faj) {
        this.faj = faj;
    }

    // Visszaadja a gombatest gombafaját
    public Gombafaj getFaj() {
        return faj;
    }

    // Megmondja, hogy az entitás még érvényes-e (van-e még spóraszórási lehetősége)
    @Override
    public boolean érvényesE() {
        return spóraszórásLehetőség > 0;
    }

    // Frissíti az állapotot
    @Override
    public boolean frissítés() {
        if (!passzív) {
            if (spóraszórásVárakozásIdő > 0) {
                spóraszórásVárakozásIdő -= 1;
            }
            mozgatott = false; // minden kör elején újra mozoghat
        }
        return érvényesE(); // visszajelzés, hogy még élő/érvényes-e az entitás
    }

    // Megmondja, hogy ebben a körben már végzett-e műveletet
    public boolean mozgatott() {
        return mozgatott;
    }

    // Megmondja, hogy növeszthet-e fonalat ebben a körben
    public boolean növeszthetFonalat() {
        return !mozgatott && !passzív;
    }

    // Beállítja, hogy már végzett fonalnövesztést
    public void fonalatNöveszt() {
        mozgatott = true;
    }

    // Beállítja, hogy passzív legyen-e a gombatest
    public void passzívBeállítás(boolean p) {
        passzív = p;
    }

    // Visszaadja, hogy a gombatest passzív-e
    public boolean isPasszív() {
        return passzív;
    }

    // Megjelöli, hogy kezdő a gombatest
    public void setKezdő() {
        kezdő = true;
    }

    // Visszaadja, hogy kezdő-e
    public boolean isKezdő() {
        return kezdő;
    }

    // Visszaadja, hogy fejlődött-e
    public boolean isFejlődött() {
        return fejlődött;
    }

    // Fejlesztés végrehajtása (további spóraszórási lehetőség és várakozási idő)
    public void fejleszt() {
        // Nem lehet fejleszteni, ha mozgatott, passzív vagy még nem telt le a várakozási idő
        if (mozgatott || passzív || spóraszórásVárakozásIdő > 0) {
            return;
        }

        fejlődött = true;
        spóraszórásLehetőség += 3;        // több spórát tud majd szórni
        spóraszórásVárakozásIdő = 2;      // fejlődés után újra kell várni
        mozgatott = true;                 // fejlesztés is mozgásnak számít
    }

    // Spóra szórása egy célterületre a forrásterületről, ha a szabályok engedik
    public boolean spórátSzór(Tektonrész forrás, Tektonrész cél, Fungorium fungorium) {
        // Ellenőrzések:
        if (
            // Ha fejlődött, akkor 2. fokú szomszédnak kell lennie a célterületnek
            (fejlődött && !fungorium.getMásodfokúTektonSzomszédosságok(forrás.getTektonID()).contains(cél.getTektonID()))
            // Ha nem fejlődött, akkor 1. fokú szomszédnak kell lennie
            || (!fejlődött && !fungorium.getElsőfokúTektonSzomszédosságok(forrás.getTektonID()).contains(cél.getTektonID()))
            // Ha még nem telt le a várakozási idő
            || spóraszórásVárakozásIdő > 0
            // Ha kezdő, de már csak 1 lehetősége maradt
            || (kezdő && spóraszórásLehetőség < 2)
            // Ha passzív vagy már mozgott
            || passzív
            || mozgatott
        ) {
            return false;
        }

        System.out.println("Spora szorasa");

        // Spórák szórása a célterületre és annak szomszédaira
        Tektonrész[][] célok = fungorium.getSpóraTektonrészSzomszédok(cél);

        // Középpontba 5 spóraszámú spóra kerül
        célok[0][0].entitásHozzáadás(new Spóra(faj, 5));

        // Első szomszédokra 2 spóraszámú
        for (Tektonrész tr : célok[1]) {
            System.out.println("Spora szorasa szomszedra");
            tr.entitásHozzáadás(new Spóra(faj, 2));
        }

        // Másodfokú szomszédokra 1 spóraszámú
        for (Tektonrész tr : célok[2]) {
            System.out.println("Spora szorasa szomszed szomszedjara");
            tr.entitásHozzáadás(new Spóra(faj, 1));
        }

        // Frissítjük az állapotokat
        spóraszórásVárakozásIdő = 2;
        spóraszórásLehetőség--;
        mozgatott = true;

        return true;
    }
}
