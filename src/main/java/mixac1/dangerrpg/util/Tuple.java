package mixac1.dangerrpg.util;

public class Tuple
{
    public static class Tuple1<T1> extends Tuple
    {
        public T1 value1;

        public Tuple1(T1 value1)
        {
            this.value1 = value1;
        }
    }

    public static class Tuple2<T1, T2> extends Tuple1<T1>
    {
        public T2 value2;

        public Tuple2(T1 value1, T2 value2)
        {
            super(value1);
            this.value2 = value2;
        }
    }

    public static class Tuple3<T1, T2, T3> extends Tuple2<T1, T2>
    {
        public T3 value3;

        public Tuple3(T1 value1, T2 value2, T3 value3)
        {
            super(value1, value2);
            this.value3 = value3;
        }
    }
}
