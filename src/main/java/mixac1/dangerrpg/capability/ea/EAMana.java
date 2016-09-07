package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import net.minecraft.entity.EntityLivingBase;

public class EAMana extends EAFloat
{
    public EAMana(String name, Float startValue, LvlEAProvider<Float> lvlProvider)
    {
        super(name, startValue, lvlProvider);
    }

    @Override
    public void setValue(Float value, EntityLivingBase entity)
    {
        if (isValid(value, entity)) {
            float max = getValue(entity);
            setValueRaw(value, entity);
            apply(entity, value);
            sync(entity);
            PlayerAttributes.CURR_MANA.setValue(value * PlayerAttributes.CURR_MANA.getValue(entity) / max, entity);
        }
    }
}
