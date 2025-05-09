package fungorium.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/** A Fungorium bolygó, avagy a pálya osztálya. */
public class Fungorium {
    /** Random szám generátor */
    private static Random r = new Random();

    /**
     * Ebben vannak a tektonrészek, x, y a koordináták sorrendje. For ciklusnál
     * figyelj oda!!!
     */
    private Tektonrész[][] tektonrészek = new Tektonrész[20][20];

    // /** A létező tektonID-ket tartalmazza. */
    // private List<Integer> tektonIDk = Arrays.asList(0, 1, 2, 3);

    /** A legnagyobb tektonID. Új megadásakor növelni kell. */
    private int maxTektonID = 3;

    /** A törés esélyét tartja nyilván. */
    private double törésEsély = (double) 1 / 16;

    /** Default konstruktor */
    public Fungorium() {
        // Nem akartam több ciklusra bontani, sry
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                tektonrészek[i][j] = new TöbbfonalasTektonrész();
                // tektonrészek[i][j].setTektonID(tektonIDk.get(0));
                tektonrészek[i][j].setTektonID(0);

                tektonrészek[i][j + 10] = new TöbbfonalasTektonrész();
                // tektonrészek[i][j + 10].setTektonID(tektonIDk.get(1));
                tektonrészek[i][j + 10].setTektonID(1);

                tektonrészek[i + 10][j] = new TöbbfonalasTektonrész();
                // tektonrészek[i + 10][j].setTektonID(tektonIDk.get(2));
                tektonrészek[i + 10][j].setTektonID(2);

                tektonrészek[i + 10][j + 10] = new TöbbfonalasTektonrész();
                // tektonrészek[i + 10][j + 10].setTektonID(tektonIDk.get(3));
                tektonrészek[i + 10][j + 10].setTektonID(3);
            }
        }

        // Szélek beállítása
        for (int i = 0; i < 20; ++i) {
            // Perem
            tektonrészek[i][0].getTektonSzéleE()[0] = true;
            tektonrészek[0][i].getTektonSzéleE()[3] = true;
            tektonrészek[i][19].getTektonSzéleE()[2] = true;
            tektonrészek[19][i].getTektonSzéleE()[1] = true;

            // Belső
            tektonrészek[i][9].getTektonSzéleE()[2] = true;
            tektonrészek[i][10].getTektonSzéleE()[0] = true;
            tektonrészek[9][i].getTektonSzéleE()[1] = true;
            tektonrészek[10][i].getTektonSzéleE()[3] = true;
        }
    }

    /**
     * Visszaadja az x, y koordinátákkal megadott Tektonrészt.
     * 
     * @param x x koordináta
     * @param y y koordináta
     * @return Egy Tektonrész
     */
    public Tektonrész getTektonrész(int x, int y) {
        return tektonrészek[x][y];
    }

    public int[] getTektonrészKoordináta(Tektonrész t) {
        for (int x = 0; x < 20; ++x) {
            for (int y = 0; y < 20; ++y) {
                if (tektonrészek[x][y] == t) {
                    return new int[] { x, y };
                }
            }
        }
        return new int[] { -1, -1 };
    }

    /**
     * x, y koordináta összefogása a töréshez.
     */
    private class Point {
        public double x = 0, y = 0;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * A fekete mágia lényege, hogy két random pontot kapunk, amit kitolunk [0;
     * 20]^2-ről, majd slerp-pel végigmegyünk rajta
     * és szűrűnk a megfelelő pontokra. Azoknak a pontoknak a koordinátái lesznek
     * majd az egyenesen levő tektonrészek indexei.
     */
    private List<Point> indexekMeghatározása() {
        // x1, x2, y1, y2
        int[] v = new int[4];
        for (int i = 0; i < 4; ++i) {
            v[i] = r.nextInt(20);
        }
        while (v[0] == v[1] && v[1] == v[2] && v[2] == v[3]) {
            v[3] = r.nextInt(20);
        }

        Point a = new Point(v[0], v[1]);
        Point b = new Point(v[2], v[3]);

        Point vba = new Point(v[0] - v[2], v[1] - v[3]);

        while (a.x >= -5 && a.x <= 25 && a.y >= -5 && a.y <= 25) {
            a.x -= vba.x;
            a.y -= vba.y;
        }
        while (b.x >= -5 && b.x <= 25 && b.y >= -5 && b.y <= 25) {
            b.x += vba.x;
            b.y += vba.y;
        }

        // points-ban vannak az indexek
        List<Point> points = new ArrayList<>();
        for (int i = 0; i <= 1000; ++i) {
            double t = (double) i / 1000;
            double x = (1 - t) * a.x + t * b.x;
            double y = (1 - t) * a.y + t * b.y;
            Point p = new Point(x, y);

            if (p.x <= -1 || p.y <= -1 || p.x > 19 || p.y > 19) {
                continue;
            }
            Point rounded = new Point(Math.ceil(p.x), Math.ceil(p.y));
            boolean found = false;
            for (int j = 0; j < points.size(); ++j) {
                if (points.get(j).x == rounded.x && points.get(j).y == rounded.y) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                points.add(rounded);
            }
        }
        return points;
    }

    /**
     * Törés utáni fonalszakítás
     * Elvesztettem a fonalat (hahahahaha), nem tudom, hogy működni fog-e...
     */
    private void fonalSzakítás() {
        for (int x = 0; x < 20; ++x) {
            for (int y = 0; y < 20; ++y) {
                Tektonrész current = tektonrészek[x][y];
                List<Gombafonal> currFonalak = current.getGombafonalak();
                if (currFonalak.isEmpty()) {
                    continue;
                }

                Tektonrész[] szomszédok = getTektonrészSzomszédok(x, y);
                for (Gombafonal f : currFonalak) {
                    for (int i = 0; i < szomszédok.length; ++i) {
                        Tektonrész szomszéd = szomszédok[i];
                        List<Gombafonal> szomszFonal = szomszéd.getGombafonalak();
                        if (szomszFonal.isEmpty() || szomszéd.getTektonID() == current.getTektonID()) {
                            continue;
                        }

                        for (Gombafonal szF : szomszFonal) {
                            if (f.getKapcsolodoFonalak()[i] == szF) {
                                f.getKapcsolodoFonalak()[i] = null;
                                for (int j = 0; i < 4; ++i) {
                                    if (szF.getKapcsolodoFonalak()[j] == f) {
                                        szF.getKapcsolodoFonalak()[j] = null;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    // 9 kapcsoszárójel azért az ingerküszöbömet erősen kínozza

    /**
     * Koordináta alapján visszaadja egy Tektonrész szomszédjait.
     * [0]: Felfelé,
     * [1]: Jobbra,
     * [2]: Lefelé,
     * [3]: Balra.
     */
    private Tektonrész[] getTektonrészSzomszédok(int x, int y) {
        Tektonrész[] ret = new Tektonrész[4];
        for (int i = 0; i < 4; ++i) {
            ret[i] = new TöbbfonalasTektonrész();
        }

        if (x > 0) {
            ret[3] = tektonrészek[x - 1][y];
        }
        if (y > 0) {
            ret[0] = tektonrészek[x][y - 1];
        }
        if (x < 19) {
            ret[1] = tektonrészek[x + 1][y];
        }
        if (y < 19) {
            ret[2] = tektonrészek[x][y + 1];
        }

        return ret;
    }

    /**
     * Törés utáni korrigálás a szélekre
     */
    private void szélKorrigálás() {
        for (int x = 0; x < 20; ++x) {
            for (int y = 0; y < 20; ++y) {
                Tektonrész[] szomszédok = getTektonrészSzomszédok(x, y);

                for (int i = 0; i < szomszédok.length; ++i) {
                    if (szomszédok[i].getTektonID() != tektonrészek[x][y].getTektonID()) {
                        tektonrészek[x][y].getTektonSzéleE()[i] = true;
                    }
                }
            }
        }
    }

    /**
     * Tektonrész randomizáló. Nagyon csúnya lesz, van egy olyan sejtésem...
     */
    private void tektonrészRandomizálás() {
        // Class<? extends Tektonrész>[] ujHatasok = new Class<? extends Tektonrész>[maxTektonID];
        List<Class<? extends Tektonrész>> ujHatasok = new ArrayList<>(maxTektonID);
        for (int i = 0; i <= maxTektonID; ++i) {
            int n = r.nextInt(5);
            switch (n) {
                case 0:
                    ujHatasok.add(TöbbfonalasTektonrész.class);
                    break;
                case 1:
                    ujHatasok.add(ÉletbentartóTektonrész.class);
                    break;
                case 2:
                    ujHatasok.add(EgyfonalasTektonrész.class);
                    break;
                case 3:
                    ujHatasok.add(FonalfelszívóTektonrész.class);
                    break;
                case 4:
                    ujHatasok.add(GombatestTiltóTektonrész.class);
                    break;
            }
        }
        for (int x = 0; x < 20; ++x) {
            for (int y = 0; y < 20; ++y) {
                try {
                    Tektonrész tr = ujHatasok.get(tektonrészek[x][y].getTektonID())
                        .getConstructor(Tektonrész.class)
                        .newInstance(tektonrészek[x][y]);
                    tektonrészek[x][y] = tr;
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Végrehajtja a törést.
     */
    public void törés() {
        List<Point> points = indexekMeghatározása();

        /** Tektonrészek szétválogatása... */
        Point kezdo = points.get(0);
        Point veg = points.get(points.size() - 1);

        Map<Integer, Integer> idMap = new HashMap<>();

        // Függőleges
        if (kezdo.x == veg.x) {
            for (int y = 0; y < 20; ++y) {
                for (int x = (int)kezdo.x + 1; x < 20; ++x) {
                    Tektonrész curr = tektonrészek[x][y];
                    if (!idMap.containsKey(curr.getTektonID())) {
                        idMap.put(curr.getTektonID(), ++maxTektonID);
                    }
                    curr.setTektonID(idMap.get(curr.getTektonID()));
                }
            }
        }
        // Vízszintes
        else if (kezdo.y == veg.y) {
            for (int x = 0; x < 20; ++x) {
                for (int y = (int)kezdo.y + 1; y < 20; ++y) {
                    Tektonrész curr = tektonrészek[x][y];
                    if (!idMap.containsKey(curr.getTektonID())) {
                        idMap.put(curr.getTektonID(), ++maxTektonID);
                    }
                    curr.setTektonID(idMap.get(curr.getTektonID()));
                }
            }
        }
        // Átlósan valahogy
        else {
            int maxX = (int)points.get(0).x;
            int currY = (int)points.get(0).y;
            for (Point p : points) {
                if (currY != (int)p.y) {
                    for (int x = maxX + 1; x < 20; ++x) {
                        Tektonrész curr = tektonrészek[x][currY];
                        // System.out.print(x + ", " + currY + " changed from " + curr.getTektonID() + " to ");
                        if (!idMap.containsKey(curr.getTektonID())) {
                            idMap.put(curr.getTektonID(), ++maxTektonID);
                        }
                        curr.setTektonID(idMap.get(curr.getTektonID()));
                    }

                    currY = (int)p.y;
                    maxX = (int)p.x;
                }
                else if (maxX < (int)p.x) {
                    maxX = (int)p.x;
                }
            }
            for (int x = maxX + 1; x < 20; ++x) {
                Tektonrész curr = tektonrészek[x][currY];
                // System.out.print(x + ", " + currY + " changed from " + curr.getTektonID() + " to ");
                if (!idMap.containsKey(curr.getTektonID())) {
                    idMap.put(curr.getTektonID(), ++maxTektonID);
                }
                curr.setTektonID(idMap.get(curr.getTektonID()));
                // System.out.println(curr.getTektonID() + " via Point " + maxX + ", " + currY);
            }
        }
        
        szélKorrigálás();

        /** GOMBAFONAL SZAKÍTÁS */
        fonalSzakítás();

        /** TEKTONRÉSZ EFFEKT RANDOMIZÁLÁS */
        tektonrészRandomizálás();
    }

    /**
     * Kör léptetéséhez hívható. Az aktuális kör alapján változik a törés sorsolása.
     * 
     * @param kör Új kör száma
     */
    public void körtLéptet(int kör) {
        // Entitások frissítésének átadása
        for (int i = 0; i < 20; ++i) {
            for (int j = 0; j < 20; ++j) {
                tektonrészek[j][i].frissítés();
            }
        }

        // Törés
        int random = r.nextInt((int) (1 / törésEsély));
        if (random == 0) {
            törés();
            if (kör < 16) {
                törésEsély = (double) 1 / 16;
            }
        } else if (random != 0 && kör < 16) {
            törésEsély *= 2;
        }
        if (kör == 16) {
            törésEsély = (double) 1 / 4;
        }
    }
}
