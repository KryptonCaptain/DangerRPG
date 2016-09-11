package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.util.IMultiplier.IMultiplierE;
import mixac1.dangerrpg.util.IMultiplier.MultiplierAdd;

public class PlayerAttributes extends EntityAttributes
{
    public static final IMultiplierE<Float> ADD_0d001 = new MultiplierAdd(0.001F);
    public static final IMultiplierE<Float> ADD_0d014 = new MultiplierAdd(0.014F);
    public static final IMultiplierE<Float> ADD_0d2   = new MultiplierAdd(0.2F);
    public static final IMultiplierE<Float> ADD_0d5   = new MultiplierAdd(0.5F);

    public static final EAFloat     MANA          = new EAMana      ("mana");
    public static final EAFloat     STRENGTH      = new EAFloat     ("str");
    public static final EAFloat     AGILITY       = new EAFloat     ("agi");
    public static final EAFloat     INTELLIGENCE  = new EAFloat     ("int");
    public static final EAFloat     MANA_REGEN    = new EAFloat     ("mana_regen");
    public static final EAFloat     HEALTH_REGEN  = new EAFloat     ("health_regen");
    public static final EAFloat     EFFICIENCY    = new EAFloat     ("effic");

    public static final EAFloat     MOVE_SPEED    = new EAMotion    ("move_speed");
    public static final EAFloat     SNEAK_SPEED   = new EAMotion    ("sneak_speed");
    public static final EAFloat     FLY_SPEED     = new EAMotion    ("fly_speed");
    public static final EAFloat     SWIM_SPEED    = new EAMotion    ("swim_speed");
    public static final EAFloat     JUMP_HEIGHT   = new EAMotion    ("jump_height");
    public static final EAFloat     JUMP_RANGE    = new EAMotion    ("jump_range");

    public static final EAFloat     STEEL_MUSC    = new EAFloat     ("steel_musc");
    public static final EAFloat     STONESKIN     = new EAPercent   ("stoneskin");
    public static final EAFloat     MAG_IMUN      = new EAPercent   ("mag_imun");

    public static final EAFloat     CURR_MANA     = new EACurrMana      ("curr_mana");
    public static final EAFloat     SPEED_COUNTER = new EASpeedCounter  ("speed_counter");
}