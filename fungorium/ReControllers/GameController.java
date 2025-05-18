package fungorium.ReControllers;

import fungorium.ReViews.*;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;


public class GameController {
    private PlayerManager playerManager;
    private FungoriumView view;
    private TektonrészView selectedTektonrész;
    private GameLogic gameLogic;
    private GameStateManager gameState;
    private GamePanel gamePanel;

    public GameController(GameStateManager gameState, PlayerManager manager, FungoriumView view, GamePanel gp) {
        this.gameState =gameState;
        this.playerManager = manager;
        this.view = view;
        gameLogic = new GameLogic(playerManager);
        gombászKeyAdapter = new FungoriumGombászKeyAdapter(this, this.gameState);
        rovarászKeyAdapter = new FungoriumRovarászKeyAdapter(this, this.gameState);
        this.gamePanel = gp;
    }

    public FungoriumView getFungoriumView() {
        return view;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gp) {
        this.gamePanel = gp;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void selectByWindowPoint(Point p) {
        if (selectedTektonrész != null) {
            selectedTektonrész.toggleSelected();
        }

        selectedTektonrész = (TektonrészView)view.getComponentAt(p);

        if (selectedTektonrész != null) {
            selectedTektonrész.toggleSelected();
        } 
    }

    public void selectByTectonCoordinates(int x, int y) {
        if (x < 0 || x >= playerManager.getFungorium().getWidth() || y < 0 || y >= playerManager.getFungorium().getHeight()) {
            return;
        }

        for (Component c : view.getComponents()) {
            if (c instanceof TektonrészView) {
                TektonrészView v = (TektonrészView)c;
                if (v.x == x && v.y == y) {
                    selectedTektonrész.toggleSelected();
                    selectedTektonrész = v;
                    selectedTektonrész.toggleSelected();
                    return;
                }
            }
        }
    }

    public void deselect() {
        selectedTektonrész.toggleSelected();
        selectedTektonrész = null;
    }

    public TektonrészView getSelectedTektonrészView() {
        return selectedTektonrész;
    }

    private FungoriumMouseAdapter mouseAdapter = new FungoriumMouseAdapter(this);

    public MouseAdapter getFungoriumMouseAdapter() {
        return mouseAdapter;
    }

    private FungoriumGombászKeyAdapter gombászKeyAdapter = new FungoriumGombászKeyAdapter(this, this.gameState);

    public KeyAdapter getGombászKeyAdapter() {
        return gombászKeyAdapter;
    }

    private FungoriumRovarászKeyAdapter rovarászKeyAdapter = new FungoriumRovarászKeyAdapter(this, this.gameState);

    public KeyAdapter getRovarászKeyAdapter() {
        return rovarászKeyAdapter;
    }
}
