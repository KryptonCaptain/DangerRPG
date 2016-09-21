package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.EntityLivingBase;

public class EACurrMana extends EAFloat
{
    public EACurrMana(String name)
    {
        super(name);
    }

    @Override
    public void serverInit(EntityLivingBase entity)
    {
        setValueRaw(PlayerAttributes.MANA.getValue(entity), entity);
    }

    @Override
    @Deprecated
    public boolean setValueRaw(Float value, EntityLivingBase entity)
    {
        if (!value.equals(getValueRaw(entity))) {
            getEntityData(entity).attributeMap.get(hash).value1 = Utils.alignment(value, 0f, PlayerAttributes.MANA.getValueRaw(entity));
            return true;
        }
        return false;
    }
}
