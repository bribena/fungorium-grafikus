package fungorium.ReControllers;

import java.util.ArrayList;
import java.util.List;

import fungorium.Models.EgyfonalasTektonrész;
import fungorium.ReModels.Entitás;
import fungorium.ReModels.Fungorium;
import fungorium.ReModels.Gombafaj;
import fungorium.ReModels.Gombafonal;
import fungorium.ReModels.Gombatest;
import fungorium.ReModels.GombatestTiltóTektonrész;
import fungorium.ReModels.Gombász;
import fungorium.ReModels.Játékos;
import fungorium.ReModels.Rovar;
import fungorium.ReModels.Rovarász;
import fungorium.ReModels.Tektonrész;
import fungorium.ReViews.EntitásView;
import fungorium.ReViews.GombafonalView;
import fungorium.ReViews.GombatestView;
import fungorium.ReViews.RovarView;

public class GameLogic {
    private Fungorium fungorium;
    private List<EntitásView> views = new ArrayList<>();
    private GameStateManager stateManager;
    private PlayerManager playerManager;

    public GameLogic(PlayerManager playerManager) {
        this.playerManager = playerManager;
        this.fungorium = playerManager.getFungorium();
        this.stateManager = new GameStateManager(playerManager);
    }

    public void helyezGombatest(Tektonrész hova) {
        if (hova == null || hova.vanGomba() || hova.getClass() == GombatestTiltóTektonrész.class) return;

        Gombász player = (Gombász)playerManager.getAktuálisJátékos();

        boolean kezdo = stateManager.getKorokSzama() == 1;

        Gombafaj faj = player.getKezeltFaj();
        Gombatest test = new Gombatest(faj, kezdo);
        hova.entitásHozzáadás(test);

        if (kezdo)
        {
            Gombafonal fonal = new Gombafonal(faj);
            hova.entitásHozzáadás(fonal);
            fonal.addTest(test);

            for (int i = 0; i < 4; i++) {
                int dx, dy;

                switch (i) {
                    case 1:
                        dx = 1;
                        break;
                    case 3:
                        dx = -1;
                        break;
                    default:
                        dx = 0;
                        break;
                }

                switch (i) {
                    case 0:
                        dy = -1;
                        break;
                    case 2:
                        dy = 1;
                        break;
                    default:
                        dy = 0;
                        break;
                }

                views.add(new GombafonalView(hova, fonal));
            }
        }

        views.add(new GombatestView(hova, test));
        
        if (playerManager.getJatekosok().indexOf(player) == 3) stateManager.leptetFazist();
        playerManager.következőJátékos();
    }

    public void helyezRovart(Tektonrész hova) {
        if (hova == null || !hova.vanFonal() || stateManager.getKorokSzama() != 1) return;

        Rovarász player = (Rovarász)playerManager.getAktuálisJátékos();

        Rovar rovar = new Rovar(player.getKezeltFaj());
        hova.entitásHozzáadás(rovar);
        views.add(new RovarView(hova, rovar));
       
        if (playerManager.getJatekosok().indexOf(player) == 7) stateManager.leptetFazist();
        playerManager.következőJátékos();
    }
    
    public void helyezGombafonalat(Tektonrész honnan, Tektonrész hova) {
        if (hova == null) return;

        Gombász player = (Gombász)playerManager.getAktuálisJátékos();

        Gombafonal kapcsolodFonal = honnan.getKapcsolodoFonal(player.getKezeltFaj());

        if (kapcsolodFonal == null || !kapcsolodFonal.kapcsolódikGombatesthez())
        {
            return;
        }

        kapcsolodFonal.gombafonalatNöveszt(honnan, hova, fungorium);
        if (playerManager.getJatekosok().indexOf(player) == 3) stateManager.leptetFazist();
        playerManager.következőJátékos();
    }
    
    public void helyezSporat(int x, int y) {
        // TODO
    }

    public void szamolGyoztest() {
        // TODO: összesíteni kell a gombatesteket és tápanyagokat, majd győztest választani
    }
}
