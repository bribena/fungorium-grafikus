package fungorium.ReModels;

import java.util.ArrayList;
import java.util.List;

public class Gombafonal implements Entitás {
    private Gombafonal[] kapcsolódóFonalak = new Gombafonal[4];
    private List<Gombatest> gombaTestek = new ArrayList<>();
    private Gombafaj faj;
    private int szakadt = 0;
    private boolean specFrissítés = false;

    public Gombafonal(Gombafaj faj) {
        this.faj = faj;
    }

    public void gombafonalÖsszekapcsolás(Gombafonal szomszéd, int irány) {
        kapcsolódóFonalak[irány] = szomszéd;
        szomszéd.kapcsolódóFonalak[(irány + 2) % 4] = this;
    }

    public Gombafaj getFaj() {
        return faj;
    }

    @Override
    public boolean érvényesE() {
        return true;
    }

    public void specBeállítás(boolean spec) {
        specFrissítés = spec;
    }

    public boolean kapcsolódikGombatesthez()
    {
        return gombaTestek.size() > 0;
    }

    public boolean addTest(Gombatest test)
    {
        if (!gombaTestek.contains(test))
        {
             gombaTestek.add(test);
             return true;
        }
        else
        {
            return false;
        }
    }

    public boolean removeTest(Gombatest test)
    {
        if (gombaTestek.contains(test))
        {
             gombaTestek.remove(test);
             return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean frissítés() {
        if (!kapcsolódikGombatesthez())
        {
            szakadt += 1;
            if (szakadt > 2)
            {
                return true;
            }
        }
        else
        {
            szakadt = 0;
        }

        return false;
    }

    @Override
    public boolean speciálisFrissítés() {
        /** TODO */
        return false;
    }

    public void szakad(int irany)
    {
        Gombafonal szakitando = kapcsolódóFonalak[irany];

        if (szakitando != null)
        {
            kapcsolódóFonalak[irany] = null;

            irany -= 2;
            if (irany < 0)
            {
                irany += 4;
            }

            szakitando.szakad(irany);
        }
    }

    public Gombafonal gombafonalatNöveszt(Tektonrész honnan, Tektonrész hova, Fungorium fungorium)
    {
        if (!kapcsolódikGombatesthez())
        {
            return null;
        }

        Gombafonal fonal = new Gombafonal(faj);

        for (int i = 0; i < gombaTestek.size(); i++)
        {
            fonal.gombaTestek.add(gombaTestek.get(i)); // ugyanazokhoz a gombatestekhez fog kapcsolodni az uj fonal is
        }

        if (hova.entitásHozzáadás(fonal)) // a tektonresz eldonti, hogy lehetseges-e a novesztes
        {
            int[] coords = fungorium.getTektonrészKoordináta(hova);
            int x = coords[0];
            int y = coords[1];

            int[][] szomszedokCoords = {{x + 1, y}, {x - 1, y}, {x, y + 1}, {x, y - 1}};

            for (int i = 0; i < szomszedokCoords.length; i++)
            {
                int szomszedX = szomszedokCoords[i][0];
                int szomszedY = szomszedokCoords[i][1];
                Tektonrész tekton = fungorium.getTektonrész(szomszedX, szomszedY);
                if (tekton != null)
                {
                    Gombafonal kapcsolodoFonal = tekton.getKapcsolodoFonal(faj);
                    if (kapcsolodoFonal != null)
                    {
                        if (szomszedX - x == 0)
                        {
                            if(szomszedY - y == 1)
                            {
                                fonal.setKapcsolodoFonal(2, kapcsolodoFonal);
                                kapcsolodoFonal.setKapcsolodoFonal(0, fonal);
                            }
                            else if(szomszedY - y == -1)
                            {
                                fonal.setKapcsolodoFonal(0, kapcsolodoFonal);
                                kapcsolodoFonal.setKapcsolodoFonal(2, fonal);
                            }
                        }
                        else if(szomszedY - y == 0)
                        {
                            if (szomszedX - x == 1)
                            {
                                fonal.setKapcsolodoFonal(1, kapcsolodoFonal);
                                kapcsolodoFonal.setKapcsolodoFonal(3, fonal);
                            }
                            else if (szomszedX - x == -1)
                            {
                                fonal.setKapcsolodoFonal(3, kapcsolodoFonal);
                                kapcsolodoFonal.setKapcsolodoFonal(1, fonal);
                            }
                        }
                    }
                }
            }

            return fonal;
        }
        else
        {
            return null;
        }
    }

    public boolean gombatestetNöveszt(Tektonrész aholVan, Fungorium fungorium) {
        if (!aholVan.vanSpóra()) {
            return false;
        }

        List<Entitás> entitások = aholVan.getEntitások();

        for (int i = 0; i < entitások.size(); i++) {
            if (entitások.get(i) instanceof Spóra) {
                Spóra spóra = (Spóra) entitások.get(i);
                if (spóra.getFaj() == faj && spóra.getSpóraSzám() > 5) {
                    Gombatest test = new Gombatest();
                    if (aholVan.entitásHozzáadás(test)) {
                        gombaTestek.add(test);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public void setKapcsolodoFonal(int irany, Gombafonal fonal)
    {
        if (irany < 0 || irany > 3)
        {
            return;
        }

        kapcsolódóFonalak[irany] = fonal;
    }

    public Gombafonal[] getKapcsolódóFonalak() {
        return kapcsolódóFonalak;
    }
}
