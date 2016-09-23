package mixac1.dangerrpg.capability.ea;

import java.util.UUID;

import mixac1.dangerrpg.api.entity.EAWithExistIAttr;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;

public class EAHealth extends EAWithExistIAttr
{
    public EAHealth(String name, UUID IDBase, IAttribute attribute)
    {
        super(name, IDBase, attribute);
    }

    @Override
    public void serverInit(EntityLivingBase entity)
    {
        setValueRaw(entity.getHealth(), entity);
    }

    @Override
    @Deprecated
    public Float getValueRaw(EntityLivingBase entity)
    {
        return entity.getMaxHealth() + entity.getAbsorptionAmount();
    }

    @Override
    @Deprecated
    public boolean setValueRaw(Float value, EntityLivingBase entity)
    {
        float tmp = entity.getHealth() / entity.getMaxHealth();
        if (super.setValueRaw(value, entity)) {
            entity.setHealth(entity.getMaxHealth() * tmp);
            return true;
        }
        return false;
    }
}
