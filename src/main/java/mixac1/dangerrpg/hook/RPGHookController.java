package mixac1.dangerrpg.hook;

import java.util.ArrayList;
import java.util.List;

import mixac1.dangerrpg.util.Utils;

public class RPGHookController
{
    private static boolean wasLoad = false;

    private static List<HookException> exceptions;

    public static void load()
    {
        for (HookException e : Utils.safe(exceptions)) {
            throw e;
        }

        wasLoad = true;
    }

    public static void addException(HookException e)
    {
        if (wasLoad) {
            throw e;
        }
        if (exceptions == null) {
            exceptions = new ArrayList<HookException>();
        }
        exceptions.add(e);
    }
}
