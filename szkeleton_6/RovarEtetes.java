package szkeleton_6;

import java.util.Scanner;

public class RovarEtetes {
    void rovartEtet() {
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);
        char choice;

        System.out.println("Rovaretetés\n\n" +
                "a. Sikeres spórafogyasztás\n" +
                "b. Sikeres spórafogyasztás + hatásfrissülés\n" +
                "c. Sikertelen fogyasztás\n" +
                "0. Vissza a főmenübe\n");

        choice = scanner.next().charAt(0);

        switch (choice) {
            case 'a':
                System.out.println("Sikeres rovaretetés forgatókönyve\n");
                break;
            case 'b':
                System.out.println("Sikeres rovaretetés forgatókönyve hatásfrissítéssel\n");
                break;
            case 'c':
                System.out.println("Sikertelen rovaretetés forgatókönyve\n");
                break;
            case '0':
                main.main(new String[]{"a"});
                return;
            default:
                System.out.println("Érvénytelen aleset, adj meg egy érvényes betűt!");
        }
        scanner.close();
    }
}
