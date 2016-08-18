package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.util.IMultiplier;
import mixac1.dangerrpg.util.IMultiplier.IMultiplierE;
import mixac1.dangerrpg.util.IMultiplier.MultiplierAdd;

public class EntityAttributes
{
    protected static final IMultiplierE<Float> ADD_1   = IMultiplier.ADD_1;

    protected static final IMultiplierE<Float> ADD_2   = new MultiplierAdd(2F);

    protected static final IMultiplierE        MUL_1   = IMultiplier.MUL_1;

    public static final EALvl       LVL     = new EALvl     ("lvl");

    public static final EAFloat     HEALTH  = new EAHealth  ("health", new LvlEAProvider<Float>(5, 999, ADD_2, MUL_1));

    public static final EADamage    DAMAGE  = new EADamage  ("damage", null);
}
