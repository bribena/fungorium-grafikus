package prototipus_9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Prototipus {
    private int sporaSzam = 0;
    // tarolok az entitasokhoz, jatekosokhoz
    private final Set<String> gombaszok = new HashSet<>();
    private final Set<String> rovaraszok = new HashSet<>();
    private final Map<String, Tektonresz> tektonok = new HashMap<>();
    private final Set<String> gombafonalak = new HashSet<>();
    private final Map<String, Gombatest> gombatestek = new HashMap<>();
    private final Map<String, Rovar> rovarok = new HashMap<>();
    private final Map<String, List<String>> fonalKapcsolatok = new HashMap<>();
    private final Map<String, List<String>> szomszedsagok = new HashMap<>();
    private final Fungorium fungorium = new Fungorium();

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

    /**
     * Egy azonosítóval rendelkező entitást töröl a játéktérről.
     * Most már nem csak a Prototipus térképből, hanem a Fungorium pályáról is!
     * 
     * @param id Az entitás azonosítója (String)
     */
    private void fungorium_torol(String id) {
        boolean found = false;

        // Gombászok közül törlés
        if (gombaszok.contains(id)) {
            gombaszok.remove(id);
            found = true;
        }

        // Rovarászok közül törlés
        if (rovaraszok.contains(id)) {
            rovaraszok.remove(id);
            found = true;
        }

        // Tektonok közül törlés
        if (tektonok.containsKey(id)) {
            Tektonrész torlendo = tektonok.get(id);
            tektonok.remove(id); // Prototipus belső térképről törlés
            found = true;

            // Fungorium belső rácsából is eltávolítás
            int[] coords = fungorium.getTektonrészKoordináta(torlendo);
            int x = coords[0];
            int y = coords[1];

            if (x >= 0 && y >= 0 && x < 20 && y < 20) {
                // Beállítunk egy új TöbbfonalasTektonrészt alapállapotban
                fungorium.getTektonrész(x, y).setTektonID(-1); // "-1" jelezheti, hogy üres/törölt
            }
        }

        // Gombafonalak közül törlés
        if (gombafonalak.contains(id)) {
            gombafonalak.remove(id);
            found = true;
        }

        // Gombatestek közül törlés
        if (gombatestek.containsKey(id)) {
            gombatestek.remove(id);
            found = true;
        }

        // Rovarok közül törlés
        if (rovarok.containsKey(id)) {
            rovarok.remove(id);
            found = true;
        }

        // Kiírás
        if (found) {
            System.out.printf("fungorium torol %s -> OK: %s torolve a palyarol\n", id, id);
        } else {
            System.out.printf("fungorium torol %s -> FAIL: nincs ilyen azonositoju entitas (%s)\n", id, id);
        }
    }

    /**
     * Egy tektonrészt megpróbál eltörni (valójában globális törés).
     * Csak akkor hívja meg a Fungorium törését, ha a megadott tekton létezik.
     * 
     * @param tektonId A törni kívánt tekton azonosítója (String)
     */
    private void fungorium_tor(String tektonId) {
        // 1. Ellenőrizzük, hogy létezik-e ilyen tekton az adatainkban
        if (!tektonok.containsKey(tektonId)) {
            System.out.printf("fungorium tor %s -> FAIL: hibás tekton azonosító %s\n", tektonId, tektonId);
            return;
        }

        // 2. Globális törést hajtunk végre a Fungorium pályán
        fungorium.törés();

        // 3. Kimenet: mindig OK, ha a törés lefutott
        System.out.printf("fungorium tor %s -> OK: törés végrehajtva a pályán\n", tektonId);
    }

    /**
     * Új gombászt hoz létre a megadott azonosítóval.
     * Az azonosítók String típusúak.
     * 
     * @param id A gombász azonosítója (String)
     */
    private void gombasz_uj(String id) {
        // ha a gombaszok kozott van mar ilyen nevu jatekos, nem lehet megegy
        if (gombaszok.contains(id)) {
            System.out.printf("gombasz uj %s -> FAIL: mar van ilyen nevu jatekos (%s)\n", id, id);
        } else {
            gombaszok.add(id);
            System.out.printf("gombasz uj %s -> OK: uj gombasz %s letrehozva\n", id, id);
        }
    }

    /**
     * Új rovarászt hoz létre a megadott azonosítóval.
     * 
     * @param id A rovarász azonosítója (String)
     */
    private void rovarasz_uj(String id) {
        if (rovaraszok.contains(id)) {
            System.out.printf("rovarasz uj %s -> FAIL: mar van ilyen nevu jatekos (%s)\n", id, id);
        } else {
            rovaraszok.add(id);
            System.out.printf("rovarasz uj %s -> OK: uj rovarasz %s letrehozva\n", id, id);
        }
    }

    /**
     * Új tektonrészt hoz létre a megadott azonosítóval és típussal.
     * 
     * @param id    A tektonrész azonosítója (String)
     * @param tipus A tektonrész típusa (String)
     */
    private void tekton_uj(String id, String tipus) {
        if (TektonreszTipus.exists(tipus)) {
            Tektonrész ujTekton;

            // típus alapján példányosítjuk a megfelelő Tektonrész alosztályt
            switch (tipus.toLowerCase()) {
                case "tobbfonalas":
                    ujTekton = new TöbbfonalasTektonrész();
                    break;
                case "egyfonalas":
                    ujTekton = new EgyfonalasTektonrész();
                    break;
                case "gombatesttilto":
                    ujTekton = new GombatestTiltóTektonrész();
                    break;
                case "eletbentarto":
                    ujTekton = new ÉletbentartóTektonrész();
                    break;
                case "fonalfelszivo":
                    ujTekton = new FonalfelszívóTektonrész();
                    break;
                default:
                    // Ha valami hiba lenne, default többfonalas
                    ujTekton = new TöbbfonalasTektonrész();
                    break;
            }

            // hozzáadjuk az új Tektonrészt az azonosítóhoz
            tektonok.put(id, ujTekton);

            // OK üzenet
            System.out.printf("tekton uj %s %s -> OK: %s tekton letrehozva (%s)\n", id, tipus, id, tipus);
        } else {
            // ha a típus nem ismert
            System.out.printf("tekton uj %s %s -> FAIL: %s letrehozasa sikertelen, ismeretlen fajta\n", id, tipus, id);
        }
    }

    /**
     * Két tektonrész között szomszédsági kapcsolatot hoz létre.
     * Az azonosítók String típusúak.
     * 
     * @param t1 Az első tekton azonosítója
     * @param t2 A második tekton azonosítója
     */
    private void tekton_szomszed(String t1, String t2) {
        // 1. Hozzáadjuk t2-t t1 szomszédjaihoz
        szomszedsagok.putIfAbsent(t1, new ArrayList<>());
        if (!szomszedsagok.get(t1).contains(t2)) {
            szomszedsagok.get(t1).add(t2);
        }

        // 2. Hozzáadjuk t1-et t2 szomszédjaihoz
        szomszedsagok.putIfAbsent(t2, new ArrayList<>());
        if (!szomszedsagok.get(t2).contains(t1)) {
            szomszedsagok.get(t2).add(t1);
        }

        // 3. OK üzenet pontosan a dokumentumban megadott formátumban
        System.out.printf("tekton szomszed %s %s -> OK: (%s es %s) mostmar szomszedosak\n", t1, t2, t1, t2);
    }

    /**
     * Egy új gombatestet növeszt a megadott játékos egy megadott tektonrészre.
     * Az azonosítók String típusúak. Használja a Fungorium objektumot.
     * 
     * @param gombaId   Az új gombatest azonosítója (String)
     * @param jatekosId A játékos azonosítója (String)
     * @param tektonId  A tektonrész azonosítója (String)
     */
    private void gomba_noveszt(String gombaId, String jatekosId, String tektonId) {
        // 1. Ellenőrizzük, hogy a játékos létezik-e
        if (!gombaszok.contains(jatekosId)) {
            System.out.printf("gomba noveszt %s %s %s -> FAIL: hibás játékos név (%s)\n", gombaId, jatekosId, tektonId,
                    jatekosId);
            return;
        }

        // 2. Ellenőrizzük, hogy a tekton létezik-e a Prototípus mapban
        if (!tektonok.containsKey(tektonId)) {
            System.out.printf("gomba noveszt %s %s %s -> FAIL: hibás tekton név (%s)\n", gombaId, jatekosId, tektonId,
                    tektonId);
            return;
        }

        // 3. Megpróbáljuk lekérni a valódi Tektonrészt a Fungoriumból
        Tektonrész tekton = tektonok.get(tektonId);
        int[] coords = fungorium.getTektonrészKoordináta(tekton);
        int x = coords[0];
        int y = coords[1];

        // Ha nem található meg a pályán, hibát írunk
        if (x < 0 || y < 0 || x >= 20 || y >= 20) {
            System.out.printf("gomba noveszt %s %s %s -> FAIL: hibás tekton név (%s)\n", gombaId, jatekosId, tektonId,
                    tektonId);
            return;
        }

        // 4. Ellenőrizzük, hogy a tekton típusa tiltott-e (pl.
        // GombatestTiltóTektonrész)
        Tektonrész valodiTekton = fungorium.getTektonrész(x, y);
        if (valodiTekton instanceof GombatestTiltóTektonrész) {
            System.out.printf(
                    "gomba noveszt %s %s %s -> FAIL: nem lehet gombatestet növeszteni %s-n (GombatestTilto)\n", gombaId,
                    jatekosId, tektonId, tektonId);
            return;
        }

        // 5. Ellenőrizzük, hogy a tektonon van-e már gombatest
        if (gombatestek.containsKey(tektonId)) {
            System.out.printf("gomba noveszt %s %s %s -> FAIL: már van gombatest %s-n\n", gombaId, jatekosId, tektonId,
                    tektonId);
            return;
        }

        // 6. Minden ellenőrzés sikeres: új Gombatest létrehozása
        Gombatest ujGombatest = new Gombatest();
        gombatestek.put(tektonId, ujGombatest);

        // 7. OK üzenet
        System.out.printf("gomba noveszt %s %s %s -> OK: új gomba %s létrehozva %s-n\n", gombaId, jatekosId, tektonId,
                gombaId, tektonId);
    }

    /**
     * Egy meglévő gombatestet fejleszt tovább.
     * Az azonosítók String típusúak.
     * 
     * @param gombaId A gombatest azonosítója (String)
     */
    private void gomba_fejleszt(String gombaId) {
        // Ha nem létezik ilyen gombatest
        if (!gombatestek.containsKey(gombaId)) {
            System.out.printf("gomba fejleszt %s -> FAIL: hibás gomba név (%s)\n", gombaId, gombaId);
            return;
        }

        // Lekérjük a Gombatest objektumot
        Gombatest gomba = gombatestek.get(gombaId);

        // Ha még nincs fejlesztve
        if (!gomba.isFejlesztve()) {
            gomba.setFejlodott(true);
            System.out.printf("gomba fejleszt %s -> OK: %s fejlesztve\n", gombaId, gombaId);
        }
        // Ha már fejlesztve volt
        else {
            System.out.printf("gomba fejleszt %s -> FAIL: %s már fejlesztve van\n", gombaId, gombaId);
        }
    }

    /**
     * Egy gombatest megpróbál spórát szórni.
     * Ellenőrzi a gombatest létezését és fejlettségét.
     * 
     * @param gombaId A gombatest azonosítója
     */
    private void gomba_szoras(int gombaId) {
        // 1. Ellenőrizzük, hogy létezik-e a gombatest
        if (!gombatestek.containsKey(gombaId)) {
            System.out.printf("gomba szoras %d -> FAIL: hibás gomba név (%d)\n", gombaId, gombaId);
            return;
        }

        // 2. Megnézzük, hogy fejlődött-e a gomba
        boolean fejlett = gombatestek.get(gombaId);

        if (fejlett) {
            // 3. Ha fejlődött, sikeres szórás
            System.out.printf("gomba szoras %d -> OK: %d sporat szort\n", gombaId, gombaId);
        } else {
            // 4. Ha még nem fejlődött ki, nem tud szórni
            System.out.printf("gomba szoras %d -> FAIL: %d nem tud sporat szorni\n", gombaId, gombaId);
        }
    }

    /**
     * Két tektonrész közötti gombafonalat szakítja el.
     * Ellenőrzi a tektonok létezését és a fonal meglétét.
     * 
     * @param forrasId A forrás tekton azonosítója
     * @param celId    A cél tekton azonosítója
     */
    private void gombaf_szakit(int forrasId, int celId) {
        // 1. Ellenőrizzük, hogy mindkét tekton létezik-e
        if (!tektonok.containsKey(forrasId) || !tektonok.containsKey(celId)) {
            System.out.printf("gombaf szakit %d %d -> FAIL: hibas tekton azonostio(k)\n", forrasId, celId);
            return;
        }

        // 2. Keresünk fonalat, ami a forrasId és celId között van
        boolean sikeresVagas = false;
        int torlendoFonalId = -1;

        for (Map.Entry<Integer, List<Integer>> entry : fonalKapcsolatok.entrySet()) {
            List<Integer> par = entry.getValue();
            if ((par.get(0) == forrasId && par.get(1) == celId) || (par.get(0) == celId && par.get(1) == forrasId)) {
                torlendoFonalId = entry.getKey();
                sikeresVagas = true;
                break;
            }
        }

        if (sikeresVagas) {
            // 3. Ha van fonal, eltávolítjuk
            gombafonalak.remove(torlendoFonalId);
            fonalKapcsolatok.remove(torlendoFonalId);

            // 4. OK üzenet
            System.out.printf("gombaf szakit %d %d -> OK: gombafonal elszakitva a ket megadott tekton kozott\n",
                    forrasId, celId);
        } else {
            // 5. Ha nincs fonal, FAIL üzenet
            System.out.printf("gombaf szakit %d %d -> FAIL: hibas tekton azonostio(k)\n", forrasId, celId);
        }
    }

    private void gombaf_noveszt(int fonalId, int forrasId, int celId) {
        if (gombafonalak.contains(fonalId) && tektonok.containsKey(forrasId) && tektonok.containsKey(celId)) {
            gombafonalak.add(fonalId);
            fonalKapcsolatok.put(fonalId, List.of(forrasId, celId));
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

    /**
     * Egy rovar megpróbálja elvágni a két megadott tekton közötti gombafonalat.
     * Ellenőrzi a rovar állapotát és a fonal létezését a kapcsolat alapján.
     * 
     * @param rovarId  A rovar azonosítója
     * @param forrasId A forrás tekton azonosítója
     * @param celId    A cél tekton azonosítója
     */
    private void rovar_vag(int rovarId, int forrasId, int celId) {
        // 1. Ellenőrizzük, hogy a rovar létezik-e
        if (!rovarok.containsKey(rovarId)) {
            System.out.printf("rovar vag %d %d %d -> FAIL: hibás rovar azonosító (%d)\n", rovarId, forrasId, celId,
                    rovarId);
            return;
        }

        // 2. Ellenőrizzük, hogy a rovar nem "bena" vagy "gyenge"
        String statusz = rovarok.get(rovarId);
        int bena = Character.getNumericValue(statusz.charAt(3)); // "béna" státusz: 4. karakter
        int gyenge = Character.getNumericValue(statusz.charAt(2)); // "gyenge" státusz: 3. karakter

        if (bena > 0 || gyenge > 0) {
            System.out.printf("rovar vag %d %d %d -> FAIL: rovar bena/gyenge, nem vaghat (%d)\n", rovarId, forrasId,
                    celId, rovarId);
            return;
        }

        // 3. Keresünk olyan fonalat, ami a forrasId és celId között húzódik
        boolean sikeresVagas = false;
        int torlendoFonalId = -1;

        for (Map.Entry<Integer, List<Integer>> entry : fonalKapcsolatok.entrySet()) {
            List<Integer> par = entry.getValue();
            if ((par.get(0) == forrasId && par.get(1) == celId) || (par.get(0) == celId && par.get(1) == forrasId)) {
                // Megtaláltuk a fonalat
                torlendoFonalId = entry.getKey();
                sikeresVagas = true;
                break;
            }
        }

        if (sikeresVagas) {
            // 4. Töröljük a fonalat mindkét helyről
            gombafonalak.remove(torlendoFonalId);
            fonalKapcsolatok.remove(torlendoFonalId);

            System.out.printf("rovar vag %d %d %d -> OK: fonal elvágva %d és %d között\n", rovarId, forrasId, celId,
                    forrasId, celId);
        } else {
            // 5. Ha nincs ilyen fonal
            System.out.printf("rovar vag %d %d %d -> FAIL: nincs fonal %d es %d kozott\n", rovarId, forrasId, celId,
                    forrasId, celId);
        }
    }

    /**
     * Egy rovar megpróbál átmászni a forrás tektonról a cél tektonra.
     * Ellenőrzi a rovar állapotát és a fonal meglétét.
     * 
     * @param rovarId  A rovar azonosítója
     * @param forrasId A forrás tekton azonosítója
     * @param celId    A cél tekton azonosítója
     */
    private void rovar_mozog(int rovarId, int forrasId, int celId) {
        // 1. Ellenőrizzük, hogy a rovar létezik-e
        if (!rovarok.containsKey(rovarId)) {
            System.out.printf("rovar mozog %d %d %d -> FAIL: hibás rovar azonosító (%d)\n", rovarId, forrasId, celId,
                    rovarId);
            return;
        }

        // 2. Ellenőrizzük, hogy a rovar nem "bena"
        String statusz = rovarok.get(rovarId);
        int bena = Character.getNumericValue(statusz.charAt(3)); // 4. karakter: "bena"

        if (bena > 0) {
            System.out.printf("rovar mozog %d %d %d -> FAIL: rovar bena, nem mozoghat (%d)\n", rovarId, forrasId, celId,
                    rovarId);
            return;
        }

        // 3. Ellenőrizzük, hogy van-e fonal közöttük
        boolean vanFonal = false;

        for (Map.Entry<Integer, List<Integer>> entry : fonalKapcsolatok.entrySet()) {
            List<Integer> par = entry.getValue();
            if ((par.get(0) == forrasId && par.get(1) == celId) || (par.get(0) == celId && par.get(1) == forrasId)) {
                vanFonal = true;
                break;
            }
        }

        if (vanFonal) {
            // 4. Sikeres mozgás
            System.out.printf("rovar mozog %d %d %d -> OK: rovar mozog %d -rol %d -ra\n", rovarId, forrasId, celId,
                    forrasId, celId);
        } else {
            // 5. Ha nincs fonal
            System.out.printf("rovar mozog %d %d %d -> FAIL: nincs fonal %d es %d kozott\n", rovarId, forrasId, celId,
                    forrasId, celId);
        }
    }

    /**
     * Egy rovar megpróbál spórát enni a megadott tektonról.
     * Ha nem "bena", akkor 1 spórával csökkenti a spórák számát.
     * 
     * @param rovarId  A rovar azonosítója
     * @param tektonId A tektonrész azonosítója
     */
    private void rovar_eszik(int rovarId, int tektonId) {
        // 1. Ellenőrizzük, hogy a rovar létezik-e
        if (!rovarok.containsKey(rovarId)) {
            System.out.printf("rovar eszik %d %d -> FAIL: hibás rovar azonosító (%d)\n", rovarId, tektonId, rovarId);
            return;
        }

        // 2. Ellenőrizzük, hogy a rovar nem "bena"
        String statusz = rovarok.get(rovarId);
        int bena = Character.getNumericValue(statusz.charAt(3)); // 4. karakter: "bena"

        if (bena > 0) {
            System.out.printf("rovar eszik %d %d -> FAIL: rovar bena, nem ehet (%d)\n", rovarId, tektonId, rovarId);
            return;
        }

        // 3. Ha létezik a tekton, csökkentjük a spórák számát 1-gyel
        if (tektonok.containsKey(tektonId)) {
            int aktualisSpora = tektonok.get(tektonId);
            if (aktualisSpora > 0) {
                tektonok.put(tektonId, aktualisSpora - 1);
            }
            // Ha 0 spóra van, akkor is kiírjuk az OK üzenetet
        }

        // 4. OK üzenet
        System.out.printf("rovar eszik %d %d -> OK: sporak elfogyasztva %d-rol\n", rovarId, tektonId, tektonId);
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
