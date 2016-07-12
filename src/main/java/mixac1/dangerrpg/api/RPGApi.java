package mixac1.dangerrpg.api;

import mixac1.dangerrpg.api.player.PlayerAttribute;
import mixac1.dangerrpg.api.player.PlayerAttributeE;
import mixac1.dangerrpg.capability.PlayerData;

public class RPGApi
{
    public static void registerPlayerAttribute(PlayerAttribute pa)
    {
        PlayerData.workAttributes.add(pa);
    }

    public static void registerPlayerAttributeE(PlayerAttributeE pa)
    {
        PlayerData.playerAttributes.add(pa);
    }
}
