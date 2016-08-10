package mixac1.dangerrpg.util;

public interface Multiplier<Type>
{
    public Type up(Type value);

    public interface MultiplierE<Type> extends Multiplier<Type>
    {
        public Type down(Type value);
    }

    public static class MultiplierAdd implements MultiplierE<Float>
    {
        private Float add;

        public MultiplierAdd(Float add)
        {
            this.add = add;
        }

        @Override
        public Float up(Float value)
        {
            return value + add;
        }

        @Override
        public Float down(Float value)
        {
            return value - add;
        }
    }

    public static final MultiplierE ADD_1 = new MultiplierAdd(1F);

    public static MultiplierE MUL_1 = new MultiplierE()
    {
        @Override
        public Object up(Object value)
        {
            return value;
        }

        @Override
        public Object down(Object value)
        {
            return value;
        }
    };
}
