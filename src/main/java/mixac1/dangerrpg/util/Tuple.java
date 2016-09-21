package mixac1.dangerrpg.util;

import java.io.Serializable;

public class Tuple implements Serializable
{
    public static class Stub<T1> extends Tuple
    {
        public T1 value1;

        public Stub(T1 value1)
        {
            this.value1 = value1;
        }
    }

    public static class Pair<T1, T2> extends Stub<T1>
    {
        public T2 value2;

        public Pair(T1 value1, T2 value2)
        {
            super(value1);
            this.value2 = value2;
        }
    }

    public static class Triple<T1, T2, T3> extends Pair<T1, T2>
    {
        public T3 value3;

        public Triple(T1 value1, T2 value2, T3 value3)
        {
            super(value1, value2);
            this.value3 = value3;
        }
    }
}
