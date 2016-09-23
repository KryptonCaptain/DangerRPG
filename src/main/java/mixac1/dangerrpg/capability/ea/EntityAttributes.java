package mixac1.dangerrpg.capability.ea;

import java.util.UUID;

import mixac1.dangerrpg.api.entity.EAWithExistIAttr;
import mixac1.dangerrpg.api.entity.EAWithIAttr;
import mixac1.dangerrpg.api.entity.EAWithIAttr.RPGAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;

public class EntityAttributes
{
    public static final IAttribute rangeAttackDamage = new RPGAttribute("range_damage");


    public static final EALvl           LVL                = new EALvl           ("lvl");
    public static final EAWithIAttr     HEALTH             = new EAHealth        ("health",       UUID.fromString("fd6315bf-9f57-46cb-bb38-4aacb5d2967a"), SharedMonsterAttributes.maxHealth);
    public static final EAWithIAttr     MELEE_DAMAGE       = new EAWithExistIAttr("melee_damage", UUID.fromString("04a931c2-b0bf-44de-bbed-1a8f0d56c584"), SharedMonsterAttributes.attackDamage);
    public static final EAWithIAttr     RANGE_DAMAGE       = new EAWithIAttr     ("range_damage", rangeAttackDamage);

    public static final EAWithIAttr     MELEE_DAMAGE_STAB  = new EAWithIAttr     ("melee_damage", SharedMonsterAttributes.attackDamage);
    public static final EAWithIAttr     MELEE_DAMAGE_SLIME = new EASlimeDamage   ("melee_damage", SharedMonsterAttributes.attackDamage);
}
