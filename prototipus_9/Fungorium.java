package prototipus_9;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** A Fungorium bolygó, avagy a pálya osztálya. */
public class Fungorium {
    /** Random szám generátor */
    private static Random r = new Random();

    /** Ebben vannak a tektonrészek, x, y a koordináták sorrendje. For ciklusnál figyelj oda!!! */
    private Tektonrész[][] tektonrészek = new Tektonrész[20][20];

    // /** A létező tektonID-ket tartalmazza. */
    // private List<Integer> tektonIDk = Arrays.asList(0, 1, 2, 3);

    /** A legnagyobb tektonID. Új megadásakor növelni kell. */
    private int maxTektonID = 3;

    /** A törés esélyét tartja nyilván. */
    private double törésEsély = (double)1 / 16;

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
    }
    
    /**
     * Visszaadja az x, y koordinátákkal megadott Tektonrészt.
     * @param x x koordináta
     * @param y y koordináta
     * @return Egy Tektonrész
     */
    public Tektonrész getTektonrész(int x, int y) {
        return tektonrészek[x][y];
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
     * A fekete mágia lényege, hogy két random pontot kapunk, amit kitolunk [0; 20]^2-ről, majd slerp-pel végigmegyünk rajta
     * és szűrűnk a megfelelő pontokra. Azoknak a pontoknak a koordinátái lesznek majd az egyenesen levő tektonrészek indexei.
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
            double t = (double)i / 1000;
            double x = (1 - t) * a.x + t * b.x;
            double y = (1 - t) * a.y + t * b.y;
            Point p = new Point(x, y);

            if (p.x < -1 || p.y < -1 || p.x > 19 || p.y > 19) {
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
     * Végrehajtja a törést.
     */
    private void törés() {
        List<Point> points = indexekMeghatározása();

        /** TODO: Tektonrészek szétválogatása... */
        
        /** TODO: GOMBAFONAL SZAKÍTÁS */
    }


    /**
     * Kör léptetéséhez hívható. Az aktuális kör alapján változik a törés sorsolása.
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
        int random = r.nextInt((int)(1 / törésEsély));
        if (random == 0) {
            törés();
            if (kör < 16) {
                törésEsély = (double)1 / 16;
            }
        }
        else if (random != 0 && kör < 16) {
            törésEsély *= 2;
        }
        if (kör == 16) {
            törésEsély = (double)1 / 4;
        }
    }
}
