package mixac1.dangerrpg.item;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.item.Item;

public class ItemE extends Item
{
    public ItemE(String name)
    {
        setUnlocalizedName(name);
        setTextureName(Utils.toString(DangerRPG.MODID, ":", name));
        setCreativeTab(RPGOther.tabDangerRPG);
    }
}
