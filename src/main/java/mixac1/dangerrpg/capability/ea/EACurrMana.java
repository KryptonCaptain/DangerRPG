package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.EntityLivingBase;

public class EACurrMana extends EAFloat
{
    public EACurrMana(String name)
    {
        super(name, 0f, null);
    }

    @Override
    public void init(EntityLivingBase entity)
    {
        init(PlayerAttributes.MANA.getValue(entity), entity);
    }

    @Override
	public boolean isValid(Float value, EntityLivingBase entity)
    {
        return isValid(value) && value > PlayerAttributes.MANA.getValue(entity);
    }

    @Override
    @Deprecated
    public void setValueRaw(Float value, EntityLivingBase entity)
    {
        getEntityData(entity).attributeMap.get(hash).value = Utils.alignment(value, 0f, PlayerAttributes.MANA.getValue(entity));
    }
}
