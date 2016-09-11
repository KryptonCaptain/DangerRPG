package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.util.IMultiplier;
import mixac1.dangerrpg.util.IMultiplier.IMultiplierE;
import mixac1.dangerrpg.util.IMultiplier.MultiplierAdd;

public class EntityAttributes
{
    public static final IMultiplierE<Float> ADD_1   = IMultiplier.ADD_1;
    public static final IMultiplierE<Float> ADD_2   = new MultiplierAdd(2F);
    public static final IMultiplierE        MUL_1   = IMultiplier.MUL_1;

    public static final EALvl           LVL             = new EALvl         ("lvl");
    public static final EAFloat         HEALTH          = new EAHealth      ("health");
    public static final EAMeleeDamage   MELEE_DAMAGE    = new EAMeleeDamage ("melee_damage");
    public static final EAFloat         RANGE_DAMAGE    = new EAFloat       ("range_damage");
}
