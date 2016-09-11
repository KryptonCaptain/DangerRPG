package mixac1.dangerrpg.util;

public interface IMultiplier<Type>
{
    public Type up(Type value, Object... objs);

    public interface IMultiplierE<Type> extends IMultiplier<Type>
    {
        public Type down(Type value, Object... meta);
    }

    public static class MultiplierAdd implements IMultiplierE<Float>
    {
        private Float add;

        public MultiplierAdd(Float add)
        {
            this.add = add;
        }

        @Override
        public Float up(Float value, Object... meta)
        {
            return value + add;
        }

        @Override
        public Float down(Float value, Object... meta)
        {
            return value - add;
        }
    }

    public static final IMultiplierE ADD_1 = new MultiplierAdd(1F);

    public static IMultiplierE MUL_1 = new IMultiplierE()
    {
        @Override
        public Object up(Object value, Object... meta)
        {
            return value;
        }

        @Override
        public Object down(Object value, Object... meta)
        {
            return value;
        }
    };
}
