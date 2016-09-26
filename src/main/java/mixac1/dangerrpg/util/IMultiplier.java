package mixac1.dangerrpg.util;

import java.io.Serializable;

public interface IMultiplier<Type> extends Serializable
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

        public static IMulConfigurable getMul(String str)
        {
            String[] strs = str.split(" ");
            if (strs.length == 2) {
                MulType type = MulType.valueOf(strs[0].toUpperCase());
                Float value = Float.valueOf(strs[1]);
                if (type != null && value != null) {
                    return type.getMul(value);
                }
            }
            return null;
        }
    }

    public static class MultiplierAdd implements IMulConfigurable
    {
        public final Float add;

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
        public final Float mul;

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
