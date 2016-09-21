package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;

public class EASlimeDamage extends EAFloat
{
    public EASlimeDamage(String name)
    {
        super(name);
    }

    @Override
    @Deprecated
    public Float getValueRaw(EntityLivingBase entity)
    {
        if (entity instanceof EntitySlime) {
            int size = ((EntitySlime) entity).getSlimeSize();
            return (Float) getEntityData(entity).attributeMap.get(hash).value1 * size / 4 + size;
        }
        return (Float) getEntityData(entity).attributeMap.get(hash).value1;
    }
}
