package mixac1.dangerrpg.util;

public interface Multiplier
{
    public static final MultiplierE ADD_1 = new MultiplierAdd(1F);

    public float up(float value);

    public interface MultiplierE extends Multiplier
    {
        public float down(float value);
    }

    public static class MultiplierAdd implements MultiplierE
    {
        private float add;

        public MultiplierAdd(float add)
        {
            this.add = add;
        }

        @Override
        public float up(float value)
        {
            return value + add;
        }

        @Override
        public float down(float value)
        {
            return value - add;
        }
    }
}
