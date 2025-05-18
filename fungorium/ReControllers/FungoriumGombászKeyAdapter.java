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
        TektonrészView selectedView = controller.getSelectedTektonrészView();
        if (selectedView == null) return;

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
        //gameState.kovetkezoKor();
        controller.getGamePanel().updateStatusLabel();
    }

    private void gombatestFejlesztés() {

    }

    private void spóraSzórás() {

    }



    @Override
    public void keyReleased(KeyEvent e) {
        if (controller.getSelectedTektonrészView() == null || !(controller.getPlayerManager().getAktuálisJátékos() instanceof Gombász)) {
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_N:
                System.out.println("N lenyomva");
                gombatestNövesztés();
                break;
            case KeyEvent.VK_F:
                gombatestFejlesztés();
                break;
            case KeyEvent.VK_S:
                spóraSzórás();
                break;
            case KeyEvent.VK_UP:
                System.out.println("up");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("down");
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("right");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("left");
                break;
        }
    }
}
