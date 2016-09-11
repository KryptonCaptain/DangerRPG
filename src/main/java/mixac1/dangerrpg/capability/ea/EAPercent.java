package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import net.minecraft.entity.EntityLivingBase;

public class EAPercent extends EAFloat
{
    public EAPercent(String name)
    {
        super(name);
    }

    @Override
    public String getDisplayValue(EntityLivingBase entity)
    {
        return String.format("%d%c", getValue(entity).intValue(), '%');
    }
}
