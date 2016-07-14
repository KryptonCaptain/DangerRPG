package mixac1.dangerrpg.capability;

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
    
    public interface Multiplier
    {
        public float up(float value);
    }
}
