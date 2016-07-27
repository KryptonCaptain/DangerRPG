package mixac1.dangerrpg.capability.playerattr;

import mixac1.dangerrpg.api.entity.EntityAttributeE;
import mixac1.dangerrpg.capability.CommonEntityData.EAEValues;
import mixac1.dangerrpg.util.Multiplier;
import mixac1.dangerrpg.util.Multiplier.MultiplierE;
import net.minecraft.entity.EntityLivingBase;

public class PAMana extends EntityAttributeE
{
    public PAMana(String name, float startValue, float startExpCost, float maxLvl, MultiplierE mulValue, Multiplier mulExpCost)
    {
        super(name, startValue, startExpCost, maxLvl, mulValue, mulExpCost);
    }

    @Override
    public void setValue(float value, EntityLivingBase entity, boolean needSync)
    {
        if (isValid(entity, value)) {
            EAEValues tmp = getValues(entity);
            float max = tmp.value;
            tmp.value = value;
            PlayerAttributes.CURR_MANA.setValue(value * PlayerAttributes.CURR_MANA.getValue(entity) / max, entity);
            apply(entity);
            if (needSync) {
            	sync(entity);
            }
        }
    }
}
