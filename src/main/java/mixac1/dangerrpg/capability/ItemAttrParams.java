package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.util.Multiplier;

public class ItemAttrParams
{
    public float value;
    public Multiplier multiplier;

    public ItemAttrParams(float value, Multiplier multiplier)
    {
        this.value = value;
        this.multiplier = multiplier;
    }

    public float up(float value)
    {
        return multiplier.up(value);
    }
}
