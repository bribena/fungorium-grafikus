package Game;

import java.awt.event.KeyEvent;
import java.util.List;

import prototipus_9.Fungorium;
import prototipus_9.Gombafaj;
import prototipus_9.Gombafonal;
import prototipus_9.Gombatest;
import prototipus_9.Rovar;
import prototipus_9.Spóra;
import prototipus_9.Tektonrész;

public class GameController {
    private GamePanel panel;
    private Fungorium fungorium;
    private List<EntityView> views;

    private int jatekosIndex = 0;
    private int gombatestCount = 0;
    private int rovarCount = 0;
    private int korokSzama;

    public enum JatekFazis {
        GOMBA_HELYEZES,
        ROVAR_HELYEZES,
        KOROK,
        VEGE
    }

    private JatekFazis jatekFazis = JatekFazis.GOMBA_HELYEZES;

    public GameController(GamePanel panel, List<EntityView> views) {
        this.panel = panel;
        this.views = views;
        this.fungorium = new Fungorium();
    }

    public void handleClick(int x, int y) {
        Tektonrész resz = fungorium.getTektonrész(x, y);
        if (resz == null) return;

        switch (jatekFazis) {
        case GOMBA_HELYEZES:
            // Ellenőrizni, hogy van-e már gombatest a tektonrészen
            if (resz.vanGomba()) {
                // Ha már van gombatest, nem helyezhetünk rá újat
                return;
            }

            Gombatest g = new Gombatest(true); // kezdő gombatest
            Gombafaj faj = Gombafaj.Amanita; // Enum alapján beállítjuk a fajt

            // Gombafonalak és gombatest hozzáadása
            for (int i = 0; i < 4; i++) {
                Gombafonal fonal = new Gombafonal(faj); // új metódus Gombafonal osztályban
                fonal.gombatestHozzáadása(g);
                resz.entitásHozzáadás(fonal);

                int dx = 0, dy = 0;
                switch (i) {
                    case 0: dy = -1; break; // fel
                    case 1: dx = 1; break;  // jobb
                    case 2: dy = 1; break;  // le
                    case 3: dx = -1; break; // bal
                }

                resz.entitásHozzáadás(g); // Gombatest hozzáadása
                views.add(new GombafonalView(fonal, x, y, dx, dy));
            }

            // Gombatest hozzáadása a tektonrészhez
            resz.entitásHozzáadás(g);
            views.add(new GombatestView(g, fungorium, x, y));

            gombatestCount++;
            kovetkezoJatekos();

            if (gombatestCount >= 4) {
                jatekFazis = JatekFazis.ROVAR_HELYEZES;
                jatekosIndex = 0;
            }
            break;

        case ROVAR_HELYEZES:
            // Ellenőrizni, hogy a kiválasztott tektonrész egy gombatesthez tartozik-e
            if (!resz.vanGomba()) {
                // Ha nincs gombatest, nem helyezhetjük el a rovart
                return;
            }

            Rovar r = new Rovar(); // Példányosítsd megfelelően
            resz.entitásHozzáadás(r);
            views.add(new RovarView(r, x, y));
            rovarCount++;
            kovetkezoJatekos();
            if (rovarCount >= 4) {
                jatekFazis = JatekFazis.KOROK;
                jatekosIndex = 0;
            }
            break;
        
        case KOROK:
            // Kijelölés
            // Ha a játékos Gombász, akkor csak Gombatest vagy Gombafonal lehet kiválasztva
        	//TODO
            // Ha a játékos Rovarász, akkor csak Rovar lehet kiválasztva
        	//TODO
        	//Ha a körök száma elérte a 24-et, VEGE

            break;
       
        case VEGE:
            
            // gombatestszámok lekérdezése fajok alapján
        	//TODO
        	// tápanyagszámok lekérdezése fajok alapján
        	//TODO
        	//Legnagyobb gombatestszám és tápanyagszám lekérdezése
        	// győztesek beállítása
            break;
            
        default:
            break;
        }

        panel.repaint();
    }
    
    private boolean isJatekosGombafaj(Tektonrész resz) {
        // Ellenőrizni, hogy a kiválasztott entitás a játékos fajához tartozik
    	//TODO
    	return false;
    }

    private boolean isJatekosRovar(Tektonrész resz) {
        // Ellenőrizni, hogy a kiválasztott entitás a játékos rovárfajához tartozik
    	//TODO
    	return false;
    }

    private void kijelolEntitast(Tektonrész resz) {
        // Kijelölni az entitást, hogy a játékos ezt használhassa a következő lépéshez
    	//TODO
    }

    private void kovetkezoJatekos() {
        jatekosIndex = (jatekosIndex + 1) % 4;
    }
    
    public void következőKör() {
    	//kör léptetése
    	//TODO
    }

    public void számítsGyőztest() {
    	//győztesek keresése és beállítása
    	//TODO
    }
    
    public void gombatestSzamokLekérdezése() {
    	// gombatestek számának lekérdezése gombafajok alapján
    	//TODO
    }
    
    public void tapanyagSzamokLekérdezése() {
    	// tápanyagok számának lekérdezése rovarfajok alapján
    	//TODO
    }

    public void handleKey(int keyCode) {
        //TODO
    	//Irényitás (billentyűk) lekezelése, megfelelő függvények hívása
    }
}