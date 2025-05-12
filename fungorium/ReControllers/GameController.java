package fungorium.ReControllers;

import fungorium.ReModels.JátékKezelő;

public class GameController {
    private JátékKezelő kezelő;

    public GameController(JátékKezelő kezelő) {
        this.kezelő = kezelő;
    }

    public JátékKezelő getJátékKezelő() {
        return kezelő;
    }
}
