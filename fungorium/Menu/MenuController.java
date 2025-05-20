package fungorium.Menu;

import fungorium.ReControllers.GameController;
import fungorium.ReControllers.GameStateManager;
import fungorium.ReControllers.PlayerManager;
import fungorium.ReViews.FungoriumView;
import fungorium.ReViews.GamePanel;
import javax.swing.JFrame;

public class MenuController {
    private JFrame frame;
    private PlayerManager playerManager;
    private GameController controller;

    public MenuController(JFrame frame, PlayerManager playerManager, GameController controller) {
        this.frame = frame;
        this.playerManager = playerManager;
        this.controller = controller;
    }

    public MenuController(JFrame frame) {
        this.frame = frame;
        this.controller = null;
    }

    public void showMainMenu() {
        frame.setContentPane(new MainMenuView(this));
        frame.revalidate();
        frame.repaint();
    }

    public void showPlayerEdit() {
        frame.setContentPane(new PlayerEditView(this, playerManager));
        frame.revalidate();
        frame.repaint();
    }

    public void showVictoryScreen() {
        frame.setContentPane(new VictoryScreenView(this));
        frame.revalidate();
        frame.repaint();
    }

    public void startGame() {
        playerManager = new PlayerManager();
        FungoriumView fungView = new FungoriumView(playerManager.getFungorium());
        GameStateManager gameState = new GameStateManager(playerManager, this);
        GameController controller = new GameController(gameState, playerManager, fungView, null);

        GamePanel panel = new GamePanel(fungView, controller);  // ÁTADÁS

        // Mostantól a controller is tudja, melyik panelje van
        controller.setGamePanel(panel);

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();

        panel.addKeyListener(controller.getGombászKeyAdapter());
        panel.addKeyListener(controller.getRovarászKeyAdapter());
        panel.setFocusable(true);
        panel.requestFocusInWindow();
    }
}
