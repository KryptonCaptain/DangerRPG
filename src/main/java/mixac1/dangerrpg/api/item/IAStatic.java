package mixac1.dangerrpg.api.item;

import mixac1.dangerrpg.capability.LvlableItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Extends this class for creating Static {@link ItemAttribute} 
 */
public class IAStatic extends ItemAttribute
{
    public IAStatic(String name)
    {
        super(name);
    }
    
    @Override
    public boolean hasIt(ItemStack stack)
    {
        return LvlableItem.itemsAttrebutes.get(stack.getItem()) != null
            && LvlableItem.itemsAttrebutes.get(stack.getItem()).containsKey(this);
    }
    
    @Override
    public float get(ItemStack stack)
    {
        float value = LvlableItem.itemsAttrebutes.get(stack.getItem()).get(this).value;
        if (!isValid(value)) {
            init(stack);
            value = LvlableItem.itemsAttrebutes.get(stack.getItem()).get(this).value;
        }
        return value;
    }
    
    @Override
    public float get(ItemStack stack, EntityPlayer player)
    {
        return get(stack);
    }
    
    @Override
    public final void set(ItemStack stack, float value)
    {
    }
    
    @Override
    public final void add(ItemStack stack, float value)
    {
    }
    
    @Override
    public final void init(ItemStack stack)
    {
    }
    
    @Override
    public final void lvlUp(ItemStack stack)
    {
    }
}
