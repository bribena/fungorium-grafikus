package Menu;

import Game.PlayerManager;

public class PlayerEditController {
    private PlayerManager playerManager;

    public PlayerEditController(PlayerManager manager) {
        this.playerManager = manager;
    }

    public void updatePlayerName(int index, String name) {
        playerManager.setName(index, name);
    }
}
