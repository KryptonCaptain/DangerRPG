package mixac1.dangerrpg.init;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.registry.GameData;
import mixac1.dangerrpg.api.RPGRegister;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import mixac1.dangerrpg.capability.ea.PlayerAttributes;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Property;

public abstract class RPGCapability
{
    public static void load()
    {
        loadEntities();

        loadItems();
    }

    private static void loadEntities()
    {
        RPGRegister.registerCommonEntityAttribute(EntityAttributes.IS_INIT);
        RPGRegister.registerCommonEntityAttribute(EntityAttributes.LVL);

        RPGRegister.registerEntityAttribute(EntityAttributes.HEALTH);

        RPGRegister.registerLvlPlayerAttribute(PlayerAttributes.HEALTH);
        RPGRegister.registerLvlPlayerAttribute(PlayerAttributes.MANA);
        RPGRegister.registerLvlPlayerAttribute(PlayerAttributes.STRENGTH);
        RPGRegister.registerLvlPlayerAttribute(PlayerAttributes.AGILITY);
        RPGRegister.registerLvlPlayerAttribute(PlayerAttributes.INTELLIGENCE);
        RPGRegister.registerLvlPlayerAttribute(PlayerAttributes.EFFICIENCY);
        RPGRegister.registerLvlPlayerAttribute(PlayerAttributes.MANA_REGEN);

        RPGRegister.registerPlayerAttribute(PlayerAttributes.CURR_MANA);
        RPGRegister.registerPlayerAttribute(PlayerAttributes.SPEED_COUNTER);
    }

    private static void loadItems()
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
