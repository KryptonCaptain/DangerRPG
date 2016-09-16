package mixac1.dangerrpg.capability.ea;

import java.util.UUID;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.api.entity.EntityAttributeE;
import net.minecraft.entity.SharedMonsterAttributes;

public class EntityAttributes
{
    public static final EALvl           LVL                = new EALvl           ("lvl");
    public static final EAFloat         HEALTH             = new EAHealth        ("health",       UUID.fromString("fd6315bf-9f57-46cb-bb38-4aacb5d2967a"), SharedMonsterAttributes.maxHealth);
    public static final EAFloat         MELEE_DAMAGE       = new EntityAttributeE("melee_damage", UUID.fromString("04a931c2-b0bf-44de-bbed-1a8f0d56c584"), SharedMonsterAttributes.attackDamage);
    public static final EAFloat         RANGE_DAMAGE       = new EAFloat         ("range_damage");

    public static final EAFloat         MELEE_DAMAGE_STAB  = new EAFloat         ("melee_damage");
    public static final EAFloat         MELEE_DAMAGE_SLIME = new EASlimeDamage   ("melee_damage");
}
