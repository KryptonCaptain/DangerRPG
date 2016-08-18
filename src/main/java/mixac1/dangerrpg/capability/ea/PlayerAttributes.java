package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.util.IMultiplier.IMultiplierE;
import mixac1.dangerrpg.util.IMultiplier.MultiplierAdd;

public class PlayerAttributes extends EntityAttributes
{
    protected static final IMultiplierE<Float> ADD_0d001 = new MultiplierAdd(0.001F);
    protected static final IMultiplierE<Float> ADD_0d014 = new MultiplierAdd(0.014F);
    protected static final IMultiplierE<Float> ADD_0d5 = new MultiplierAdd(0.5F);
    protected static final IMultiplierE<Float> ADD_0d2 = new MultiplierAdd(0.2F);
                                                                                 /* sVal                         sXP mLvl mulVal    mulXP */
    public static final EAFloat     MANA          = new EAMana      ("mana",        10F, new LvlEAProvider<Float>(2, 1000, ADD_2,     MUL_1));
    public static final EAFloat     STRENGTH      = new EAFloat     ("str",         0F,  new LvlEAProvider<Float>(2, 100,  ADD_1,     MUL_1));
    public static final EAFloat     AGILITY       = new EAFloat     ("agi",         0F,  new LvlEAProvider<Float>(2, 100,  ADD_1,     MUL_1));
    public static final EAFloat     INTELLIGENCE  = new EAFloat     ("int",         0F,  new LvlEAProvider<Float>(2, 100,  ADD_1,     MUL_1));
    public static final EAFloat     MANA_REGEN    = new EAFloat     ("mana_regen",  1F,  new LvlEAProvider<Float>(2, 1000, ADD_0d2,   MUL_1));
    public static final EAFloat     EFFICIENCY    = new EAFloat     ("effic",       0F,  new LvlEAProvider<Float>(2, 100,  ADD_2,     MUL_1));

    public static final EAFloat     MOVE_SPEED    = new EAMotion    ("move_speed",       new LvlEAProvider<Float>(2, 20,   ADD_0d001, MUL_1));
    public static final EAFloat     SNEAK_SPEED   = new EAMotion    ("sneak_speed",      new LvlEAProvider<Float>(2, 20,   ADD_0d001, MUL_1));
    public static final EAFloat     FLY_SPEED     = new EAMotion    ("fly_speed",        new LvlEAProvider<Float>(2, 20,   ADD_0d001, MUL_1));
    public static final EAFloat     SWIM_SPEED    = new EAMotion    ("swim_speed",       new LvlEAProvider<Float>(2, 20,   ADD_0d001, MUL_1));
    public static final EAFloat     JUMP_HEIGHT   = new EAMotion    ("jump_height",      new LvlEAProvider<Float>(2, 20,   ADD_0d014, MUL_1));
    public static final EAFloat     JUMP_RANGE    = new EAMotion    ("jump_range",       new LvlEAProvider<Float>(2, 20,   ADD_0d001, MUL_1));

    public static final EAFloat     STEEL_MUSC    = new EAFloat     ("steel_musc",  0f,  new LvlEAProvider<Float>(2, 20,   ADD_0d5,   MUL_1));
    public static final EAFloat     STONESKIN     = new EAPercent   ("stoneskin",   0f,  new LvlEAProvider<Float>(2, 20,   ADD_1,     MUL_1));
    public static final EAFloat     MAG_IMUN      = new EAPercent   ("mag_imun",    0f,  new LvlEAProvider<Float>(2, 20,   ADD_1,     MUL_1));

    public static final EAFloat     CURR_MANA     = new EACurrMana      ("curr_mana");
    public static final EAFloat     SPEED_COUNTER = new EASpeedCounter  ("speed_counter");
}