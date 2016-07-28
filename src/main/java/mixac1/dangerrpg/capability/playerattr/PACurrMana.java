package mixac1.dangerrpg.capability.playerattr;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.EntityLivingBase;

public class PACurrMana extends EntityAttribute
{
    public PACurrMana(String name)
    {
        super(name);
    }

    @Override
    public void init(EntityLivingBase entity)
    {
        init(PlayerAttributes.MANA.getValue(entity), entity);
    }

    @Override
	public boolean isValid(EntityLivingBase entity, float value)
    {
        return isValid(value) && value > PlayerAttributes.MANA.getValue(entity);
    }

    @Override
    public void setValue(float value, EntityLivingBase entity, boolean needSync)
    {
        if (isValid(entity, value)) {
        	getEntityData(entity).attributeMap.get(hash).value = Utils.alignment(value, 0f, PlayerAttributes.MANA.getValue(entity));
            apply(entity);
            if (needSync) {
            	sync(entity);
            }
        }
    }
}
