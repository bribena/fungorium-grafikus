package fungorium.ReModels;

import java.util.ArrayList;
import java.util.List;

public class Gombatest implements Entitás {
    private boolean passzív = false;
    private int spóraSzórásVárakozásIdő = 0;
    private boolean fejlődött = false;
    private boolean kezdő = false;
    private Gombafaj faj;

    public Gombatest(){}

    public Gombatest(Gombafaj faj) {
        this.faj = faj;
    }

    public Gombatest(Gombafaj faj, boolean kezdo) {
        this.faj = faj;
        kezdő = kezdo;
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
        List<Tektonrész> marSzort = new ArrayList<>(); // itt taroljuk a tektonokat ahova mar lett szorva, mivel a szomszed returnnel kesobb olyant is visszaad amin mar jartunk

        int[] tektonCoords = f.getTektonrészKoordináta(t);
        Tektonrész[] kozeliSzomszedok = f.getTektonrészSzomszédok(tektonCoords[0], tektonCoords[1]);

        if (!t.entitásHozzáadás(new Spóra(faj, 5))) // sok ilyen csunya check van mert fogalmam sincs hogyan mashogy tudna ez false-t returnolni
        {
            return false;
        }
        marSzort.add(t);

        for (int i = 0; i < kozeliSzomszedok.length; i++)
        {
            if (!kozeliSzomszedok[i].entitásHozzáadás(new Spóra(faj, 2)))
            {
                return false;
            }
            marSzort.add(kozeliSzomszedok[i]);
        }

        if (fejlődött)
        {
            for (int i = 0; i < marSzort.size(); i++) // vegig megyunk azokon a tektonokon ahol mar szortunk (a kozepson is, de nyilvan nem fog ujra szorni arra)
            {
                tektonCoords = f.getTektonrészKoordináta(kozeliSzomszedok[i]);
                Tektonrész[] tavoliSzomszedok = f.getTektonrészSzomszédok(tektonCoords[0], tektonCoords[1]);

                for (int j = 0; j < tavoliSzomszedok.length; j++)
                {
                    if (!marSzort.contains(tavoliSzomszedok[j])) // csak akkor szorunk ha itt meg nem szortunk
                    {
                        if (!tavoliSzomszedok[j].entitásHozzáadás(new Spóra(faj, 1)))
                        {
                            return false;
                        }
                        marSzort.add(tavoliSzomszedok[j]);
                    }
                }
            }
        }

        return true;
    }
}
