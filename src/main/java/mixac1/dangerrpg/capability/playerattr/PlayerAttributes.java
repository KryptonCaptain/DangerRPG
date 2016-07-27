package mixac1.dangerrpg.capability.playerattr;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.EntityAttributeE;
import mixac1.dangerrpg.capability.entityattr.EntityAttributes;
import mixac1.dangerrpg.util.Multiplier;

public class PlayerAttributes extends EntityAttributes
{
                                                                                          /* sVal  sXP  mLvl  mulVal            mulXP */

    public static final EntityAttributeE HEALTH        = new PAHealth        ("health",      0F,   5F,  999,  ADD_2,            MUL_1);
    public static final EntityAttributeE MANA          = new PAMana          ("mana",        10F,  5F,  999,  ADD_2,            MUL_1);
    public static final EntityAttributeE STRENGTH      = new EntityAttributeE("str",         0F,   5F,  99,   Multiplier.ADD_1, MUL_1);
    public static final EntityAttributeE AGILITY       = new EntityAttributeE("agi",         0F,   5F,  99,   Multiplier.ADD_1, MUL_1);
    public static final EntityAttributeE INTELLIGENCE  = new EntityAttributeE("int",         0F,   5F,  99,   Multiplier.ADD_1, MUL_1);
    public static final EntityAttributeE MANA_REGEN    = new EntityAttributeE("mana_regen",  1F,   5F,  999,  ADD_0d2,          MUL_1);
    public static final EntityAttributeE EFFICIENCY    = new EntityAttributeE("effic",       0F,   5F,  99,   ADD_2,            MUL_1);

    public static final EntityAttribute  CURR_MANA     = new PACurrMana      ("curr_mana");
    public static final EntityAttribute  SPEED_COUNTER = new PASpeedCounter  ("speed_counter");
}
