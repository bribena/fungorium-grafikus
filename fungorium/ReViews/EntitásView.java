package fungorium.ReViews;

import javax.swing.JComponent;

import fungorium.ReModels.Entitás;

public abstract class EntitásView extends JComponent {
    public abstract boolean isValid();
    public abstract boolean contains(Entitás e);
}
