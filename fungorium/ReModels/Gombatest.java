package fungorium.ReModels;


public class Gombatest implements Entitás {
    private boolean passzív = false;
    private int spóraszórásVárakozásIdő = 0;
    private int spóraszórásLehetőség = 4;
    private boolean fejlődött = false;
    private boolean kezdő = false;
    private boolean fonalatNövesztett = false;
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
            fonalatNövesztett = false;
        }
        return érvényesE();
    }

    public boolean növeszthetFonalat() {
        return !fonalatNövesztett && !passzív;
    }
    public void fonalatNöveszt() {
        fonalatNövesztett = true;
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
        if (passzív || spóraszórásVárakozásIdő > 0) {
            return;
        }

        fejlődött = true;
        spóraszórásLehetőség += 3;
        spóraszórásVárakozásIdő = 2;
    }

<<<<<<< HEAD
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
=======
    public List<Spóra> spórátSzór(Tektonrész t, Fungorium f) {
        List<Tektonrész> marSzort = new ArrayList<>(); // itt taroljuk a tektonokat ahova mar lett szorva, mivel a
                                                       // szomszed returnnel kesobb olyant is visszaad amin mar jartunk
        List<Spóra> sporak = new ArrayList<>();

        int[] tektonCoords = f.getTektonrészKoordináta(t);
        Tektonrész[] kozeliSzomszedok = f.getTektonrészSzomszédok(tektonCoords[0], tektonCoords[1]);

        Spóra spora = new Spóra(faj, 5, tektonCoords);
        if (t.entitásHozzáadás(spora)) // sok ilyen csunya check van mert fogalmam sincs hogyan mashogy tudna ez
                                       // false-t returnolni
        {
            sporak.add(spora);
        }
        marSzort.add(t);

        for (int i = 0; i < kozeliSzomszedok.length; i++) {
            spora = new Spóra(faj, 2, f.getTektonrészKoordináta(kozeliSzomszedok[i]));
            if (kozeliSzomszedok[i].entitásHozzáadás(spora)) {
                sporak.add(spora);
            }
            marSzort.add(kozeliSzomszedok[i]);
        }

        if (fejlődött) {
            for (int i = 0; i < marSzort.size(); i++) // vegig megyunk azokon a tektonokon ahol mar szortunk (a kozepson
                                                      // is, de nyilvan nem fog ujra szorni arra)
            {
                tektonCoords = f.getTektonrészKoordináta(marSzort.get(i));
                Tektonrész[] tavoliSzomszedok = f.getTektonrészSzomszédok(tektonCoords[0], tektonCoords[1]);

                for (int j = 0; j < tavoliSzomszedok.length; j++) {
                    if (!marSzort.contains(tavoliSzomszedok[j])) // csak akkor szorunk ha itt meg nem szortunk
                    {
                        spora = new Spóra(faj, 1, f.getTektonrészKoordináta(tavoliSzomszedok[j]));
                        if (tavoliSzomszedok[j].entitásHozzáadás(spora)) {
                            sporak.add(spora);
                        }
                        marSzort.add(tavoliSzomszedok[j]);
                    }
                }
            }
>>>>>>> origin/main
        }
        for (Tektonrész tr : célok[2]) {
            tr.entitásHozzáadás(new Spóra(faj, 1));
        }
        spóraszórásVárakozásIdő = 2;
        spóraszórásLehetőség--;
    }
}
