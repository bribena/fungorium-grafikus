package szkeleton_6;

import java.util.HashMap;
import java.util.Map;

public class UseCase {
    protected Map<String, Runnable> functions = new HashMap<>();
    protected MultipleChoice mc;

    public void run()
    {
        mc.run();
    }
}
