package fungorium.Views;

import javax.swing.JComponent;

import fungorium.Models.Entitás;

public abstract class EntitásView extends JComponent {
    public abstract boolean isValid();
    public abstract boolean contains(Entitás e);
}
