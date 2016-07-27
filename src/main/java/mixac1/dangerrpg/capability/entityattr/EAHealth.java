package mixac1.dangerrpg.capability.entityattr;

import java.util.UUID;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class EAHealth extends EntityAttribute
{
	private final UUID ID = UUID.randomUUID();
	
	public EAHealth(String name)
	{
		super(name);
	}
	
	@Override
    public float displayValue(EntityLivingBase entity)
    {
        return entity.getMaxHealth();
    }

    @Override
    public void apply(EntityLivingBase entity)
    {
        float proc = entity.getHealth() / entity.getMaxHealth();

        IAttributeInstance attribute = entity.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        AttributeModifier bonusHeartModifier = attribute.getModifier(ID);
        if (bonusHeartModifier != null) {
            attribute.removeModifier(bonusHeartModifier);
        }
        AttributeModifier newModifier = new AttributeModifier(ID, name, getValue(entity), 0).setSaved(true);
        attribute.applyModifier(newModifier);

        entity.setHealth(entity.getMaxHealth() * proc);
    }
}
