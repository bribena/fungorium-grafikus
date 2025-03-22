package szkeleton_6;

public class GombafonalVagas extends UseCase {
    public GombafonalVagas()
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
        System.out.println("Felhasználó -> r1: Rovar: fonalatVág(tr1: Tektonrész, gf1: Gombafonal)");
    }

    public void sikeres()
    {
        defaultStart();
        System.out.println("r1: Rovar -> gf1: Gombafonal: szakít(gf1: Gombafonal)");
        System.out.println("r1: Rovar <- gf1: Gombafonal: fonal megszakad");
    }

    public void sikertelen()
    {
        defaultStart();
        System.out.println("Felhasználó <- r1: Rovar: nem tud vágni");
    }
}
