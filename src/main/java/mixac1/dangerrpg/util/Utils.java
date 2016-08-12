package mixac1.dangerrpg.util;

import java.util.Collections;

public abstract class Utils
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

    public static float invert(float value)
    {
        return -value;
    }

    public static float invert(float value, boolean isInvert)
    {
        return isInvert ? invert(value) : value;
    }

    public static String toString(Object... objs)
    {
        StringBuilder buf = new StringBuilder();
        for (Object obj : objs) {
            buf.append(obj.toString());
        }
        return buf.toString();
    }
}
