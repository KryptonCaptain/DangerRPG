package mixac1.dangerrpg.capability.gt;

import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.item.gem.GemPassiveAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GTPassiveAttribute extends GemType
{
    public GTPassiveAttribute(String name)
    {
        super(name);
    }

    @Override
    public void activate(ItemStack stack, EntityPlayer player)
    {
        if (stack.getItem() instanceof GemPassiveAttribute) {
            ((GemPassiveAttribute) stack.getItem()).activate(stack, player);
        }
    }

    @Override
    public void deactivate(ItemStack stack, EntityPlayer player)
    {
        if (stack.getItem() instanceof GemPassiveAttribute) {
            ((GemPassiveAttribute) stack.getItem()).deactivate(stack, player);
        }
    }

    public static class GTPAWeapon extends GTPassiveAttribute
    {
        public GTPAWeapon(String name)
        {
            super(name);
        }
    }

    public static class GTPAArmor extends GTPassiveAttribute
    {
        public GTPAArmor(String name)
        {
            super(name);
        }
    }
}
