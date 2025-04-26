package prototipus_9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static void futtat(String file)
    {

    }

    private static void load()
    {

    }

    private static void fungorium_torol(int id)
    {

    }

    private static void fungorium_tor(int tektonId)
    {

    }

    private static void gombasz_uj(int id)
    {

    }

    private static void rovarasz_uj(int id)
    {

    }
    
    private static void tekton_uj(int id, String tipus)
    {

    }

    private static void tekton_szomszed(int t1_id, int t2_id)
    {

    }

    private static void gomba_noveszt(int gombaId, int jatekosId, int tektonId)
    {

    }

    private static void gomba_fejleszt(int gombaId)
    {

    }

    private static void gomba_szoras(int gombaId)
    {

    }

    private static void gombaf_szakit(int forrasId, int celId)
    {

    }

    private static void gombaf_noveszt(int fonalId, int forrasId, int celId)
    {

    }

    private static void gombaf_rovarbol(int fonalId, int jatekosId, int rovarId)
    {

    }

    private static void spora_szam(int ertek)
    {

    }

    private static void rovar_hatas(int rovarId, String hatas, int ertek)
    {

    }

    private static void rovar_vag(int rovarId, int forrasId, int celId)
    {

    }

    private static void rovar_mozog(int rovarId, int forrasId, int celId)
    {

    }

    private static void rovar_eszik(int rovarId, int tektonId)
    {

    }

    public static void main(String[] args) {
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
            List<String> parancs = new ArrayList<>(List.of(scanner.next().split(" ")));

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
