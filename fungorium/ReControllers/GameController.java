package fungorium.ReControllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fungorium.ReViews.FungoriumView;
import fungorium.ReViews.TektonrészView;
import fungorium.ReModels.Entitás;
import fungorium.ReModels.Gombász;
import fungorium.ReModels.Rovar;
import fungorium.ReModels.Rovarász;
import fungorium.ReModels.Tektonrész;

public class GameController {
    private PlayerManager playerManager;
    private FungoriumView view;
    private TektonrészView selectedTektonrész;
    private GameLogic gameLogic;

    public GameController(PlayerManager manager, FungoriumView view) {
        this.playerManager = manager;
        this.view = view;
        gameLogic = new GameLogic(playerManager);
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    private FungoriumMouseAdapter mouseAdapter = new FungoriumMouseAdapter();

    private class FungoriumMouseAdapter extends MouseAdapter {
        private FungoriumMouseAdapter() {}
        @Override
        public void mouseClicked(MouseEvent e) {
            if (selectedTektonrész != null) {
                selectedTektonrész.toggleSelected();
            } 
            selectedTektonrész = (TektonrészView)view.getComponentAt(e.getPoint());
            if (selectedTektonrész != null) {
                selectedTektonrész.toggleSelected();
            } 
        }
    }

    public MouseAdapter getFungoriumMouseAdapter() {
        return mouseAdapter;
    }

    private FungoriumKeyAdapter keyAdapter = new FungoriumKeyAdapter();

    private class FungoriumKeyAdapter extends KeyAdapter {
        private FungoriumKeyAdapter() {}

        private void deselect() {
            selectedTektonrész.toggleSelected();
            selectedTektonrész = null;
        }
        
        private void reselect(int x, int y) {
            if (x < 0 || x >= playerManager.getFungorium().getWidth() || y < 0 || y >= playerManager.getFungorium().getHeight()) {
                return;
            }
            for (int i = 0; i < view.getComponentCount(); ++i) {
                TektonrészView v = (TektonrészView)view.getComponent(i);
                if (v.x == x && v.y == y) {
                    selectedTektonrész.toggleSelected();
                    selectedTektonrész = v;
                    selectedTektonrész.toggleSelected();
                }
            }
        }

        private void gombászAction(KeyEvent e) {
            Tektonrész tr = playerManager.getFungorium().getTektonrész(selectedTektonrész.x, selectedTektonrész.y);

            switch (e.getKeyCode()) {
                case KeyEvent.VK_N:
                    gameLogic.helyezGombatest(tr);
                    System.out.println("N");
                    break;
                case KeyEvent.VK_F:
                    System.out.println("F");
                    break;
                case KeyEvent.VK_S:
                    System.out.println("S");
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
            deselect();
        }

        private void rovarászAction(KeyEvent e) {
            Tektonrész tr = playerManager.getFungorium().getTektonrész(selectedTektonrész.x, selectedTektonrész.y);
            Rovar selectedRovar = null;
            for (Entitás ent : tr.getEntitások()) {
                if (ent instanceof Rovar && ((Rovar)ent).getFaj() == ((Rovarász)playerManager.getAktuálisJátékos()).getKezeltFaj()) {
                    selectedRovar = (Rovar)ent;
                    break;
                }
            }

            switch (e.getKeyCode()) {
                case KeyEvent.VK_V:
                    System.out.println("V");
                    break;
                case KeyEvent.VK_E:
                    System.out.println("E");
                    break;
                case KeyEvent.VK_UP:
                    // System.out.println("up");
                    reselect(selectedTektonrész.x, selectedTektonrész.y - 1);
                    break;
                case KeyEvent.VK_DOWN:
                    // System.out.println("down");
                    reselect(selectedTektonrész.x, selectedTektonrész.y + 1);
                    break;
                case KeyEvent.VK_RIGHT:
                    // System.out.println("right");
                    reselect(selectedTektonrész.x + 1, selectedTektonrész.y);
                    break;
                case KeyEvent.VK_LEFT:
                    // System.out.println("left");
                    reselect(selectedTektonrész.x - 1, selectedTektonrész.y);
                    break;
            }
            // deselect();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (selectedTektonrész != null && playerManager.getAktuálisJátékos() instanceof Rovarász) {
                rovarászAction(e);
            }
            else if (selectedTektonrész != null && playerManager.getAktuálisJátékos() instanceof Gombász) {
                gombászAction(e);
            }
        }
    }

    public KeyAdapter getFungoriumKeyAdapter() {
        return keyAdapter;
    }
}
