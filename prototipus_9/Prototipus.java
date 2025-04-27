package prototipus_9;

import java.util.*;
import java.util.regex.Pattern;

public class Prototipus {
    private int sporaSzam = 0;
    private Set<Integer> gombaszok = new HashSet<>();
    private Set<Integer> rovaraszok = new HashSet<>();
    private Set<Integer> tektonok = new HashSet<>();
    private Set<Integer> gombafonalak = Set.of(1,2,3,4,5);

    private void futtat(String file)
    {

    }

    private void load()
    {

    }

    private void fungorium_torol(int id)
    {

    }

    private void fungorium_tor(int tektonId)
    {

    }

    private void gombasz_uj(int id)
    {
        if(gombaszok.contains(id)){
            System.out.printf("gombasz uj %d -> FAIL: mar van ilyen nevu jatekos (%d)\n", id, id);
        }else{
            gombaszok.add(id);
            System.out.printf("gombasz uj %d -> OK: uj gombasz %d letrehozva\n", id, id);
        }
    }

    private void rovarasz_uj(int id)
    {
        if(rovaraszok.contains(id)){
            System.out.printf("rovarasz uj %d -> FAIL: mar van ilyen nevu jatekos (%d)\n", id, id);
        }else{
            rovaraszok.add(id);
            System.out.printf("rovarasz uj %d -> OK: uj rovarasz %d letrehozva\n", id, id);
        }
    }
    
    private void tekton_uj(int id, String tipus)
    {
        if(TektonreszTipus.exsists(tipus)){
            tektonok.add(id);
            System.out.printf("tekton uj %d %s -> OK: %d tekton letrehozva (%s)\n", id, tipus, id, tipus);
        }else{
            System.out.printf("tekton uj %d %s -> FAIL: %d letrehozasa sikertelen, ismeretlen fajta\n", id, tipus, id);
        }
    }

    private void tekton_szomszed(int t1_id, int t2_id)
    {

    }

    private void gomba_noveszt(int gombaId, int jatekosId, int tektonId)
    {

    }

    private void gomba_fejleszt(int gombaId)
    {

    }

    private void gomba_szoras(int gombaId)
    {

    }

    private void gombaf_szakit(int forrasId, int celId)
    {

    }

    private void gombaf_noveszt(int fonalId, int forrasId, int celId)
    {
        if(gombafonalak.contains(fonalId) && tektonok.contains(forrasId) && tektonok.contains(celId)){
            System.out.printf("gombaf noveszt %d %d %d -> OK: %d novesztve %d es %d kozott",  fonalId, forrasId, celId, fonalId, forrasId, celId);
        } else if (!tektonok.contains(forrasId) || !tektonok.contains(celId)) {
            System.out.printf("gombaf noveszt %d %d %d -> FAIL: gombafonal nem novesztheto %d es %d kozott",  fonalId, forrasId, celId, forrasId, celId);

        }
    }

    private void gombaf_rovarbol(int fonalId, int jatekosId, int rovarId)
    {

    }

    private void spora_szam(int ertek)
    {
        if(ertek < 0){
            System.out.printf("spora szam %d -> FAIL: hibas sporaszam\n", ertek);
        }else {
            sporaSzam = ertek;
            System.out.printf("spora szam %d -> OK: sporaszam beallitva: %d\n", ertek, ertek);
        }
    }

    private void rovar_hatas(int rovarId, String hatas, int ertek)
    {

    }

    private void rovar_vag(int rovarId, int forrasId, int celId)
    {

    }

    private void rovar_mozog(int rovarId, int forrasId, int celId)
    {

    }

    private void rovar_eszik(int rovarId, int tektonId)
    {

    }

