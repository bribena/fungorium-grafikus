package szkeleton_6;

import java.util.Map;
import java.util.Scanner;

public class MultipleChoice {
    private final char[] abc = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    Map<String, Runnable> functions;
    Object[] keys = {};
    String title;
    Scanner scanner = new Scanner(System.in);

    public MultipleChoice(String t, Map<String, Runnable> f)
    {
        title = t;
        functions = f;
        keys = functions.keySet().toArray();
    }

    public void revealChoices()
    {
        System.out.println(title);

        for (int i = 0; i < functions.size(); i++)
        {
            System.out.println(abc[i]+". "+keys[i]); 
        }

        System.out.println("0. Vissza");
    }

    public void run()
    {
        revealChoices();

        while (true)
        {
            char choice = scanner.next().charAt(0);

            for (int i = 0; i < functions.size(); i++)
            {
                if (choice == abc[i])
                {
                    functions.get(keys[i]).run();
                }
            }

            if (choice == '0')
            {
                break;
            }
            else
            {
                revealChoices();
            }
        }
    }
}
