package prototipus_9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Prototipus {
    private int sporaSzam = 0;
    // tarolok az entitasokhoz, jatekosokhoz
    private final Set<String> gombaszok = new HashSet<>();
    private final Set<String> rovaraszok = new HashSet<>();
    private final Map<String, Tektonrész> tektonok = new HashMap<>();
    private final Map<String, Gombafonal> gombafonalak = new HashMap<>();
    private final Map<String, Gombatest> gombatestek = new HashMap<>();
    private final Map<String, Rovar> rovarok = new HashMap<>();
    private final Map<String, List<String>> fonalKapcsolatok = new HashMap<>();
    private final Map<String, List<String>> szomszedsagok = new HashMap<>();
    private final Fungorium fungorium = new Fungorium();

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
        if (gombafonalak.containsKey(id)) {
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

            // új: megpróbáljuk elhelyezni a pályán!
            boolean sikeres = fungorium.ujTektonElhelyezese(ujTekton);

            if (!sikeres) {
                System.out.printf("tekton uj %s %s -> FAIL: nem sikerült pályára helyezni\n", id, tipus);
                return;
            }

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
        Gombatest ujGombatest = new Gombatest(false);
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
        if (!gomba.isFejlődött()) {
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
     * @param gombaId A gombatest azonosítója (String)
     */
    private void gomba_szoras(String gombaId) {
        // 1. Ellenőrizzük, hogy létezik-e a gombatest
        if (!gombatestek.containsKey(gombaId)) {
            System.out.printf("gomba szoras %s -> FAIL: hibás gomba név (%s)\n", gombaId, gombaId);
            return;
        }

        // 2. Lekérjük a Gombatest objektumot
        Gombatest gomba = gombatestek.get(gombaId);

        // 3. Megnézzük, hogy fejlődött-e a gomba
        if (gomba.fejlődik()) {
            // 4. Ha fejlődött, sikeres szórás
            System.out.printf("gomba szoras %s -> OK: %s sporat szort\n", gombaId, gombaId);
        } else {
            // 5. Ha még nem fejlődött ki, nem tud szórni
            System.out.printf("gomba szoras %s -> FAIL: %s nem tud sporat szorni\n", gombaId, gombaId);
        }
    }

    /**
     * Két tektonrész közötti gombafonalat szakítja el.
     * Ellenőrzi a tektonok létezését és a fonal meglétét.
     * 
     * @param forrasId A forrás tekton azonosítója (String)
     * @param celId    A cél tekton azonosítója (String)
     */
    private void gombaf_szakit(String forrasId, String celId) {
        // 1. Ellenőrizzük, hogy mindkét tekton létezik-e
        if (!tektonok.containsKey(forrasId) || !tektonok.containsKey(celId)) {
            System.out.printf("gombaf szakit %s %s -> FAIL: hibás tekton azonostio(k)\n", forrasId, celId);
            return;
        }

        // 2. Keresünk fonalat, ami a forrás és cél között van
        boolean sikeresVagas = false;
        String torlendoFonalId = null;

        for (Map.Entry<String, List<String>> entry : fonalKapcsolatok.entrySet()) {
            List<String> par = entry.getValue();
            if ((par.get(0).equals(forrasId) && par.get(1).equals(celId)) ||
                    (par.get(0).equals(celId) && par.get(1).equals(forrasId))) {
                torlendoFonalId = entry.getKey();
                sikeresVagas = true;
                break;
            }
        }

        if (sikeresVagas && torlendoFonalId != null) {
            // 3. Lekérjük a törlendő Gombafonalt
            Gombafonal fonal = gombafonalak.get(torlendoFonalId);

            if (fonal != null) {
                // 4. A kapcsolódó fonalakat is eltávolítjuk
                Gombafonal[] kapcsolatok = fonal.getKapcsolodoFonalak();
                for (int i = 0; i < 4; ++i) {
                    if (kapcsolatok[i] != null) {
                        // A másik fonalból is töröljük a visszamutatást
                        Gombafonal szomszed = kapcsolatok[i];
                        Gombafonal[] szomszedKapcs = szomszed.getKapcsolodoFonalak();
                        for (int j = 0; j < 4; ++j) {
                            if (szomszedKapcs[j].equals(fonal)) {
                                szomszedKapcs[j] = null;
                                break;
                            }
                        }
                    }
                }
            }

            // 5. Eltávolítjuk a fonalat a mapokból
            gombafonalak.remove(torlendoFonalId);
            fonalKapcsolatok.remove(torlendoFonalId);

            // 6. OK üzenet
            System.out.printf("gombaf szakit %s %s -> OK: gombafonal elszakitva a ket megadott tekton kozott\n",
                    forrasId, celId);
        } else {
            // 7. Ha nincs ilyen fonal
            System.out.printf("gombaf szakit %s %s -> FAIL: hibás tekton azonostio(k)\n", forrasId, celId);
        }
    }

    /**
     * Új gombafonal növesztése két tektonrész között.
     * 
     * @param fonalId  Az új gombafonal azonosítója (String)
     * @param forrasId A forrás tekton azonosítója (String)
     * @param celId    A cél tekton azonosítója (String)
     */
    private void gombaf_noveszt(String fonalId, String forrasId, String celId) {
        // 1. Ellenőrizzük, hogy mindkét tekton létezik-e
        if (!tektonok.containsKey(forrasId) || !tektonok.containsKey(celId)) {
            System.out.printf("gombaf noveszt %s %s %s -> FAIL: gombafonal nem novesztheto %s es %s kozott\n",
                    fonalId, forrasId, celId, forrasId, celId);
            return;
        }

        // 2. Ellenőrizzük, hogy létezik-e már ilyen fonal ID
        if (gombafonalak.containsKey(fonalId)) {
            System.out.printf("gombaf noveszt %s %s %s -> FAIL: ilyen fonal ID (%s) mar letezik\n",
                    fonalId, forrasId, celId, fonalId);
            return;
        }

        // 3. Létrehozzuk az új Gombafonal példányt
        Gombafonal ujFonal = new Gombafonal(new Gombafonal(null)); // Alapból egy üres példányból példányosítva

        // 4. Hozzáadjuk a fonalat a mapokhoz
        gombafonalak.put(fonalId, ujFonal);
        fonalKapcsolatok.put(fonalId, List.of(forrasId, celId));

        // 5. OK üzenet
        System.out.printf("gombaf noveszt %s %s %s -> OK: %s novesztve %s es %s kozott\n",
                fonalId, forrasId, celId, fonalId, forrasId, celId);
    }

    /**
     * Új gombafonal növesztése egy bénult rovarból.
     * Ellenőrzi a gombafonal, a rovar és a játékos létezését és állapotát,
     * majd ténylegesen létrehozza az új Gombafonal példányt.
     * 
     * @param fonalId   Az új gombafonal azonosítója (String)
     * @param jatekosId A játékos azonosítója (String)
     * @param rovarId   A rovar azonosítója (String)
     */
    private void gombaf_rovarbol(String fonalId, String jatekosId, String rovarId) {
        // 1. Ellenőrizzük a játékos létezését
        if (!(gombaszok.contains(jatekosId) || rovaraszok.contains(jatekosId))) {
            System.out.printf("gombaf rovarbol %s %s %s -> FAIL: hibás játékos név (%s)\n", fonalId, jatekosId, rovarId,
                    jatekosId);
            return;
        }

        // 2. Ellenőrizzük a rovar létezését
        if (!rovarok.containsKey(rovarId)) {
            System.out.printf("gombaf rovarbol %s %s %s -> FAIL: hibás rovar név (%s)\n", fonalId, jatekosId, rovarId,
                    rovarId);
            return;
        }

        // 3. Ellenőrizzük a rovar bénaságát
        Rovar rovar = rovarok.get(rovarId);
        if (!rovar.isBena()) {
            System.out.printf("gombaf rovarbol %s %s %s -> FAIL: %s nem béna\n", fonalId, jatekosId, rovarId, rovarId);
            return;
        }

        // 4. Ellenőrizzük, hogy nincs-e már ilyen fonal ID
        if (gombafonalak.containsKey(fonalId)) {
            System.out.printf("gombaf rovarbol %s %s %s -> FAIL: ilyen fonal ID (%s) már létezik\n", fonalId, jatekosId,
                    rovarId, fonalId);
            return;
        }

        // 5. Tényleges gombafonal létrehozás
        Gombafonal ujFonal = new Gombafonal(new Gombafonal(null)); // példányosítás másolatból (most üresen)

        // 6. Hozzáadjuk az új fonalat a gombafonalakhoz
        gombafonalak.put(fonalId, ujFonal);

        // 7. Rovar helyének kiderítése
        Tektonrész rovarTekton = rovar.getTektonrész(); // Feltételezzük, hogy van ilyen getter, amit bevezettünk

        if (rovarTekton != null) {
            rovarTekton.entitásHozzáadás(ujFonal);
        }

        // 8. Kimenet - OK üzenet
        System.out.printf("gombaf rovarbol %s %s %s -> OK: új gomba %s létrehozva %s helyen\n", fonalId, jatekosId,
                rovarId, fonalId, rovarId);
    }

    /**
     * A megadott tektonrész spóraszámát beállítja.
     * 
     * @param ertek    Spórák száma
     * @param tektonId A tektonrész azonosítója (String)
     */
    private void spora_szam(int ertek, String tektonId) {
        if (ertek < 0) {
            System.out.printf("spora szam %d -> FAIL: hibás spóraszám\n", ertek);
            return;
        }

        if (!tektonok.containsKey(tektonId)) {
            System.out.printf("spora szam %d -> FAIL: hibás tekton azonosító (%s)\n", ertek, tektonId);
            return;
        }
        Tektonrész tekton = tektonok.get(tektonId);
        if (tekton != null) {
            tekton.setSporaSzam(ertek);
            System.out.printf("spora szam %d -> OK: sporaszam beallitva %d tektonon: %d\n", ertek, ertek, tektonId);
        } else {
            System.out.printf("spora szam %d -> FAIL: hibas tekton azonostio (%d)\n", ertek, tektonId);
        }

        System.out.printf("spora szam %d -> OK: spóraszám beállítva %s tektonon: %d\n", ertek, tektonId, ertek);
    }

    /**
     * Egy rovar hatásának kézi beállítása.
     * 
     * @param rovarId A rovar azonosítója (String)
     * @param hatas   A hatás neve (gyors, lassu, gyenge, bena, osztodik)
     * @param ertek   Az új érték
     */
    private void rovar_hatas(String rovarId, String hatas, int ertek) {
        if (!rovarok.containsKey(rovarId)) {
            System.out.printf("rovar hatas %s %s %d -> FAIL: hibás rovar név (%s)\n", rovarId, hatas, ertek, rovarId);
            return;
        }
        if (ertek < 0) {
            System.out.printf("rovar hatas %s %s %d -> FAIL: invalid érték (%d)\n", rovarId, hatas, ertek, ertek);
            return;
        }

        Rovar rovar = rovarok.get(rovarId);

        int idx = -1;
        switch (hatas) {
            case "lassu":
                idx = 0;
                break;
            case "gyors":
                idx = 1;
                break;
            case "bena":
                idx = 2;
                break;
            case "gyenge":
                idx = 3;
                break;
            case "osztodik":
                idx = 4;
                break;
        }

        if (idx == -1) {
            System.out.printf("rovar hatas %s %s %d -> FAIL: ismeretlen hatás típus (%s)\n", rovarId, hatas, ertek,
                    hatas);
            return;
        }

        rovar.setHatas(idx, ertek); // ehhez a Rovar osztályban kell egy setHatas(int idx, int ertek) metódus

        System.out.printf("rovar hatas %s %s %d -> OK: %s beállítva %d-re\n", rovarId, hatas, ertek, hatas, ertek);
    }

    /**
     * Egy rovar megpróbál elvágni egy gombafonalat két tekton között.
     */
    private void rovar_vag(String rovarId, String forrasId, String celId) {
        if (!rovarok.containsKey(rovarId)) {
            System.out.printf("rovar vag %s %s %s -> FAIL: hibás rovar azonosító (%s)\n", rovarId, forrasId, celId,
                    rovarId);
            return;
        }

        Rovar rovar = rovarok.get(rovarId);
        if (rovar.isBena() || rovar.gyenge()) {
            System.out.printf("rovar vag %s %s %s -> FAIL: rovar béna/gyenge, nem vághat (%s)\n", rovarId, forrasId,
                    celId, rovarId);
            return;
        }

        // fonal keresése
        String fonalId = null;
        for (Map.Entry<String, List<String>> entry : fonalKapcsolatok.entrySet()) {
            List<String> par = entry.getValue();
            if ((par.get(0).equals(forrasId) && par.get(1).equals(celId))
                    || (par.get(0).equals(celId) && par.get(1).equals(forrasId))) {
                fonalId = entry.getKey();
                break;
            }
        }

        if (fonalId != null) {
            gombafonalak.remove(fonalId);
            fonalKapcsolatok.remove(fonalId);
            System.out.printf("rovar vag %s %s %s -> OK: fonal elvágva %s és %s között\n", rovarId, forrasId, celId,
                    forrasId, celId);
        } else {
            System.out.printf("rovar vag %s %s %s -> FAIL: nincs fonal %s és %s között\n", rovarId, forrasId, celId,
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
    private void rovar_mozog(String rovarId, String forrasId, String celId) {
        if (!rovarok.containsKey(rovarId)) {
            System.out.printf("rovar mozog %s %s %s -> FAIL: hibás rovar azonosító (%s)\n", rovarId, forrasId, celId,
                    rovarId);
            return;
        }

        Rovar rovar = rovarok.get(rovarId);
        if (rovar.isBena()) {
            System.out.printf("rovar mozog %s %s %s -> FAIL: rovar béna, nem mozoghat (%s)\n", rovarId, forrasId, celId,
                    rovarId);
            return;
        }

        boolean vanFonal = false;
        for (Map.Entry<String, List<String>> entry : fonalKapcsolatok.entrySet()) {
            List<String> par = entry.getValue();
            if ((par.get(0).equals(forrasId) && par.get(1).equals(celId))
                    || (par.get(0).equals(celId) && par.get(1).equals(forrasId))) {
                vanFonal = true;
                break;
            }
        }

        if (vanFonal) {
            System.out.printf("rovar mozog %s %s %s -> OK: rovar mozog %s -ról %s -ra\n", rovarId, forrasId, celId,
                    forrasId, celId);
        } else {
            System.out.printf("rovar mozog %s %s %s -> FAIL: nincs fonal %s és %s között\n", rovarId, forrasId, celId,
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
    private void rovar_eszik(String rovarId, String tektonId) {
        if (!rovarok.containsKey(rovarId)) {
            System.out.printf("rovar eszik %s %s -> FAIL: hibás rovar azonosító (%s)\n", rovarId, tektonId, rovarId);
            return;
        }

        Rovar rovar = rovarok.get(rovarId);
        if (rovar.isBena()) {
            System.out.printf("rovar eszik %s %s -> FAIL: rovar béna, nem ehet (%s)\n", rovarId, tektonId, rovarId);
            return;
        }
        Tektonrész tekton = tektonok.get(tektonId);
        if (tektonok.containsKey(tektonId)) {
            if (tekton != null) {
                int aktualisSpora = tekton.getSporaSzam();
                if (aktualisSpora > 0) {
                    tekton.setSporaSzam(aktualisSpora - 1);
                }
                // OK üzenetet ugyanúgy kiírjuk
                System.out.printf("rovar eszik %s %s -> OK: sporak elfogyasztva %s-rol\n", rovarId, tektonId, tektonId);
            }
        }

        System.out.printf("rovar eszik %s %s -> OK: spórák elfogyasztva %s-ról\n", rovarId, tektonId, tektonId);
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
                        fungorium_torol(parancs.get(2));
                        break;
                    // palya szettoresehez (ha sikeresen sorsol)
                    case "tor":
                        fungorium_tor(parancs.get(2));
                        break;
                    default:
                        break;
                }
                break;
            // gombasz jatekos letrehozasahoz
            case "gombasz":
                if (parancs.get(1).equals("uj")) {
                    gombasz_uj(parancs.get(2));
                }
                break;
            // rovarasz jatekos letrehozasahoz
            case "rovarasz":
                if (parancs.get(1).equals("uj")) {
                    rovarasz_uj(parancs.get(2));
                }
                break;
            case "tekton":
                switch (parancs.get(1)) {
                    // uj tekton hozzaadasahoz
                    case "uj":
                        tekton_uj(parancs.get(2), parancs.get(3));
                        break;
                    // ket tekton szomszedossa tetelehez
                    case "szomszed":
                        tekton_szomszed(parancs.get(2), parancs.get(3));
                        break;
                    default:
                        break;
                }
                break;
            case "gomba":
                switch (parancs.get(1)) {
                    // gomba novesztese, ha lehetseges
                    case "noveszt":
                        gomba_noveszt(parancs.get(2), parancs.get(3),
                                parancs.get(4));
                        break;
                    // gomba fejlesztese, ha lehetseges
                    case "fejleszt":
                        gomba_fejleszt(parancs.get(2));
                        break;
                    // spora szoras kornyezo tektonokra
                    case "szoras":
                        gomba_szoras(parancs.get(2));
                        break;
                    default:
                        break;
                }
                break;
            case "gombaf":
                switch (parancs.get(1)) {
                    // fonal elszakitasa
                    case "szakit":
                        gombaf_szakit(parancs.get(2), parancs.get(3));
                        break;
                    // fonal novesztese egy iranyba
                    case "noveszt":
                        gombaf_noveszt(parancs.get(2), parancs.get(3),
                                parancs.get(4));
                        break;
                    // gombatest novesztes fonalbol egy bena rovar helyere
                    case "rovarbol":
                        gombaf_rovarbol(parancs.get(2), parancs.get(3),
                                parancs.get(4));
                        break;
                    default:
                        break;
                }
                break;
            // sporaszam beallitasa
            case "spora":
                if (parancs.get(1).equals("szam")) {
                    spora_szam(Integer.parseInt(parancs.get(2)), parancs.get(3));
                }
                break;
            case "rovar":
                switch (parancs.get(1)) {
                    // hatas beallitasa
                    case "hatas":
                        rovar_hatas(parancs.get(2), parancs.get(3), Integer.parseInt(parancs.get(4)));
                        break;
                    // fonalvagas
                    case "vag":
                        rovar_vag(parancs.get(2), parancs.get(3),
                                parancs.get(4));
                        break;
                    // mozgas
                    case "mozog":
                        rovar_mozog(parancs.get(2), parancs.get(3),
                                parancs.get(4));
                        break;
                    // eves
                    case "eszik":
                        rovar_eszik(parancs.get(2), parancs.get(3));
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
