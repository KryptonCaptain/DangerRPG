package mixac1.dangerrpg.capability.playerattr;

import java.util.UUID;

import mixac1.dangerrpg.api.entity.EntityAttributeE;
import mixac1.dangerrpg.util.Multiplier;
import mixac1.dangerrpg.util.Multiplier.MultiplierE;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class PAHealth extends EntityAttributeE
{
    private final UUID ID = UUID.randomUUID();

    public PAHealth(String name, float startValue, float startExpCost, float maxLvl, MultiplierE mulValue, Multiplier mulExpCost)
    {
        super(name, startValue, startExpCost, maxLvl, mulValue, mulExpCost);
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
