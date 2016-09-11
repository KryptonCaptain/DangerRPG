package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.IAStatic;
import net.minecraft.item.ItemStack;

public class IADurability extends IAStatic
{
    public IADurability(String name)
    {
        super(name);
    }

    @Override
    public boolean hasIt(ItemStack stack)
    {
        return ItemAttributes.MAX_DURABILITY.hasIt(stack);
    }

    @Override
    public float get(ItemStack stack)
    {
        return stack.getItemDamage();
    }
}
