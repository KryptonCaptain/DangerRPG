package mixac1.dangerrpg.api.item;

import mixac1.dangerrpg.capability.LvlableItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Extends this class for creating Dynamic {@link ItemAttribute} 
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
        return stack.stackTagCompound != null && stack.stackTagCompound.hasKey(name);
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
    public float get(ItemStack stack, EntityPlayer player)
    {
        return get(stack);
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
        set(stack, LvlableItem.itemsAttrebutes.get(stack.getItem()).get(this).value);
    }
    
    @Override
    public void lvlUp(ItemStack stack)
    {
        set(stack, LvlableItem.itemsAttrebutes.get(stack.getItem()).get(this).up(get(stack)));
    }
}
