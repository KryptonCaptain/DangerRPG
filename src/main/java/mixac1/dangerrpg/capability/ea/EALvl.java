package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAInteger;
import net.minecraft.entity.EntityLivingBase;

public class EALvl extends EAInteger
{
    public EALvl(String name)
    {
        super(name, 1, null);
    }

    public boolean isInitedEntity(EntityLivingBase entity)
    {
        return getValueRaw(entity) > 0;
    }
}