    public void proto() {
        Scanner scanner = new Scanner(System.in);

        String[] parancsok = {
            "help", 
            "futtat <fajl>", 
            "load",
            "fungorium torol <azonosito>",
            "fungorium tor <tekton azonosito>",
            "gombasz uj <j_azonosito>",
            "rovarasz uj <j_azonosito>",
            "tekton uj <t_azonosito> <tipus>",
            "tekton szomszed <t1_azonosito> <t2_azonosito>",
            "gomba noveszt <g_azonosito> < j_azonosito> <t_azonosito>",
            "gomba fejleszt <g_azonosito>",
            "gomba szoras <g_azonosito>",
            "gombaf szakit <forrast_azonosito> <celt_azonosito>",
            "gombaf noveszt <gf_azonosito> <forrast_azonosito> <celt_azonosito>",
            "gombaf rovarbol <gf_azonosito> <j_azonosito> <r_azonosito>",
            "spora szam <ertek>",
            "rovar hatas <r_azonosito> <hatasnev> <ertek>",
            "rovar vag <r_azonosito> <forrast_azonosito> <celt_azonosito>",
            "rovar mozog <r_azonosito> <forrast_azonosito> <celt_azonosito>",
            "rovar eszik <r_azonosito> <t_azonosito>"
        };

        boolean exit = false;

        while (!exit)
        {
            List<String> parancs = new ArrayList<>(List.of(scanner.nextLine().split(" ")));

            if (parancs.size() < 5) // max ennyi parameter lehet
            {
                for(int i = parancs.size(); i < 5; i++) // ez azert kell, hogy ne kelljen lekezelni azt, ha nem adott meg minden parameter, majd a fuggvenyben eleg leellorizni, hogy ures-e a parameter
                {
                    parancs.add(""); // basically kitoltom a hianyzo parametereket ures stringgel
                }
            }

            // mivel vannak egyszavas es ketszavas parancsok is, ezert inkabb ujabb switchcase/if-be foglaltam a ketszavasakat ahogy lentebb lathato

            switch (parancs.get(0))
            {
                case "help":
                    for (int i = 0; i < parancsok.length; i++)
                    {
                        System.out.println(parancsok[i]);
                    }
                    break;
                case "exit":
                    exit = true;
                    break;
                case "futtat":
                    futtat(parancs.get(1));
                    break;
                case "load":
                    load();
                    break;
                case "fungorium":
                    switch (parancs.get(1))
                    {
                        case "torol":
                            fungorium_torol(Integer.parseInt(parancs.get(2)));
                            break;
                        case "tor":
                            fungorium_tor(Integer.parseInt(parancs.get(2)));
                            break;
                        default:
                            break;
                    }
                    break;
                case "gombasz":
                    if (parancs.get(1).equals("uj"))
                    {
                        gombasz_uj(Integer.parseInt(parancs.get(2)));
                    }
                    break;
                case "rovarasz":
                    if (parancs.get(1).equals("uj"))
                    {
                        rovarasz_uj(Integer.parseInt(parancs.get(2)));
                    }
                    break;
                case "tekton":
                    switch (parancs.get(1)) 
                    {
                        case "uj":
                            tekton_uj(Integer.parseInt(parancs.get(2)), parancs.get(3));
                            break;
                        case "szomszed":
                            tekton_szomszed(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)));
                            break;
                        default:
                            break;
                    }
                    break;
                case "gomba":
                    switch (parancs.get(1)) 
                    {
                        case "noveszt":
                            gomba_noveszt(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)), Integer.parseInt(parancs.get(4)));
                            break;
                        case "fejleszt":
                            gomba_fejleszt(Integer.parseInt(parancs.get(2)));
                            break;
                        case "szoras":
                            gomba_szoras(Integer.parseInt(parancs.get(2)));
                            break;
                        default:
                            break;
                    }
                    break;
                case "gombaf":
                    switch (parancs.get(1)) 
                    {
                        case "szakit":
                            gombaf_szakit(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)));
                            break;
                        case "noveszt":
                            gombaf_noveszt(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)), Integer.parseInt(parancs.get(4)));
                            break;
                        case "rovarbol":
                            gombaf_rovarbol(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)), Integer.parseInt(parancs.get(4)));
                            break;
                        default:
                            break;
                    }
                    break;
                case "spora":
                    if (parancs.get(1).equals("szam"))
                    {
                        spora_szam(Integer.parseInt(parancs.get(2)));
                    }
                    break;
                case "rovar":
                    switch (parancs.get(1)) 
                    {
                        case "hatas":
                            rovar_hatas(Integer.parseInt(parancs.get(2)), parancs.get(3), Integer.parseInt(parancs.get(4)));
                            break;
                        case "vag":
                            rovar_vag(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)), Integer.parseInt(parancs.get(4)));
                            break;
                        case "mozog":
                            rovar_mozog(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)), Integer.parseInt(parancs.get(4)));
                            break;
                        case "eszik":
                            rovar_eszik(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)));
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
