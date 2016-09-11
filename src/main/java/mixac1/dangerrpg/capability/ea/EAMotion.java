package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import net.minecraft.entity.EntityLivingBase;

public class EAMotion extends EAFloat
{
    public EAMotion(String name)
    {
        super(name);
    }

    @Override
    public String getDisplayValue(EntityLivingBase entity)
    {
        return null;
    }
}
