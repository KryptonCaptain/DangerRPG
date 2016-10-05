package mixac1.dangerrpg.capability.ea;

import java.util.UUID;

import mixac1.dangerrpg.api.entity.EAWithIAttr.EAMotion;
import net.minecraft.entity.EntityLivingBase;

public class EAJumpHeight extends EAMotion
{
    public EAJumpHeight(String name)
    {
        super(name);
    }

    @Override
    public Float getValueRaw(EntityLivingBase entity)
    {
        return super.getValueRaw(entity) * getJumpMul(entity);
    }

    @Override
    public Float getBaseValue(EntityLivingBase entity)
    {
        return super.getBaseValue(entity) * getJumpMul(entity);
    }

    @Override
    public Float getModificatorValue(EntityLivingBase entity, UUID ID)
    {
        return super.getModificatorValue(entity, ID) * getJumpMul(entity);
    }

    private float getJumpMul(EntityLivingBase entity)
    {
        return 14;
    }
}
