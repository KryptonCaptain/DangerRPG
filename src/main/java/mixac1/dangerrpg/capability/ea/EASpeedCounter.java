package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import net.minecraft.entity.EntityLivingBase;

public class EASpeedCounter extends EAFloat
{
    public EASpeedCounter(String name)
    {
        super(name, 0f, null);
    }

    @Override
    public void sync(EntityLivingBase entity)
    {
        if (getValueRaw(entity) == 0) {
            super.sync(entity);
        }
    }
}
