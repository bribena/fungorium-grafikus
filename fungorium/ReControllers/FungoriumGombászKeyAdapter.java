package fungorium.ReControllers;

import fungorium.ReModels.*;
import fungorium.ReViews.*;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class FungoriumGombászKeyAdapter extends KeyAdapter {
    private GameController controller;
    private GameStateManager gameState;
    private FungoriumView fungoriumView;

    public FungoriumGombászKeyAdapter(GameController controller, GameStateManager gameState) {
        this.controller = controller;
        this.gameState = gameState;
    }

    private void gombatestNövesztés() {
        // kijelölt tektonrész lekérdezése + ha nincs kijelölve semmi, return
        TektonrészView selectedView = controller.getSelectedTektonrészView();
        if (selectedView == null) return;

        // ha a tekton hatása GombatestTiltó, nem kerülhet rá gommbatest
        Tektonrész hova = selectedView.getTektonrész();
        if (hova.vanGomba() || hova instanceof GombatestTiltóTektonrész) return;

        // Modell: Gombatest létrehozása
        Gombász player = (Gombász) controller.getPlayerManager().getAktuálisJátékos();
        Gombafaj faj = player.getKezeltFaj();
        // tesztelésre mindig igaz
        boolean kezdo = true;
        Gombatest test = new Gombatest(faj, kezdo);
        hova.entitásHozzáadás(test);

        // View: GombatestView hozzáadása
        GombatestView testView = new GombatestView(hova, test);
        selectedView.add(testView);
        selectedView.revalidate();
        selectedView.repaint();

        // Ha kezdő gombatest, Gombafonal is jár mellé, valamint a szomszédokba is
        if (kezdo) {
            Gombafonal fonal = new Gombafonal(faj);
            fonal.addTest(test);
            hova.entitásHozzáadás(fonal);
            selectedView.add(new GombafonalView(hova, fonal));

            Fungorium fungorium = controller.getPlayerManager().getFungorium();
            int x = selectedView.x;
            int y = selectedView.y;

            for (int i = 0; i < 4; ++i) {
                int dx = (i == 1) ? 1 : (i == 3) ? -1 : 0;
                int dy = (i == 0) ? -1 : (i == 2) ? 1 : 0;

                int nx = x + dx;
                int ny = y + dy;

                if (nx < 0 || ny < 0 || nx >= fungorium.getWidth() || ny >= fungorium.getHeight())
                    continue;

                Tektonrész szomszed = fungorium.getTektonrész(nx, ny);
                szomszed.entitásHozzáadás(fonal);

                for (Component c : controller.getFungoriumView().getComponents()) {
                    if (c instanceof TektonrészView tv && tv.x == nx && tv.y == ny) {
                        tv.add(new GombafonalView(szomszed, fonal));
                        tv.revalidate();
                        tv.repaint();
                        break;
                    }
                }
            }
        }

        // Kör vége
        controller.getPlayerManager().következőJátékos();
        controller.getGamePanel().updateStatusLabel();
    }

    private void gombatestFejlesztés() {
        // kiejölt tektonrész lekérése + ha nincs kijelölve semmi, return
        TektonrészView selectedView = controller.getSelectedTektonrészView();
        if (selectedView == null) return;

        // tektonrész, ahol fejleszteni akarunk testet
        Tektonrész hova = selectedView.getTektonrész();

        // Megkeressük a gombatestet a tektonrészen
        Gombatest targetTest = null;
        for (Entitás e : hova.getEntitások()) {
            if (e instanceof Gombatest gt) {
                targetTest = gt;
                break;
            }
        }

        // ha nem találunk gombatestet, akkor nincs mit fejleszteni
        if (targetTest == null) {
            System.out.println("Nincs gombatest az adott helyen.");
            return;
        }

        // Ellenőrizzük, hogy a gombatest a soron következő játékosé-e
        Gombász player = (Gombász) controller.getPlayerManager().getAktuálisJátékos();
        // És csak akkor engedjük fejleszteni, ha a saját gombatestjét próbálja fejleszteni
        if (targetTest.getFaj() != player.getKezeltFaj()) {
            System.out.println("Nem a saját gombatestet próbálod fejleszteni.");
            return;
        }

        // Ha már fejlesztett, nem csinál semmit
        if (targetTest.isFejlődött()) {
            System.out.println("A gombatest már fejlesztett.");
            return;
        }

        // Gombatest fejlesztése
        targetTest.setFejlodott(true);
        System.out.println("A gombatest fejlesztve.");

        // Nézet frissítése
        selectedView.repaint();

        // Kör vége
        controller.getPlayerManager().következőJátékos();
        controller.getGamePanel().updateStatusLabel();
    }

    // TODO NEM JÓ, mert szarul rajzol ki, tezstelésnél nézd meg
    private void gombafonalNövesztés(int irány) {
        // 0: fel, 1: jobb, 2: le, 3: bal
        TektonrészView selectedView = controller.getSelectedTektonrészView();
        if (selectedView == null) return;

        int x = selectedView.x;
        int y = selectedView.y;

        Fungorium fungorium = controller.getPlayerManager().getFungorium();

        // Szomszéd koordinátái
        int nx = x;
        int ny = y;

        switch (irány) {
            case 0: ny = y - 1; break;  // fel
            case 1: nx = x + 1; break;  // jobb
            case 2: ny = y + 1; break;  // le
            case 3: nx = x - 1; break;  // bal
        }

        Tektonrész hova = selectedView.getTektonrész();
        Tektonrész szomszed = fungorium.getTektonrész(nx, ny);
        if (szomszed == null) {
            System.out.println("Nincs szomszédos tektonrész az adott irányban.");
            return;
        }

        // Megkeressük a gombafonalat a jelenlegi tektonrészen
        Gombafonal fonal = null;
        for (Entitás e : hova.getEntitások()) {
            if (e instanceof Gombafonal gf) {
                fonal = gf;
                break;
            }
        }

        if (fonal == null) {
            System.out.println("Nincs gombafonal a kijelölt helyen.");
            return;
        }

        // Megpróbáljuk növeszteni a gombafonalat a szomszédos tektonrészre
        boolean siker = fonal.gombafonalatNöveszt(hova, szomszed, fungorium);

        if (!siker) {
            System.out.println("Nem sikerült növeszteni a gombafonalat.");
            return;
        }

        // View frissítése a szomszéd tektonrészen
        for (Component c : controller.getFungoriumView().getComponents()) {
            if (c instanceof TektonrészView tv && tv.x == nx && tv.y == ny) {
                tv.add(new GombafonalView(szomszed, fonal));
                tv.revalidate();
                tv.repaint();
                break;
            }
        }

        // Frissítjük az aktuális view-t is, hogy megjelenjen az új kapcsolat
        selectedView.repaint();

        // Kör vége
        controller.getPlayerManager().következőJátékos();
        controller.getGamePanel().updateStatusLabel();
    }

    private void spóraSzórás() {

    }



    @Override
    public void keyReleased(KeyEvent e) {
        // ha nincs kijelölt tektonrész vagy nem gombász a játékos, return
        if (controller.getSelectedTektonrészView() == null || !(controller.getPlayerManager().getAktuálisJátékos() instanceof Gombász)) {
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_N:
                // N billentyűre gombatestet növeszt
                System.out.println("N lenyomva");
                gombatestNövesztés();
                break;
            case KeyEvent.VK_F:
                // F billentyűre gombatestet fejleszt
                System.out.println("F lenyomva");
                gombatestFejlesztés();
                break;
            case KeyEvent.VK_S:
                // S billentyűre spórát szór
                spóraSzórás();
                break;
            // nyíl billentyűknek megfelelően gombafonal növesztés
            case KeyEvent.VK_UP:
                System.out.println("up");
                gombafonalNövesztés(0);
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("down");
                gombafonalNövesztés(1);
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("right");
                gombafonalNövesztés(2);
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("left");
                gombafonalNövesztés(3);
                break;
        }
    }
}
