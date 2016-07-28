package mixac1.dangerrpg.util;

import java.util.Collections;

public class Utils
{
    public static <T> Iterable<T> safe(Iterable<T> iterable)
    {
        return iterable == null ? Collections.<T>emptyList() : iterable;
    }

    public static float alignment(float value, float min, float max)
    {
        if (value < min) {
            return min;
        }
        else if (value > max) {
            return max;
        }
        return value;
    }
}
