package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.util.IMultiplier.IMultiplierE;
import mixac1.dangerrpg.util.IMultiplier.MultiplierAdd;
import mixac1.dangerrpg.util.ITypeProvider;

public class EntityAttributes
{
    protected static final IMultiplierE<Float> ADD_2   = new MultiplierAdd(2F);

    protected static final IMultiplierE<Float> ADD_0d2 = new MultiplierAdd(0.2F);

    public static final EntityAttribute<Boolean> IS_INIT = new EntityAttribute<Boolean>(ITypeProvider.BOOLEAN, "init", false, null);

    public static final EntityAttribute<Integer> LVL     = new EntityAttribute<Integer>(ITypeProvider.INTEGER, "lvl", 1, null);

    public static final EAHealth                 HEALTH  = new EAHealth("health", 0f, null);
}