package fungorium.Controllers.RovarászListeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fungorium.Controllers.GameController;
import fungorium.Models.Játékos;
import fungorium.Models.Rovar;
import fungorium.Models.Rovarfaj;
import fungorium.Models.Rovarász;
import fungorium.Models.Tektonrész;
import fungorium.Views.RovarView;
import fungorium.Views.TektonrészView;

public class RovarászElhelyezésKeyAdapter extends KeyAdapter {
    private GameController controller;

    public RovarászElhelyezésKeyAdapter(GameController controller) {
        this.controller = controller;
    }

    private void rovarElhelyezés(Rovarfaj faj) {
        TektonrészView selected = controller.getSelectedTektonrészView();
        Tektonrész selectedTektonrész = selected.getTektonrész();

        Rovar r = new Rovar(faj);
        if (!selectedTektonrész.entitásHozzáadás(r)) {
            return;
        }

        selected.add(new RovarView(r));
        controller.update();
    } 

    @Override
    public void keyReleased(KeyEvent e) {
        Játékos j = controller.getJáték().getAktuálisJátékos();

        if (controller.getSelectedTektonrészView() == null || !(j instanceof Rovarász) || !controller.getJáték().kezdő() 
            || e.getKeyCode() != KeyEvent.VK_L) {
            return;
        }

        Rovarász r = (Rovarász)j;

        System.out.println("L lenyomva");
        rovarElhelyezés(r.getKezeltFaj());
    }
}
