package fungorium.ReModels;

public class Játék {
    private Fungorium fungorium = new Fungorium();
    private Játékos aktuálisJátékos;
    private Játékos[] játékosok = new Játékos[8];

    public Játék() {
        for (int i = 0; i < 4; ++i) {
            játékosok[i] = new Gombász(Gombafaj.values()[i]);
            játékosok[i+4] = new Rovarász(Rovarfaj.values()[i]);
        }
        aktuálisJátékos = játékosok[0];
    }

    public Játékos getAktuálisJátékos() {
        return aktuálisJátékos;
    }

    public Fungorium getFungorium() {
        return fungorium;
    }
}
