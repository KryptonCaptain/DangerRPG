package mixac1.dangerrpg.capability.ea;

import java.util.UUID;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class EAHealth extends EAFloat
{
    private static float ff;
    private final UUID ID = UUID.randomUUID();

    public EAHealth(String name, float defaultValue, LvlEAProvider<Float> lvlProvider)
    {
        super(name, defaultValue, lvlProvider);
    }

    @Override
    public Float displayValue(EntityLivingBase entity)
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
