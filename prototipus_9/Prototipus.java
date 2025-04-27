package prototipus_9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Prototipus {
    private int sporaSzam = 0;
    // tarolok az entitasokhoz, jatekosokhoz
    private final Set<Integer> gombaszok = new HashSet<>();
    private final Set<Integer> rovaraszok = new HashSet<>();
    private final Map<Integer, Integer> tektonok = new HashMap<>();
    private final Set<Integer> gombafonalak = Set.of(1, 2, 3, 4, 5);
    private final Map<Integer, Boolean> gombatestek = new HashMap<>();
    private final Map<Integer, String> rovarok = new HashMap<>();

    {
        gombatestek.put(6, true);
        gombatestek.put(3, false);
        gombatestek.put(4, false);
        gombatestek.put(2, true);
    }

    // fajlbol valo parancsbeolvasas
    private void futtat(String file) {
        try (Scanner scanner = new Scanner(new File(file))) {
            while (scanner.hasNextLine()) {
                List<String> parancs = new ArrayList<>(List.of(scanner.nextLine().split(" ")));
                // parancsokat feldolhozo fuggveny meghivasa
                parancsFeldolgoz(parancs);
            }
            // sikeres futattas
            System.out.printf("futtat %s -> OK: Fajl futtatva: %s\n", file, file);
        } catch (FileNotFoundException e) {
            // barmi hiba eseten sikertelen
            System.out.printf("futtat %s -> FAIL: fajl nem talalhato: %S", file, file);
        }
    }

    private void load() {
        try (Scanner scanner = new Scanner(new File("config.txt"))) {
            System.out.println("load -> Konfiguráció betöltve:");
            while (scanner.hasNextLine()) {
                String sor = scanner.nextLine();
                System.out.println(sor); // kiírjuk a beolvasott sort
                List<String> parancs = new ArrayList<>(List.of(sor.split(" ")));
                parancsFeldolgoz(parancs); // feldolgozzuk a parancsot
            }
        } catch (FileNotFoundException e) {
            System.out.println("load -> FAIL: konfigurációs fájl nem található");
        }
    }

    private void fungorium_torol(int id) {
        boolean found = false;

        // Keresés és törlés a gombászok között
        if (gombaszok.contains(id)) {
            gombaszok.remove(id);
            found = true;
        }

        // Keresés és törlés a rovarászok között
        if (rovaraszok.contains(id)) {
            rovaraszok.remove(id);
            found = true;
        }

        // Keresés és törlés a tektonok között
        if (tektonok.containsKey(id)) {
            tektonok.remove(id);
            found = true;
        }

        // Keresés és törlés a gombafonalak között
        if (gombafonalak.contains(id)) {
            found = true;
        }

        // Keresés és törlés a gombatestek között
        if (gombatestek.containsKey(id)) {
            gombatestek.remove(id);
            found = true;
        }

        // Keresés és törlés a rovarok között
        if (rovarok.containsKey(id)) {
            rovarok.remove(id);
            found = true;
        }

        // Ha bárhol töröltünk, kiírjuk az azonosítót
        if (found) {
            System.out.printf("fungorium torol %d -> OK: %d torolve a palyarol\n", id, id);
        } else {
            System.out.printf("fungorium torol %d -> FAIL: nincs ilyen azonositoju entitas (%d)\n", id, id);
        }
    }

    private void fungorium_tor(int tektonId) {

    }

    private void gombasz_uj(int id) {
        // ha a gombaszok kozott van mar ilyen nevu jatekos, nem lehet megegy
        if (gombaszok.contains(id)) {
            System.out.printf("gombasz uj %d -> FAIL: mar van ilyen nevu jatekos (%d)\n", id, id);
            // amugy letrehozhatjuk
        } else {
            gombaszok.add(id);
            System.out.printf("gombasz uj %d -> OK: uj gombasz %d letrehozva\n", id, id);
        }
    }

    private void rovarasz_uj(int id) {
        // ha a rovaraszok kozott van mar ilyen nevu jatekos, nem lehet megegy
        if (rovaraszok.contains(id)) {
            System.out.printf("rovarasz uj %d -> FAIL: mar van ilyen nevu jatekos (%d)\n", id, id);
            // amugy letrehozhatjuk
        } else {
            rovaraszok.add(id);
            System.out.printf("rovarasz uj %d -> OK: uj rovarasz %d letrehozva\n", id, id);
        }
    }

    private void tekton_uj(int id, String tipus) {
        // ha a megadott tipus letezik, letrehozzuk az uj tektont
        if (TektonreszTipus.exsists(tipus)) {
            tektonok.put(id, 0);
            System.out.printf("tekton uj %d %s -> OK: %d tekton letrehozva (%s)\n", id, tipus, id, tipus);
            // egyebkent hiba, mert rossz a fajta
        } else {
            System.out.printf("tekton uj %d %s -> FAIL: %d letrehozasa sikertelen, ismeretlen fajta\n", id, tipus, id);
        }
    }

    private void tekton_szomszed(int t1_id, int t2_id) {
        System.out.printf("tekton szomszed %d %d -> OK: %d es %d mostmar szomszedosak\n", t1_id, t2_id, t1_id, t2_id);
    }

    private void gomba_noveszt(int gombaId, int jatekosId, int tektonId) {

    }

    private void gomba_fejleszt(int gombaId) {
        if (gombatestek.containsKey(gombaId) && !gombatestek.get(gombaId)) {
            gombatestek.replace(gombaId, true);
            System.out.printf("gomba fejleszt %d -> OK: %d fejlesztve", gombaId, gombaId);
        } else if (gombatestek.containsKey(gombaId) && gombatestek.get(gombaId)) {
            System.out.printf("gomba fejleszt %d -> FAIL: %d mar fejlesztve van", gombaId, gombaId);
        } else if (!gombatestek.containsKey(gombaId)) {
            System.out.printf("gomba fejleszt %d -> FAIL: hibas gombanev (%d)", gombaId, gombaId);
        }
    }

    private void gomba_szoras(int gombaId) {
        // ellenorizzuk, hogy a gombaId benne van-e a gombak listaban
        if (!gombatestek.containsKey(gombaId)) {
            System.out.printf("gomba szoras %d -> FAIL: hibas gombanev (%d)\n", gombaId, gombaId);
        } else {
            // ha minden oke, sikeres
            System.out.printf("gomba szoras %d -> OK: %d sporat szort\n", gombaId, gombaId);
        }
    }

    private void gombaf_szakit(int forrasId, int celId) {
        // ha nem leteznek a tektonok, fail
        if (!tektonok.containsKey(forrasId) || !tektonok.containsKey(celId)) {
            System.out.printf("gombaf szakit %d %d -> FAIL: hibas tekton azonositok (vagy nincs fonal koztuk)",
                    forrasId, celId);
        }
        System.out.printf("gombaf szakit %d %d -> OK: gombafonal elszakitva a ket megadott tekton kozott", forrasId,
                celId);
    }

    private void gombaf_noveszt(int fonalId, int forrasId, int celId) {
        if (gombafonalak.contains(fonalId) && tektonok.containsKey(forrasId) && tektonok.containsKey(celId)) {
            System.out.printf("gombaf noveszt %d %d %d -> OK: %d novesztve %d es %d kozott", fonalId, forrasId, celId,
                    fonalId, forrasId, celId);
        } else if (!tektonok.containsKey(forrasId) || !tektonok.containsKey(celId)) {
            System.out.printf("gombaf noveszt %d %d %d -> FAIL: gombafonal nem novesztheto %d es %d kozott", fonalId,
                    forrasId, celId, forrasId, celId);
        }
    }

    private void gombaf_rovarbol(int fonalId, int jatekosId, int rovarId) {
        if (gombafonalak.contains(fonalId) && rovarok.containsKey(rovarId) &&
                (gombaszok.contains(jatekosId) || rovaraszok.contains(jatekosId))) {
            System.out.printf("gombaf rovarbol %d %d %d -> OK: uj gomba %d letrehozva %d helyen\n", fonalId, jatekosId,
                    rovarId, fonalId, rovarId);
        } else if (!(gombaszok.contains(jatekosId) || rovaraszok.contains(jatekosId))) {
            System.out.printf("gombaf rovarbol %d %d %d -> FAIL: hibas jatekos nev (%d)\n", fonalId, jatekosId, rovarId,
                    jatekosId);
        } else if (!rovarok.containsKey(rovarId)) {
            System.out.printf("gombaf rovarbol %d %d %d -> FAIL: hibas rovar nev (%d)\n", fonalId, jatekosId, rovarId,
                    rovarId);
        } else if (rovarok.containsKey(rovarId) && rovarok.get(rovarId) == "0") {
            System.out.printf("gombaf rovarbol %d %d %d -> FAIL: %d nem bena\n", fonalId, jatekosId, rovarId, rovarId);
        }
    }

    private void spora_szam(int ertek, int tektonId) {
        // spora_szam beallitasa
        if (ertek < 0) {
            System.out.printf("spora szam %d -> FAIL: hibas sporaszam\n", ertek);
        } else {
            tektonok.replace(tektonId, ertek);
            System.out.printf("spora szam %d -> OK: sporaszam beallitva %d tektonon: %d\n", ertek, ertek, ertek);
        }
    }

    private void rovar_hatas(int rovarId, String hatas, int ertek) {
        // hatasok beallitasa
        if (rovarok.containsKey(rovarId)) {
            if (hatas == "gyors") {
                String ujHatas = Integer.toString(ertek) +
                        rovarok.get(rovarId).substring(1, 2) +
                        rovarok.get(rovarId).substring(2, 3) +
                        rovarok.get(rovarId).substring(3, 4) +
                        rovarok.get(rovarId).substring(4);
                rovarok.replace(rovarId, ujHatas);
            } else if (hatas == "lassu") {
                String ujHatas = rovarok.get(rovarId).substring(0, 1) +
                        Integer.toString(ertek) +
                        rovarok.get(rovarId).substring(2, 3) +
                        rovarok.get(rovarId).substring(3, 4) +
                        rovarok.get(rovarId).substring(4);
                rovarok.replace(rovarId, ujHatas);
            } else if (hatas == "gyenge") {
                String ujHatas = rovarok.get(rovarId).substring(0, 1) +
                        rovarok.get(rovarId).substring(1, 2) +
                        Integer.toString(ertek) +
                        rovarok.get(rovarId).substring(3, 4) +
                        rovarok.get(rovarId).substring(4);
                rovarok.replace(rovarId, ujHatas);
            } else if (hatas == "bena") {
                String ujHatas = rovarok.get(rovarId).substring(0, 1) +
                        rovarok.get(rovarId).substring(1, 2) +
                        rovarok.get(rovarId).substring(2, 3) +
                        Integer.toString(ertek) +
                        rovarok.get(rovarId).substring(4);
                rovarok.replace(rovarId, ujHatas);
            } else if (hatas == "osztodik") {
                String ujHatas = rovarok.get(rovarId).substring(0, 1) +
                        rovarok.get(rovarId).substring(1, 2) +
                        rovarok.get(rovarId).substring(2, 3) +
                        rovarok.get(rovarId).substring(3, 4);
                Integer.toString(ertek);
                rovarok.replace(rovarId, ujHatas);
            }
            System.out.printf("rovar hatas %d %s %d -> OK: %s beallitva %d-re", rovarId, hatas, ertek, hatas, ertek);
        } else if (!rovarok.containsKey(rovarId)) {
            System.out.printf("rovar hatas %d %s %d -> FAIL: hibas rovar nev (%d)", rovarId, hatas, ertek, rovarId);
        } else if (ertek < 0) {
            System.out.printf("rovar hatas %d %s %d -> FAIL: invalid ertek (%d)", rovarId, hatas, ertek, ertek);
        }
    }

    private void rovar_vag(int rovarId, int forrasId, int celId) {

    }

    private void rovar_mozog(int rovarId, int forrasId, int celId) {

    }

    private void rovar_eszik(int rovarId, int tektonId) {

    }

    private void parancsFeldolgoz(List<String> parancs) {
        if (parancs.size() < 5) {
            for (int i = parancs.size(); i < 5; i++) {
                parancs.add("");
            }
        }
        // parancsok listaja
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

        // mivel vannak egyszavas es ketszavas parancsok is, ezert inkabb ujabb
        // switchcase/if-be foglaltam a ketszavasakat ahogy lentebb lathato
        switch (parancs.get(0)) {
            // help fuggvény az osszes használhato parancshoz
            case "help":
                for (int i = 0; i < parancsok.length; i++) {
                    System.out.println(parancsok[i]);
                }
                break;
            // exit a programbol valo kilepeshez
            case "exit":
                System.exit(0);
                break;
            // futtat egy letezo fajlban levo parancsok beolvasasahoz es vegrehajtasahiz
            case "futtat":
                futtat(parancs.get(1));
                break;
            // elore megadott palya inicializacio betoltese es vegrehajtasa
            case "load":
                load();
                break;
            case "fungorium":
                switch (parancs.get(1)) {
                    // adott azonositoju entitas torlesehez
                    case "torol":
                        fungorium_torol(Integer.parseInt(parancs.get(2)));
                        break;
                    // palya szettoresehez (ha sikeresen sorsol)
                    case "tor":
                        fungorium_tor(Integer.parseInt(parancs.get(2)));
                        break;
                    default:
                        break;
                }
                break;
            // gombasz jatekos letrehozasahoz
            case "gombasz":
                if (parancs.get(1).equals("uj")) {
                    gombasz_uj(Integer.parseInt(parancs.get(2)));
                }
                break;
            // rovarasz jatekos letrehozasahoz
            case "rovarasz":
                if (parancs.get(1).equals("uj")) {
                    rovarasz_uj(Integer.parseInt(parancs.get(2)));
                }
                break;
            case "tekton":
                switch (parancs.get(1)) {
                    // uj tekton hozzaadasahoz
                    case "uj":
                        tekton_uj(Integer.parseInt(parancs.get(2)), parancs.get(3));
                        break;
                    // ket tekton szomszedossa tetelehez
                    case "szomszed":
                        tekton_szomszed(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)));
                        break;
                    default:
                        break;
                }
                break;
            case "gomba":
                switch (parancs.get(1)) {
                    // gomba novesztese, ha lehetseges
                    case "noveszt":
                        gomba_noveszt(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)),
                                Integer.parseInt(parancs.get(4)));
                        break;
                    // gomba fejlesztese, ha lehetseges
                    case "fejleszt":
                        gomba_fejleszt(Integer.parseInt(parancs.get(2)));
                        break;
                    // spora szoras kornyezo tektonokra
                    case "szoras":
                        gomba_szoras(Integer.parseInt(parancs.get(2)));
                        break;
                    default:
                        break;
                }
                break;
            case "gombaf":
                switch (parancs.get(1)) {
                    // fonal elszakitasa
                    case "szakit":
                        gombaf_szakit(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)));
                        break;
                    // fonal novesztese egy iranyba
                    case "noveszt":
                        gombaf_noveszt(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)),
                                Integer.parseInt(parancs.get(4)));
                        break;
                    // gombatest novesztes fonalbol egy bena rovar helyere
                    case "rovarbol":
                        gombaf_rovarbol(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)),
                                Integer.parseInt(parancs.get(4)));
                        break;
                    default:
                        break;
                }
                break;
            // sporaszam beallitasa
            case "spora":
                if (parancs.get(1).equals("szam")) {
                    spora_szam(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)));
                }
                break;
            case "rovar":
                switch (parancs.get(1)) {
                    // hatas beallitasa
                    case "hatas":
                        rovar_hatas(Integer.parseInt(parancs.get(2)), parancs.get(3), Integer.parseInt(parancs.get(4)));
                        break;
                    // fonalvagas
                    case "vag":
                        rovar_vag(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)),
                                Integer.parseInt(parancs.get(4)));
                        break;
                    // mozgas
                    case "mozog":
                        rovar_mozog(Integer.parseInt(parancs.get(2)), Integer.parseInt(parancs.get(3)),
                                Integer.parseInt(parancs.get(4)));
                        break;
                    // eves
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

    // main fuggveny
    public void proto() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // amig van aparncs, olvassa a sorokat
            List<String> parancs = new ArrayList<>(List.of(scanner.nextLine().split(" ")));
            if (parancs.get(0).equals("exit"))
                break;
            parancsFeldolgoz(parancs);
        }
    }
}
