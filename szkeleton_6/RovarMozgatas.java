package szkeleton_6;

public class RovarMozgatas extends UseCase {
    public RovarMozgatas()
    {
        Runnable sikeres = this::sikeres;
        functions.put("Sikeres", sikeres);
        Runnable sikertelen = this::sikertelen;
        functions.put("Sikertelen", sikertelen);
        
        mc = new MultipleChoice("Forgatókönyv kimenetelei: ", functions);
    }

    public void defaultStart()
    {
        System.out.println("Futás:");
        System.out.println("Felhasználó -> r1: Rovar: mozog(aholVan: Tektonrész, hova: Tektonrész)");
        System.out.println("r1: Rovar -> hova: Tektonrész: entitásokVisszaadása()");
        System.out.println("r1: Rovar <- hova: Tektonrész: entitások: List<Entitás>");
    }
    
    public void sikeres()
    {
        defaultStart();
        System.out.println("r1: Rovar -> aholVan: Tektonrész: entitástörlése(r1: Rovar)");
        System.out.println("r1: Rovar -> hova: Tektonrész: entitásHozzáadása(r1: Rovar)");
    }

    public void sikertelen()
    {
        defaultStart();
        System.out.println("Felhasználó <- r1: Rovar: mozgás sikertelen");
    }
}
