package fungorium.Menu;

import fungorium.Controllers.PlayerManager;

public class PlayerEditController {
    private PlayerManager playerManager;

    public PlayerEditController(PlayerManager manager) {
        this.playerManager = manager;
    }

    public void updatePlayerName(int index, String name) {
        playerManager.setName(index, name);
    }
}
