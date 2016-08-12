package mixac1.dangerrpg.init;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.registry.GameData;
import mixac1.dangerrpg.capability.EntityData;
import mixac1.dangerrpg.capability.EntityData.EntityAttributesSet;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.LvlableItem.ItemAttributesMap;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public abstract class RPGCapability
{
    public static HashMap<Item, ItemAttributesMap> iaValues = new HashMap<Item, ItemAttributesMap>();

    public static HashMap<Class<? extends EntityLivingBase>, EntityAttributesSet> eaMap = new HashMap<Class<? extends EntityLivingBase>, EntityAttributesSet>();

    public static void load()
    {
        loadEntities();

        loadItems();
    }

    private static void loadEntities()
    {
        if (RPGConfig.entityAllEntityRPG) {
            List<String> names = new ArrayList<String>();

            for (Object obj : EntityList.classToStringMapping.keySet()) {
                Class entityClass = (Class) obj;
                if (EntityData.registerEntity(entityClass)) {
                    names.add(entityClass.getName());
                }
            }

            Collections.sort(names);
            RPGConfig.getPropertyStrings("Supported RPG entities", "entitySupportedRPGEntities",
                    names.toArray(new String[names.size()]), null, true);
            if (RPGConfig.config.hasChanged()) {
                RPGConfig.config.save();
            }
        }
        else {
            for (Object obj : EntityList.classToStringMapping.keySet()) {
                EntityData.registerEntity((Class) obj);
            }
        }

        EntityData.registerEntity(EntityPlayer.class);
    }

    private static void loadItems()
    {
        if (RPGConfig.itemAllItemsLvlable) {
            List<String> names = new ArrayList<String>();

            Iterator iterator = GameData.getItemRegistry().iterator();
            while(iterator.hasNext()) {
                Item item = (Item)iterator.next();
                if (LvlableItem.registerLvlableItem(item)) {
                    names.add(item.getUnlocalizedName());
                }
            }

            Collections.sort(names);
            RPGConfig.getPropertyStrings("Supported Lvl items", "itemSupportedLvlItems",
                    names.toArray(new String[names.size()]), null, true);
            if (RPGConfig.config.hasChanged()) {
                RPGConfig.config.save();
            }
        }
        else {
            Iterator iterator = GameData.getItemRegistry().iterator();
            while(iterator.hasNext()) {
                LvlableItem.registerLvlableItem((Item) iterator.next());
            }
        }
    }

    public static EntityAttributesSet getEntityAttributesSet(EntityLivingBase entity)
    {
        return entity instanceof EntityPlayer ?
            RPGCapability.eaMap.get(EntityPlayer.class) :
            RPGCapability.eaMap.get(entity.getClass());
    }
}
