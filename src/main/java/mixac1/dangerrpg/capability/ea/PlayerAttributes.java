package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.util.IMultiplier;

public class PlayerAttributes extends EntityAttributes
{
                                                                                /* sVal                         sXP  mLvl mulVal            mulXP */
    public static final EAFloat     HEALTH        = new EAHealth    ("health",      0f,  new LvlEAProvider<Float>(5, 999, ADD_2,            IMultiplier.MUL_1));
    public static final EAFloat     MANA          = new EAMana      ("mana",        10F, new LvlEAProvider<Float>(5, 999, ADD_2,            IMultiplier.MUL_1));
    public static final EAFloat     STRENGTH      = new EAFloat     ("str",         0F,  new LvlEAProvider<Float>(5, 99,  IMultiplier.ADD_1, IMultiplier.MUL_1));
    public static final EAFloat     AGILITY       = new EAFloat     ("agi",         0F,  new LvlEAProvider<Float>(5, 99,  IMultiplier.ADD_1, IMultiplier.MUL_1));
    public static final EAFloat     INTELLIGENCE  = new EAFloat     ("int",         0F,  new LvlEAProvider<Float>(5, 99,  IMultiplier.ADD_1, IMultiplier.MUL_1));
    public static final EAFloat     MANA_REGEN    = new EAFloat     ("mana_regen",  1F,  new LvlEAProvider<Float>(5, 999, ADD_0d2,          IMultiplier.MUL_1));
    public static final EAFloat     EFFICIENCY    = new EAFloat     ("effic",       0F,  new LvlEAProvider<Float>(5, 99,  ADD_2,            IMultiplier.MUL_1));

    public static final EAFloat     CURR_MANA     = new EACurrMana      ("curr_mana");
    public static final EAFloat     SPEED_COUNTER = new EASpeedCounter  ("speed_counter");
}
