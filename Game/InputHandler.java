package Game;

public class InputHandler {
    private GameStateManager stateManager;
    private GameLogic logic;

    public InputHandler(GameStateManager stateManager, GameLogic logic) {
        this.stateManager = stateManager;
        this.logic = logic;
    }

    public void handleClick(int x, int y) {
        switch (stateManager.getFazis()) {
            case GOMBA_HELYEZES -> logic.helyezGombatest(x, y);
            case ROVAR_HELYEZES -> logic.helyezRovart(x, y);
            case KOROK -> {
                // TODO: kijelölés vagy akció
            }
            case VEGE -> {
                // nincs több interakció
            }
        }
    }

    public void handleKey(int keyCode) {
        // TODO: mozgatás, kör léptetése, stb.
    }
}