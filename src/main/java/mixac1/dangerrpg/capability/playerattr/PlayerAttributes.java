package mixac1.dangerrpg.capability.playerattr;

import mixac1.dangerrpg.api.player.PlayerAttribute;
import mixac1.dangerrpg.api.player.PlayerAttributeE;

public class PlayerAttributes
{
    public static final PlayerAttributeE HEALTH        = new PAHealth("health", 0F, 2F, 5F);
    public static final PlayerAttributeE MANA          = new PAMana("mana", 10F, 2F, 5F);
    public static final PlayerAttributeE STRENGTH      = new PlayerAttributeE("str", 0F, 1F, 5F);
    public static final PlayerAttributeE AGILITY       = new PlayerAttributeE("agi", 0F, 1F, 5F);
    public static final PlayerAttributeE INTELLIGENCE  = new PlayerAttributeE("int", 0F, 1F, 5F);
    public static final PlayerAttributeE MANA_REGEN    = new PlayerAttributeE("mana_regen", 1F, 0.2F, 5F);
    public static final PlayerAttributeE EFFICIENCY    = new PlayerAttributeE("effic", 0F, 2F, 5F);
                                                       
    public static final PlayerAttribute  CURR_MANA     = new PACurrMana("curr_mana");
    public static final PlayerAttribute  SPEED_COUNTER = new PASpeedCounter("speed_counter");
}
