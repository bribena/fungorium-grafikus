package fungorium.Models;

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
        for (int i = 0; i < width / 2; ++i) {
            for (int j = 0; j < height / 2; ++j) {
                tektonrészek[i][j] = new TöbbfonalasTektonrész();
                tektonrészek[i][j].setTektonID(0);

                tektonrészek[i + width / 2][j] = new TöbbfonalasTektonrész();
                tektonrészek[i + width / 2][j].setTektonID(1);

                tektonrészek[i][j + height / 2] = new TöbbfonalasTektonrész();
                tektonrészek[i][j + height / 2].setTektonID(2);

                tektonrészek[i + width / 2][j + height / 2] = new TöbbfonalasTektonrész();
                tektonrészek[i + width / 2][j + height / 2].setTektonID(3);
            }
        }

        // Szélek beállítása
        for (int i = 0; i < 20; ++i) {
            // Perem
            tektonrészek[i][0].getTektonSzéleE()[0] = true;
            tektonrészek[0][i].getTektonSzéleE()[3] = true;
            tektonrészek[i][height - 1].getTektonSzéleE()[2] = true;
            tektonrészek[width - 1][i].getTektonSzéleE()[1] = true;

            // Belső
            tektonrészek[i][height / 2 - 1].getTektonSzéleE()[2] = true;
            tektonrészek[i][height / 2].getTektonSzéleE()[0] = true;
            tektonrészek[width / 2 - 1][i].getTektonSzéleE()[1] = true;
            tektonrészek[width / 2][i].getTektonSzéleE()[3] = true;
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
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1) {
            return new TöbbfonalasTektonrész();
        }
        return tektonrészek[x][y];
    }

    public int[] getTektonrészKoordináta(Tektonrész t) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (tektonrészek[x][y] == t) {
                    return new int[] { x, y };
                }
            }
        }
        return new int[] { -1, -1 };
    }

    /**
     * Koordináta alapján visszaadja egy Tektonrész szomszédjait.
     * [0]: Felfelé,
     * [1]: Jobbra,
     * [2]: Lefelé,
     * [3]: Balra.
     */
    public Tektonrész[] getTektonrészSzomszédok(int x, int y) {
        Tektonrész[] ret = new Tektonrész[4];
        // [0]: Felfelé, [1]: Jobbra, [2]: Lefelé, [3]: Balra

        ret[0] = getTektonrész(x, y - 1); // Felfelé
        ret[1] = getTektonrész(x + 1, y); // Jobbra
        ret[2] = getTektonrész(x, y + 1); // Lefelé
        ret[3] = getTektonrész(x - 1, y); // Balra

        return ret;
    }
    public Tektonrész[] getTektonrészSzomszédok(Tektonrész tr) {
        int[] koor = getTektonrészKoordináta(tr);
        return getTektonrészSzomszédok(koor[0], koor[1]);
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
                    if (szomszédok[i] != null && tektonrészek[x][y] != null &&
                            szomszédok[i].getTektonID() != tektonrészek[x][y].getTektonID()) {
                        tektonrészek[x][y].getTektonSzéleE()[i] = true;
                    }

                    // Szakítás
                    if (régi != tektonrészek[x][y].getTektonSzéleE()[i]) {
                        for (Entitás e : tektonrészek[x][y].getEntitások()) {
                            if (e instanceof Gombafonal) {
                                Gombafonal gf = (Gombafonal) e;
                                gf.szakít(i);
                            }
                        }
                    }
                }
            }
        }

        // Mégegy korrigálás
        for (int x = 0; x < 20; ++x) {
            for (int y = 0; y < 20; ++y) {
                Tektonrész curr = tektonrészek[x][y];
                Tektonrész[] szomsz = getTektonrészSzomszédok(x, y);

                boolean found = false;

                for (int i = 0; i < 4 && !found; ++i) {
                    if (curr != null && szomsz[i] != null && szomsz[i].getTektonID() == curr.getTektonID()) {
                        found = true;
                    }
                }

                if (!found) {
                    curr.setTektonID(++maxTektonID);
                }
            }
        }
    }
    // Még mindig elég :skull: ez a sok kapcsos zárójel, de már nem érdekel...

    /**
     * Tektonrész randomizáló. Nagyon csúnya lesz, van egy olyan sejtésem...
     */
    private void tektonrészRandomizálás() {
        // Class<? extends Tektonrész>[] ujHatasok = new Class<? extends
        // Tektonrész>[maxTektonID];
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
        return ((q.y - p.y) * a.x - (q.x - p.x) * a.y + q.x * p.y - q.y * p.x)
                /
                Math.sqrt((q.y - p.y) * (q.y - p.y) + (q.x - p.x) * (q.x - p.x));
    }

    /**
     * Végrehajtja a törést.
     */
    private void törés() {
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

    /**
     * [0][0]: Az átvett tektonrész
     * [1][i]: Környező 8
     * [2][i]: A környező körüli 16
     */
    public Tektonrész[][] getSpóraTektonrészSzomszédok(int x, int y) {
        Tektonrész[][] ret = new Tektonrész[3][];
        ret[0] = new Tektonrész[] { tektonrészek[x][y] };
        ret[1] = new Tektonrész[8];
        ret[2] = new Tektonrész[16];

        int i = 0;
        for (int dx = -1; dx <= 1; ++dx) {
            ret[1][i++] = getTektonrész(x + dx, y - 1);
            ret[1][i++] = getTektonrész(x + dx, y + 1);
        }
        ret[1][i++] = getTektonrész(x - 1, y);
        ret[1][i++] = getTektonrész(x + 1, y);

        i = 0;
        for (int dx = -2; dx <= 2; ++dx) {
            ret[2][i++] = getTektonrész(x + dx, y - 2);
            ret[2][i++] = getTektonrész(x + dx, y + 2);
        }
        for (int dy = -1; dy <= 1; ++dy) {
            ret[2][i++] = getTektonrész(x - 2, y + dy);
            ret[2][i++] = getTektonrész(x + 2, y + dy);
        }
        for (int k = 1; k < ret.length; ++k) {
            for (int j = 0; j < ret[k].length; ++j) {
                if (ret[k][j].getTektonID() != ret[0][0].getTektonID()) {
                    ret[k][j] = new TöbbfonalasTektonrész();
                }
            }
        }

        return ret;
    }
    public Tektonrész[][] getSpóraTektonrészSzomszédok(Tektonrész tr) {
        int[] koor = getTektonrészKoordináta(tr);
        return getSpóraTektonrészSzomszédok(koor[0], koor[1]);
    }

    /** Visszaadja, hogy milyen tektonokkal szomszédos egy adott tekton, ID-k alapján */
    public Set<Integer> getElsőfokúTektonSzomszédosságok(int tektonID) {
        HashSet<Integer> ret = new HashSet<>();
        ret.add(tektonID);
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (tektonrészek[x][y].getTektonID() == tektonID) {
                    for (Tektonrész tr : getTektonrészSzomszédok(tektonrészek[x][y])) {
                        if (tr.getTektonID() != tektonID) {
                            ret.add(tr.getTektonID());
                        }
                    }
                }
            }
        }
        ret.remove(-1);
        return ret;
    }
    public Set<Integer> getMásodfokúTektonSzomszédosságok(int tektonID) {
        Set<Integer> elsőfokú = getElsőfokúTektonSzomszédosságok(tektonID);
        Set<Integer> ret = new HashSet<>();
        ret.addAll(elsőfokú);
        for (Integer i : elsőfokú) {
            ret.addAll(getElsőfokúTektonSzomszédosságok(i));
        }
        return ret;
    }

    public List<Spóra> getTektonSpóraszám(int tektonID, Gombafaj faj) {
        List<Spóra> ret = new ArrayList<>();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (tektonrészek[x][y].getTektonID() == tektonID) {
                    for (Entitás e : tektonrészek[x][y].getEntitások()) {
                        if (e instanceof Spóra && ((Spóra)e).getFaj() == faj && ((Spóra)e).érvényesE()) {
                            ret.add((Spóra)e);
                        }
                    }
                }
            }
        }
        return ret;
    }
    public void lassúRovarokMozgathatása() {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (Entitás e : tektonrészek[x][y].getEntitások()) {
                    if (e instanceof Rovar) {
                        Rovar r = (Rovar)e;
                        r.mozgatottFelülírás(!r.lassú());
                    }
                }
            }
        }
    }
    public boolean vanMozgathatóEntitás(Rovarfaj faj) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (Entitás e : tektonrészek[x][y].getEntitások()) {
                    if (e instanceof Rovar) {
                        Rovar r = (Rovar)e;
                        if (r.getFaj() == faj && !r.mozgatott()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean vanMozgathatóEntitás(Gombafaj faj) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (Entitás e : tektonrészek[x][y].getEntitások()) {
                    if (e instanceof Gombatest) {
                        Gombatest t = (Gombatest)e;
                        if (t.getFaj() == faj && !t.mozgatott()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
