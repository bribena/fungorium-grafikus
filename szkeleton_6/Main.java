package szkeleton_6;

import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UseCases useCases = new UseCases();
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
                useCases.rovartEtet();
                break;
            case 'b':
                useCases.rovartMozgat();
                break;
            case 'c':
                useCases.gombafonalatVag();
                break;
            case 'd':
                useCases.sporatSzor();
                break;
            case 'e':
                useCases.gombatestetNoveszt();
                break;
            case 'f':
                useCases.gombafonalatNoveszt();
                break;
            case 'g':
                useCases.gombatestetFejleszt();
                break;
            case 'h':
                useCases.tektontTor();
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