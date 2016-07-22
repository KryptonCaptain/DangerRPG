package mixac1.dangerrpg.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ModelBipedE extends ModelBiped
{
    public ModelBipedE()
    {
        super(0.0F);
    }

    public ModelBipedE(float f)
    {
        super(f, 0.0F, 64, 32);
    }

    public ModelBipedE(float f, float f1, int f2, int f3)
    {
        super(f, f1, f2, f3);
    }

    public ModelBipedE init(EntityLivingBase entity, ItemStack stack, int slot)
    {
        return (ModelBipedE) RPGRenderHelper.modelBipedInit(entity, this, slot);
    }
}
