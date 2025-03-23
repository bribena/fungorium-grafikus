package szkeleton_6;

public class RovarEtetes extends UseCase {
    public RovarEtetes()
    {
        Runnable sikeres = this::sikeres;
        functions.put("Sikeres", sikeres);
        Runnable sikertelen = this::sikertelen;
        functions.put("Sikertelen", sikertelen);

        mc = new MultipleChoice("Forgatókönyv kimenetelei: ", functions);
    }
    
    public void defaultStart()
    {
        System.out.println("Inicializálás:");
        System.out.println("Program -> aholVan Tektonrész: konstruktor");
        System.out.println("Program -> r1: Rovar: konstruktor");
        System.out.println("Program -> sp1: Spóra: konstruktor");

        System.out.println("Futás:");
        System.out.println("Felhasználó -> r1: Rovar: eszik(aholVan: Tektonrész)");
        System.out.println("r1: Rovar -> aholVan: Tektonrész: entitásokVisszaadása()");
        System.out.println("r1: Rovar <- hova: Tektonrész: entitások: List<Entitás>");
    }

    public void sikeres()
    {
        defaultStart();
        System.out.println("r1: Rovar -> s1: Spóra: spóraszámVisszaadása()");
        System.out.println("r1: Rovar <- s1: Spóra: spóraszám: int");
        System.out.println("r1: Rovar -> s1: Spóra: spóraszámÁllítás(spóraszám - megevett mennyiség: int)");
        System.out.println("r1: Rovar -> r1: Rovar: spórafeldolgozás(megevett mennyiség: int)");
    }

    public void sikertelen()
    {
        defaultStart();
        System.out.println("Felhasználó <- r1: Rovar: nem történt evés");
    }
}
