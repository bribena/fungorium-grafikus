package szkeleton_6;

import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char choice;

        System.out.println("Főmenü / Use-cas-ek:\n\n" +
                "a. Rovaretetés\n" +
                "b. Rovarmozgatás\n" +
                "c. Fonalvágás\n" +
                "d. Spórafogyasztás\n" +
                "e. Gombatestnövesztés\n" +
                "f. Gombafonalnövesztés\n" +
                "g. Gombatestfejlesztés\n" +
                "h. Tektontörés\n" +
                "0. Kilépés\n"
        );

        choice = scanner.next().charAt(0);

        switch (choice) {
            case 'a':
                RovarEtetes rovarEtetes = new RovarEtetes();
                rovarEtetes.rovartEtet();
                break;
            case 'b':
                RovarMozgatas rovarMozgatas = new RovarMozgatas();
                rovarMozgatas.rovartMozgat();
                break;
            case 'c':
                GombafonalVagas gombafonalVagas = new GombafonalVagas();
                gombafonalVagas.gombafonalatVag();
                break;
            case 'd':
                SporaSzoras sporaSzoras = new SporaSzoras();
                sporaSzoras.sporatSzor();
                break;
            case 'e':
                GombatestNovesztes gombatestNovesztes = new GombatestNovesztes();
                gombatestNovesztes.gombatestetNoveszt();
                break;
            case 'f':
                GombafonalNovesztes gombafonalNovesztes = new GombafonalNovesztes();
                gombafonalNovesztes.gombafonalatNoveszt();
                break;
            case 'g':
                GombatestFejlesztes gombatestFejlesztes = new GombatestFejlesztes();
                gombatestFejlesztes.gombatestetFejleszt();
                break;
            case 'h':
                TektonTores tektonTores = new TektonTores();
                tektonTores.tektontTor();
                break;
            case '0':
                System.out.println("Kilépés...");
                return;
            default:
                System.out.println("Érvénytelen use-case, adj meg egy érvényes betűt!");
        }
        scanner.close();
    }
}