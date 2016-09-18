package mixac1.dangerrpg.api.item;

import mixac1.dangerrpg.init.RPGCapability;
import net.minecraft.item.ItemStack;

/**
 * Extends this class for creating Dynamic {@link ItemAttribute}<br>
 * Value saving to NBT
 */
public class IADynamic extends ItemAttribute
{
    public IADynamic(String name)
    {
        super(name);
    }

    @Override
    public boolean hasIt(ItemStack stack)
    {
        return RPGCapability.lvlItemRegistr.registr.contains(stack.getItem())
               && stack.stackTagCompound.hasKey(name);
    }

    @Override
    public float get(ItemStack stack)
    {
        float value = stack.stackTagCompound.getFloat(name);
        if (!isValid(value)) {
            init(stack);
            value = stack.stackTagCompound.getFloat(name);
        }
        return value;
    }

    @Override
    public void set(ItemStack stack, float value)
    {
        if (isValid(value)) {
            stack.stackTagCompound.setFloat(name, value);
        }
    }

    @Override
    public void add(ItemStack stack, float value)
    {
        set(stack, value + get(stack));
    }

    @Override
    public void init(ItemStack stack)
    {
        set(stack, RPGCapability.lvlItemRegistr.data.get(stack.getItem()).map.get(this).value);
    }

    @Override
    public void lvlUp(ItemStack stack)
    {
        set(stack, RPGCapability.lvlItemRegistr.data.get(stack.getItem()).map.get(this).up(get(stack), stack));
    }
}
