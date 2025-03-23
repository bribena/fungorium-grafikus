package szkeleton_6;

public class SporaSzoras extends UseCase {
    public SporaSzoras()
    {
        Runnable sikeres = this::sikeres;
        functions.put("Sikeres", sikeres);
        Runnable sikertelenA = this::sikertelen;
        functions.put("Sikertelen, túl messzire akar szórni", sikertelenA);
        Runnable sikertelenB = this::sikertelen;
        functions.put("Sikertelen, még nem áll készen szórásra", sikertelenB);
        Runnable sikertelenC = this::sikertelen;
        functions.put("Sikertelen, a test először volt elhelyezve és egyszer tud már csak szórni", sikertelenC);

        mc = new MultipleChoice("Forgatókönyv kimenetelei: ", functions);
    }

    public void defaultStart()
    {
        System.out.println("Inicializálás:");
        System.out.println("Program -> gt1: Gombatest: konstruktor");
        System.out.println("Program -> fung: Fungorium: konstruktor");
        System.out.println("Program -> tr1: Tektonrész: konstruktor");
        System.out.println("Program -> tr2: Tektonrész: konstruktor");
        System.out.println("Program -> tr3: Tektonrész: konstruktor");
        System.out.println("Program -> tr4: Tektonrész: konstruktor");
        System.out.println("Program -> tr5: Tektonrész: konstruktor");
        System.out.println("Program -> sp1: Spóra: konstruktor");

        System.out.println("Futás:");
        System.out.println("Felhasználó -> gt1: Gombatest: spórátSzór(tr1: Tektonrész, fung: Fungorium)");
        System.out.println("gt1: Gombatest -> fung: Fungorium: spóraszóráshozTektonrészek(tr1: Tektonrész)");
        System.out.println("gt1: Gombatest <- fung: Fungorium: List<Tektonrész>[]");
    }

    public void sikeres()
    {
        defaultStart();
        for(int i = 1; i <= 4; i++)
        {
            System.out.println("gt1: Gombatest -> tr" + i + ": Tektonrész: entitásokVisszaadása()");
            System.out.println("gt1: Gombatest <- tr" + i + ": Tektonrész: List<Entitások>");
        }
        System.out.println("Felhasználó <- gt1: Gombatest: true");
    }

    public void sikertelen()
    {
        defaultStart();
        System.out.println("Felhasználó <- gt1: Gombatest: false");
    }
}
