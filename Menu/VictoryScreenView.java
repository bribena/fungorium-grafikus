package Menu;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class VictoryScreenView extends JPanel {
    public VictoryScreenView(MenuController controller){
        //add(new JLabel("Gombász nyertes: " + JátékKezelő.getWinnerGombasz()));
        //add(new JLabel("Rovarász nyertes: " + JátékKezelő.getWinnerRovarasz()));

        JButton backBtn = new JButton("Főmenü");
        backBtn.addActionListener(e -> controller.showMainMenu());
        add(backBtn);
    }
}
