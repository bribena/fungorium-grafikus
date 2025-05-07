package fungorium.Models;

public interface Entitás {
    public boolean frissítés();
    public default void speciálisBeállítás(boolean speciális) {}
}
