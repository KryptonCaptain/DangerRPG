package mixac1.dangerrpg.init;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.registry.GameData;
import mixac1.dangerrpg.capability.LvlableItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Property;

public abstract class RPGCapability
{
    public static void load()
    {
        List<String> itemNames = new ArrayList<String>();
        Iterator iterator = GameData.getItemRegistry().iterator();
        while(iterator.hasNext()) {
            Item item = (Item)iterator.next();
            if (LvlableItem.registerLvlableItem(item)) {
                itemNames.add(item.getUnlocalizedName());
            }
        }

        if (RPGConfig.itemAllItemsLvlable) {
            ConfigCategory cat = RPGConfig.config.getCategory("Supported Lvl items");
            cat.clear();
            Property prop = RPGConfig.config.get(cat.getName(), "itemSupportedLvlItems", itemNames.toArray(new String[itemNames.size()]));
            if (RPGConfig.config.hasChanged()) {
                RPGConfig.config.save();
            }
        }
    }
}
