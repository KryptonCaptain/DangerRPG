package mixac1.dangerrpg.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Random;

public abstract class Utils
{
    public static Random rand = new Random();

    public static <T> Iterable<T> safe(Iterable<T> iterable)
    {
        return iterable == null ? Collections.<T>emptyList() : iterable;
    }

    public static float round(float value, float min, float max)
    {
        return value < min ? min : value > max ? max : value;
    }

    public static double invert(double value)
    {
        return -value;
    }

    public static double invert(double value, boolean isInvert)
    {
        return isInvert ? invert(value) : value;
    }

    public static String toString(Object obj)
    {
        return obj != null ? obj.toString() : "(null)";
    }

    public static String toString(Object... objs)
    {
        return toString(0, objs);
    }

    public static String toString(char indent, Object... objs)
    {
        if (objs == null || objs.length == 0) {
            return null;
        }
        else if (objs.length == 1) {
            return toString(objs[0]);
        }
        else if (objs.length == 2 && indent == 0) {
            return toString(objs[0]).concat(toString(objs[1]));
        }
        else {
            StringBuilder buf = new StringBuilder();
            if (indent == 0) {
                for (Object obj : objs) {
                    buf.append(toString(obj));
                }
            }
            else {
                for (Object obj : objs) {
                    buf.append(toString(obj)).append(indent);
                }
            }
            return buf.toString();
        }
    }

    public static double getDiagonal(double x, double y)
    {
        return Math.sqrt(x * x + y * y);
    }

    public static double getDiagonal(double x, double y, double z)
    {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public static int randInt(Random rand, int bound, boolean isAroundZero)
    {
        if (isAroundZero) {
            return rand.nextInt(bound * 2) - bound;
        }
        else {
            return rand.nextInt(bound);
        }
    }

    public static int randInt(int bound, boolean isAroundZero)
    {
        return randInt(rand, bound, isAroundZero);
    }

    /**
     * @param accuracy
     *            - count of zero after dot
     */
    public static double randDouble(Random rand, double bound, int accuracy, boolean isAroundZero)
    {
        accuracy = (int) Math.pow(10, accuracy);
        return randInt(rand, (int) (accuracy * bound), isAroundZero) / accuracy;
    }

    public static double randDouble(double bound, int accuracy, boolean isAroundZero)
    {
        return randDouble(rand, bound, accuracy, isAroundZero);
    }

    public static <Type> byte[] serialize(Type obj)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] ret = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            ret = bos.toByteArray();
            bos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static <Type> Type deserialize(byte[] obj)
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(obj);
        ObjectInput in = null;
        Type ret = null;
        try {
            in = new ObjectInputStream(bis);
            ret = (Type) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
