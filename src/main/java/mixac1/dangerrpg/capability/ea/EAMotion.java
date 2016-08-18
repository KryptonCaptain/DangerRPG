package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import net.minecraft.entity.EntityLivingBase;

public class EAMotion extends EAFloat
{
    public EAMotion(String name, LvlEAProvider<Float> lvlProvider)
    {
        super(name, 0f, lvlProvider);
    }

    @Override
    public String getDisplayValue(EntityLivingBase entity)
    {
        return null;
    }
}
