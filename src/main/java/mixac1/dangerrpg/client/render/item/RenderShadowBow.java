package mixac1.dangerrpg.client.render.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class RenderShadowBow extends RenderBow
{
    public static final RenderShadowBow INSTANCE = new RenderShadowBow();
    
    @Override
    public float specific(ItemRenderType type, ItemStack stack, EntityLivingBase entity)
    { 
        return super.specific(type, stack, entity) / 1.5F;
    }
}
