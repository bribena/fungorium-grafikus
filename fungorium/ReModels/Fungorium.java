package fungorium.ReModels;

import java.util.*;

public class Fungorium {
    /** Random szám generátor */
    private static Random r = new Random();

    /** Ne változtasd meg!!! */
    private final int width = 20;
    public int getWidth() {
        return width;
    }
    private final int height = 20;
    public int getHeight() {
        return height;
    }

    /**
     * Ebben vannak a tektonrészek, x, y a koordináták sorrendje. For ciklusnál
     * figyelj oda!!!
     */
    private Tektonrész[][] tektonrészek = new Tektonrész[width][height];

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

                tektonrészek[i + 10][j] = new TöbbfonalasTektonrész();
                // tektonrészek[i + 10][j].setTektonID(tektonIDk.get(2));
                tektonrészek[i + 10][j].setTektonID(1);

                tektonrészek[i][j + 10] = new TöbbfonalasTektonrész();
                // tektonrészek[i][j + 10].setTektonID(tektonIDk.get(1));
                tektonrészek[i][j + 10].setTektonID(2);

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

    private double d(Point p, Point q, Point a) {
        return 
            ((q.y - p.y) * a.x - (q.x - p.x) * a.y + q.x * p.y - q.y * p.x) 
            / 
            Math.sqrt((q.y - p.y) * (q.y - p.y) + (q.x - p.x) * (q.x - p.x));
    }

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
    private void szélKorrigálásÉsSzakítás() {
        for (int x = 0; x < 20; ++x) {
            for (int y = 0; y < 20; ++y) {
                Tektonrész[] szomszédok = getTektonrészSzomszédok(x, y);

                for (int i = 0; i < szomszédok.length; ++i) {
                    boolean régi = tektonrészek[x][y].getTektonSzéleE()[i];

                    // Korrigálás
                    if (szomszédok[i].getTektonID() != tektonrészek[x][y].getTektonID()) {
                        tektonrészek[x][y].getTektonSzéleE()[i] = true;
                    }

                    // Szakítás
                    if (régi != tektonrészek[x][y].getTektonSzéleE()[i]) {
                        for (Entitás e : tektonrészek[x][y].getEntitások()) {
                            if (e instanceof Gombafonal) {
                                Gombafonal gf = (Gombafonal)e;
                                gf.getSzomszédosFonalak()[i] = null;
                            }
                        }
                    }
                }
            }
        }
    }
    // Még mindig elég :skull: ez a sok kapcsos zárójel, de már nem érdekel...

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
        Map<Integer, Integer> idMap = new HashMap<>();

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                double d = d(a, b, new Point(x, y));
                if (d < 0) {
                    Tektonrész tr = tektonrészek[x][y];
                    if (!idMap.containsKey(tr.getTektonID())) {
                        idMap.put(tr.getTektonID(), ++maxTektonID);
                    }
                    tr.setTektonID(idMap.get(tr.getTektonID()));
                }
            }
        }
        
        szélKorrigálásÉsSzakítás();

        /** GOMBAFONAL SZAKÍTÁS */
        // fonalSzakítás();

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
        // for (int i = 0; i < 20; ++i) {
        //     for (int j = 0; j < 20; ++j) {
        //         tektonrészek[j][i].frissítés();
        //     }
        // }

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
