package prototipus_9;

import java.util.HashMap;
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
            String[] parancs = scanner.next().split(" ");

            switch (parancs[0]) {
                case "help":
                    for (int i = 0; i < parancsok.length; i++)
                    {
                        System.out.println(parancsok[i]);
                    }
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    break;
            }
        }
    }
}
