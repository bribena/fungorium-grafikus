package szkeleton_6;

public class GombatestFejlesztes extends UseCase {
    public GombatestFejlesztes()
    {
        Runnable sikeres = this::sikeres;
        functions.put("Sikeres", sikeres);
        Runnable sikertelenA = this::sikertelen;
        functions.put("Sikertelen, már fejlett", sikertelenA);
        Runnable sikertelenB = this::sikertelen;
        functions.put("Sikertelen, már fejlett, még nem fejleszthető", sikertelenB);

        mc = new MultipleChoice("Forgatókönyv kimenetelei: ", functions);
    }

    public void defaultStart()
    {
        System.out.println("Inicializálás:");
        System.out.println("Program -> gf1: Gombafonal: konstruktor");

        System.out.println("Futás:");
        System.out.println("Felhasználó -> gf1: Gombafonal: fejlődik()");
    }

    public void sikeres()
    {
        defaultStart();
        System.out.println("Felhasználó <- gf1: Gombafonal: true");
    }

    public void sikertelen()
    {
        defaultStart();
        System.out.println("Felhasználó <- gf1: Gombafonal: false");
    }
}
