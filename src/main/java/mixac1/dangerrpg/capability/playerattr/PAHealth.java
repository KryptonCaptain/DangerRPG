package mixac1.dangerrpg.capability.playerattr;

import java.util.UUID;

import mixac1.dangerrpg.api.player.PlayerAttributeE;
import mixac1.dangerrpg.util.Multiplier;
import mixac1.dangerrpg.util.Multiplier.MultiplierE;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;

public class PAHealth extends PlayerAttributeE
{
    private final UUID id = UUID.fromString("cca09b88-445a-43c4-a7f7-bd4908de0d2a");

    public PAHealth(String name, float startValue, float startExpCost, float maxLvl, MultiplierE mulValue, Multiplier mulExpCost)
    {
        super(name, startValue, startExpCost, maxLvl, mulValue, mulExpCost);
    }

    @Override
    public float displayValue(EntityPlayer player)
    {
        return player.getMaxHealth();
    }

    @Override
    public void apply(EntityPlayer player)
    {
        float proc = player.getHealth() / player.getMaxHealth();

        IAttributeInstance attribute = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        AttributeModifier bonusHeartModifier = attribute.getModifier(id);
        if (bonusHeartModifier != null) {
            attribute.removeModifier(bonusHeartModifier);
        }
        AttributeModifier newModifier = new AttributeModifier(id, "DangerRPG Bonus Heart", getValue(player), 0).setSaved(true);
        attribute.applyModifier(newModifier);

        player.setHealth(player.getMaxHealth() * proc);
    }
}
