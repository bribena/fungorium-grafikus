package Menu;

public class PlayerManager {
    private String[] gombaszok = new String[4];
    private String[] rovaraszok = new String[4];
    
    public PlayerManager() {
        // Alapnevek beállítása
        gombaszok[0] = "Gombász1";
        gombaszok[1] = "Gombász2";
        gombaszok[2] = "Gombász3";
        gombaszok[3] = "Gombász4";
        
        rovaraszok[0] = "Rovarász1";
        rovaraszok[1] = "Rovarász2";
        rovaraszok[2] = "Rovarász3";
        rovaraszok[3] = "Rovarász4";
    }

    // Név beállítása index alapján (0-3: gombász, 4-7: rovarász)
    public void setName(int index, String name) {
        if (index < 0 || index >= 8) throw new IndexOutOfBoundsException("Érvénytelen index");
        if (index < 4) gombaszok[index] = name;
        else rovaraszok[index - 4] = name;
    }

    // Összes név lekérése egy tömbben (gombászok + rovarászok)
    public String[] getNames() {
        String[] all = new String[8];
        System.arraycopy(gombaszok, 0, all, 0, 4);
        System.arraycopy(rovaraszok, 0, all, 4, 4);
        return all;
    }

    // Egy adott név lekérése index alapján
    public String getName(int index) {
        if (index < 0 || index >= 8) throw new IndexOutOfBoundsException("Érvénytelen index");
        if (index < 4) return gombaszok[index];
        else return rovaraszok[index - 4];
    }

    // Gombászok külön lekérhetők
    public String[] getGombaszok() {
        return gombaszok.clone();
    }

    // Rovarászok külön lekérhetők
    public String[] getRovaraszok() {
        return rovaraszok.clone();
    }
}
