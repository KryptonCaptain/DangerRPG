package mixac1.dangerrpg.util;

public interface IMultiplier<Type>
{
    public Type up(Type value, Object... objs);

    public interface IMultiplierE<Type> extends IMultiplier<Type>
    {
        public Type down(Type value, Object... objs);
    }

    public interface IMulConfigurable extends IMultiplierE<Float> {}

    enum MulType
    {
        ADD
        {
            @Override
            public IMulConfigurable getMul(Float d)
            {
                return new MultiplierAdd(d);
            }
        },

        MUL
        {
            @Override
            public IMulConfigurable getMul(Float d)
            {
                return new MultiplierMul(d);
            }
        },

        HARD
        {
            @Override
            public String toString(Float value)
            {
                return name();
            }
        },

        ;

        public IMulConfigurable getMul(Float value)
        {
            return null;
        }

        public String toString(Float value)
        {
            return Utils.toString(name(), " ", value);
        }

    }

    public static class MultiplierAdd implements IMulConfigurable
    {
        private Float add;

        public MultiplierAdd(Float add)
        {
            this.add = add;
        }

        @Override
        public Float up(Float value, Object... objs)
        {
            return value + add;
        }

        @Override
        public Float down(Float value, Object... objs)
        {
            return value - add;
        }

        @Override
        public String toString()
        {
            return MulType.ADD.toString(add);
        }
    }

    public static class MultiplierMul implements IMulConfigurable
    {
        private Float mul;

        public MultiplierMul(Float mul)
        {
            this.mul = mul;
        }

        @Override
        public Float up(Float value, Object... objs)
        {
            return value * mul;
        }

        @Override
        public Float down(Float value, Object... objs)
        {
            return value / mul;
        }

        @Override
        public String toString()
        {
            return MulType.MUL.toString(mul);
        }
    }

    public static final MultiplierAdd ADD_1 = new MultiplierAdd(1F);

    public static final MultiplierMul MUL_1 = new MultiplierMul(1F);
}
