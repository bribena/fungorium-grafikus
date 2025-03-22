package szkeleton_6;

public class GombatestNovesztes extends UseCase {
    public GombatestNovesztes()
    {
        Runnable sikeres = this::sikeres;
        functions.put("Sikeres", sikeres);
        Runnable sikertelenA = this::sikertelen;
        functions.put("Sikertelen, nem volt elég spóra a tektonon", sikertelenA);
        Runnable sikertelenB = this::sikertelen;
        functions.put("Sikertelen, már van gombatest a tektonon", sikertelenB);
        Runnable sikertelenC = this::sikertelen;
        functions.put("Sikertelen, olyan tektonon akar növeszteni, ahol nem nőhet gombatest", sikertelenC);

        mc = new MultipleChoice("Forgatókönyv kimenetelei: ", functions);
    }

    public void defaultStart()
    {
        System.out.println("Inicializálás:");
        System.out.println("Program -> gf1: Gombafonal: konstruktor");
        System.out.println("Program -> fung: Fungorium: konstruktor");
        System.out.println("Program -> tr1: Tektonrész: konstruktor");

        System.out.println("Futás:");
        System.out.println("Felhasználó -> gf1: Gombafonal: gombatestNöveszt(tr1: Tektonrész, fung: Fungorium)");
        System.out.println("gf1: Gombafonal -> fung: Fungorium: hánySpóraVan(tr1: Tektonrész)");
        System.out.println("gf1: Gombafonal <- fung: Fungorium: spórákSzáma: int");
    }

    public void sikeres()
    {
        defaultStart();
        System.out.println("gf1: Gombafonal -> tr1: Tektonrész: entitásHozzáadás()");
        System.out.println("Felhasználó <- gf1: Gombafonal: true");
    }

    public void sikertelen()
    {
        defaultStart();
        System.out.println("Felhasználó <- gf1: Gombafonal: false");
    }
}
