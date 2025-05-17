package fungorium.Menu;

import fungorium.Controllers.PlayerManager;
import fungorium.ReControllers.GameController;
import fungorium.ReModels.Fungorium;
import fungorium.ReModels.Játék;
import fungorium.ReViews.EntitásView;
import fungorium.ReViews.FungoriumView;
import fungorium.ReViews.GamePanel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class MenuController {
    private JFrame frame;
    private PlayerManager playerManager;

    public MenuController(JFrame frame, PlayerManager playerManager) {
        this.frame = frame;
        this.playerManager = playerManager;
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
        List<EntitásView> entityViews = new ArrayList<>();
        Játék játék = new Játék();
        Fungorium fungorium = new Fungorium();
        FungoriumView fungoriumView = new FungoriumView(fungorium);
        GamePanel panel = new GamePanel();
        GameController gameController = new GameController(játék, fungoriumView); // újra beállítva
        frame.setContentPane(panel);
        frame.revalidate();
    }
}
