package fungorium.ReModels;

public interface Entitás {
    /** Visszaadja, hogy érvényes-e az entitás */
    boolean érvényesE();
    /**
     * Végrehajtja a frissítést
     * @return érvényes maradt-e az entitás
     */
    boolean frissítés();
}
