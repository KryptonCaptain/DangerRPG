package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import net.minecraft.entity.EntityLivingBase;

public class EAPercent extends EAFloat
{
    public EAPercent(String name, float startValue, LvlEAProvider<Float> lvlProvider)
    {
        super(name, startValue, lvlProvider);
    }

    @Override
    public String getDisplayValue(EntityLivingBase entity)
    {
        return String.format("%d%c", getValue(entity).intValue(), '%');
    }
}
