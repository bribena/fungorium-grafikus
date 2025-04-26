package prototipus_9;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static void fungorium_torol(int azonosito)
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
