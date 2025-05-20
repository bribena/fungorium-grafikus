package fungorium.Menu.MenuComponents;

import java.awt.*;
import javax.swing.*;

import fungorium.Models.Játék;

public class PlayerEditCenterPanel extends JPanel {
    public PlayerEditCenterPanel(Játék játék) {
        // belső panel header+input fieldekhez
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Két oszlop inicializálása
        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
        leftColumn.setAlignmentY(TOP_ALIGNMENT);

        JPanel rightColumn = new JPanel();
        rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
        rightColumn.setAlignmentY(TOP_ALIGNMENT);

        // Bal oszlopba a címsor
        JLabel editLabel = new JLabel("Nevek szerkesztése:");
        editLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        editLabel.setAlignmentX(CENTER_ALIGNMENT);
        leftColumn.add(editLabel);
        leftColumn.add(Box.createRigidArea(new Dimension(0, 10)));

        // Igazítás a két oszlop között közelebb
        add(Box.createHorizontalGlue());
        add(leftColumn);
        add(Box.createRigidArea(new Dimension(20, 0)));
        add(rightColumn);
        add(Box.createHorizontalGlue());

        // Helykihagyás a jobb oszlop tetején, hogy szinkronban legyenek
        rightColumn.add(Box.createRigidArea(new Dimension(0, editLabel.getPreferredSize().height + 8)));

        for (int i = 0; i < 4; i++) {
            JTextField leftField = new PlayerEditTextField(játék.getJátékosok().get(i));
            leftField.setAlignmentX(CENTER_ALIGNMENT);
            leftColumn.add(leftField);
            if (i < 3) leftColumn.add(Box.createRigidArea(new Dimension(0, 10)));

            JTextField rightField = new PlayerEditTextField(játék.getJátékosok().get(i));
            rightField.setAlignmentX(CENTER_ALIGNMENT);
            rightColumn.add(rightField);
            if (i < 3) rightColumn.add(Box.createRigidArea(new Dimension(0, 10)));
        }

    }
}
