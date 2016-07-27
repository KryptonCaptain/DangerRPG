package mixac1.dangerrpg.capability.entityattr;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.util.Multiplier;
import mixac1.dangerrpg.util.Multiplier.MultiplierAdd;
import mixac1.dangerrpg.util.Multiplier.MultiplierE;

public class EntityAttributes
{
	protected static final MultiplierE ADD_2   = new MultiplierAdd(2F);

	protected static final MultiplierE ADD_0d2 = new MultiplierAdd(0.2F);

	protected static final Multiplier  MUL_1   = new Multiplier()
    {
        @Override
        public float up(float value)
        {
            return value;
        }
    };
    
    public static final EntityAttribute LVL = new EntityAttribute("lvl", 1F);
    
    public static final EntityAttribute HEALTH = new EAHealth("ea_health");
}
