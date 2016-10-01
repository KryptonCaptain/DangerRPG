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
        return RPGCapability.rpgItemRegistr.isActivated(stack.getItem())
               && stack.stackTagCompound.hasKey(name);
    }

    @Override
    public float getRaw(ItemStack stack)
    {
        return stack.stackTagCompound.getFloat(name);
    }

    @Override
    public void setRaw(ItemStack stack, float value)
    {
        stack.stackTagCompound.setFloat(name, value);
    }

    @Override
    public void init(ItemStack stack)
    {
        set(stack, RPGCapability.rpgItemRegistr.get(stack.getItem()).attributes.get(this).value);
    }

    @Override
    public void lvlUp(ItemStack stack)
    {
        set(stack, RPGCapability.rpgItemRegistr.get(stack.getItem()).attributes.get(this).up(get(stack)));
    }
}
