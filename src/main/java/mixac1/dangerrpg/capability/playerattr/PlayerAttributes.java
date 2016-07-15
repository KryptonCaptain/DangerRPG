package mixac1.dangerrpg.capability.playerattr;

import mixac1.dangerrpg.api.player.PlayerAttribute;
import mixac1.dangerrpg.api.player.PlayerAttributeE;
import mixac1.dangerrpg.util.Multiplier;
import mixac1.dangerrpg.util.Multiplier.MultiplierAdd;
import mixac1.dangerrpg.util.Multiplier.MultiplierE;

public class PlayerAttributes
{
    private static final MultiplierE ADD_2   = new MultiplierAdd(2F);

    private static final MultiplierE ADD_0d2 = new MultiplierAdd(0.2F);

    private static final Multiplier  MUL_1   = new Multiplier()
    {
        @Override
        public float up(float value)
        {
            return value;
        }
    };

                                                                                          /* sVal  sXP  mLvl  mulVal            mulXP */

    public static final PlayerAttributeE HEALTH        = new PAHealth        ("health",      0F,   5F,  999,  ADD_2,            MUL_1);
    public static final PlayerAttributeE MANA          = new PAMana          ("mana",        10F,  5F,  999,  ADD_2,            MUL_1);
    public static final PlayerAttributeE STRENGTH      = new PlayerAttributeE("str",         0F,   5F,  99,   Multiplier.ADD_1, MUL_1);
    public static final PlayerAttributeE AGILITY       = new PlayerAttributeE("agi",         0F,   5F,  99,   Multiplier.ADD_1, MUL_1);
    public static final PlayerAttributeE INTELLIGENCE  = new PlayerAttributeE("int",         0F,   5F,  99,   Multiplier.ADD_1, MUL_1);
    public static final PlayerAttributeE MANA_REGEN    = new PlayerAttributeE("mana_regen",  1F,   5F,  999,  ADD_0d2,          MUL_1);
    public static final PlayerAttributeE EFFICIENCY    = new PlayerAttributeE("effic",       0F,   5F,  99,   ADD_2,            MUL_1);

    public static final PlayerAttribute  CURR_MANA     = new PACurrMana      ("curr_mana");
    public static final PlayerAttribute  SPEED_COUNTER = new PASpeedCounter  ("speed_counter");
}
