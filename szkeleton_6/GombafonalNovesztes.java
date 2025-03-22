package szkeleton_6;

public class GombafonalNovesztes extends UseCase {
    public GombafonalNovesztes()
    {
        Runnable sikeresA = this::sikeres;
        functions.put("Sikeres", sikeresA);
        Runnable sikeresB = this::sikeresB;
        functions.put("Sikeres, egy szakadt gombafonalat választott", sikeresB);
        Runnable sikertelenA = this::sikertelen;
        functions.put("Sikertelen, a gombafonal nem csatlakozik gombatesthez", sikertelenA);
        Runnable sikertelenB = this::sikertelen;
        functions.put("Sikertelen, olyan tektonra nő, ahol egyféle fonal lehet, és már van is", sikertelenB);

        mc = new MultipleChoice("Forgatókönyv kimenetelei: ", functions);
    }

    public void defaultStart()
    {
        System.out.println("Inicializálás:");
        System.out.println("Program -> gf1: Gombafonal: konstruktor");
        System.out.println("Program -> fung: Fungorium: konstruktor");
        System.out.println("Program -> tr1: Tektonrész: konstruktor");
        System.out.println("Program -> tr2: Tektonrész: konstruktor");

        System.out.println("Futás:");
        System.out.println("Felhasználó -> gf1: Gombafonal: gombafonalatNöveszt(tr1: Tektonrész, tr2: Tektonrész)\n");
    }

    public void sikeres()
    {
        defaultStart();
        System.out.println("gf1: Gombafonal -> tr1: Tektonrész: entitásHozzáadás()");
        System.out.println("gf1: Gombafonal -> tr2: Tektonrész: entitásHozzáadás()");
        System.out.println("Felhasználó <- gf1: Gombafonal: true");
    }

    public void sikertelen()
    {
        defaultStart();
        System.out.println("Felhasználó <- gf1: Gombafonal: false");
    }

    public void sikeresB()
    {
        sikeres();
        System.out.println("Felhasználó <- gf1: Gombafonal: fonalOsszenovesztes(gf2 Gombafonal)");
    }
}
