package mixac1.dangerrpg.client.render.item;

import org.lwjgl.opengl.GL11;

import mixac1.dangerrpg.client.model.ModelHammer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class RenderHammer extends RPGItemRenderModel
{
    public static final RenderHammer INSTANCE = new RenderHammer();

    @Override
    public float specific(ItemRenderType type, ItemStack stack, EntityLivingBase entity)
    {
        float f = 1F;
        GL11.glScalef(f, f, f);

        GL11.glRotatef(180F, 0F, 0F, 1F);
        GL11.glTranslatef(-0.88F, -0.3F, -0.05F);
        return super.specific(type, stack, entity);
    }

    @Override
    public ModelBase getModel()
    {
        return ModelHammer.INSTANCE;
    }
}
