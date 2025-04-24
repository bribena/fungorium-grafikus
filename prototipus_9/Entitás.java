package prototipus_9;

public interface Entitás {
    public void frissítés();
    //amennyiben egy Entitásra eltávolítást kértek, tehát el kell távolítani, false-ot ad vissza, amúgy true-t
    public default void speciálisBeállítás(boolean speciális){}
}
