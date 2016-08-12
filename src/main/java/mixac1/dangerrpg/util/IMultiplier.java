package mixac1.dangerrpg.util;

public interface IMultiplier<Type>
{
    public Type up(Type value);

    public interface IMultiplierE<Type> extends IMultiplier<Type>
    {
        public Type down(Type value);
    }

    public static class MultiplierAdd implements IMultiplierE<Float>
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

    public static final IMultiplierE ADD_1 = new MultiplierAdd(1F);

    public static IMultiplierE MUL_1 = new IMultiplierE()
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
