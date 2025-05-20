package fungorium.Views;

import javax.swing.JComponent;

import fungorium.Models.Entitás;

//entitás megjelenítése, konkrétumokat a leszármazottak csinálnak
public abstract class EntitásView extends JComponent {
    public abstract boolean isValid();
    public abstract boolean contains(Entitás e);
}
