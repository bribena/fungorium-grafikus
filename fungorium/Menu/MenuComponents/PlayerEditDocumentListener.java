package fungorium.Menu.MenuComponents;

import javax.swing.JTextField;
import javax.swing.event.*;

import fungorium.Models.Játékos;

public class PlayerEditDocumentListener implements DocumentListener {
    private Játékos játékos;
    private JTextField observed;

    public PlayerEditDocumentListener(JTextField observing, Játékos játékos) {
        this.játékos = játékos;
        observed = observing;
    }

    private void update() {
        játékos.setName(observed.getText());
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        update();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        update();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        update();
    }
    
}
