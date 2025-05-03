package Game;

import java.util.List;

import prototipus_9.*;

public class GameLogic {
    private Fungorium fungorium;
    private List<EntityView> views;
    private GameStateManager stateManager;
    private PlayerManager playerManager;

    private int gombatestCount = 0;
    private int rovarCount = 0;

    public GameLogic(Fungorium fungorium, List<EntityView> views,
                     GameStateManager stateManager, PlayerManager playerManager) {
        this.fungorium = fungorium;
        this.views = views;
        this.stateManager = stateManager;
        this.playerManager = playerManager;
    }

    public void helyezGombatest(int x, int y) {
        Tektonrész resz = fungorium.getTektonrész(x, y);
        if (resz == null || resz.vanGomba()) return;

        int index = stateManager.getAktualisJatekosIndex();
        Gombafaj faj = playerManager.getGombafaj(index);
        Gombatest g = new Gombatest(true);
        
        for (int i = 0; i < 4; i++) {
            Gombafonal fonal = new Gombafonal(faj);
            fonal.gombatestHozzáadása(g);
            resz.entitásHozzáadás(fonal);

            int dx = switch (i) {
                case 1 -> 1;
                case 3 -> -1;
                default -> 0;
            };
            int dy = switch (i) {
                case 0 -> -1;
                case 2 -> 1;
                default -> 0;
            };

            views.add(new GombafonalView(fonal, x, y, dx, dy));
        }

        resz.entitásHozzáadás(g);
        views.add(new GombatestView(g, fungorium, x, y));

        gombatestCount++;
        stateManager.kovetkezoJatekos();
        if (gombatestCount >= 4) stateManager.leptetFazist();
    }

    public void helyezRovart(int x, int y) {
        Tektonrész resz = fungorium.getTektonrész(x, y);
        if (resz == null || !resz.vanGomba()) return;

        Rovar r = new Rovar();
        resz.entitásHozzáadás(r);
        views.add(new RovarView(r, x, y));

        rovarCount++;
        stateManager.kovetkezoJatekos();
        if (rovarCount >= 4) stateManager.leptetFazist();
    }
    
    public void helyezGombafonalat(int x, int y) {
        // TODO
    }
    
    public void helyezSporat(int x, int y) {
        // TODO
    }

    public void szamolGyoztest() {
        // TODO: összesíteni kell a gombatesteket és tápanyagokat, majd győztest választani
    }
}