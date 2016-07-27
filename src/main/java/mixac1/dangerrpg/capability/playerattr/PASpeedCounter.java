package mixac1.dangerrpg.capability.playerattr;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import net.minecraft.entity.EntityLivingBase;

public class PASpeedCounter extends EntityAttribute
{
    public PASpeedCounter(String name)
    {
        super(name);
    }
    
    @Override
    public void setValue(float value, EntityLivingBase entity, boolean needSync)
    {
        if (isValid(entity, value)) {
            getEntityData(entity).attributeMap.get(hash).value = value;
            apply(entity);
            if (needSync && value == 0) {
                sync(entity);
            }
        }
    }
}
