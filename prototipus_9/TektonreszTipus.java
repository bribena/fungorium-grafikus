package prototipus_9;

public enum TektonreszTipus {
    EGYFONALAS("EgyFonalas"),
    FONALFELSZIVO("FonalFelszivo"),
    GOMBATEST_TILTO("GombatestTilto"),
    TOBBFONALAS("TobbFonalas"),
    ELETBENTARTO("Eletbentarto"),;

    private final String tipus;

    TektonreszTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getTipus() {
        return tipus;
    }

    public static boolean exsists(String tipus) {
        for(TektonreszTipus t : values()){
            if(t.tipus.equals(tipus)){
                return true;
            }
        }
        return false;
    }
}


