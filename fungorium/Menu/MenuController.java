package fungorium.Menu;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import fungorium.Controllers.GameController;
import fungorium.Controllers.PlayerManager;
import fungorium.Models.Fungorium;
import fungorium.Views.EntityView;
import fungorium.Views.GamePanel;
import fungorium.Views.PalyaView;

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
        List<EntityView> entityViews = new ArrayList<>();
        Fungorium fungorium = new Fungorium();
        PalyaView palyaView = new PalyaView(fungorium);
        GamePanel panel = new GamePanel(entityViews, palyaView);
        GameController gameController = new GameController(panel, entityViews); // újra beállítva
        panel.setController(gameController);
        frame.setContentPane(panel);
        frame.revalidate();
    }
}
