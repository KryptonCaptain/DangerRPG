package mixac1.dangerrpg.api;

import java.util.HashMap;

import mixac1.dangerrpg.api.item.IADynamic;
import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.api.player.PlayerAttribute;
import mixac1.dangerrpg.api.player.PlayerAttributeE;
import mixac1.dangerrpg.capability.ItemAttrParams;
import mixac1.dangerrpg.capability.ItemAttrParams.Multiplier;
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

    public static void registerStaticItemAttribute(HashMap<ItemAttribute, ItemAttrParams> map, IAStatic attr, float value)
    {
        map.put(attr, new ItemAttrParams(value, null));
    }

    public static void registerDynamicItemAttribute(HashMap<ItemAttribute, ItemAttrParams> map, IADynamic attr, float value, Multiplier mul)
    {
        map.put(attr, new ItemAttrParams(value, mul));
    }
}
