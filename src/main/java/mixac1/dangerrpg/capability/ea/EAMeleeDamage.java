package mixac1.dangerrpg.capability.ea;

import java.util.UUID;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class EAMeleeDamage extends EAFloat
{
    private final UUID ID = UUID.fromString("04a931c2-b0bf-44de-bbed-1a8f0d56c584");

    public EAMeleeDamage(String name)
    {
        super(name);
    }

    @Override
    public String getDisplayValue(EntityLivingBase entity)
    {
        return String.format("%.1f", entity.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
    }

    @Override
    public void apply(EntityLivingBase entity, Float value)
    {
        if (!entity.worldObj.isRemote) {
            IAttributeInstance attr= entity.getEntityAttribute(SharedMonsterAttributes.attackDamage);
            float tmp = (float) attr.getAttributeValue();
            AttributeModifier mod = attr.getModifier(ID);
            if (mod != null) {
                attr.removeModifier(mod);
            }
            AttributeModifier newMod = new AttributeModifier(ID, name, getValueRaw(entity), 0).setSaved(true);
            attr.applyModifier(newMod);
        }
    }
}
