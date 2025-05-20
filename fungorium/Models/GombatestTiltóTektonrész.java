package fungorium.Models;

/**
 * Ez az osztály a Tektonrész egy speciális változata, amely tiltja a Gombatestek hozzáadását.
 * Ha meglévő Tektonrészből jön létre, akkor azon lévő nem-kezdő Gombatesteket passzívvá teszi.
 */
public class GombatestTiltóTektonrész extends Tektonrész {

    public GombatestTiltóTektonrész() {}

    /**
     * Az átvett entitások közül minden nem-kezdő Gombatestet passzívvá tesz.
     *
     * @param t A kiindulási Tektonrész, amelyből az új példány létrejön.
     */
    public GombatestTiltóTektonrész(Tektonrész t) {
        super(t); // Meghívja az ősosztály másoló konstruktorát
        for (Entitás e : entitások) {
            if (e instanceof Gombatest) {
                Gombatest test = (Gombatest)e;
                // Csak akkor teszi passzívvá a gombatestet, ha az nem kezdő
                if (!test.isKezdő()) {
                    test.passzívBeállítás(true);
                }
            }
        }
    }

    /**
     * Felülírja az entitásHozzáadás metódust, hogy megakadályozza Gombatest példányok hozzáadását.
     *
     * @param gt A hozzáadni kívánt Gombatest.
     * @return Mindig false – nem engedélyezi a Gombatest hozzáadását.
     */
    @Override
    public boolean entitásHozzáadás(Gombatest gt) {
        return false;
    }
}
