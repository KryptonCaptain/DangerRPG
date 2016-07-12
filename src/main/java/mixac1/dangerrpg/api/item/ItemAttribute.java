package mixac1.dangerrpg.api.item;

import mixac1.dangerrpg.util.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract class ItemAttribute
{
    public final String name;
    
    public ItemAttribute(String name)
    {
        this.name = name;
    }
    
    public boolean isValid(float value)
    {
        return value >= 0;
    }
    
    public boolean isValid(ItemStack stack)
    {
        return isValid(get(stack));
    }
    
    public abstract boolean hasIt(ItemStack stack);
    
    /**
     * Warning: Check {@link #hasIt(ItemStack)} before use this method
     */
    public abstract float get(ItemStack stack);
    
    /**
     * Warning: Check {@link #hasIt(ItemStack)} before use this method
     */
    public abstract float get(ItemStack stack, EntityPlayer player);
    
    public abstract void set(ItemStack stack, float value);
    
    public abstract void add(ItemStack stack, float value);
    
    public abstract void init(ItemStack stack);
    
    public abstract void lvlUp(ItemStack stack);
    
    public String getDispayName()
    {
        return Translator.trans("it_atr.".concat(name));
    }
    
    public String getDispayValue(ItemStack stack, EntityPlayer player)
    {
        return String.format("%.2f", get(stack, player));
    }
    
    public boolean isVisibleInInfoBook(ItemStack stack)
    {
        return true;
    }
}
