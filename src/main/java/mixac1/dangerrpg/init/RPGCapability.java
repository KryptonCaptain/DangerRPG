package mixac1.dangerrpg.init;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameData;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.RPGRegister;
import mixac1.dangerrpg.api.entity.IRPGEntity.RPGCommonEntityMob;
import mixac1.dangerrpg.api.entity.IRPGEntity.RPGEntityRangeMob;
import mixac1.dangerrpg.api.item.IRPGItem;
import mixac1.dangerrpg.capability.RPGableEntity;
import mixac1.dangerrpg.capability.RPGableEntity.EntityAttributesMap;
import mixac1.dangerrpg.capability.RPGableItem;
import mixac1.dangerrpg.capability.RPGableItem.ItemAttributesMap;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public abstract class RPGCapability
{
    public static LvlItemRegistr lvlItemRegistr = new LvlItemRegistr();

    public static RPGEntityRegistr rpgEntityRegistr = new RPGEntityRegistr();

    public static void preLoad(FMLPostInitializationEvent e)
    {
        registerDefaultLvlableItems();

        registerDefaultRPGEntities();
    }

    public static void load(FMLPostInitializationEvent e)
    {
        loadEntities();

        loadItems();

        lvlItemRegistr.createCloneSet();
        rpgEntityRegistr.createCloneSet();
    }

    private static void registerDefaultLvlableItems()
    {
        Iterator iterator = GameData.getItemRegistry().iterator();
        while(iterator.hasNext()) {
            Item item = (Item) iterator.next();
            if (item instanceof IRPGItem) {
                RPGRegister.registerRPGItem(item, (IRPGItem) item);
            }
        }

        RPGRegister.registerRPGItem(Items.wooden_hoe, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.stone_hoe, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.iron_hoe, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.golden_hoe, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.diamond_hoe, IRPGItem.DEFAULT_SWORD);

        RPGRegister.registerRPGItem(Items.wooden_axe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.stone_axe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.iron_axe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.golden_axe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.diamond_axe, IRPGItem.DEFAULT_TOOL);

        RPGRegister.registerRPGItem(Items.wooden_pickaxe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.stone_pickaxe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.iron_pickaxe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.golden_pickaxe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.diamond_pickaxe, IRPGItem.DEFAULT_TOOL);

        RPGRegister.registerRPGItem(Items.wooden_shovel, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.stone_shovel, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.iron_shovel, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.golden_shovel, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.diamond_shovel, IRPGItem.DEFAULT_TOOL);

        RPGRegister.registerRPGItem(Items.wooden_hoe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.stone_hoe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.iron_hoe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.golden_hoe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.diamond_hoe, IRPGItem.DEFAULT_TOOL);

        RPGRegister.registerRPGItem(Items.wooden_sword, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.stone_sword, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.iron_sword, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.golden_sword, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.diamond_sword, IRPGItem.DEFAULT_SWORD);

        RPGRegister.registerRPGItem(Items.leather_boots, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.leather_chestplate, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.leather_helmet, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.leather_leggings, IRPGItem.DEFAULT_ARMOR);

        RPGRegister.registerRPGItem(Items.chainmail_boots, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.chainmail_chestplate, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.chainmail_helmet, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.chainmail_leggings, IRPGItem.DEFAULT_ARMOR);

        RPGRegister.registerRPGItem(Items.iron_boots, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.iron_chestplate, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.iron_helmet, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.iron_leggings, IRPGItem.DEFAULT_ARMOR);

        RPGRegister.registerRPGItem(Items.golden_boots, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.golden_chestplate, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.golden_helmet, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.golden_leggings, IRPGItem.DEFAULT_ARMOR);

        RPGRegister.registerRPGItem(Items.diamond_boots, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.diamond_chestplate, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.diamond_helmet, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.diamond_leggings, IRPGItem.DEFAULT_ARMOR);

        RPGRegister.registerRPGItem(Items.bow, IRPGItem.DEFAULT_BOW);
    }

    private static void registerDefaultRPGEntities()
    {
        RPGRegister.registerRPGEntity(EntityBlaze.class, new RPGEntityRangeMob(5f));
        RPGRegister.registerRPGEntity(EntitySkeleton.class, new RPGEntityRangeMob(2f));
        RPGRegister.registerRPGEntity(EntityGhast.class, new RPGEntityRangeMob(6f));
        RPGRegister.registerRPGEntity(EntityWither.class, new RPGEntityRangeMob(8f));

        RPGRegister.registerRPGEntity(EntitySlime.class, new RPGCommonEntityMob(EntityAttributes.MELEE_DAMAGE_SLIME, 0f));
        RPGRegister.registerRPGEntity(EntityMagmaCube.class, new RPGCommonEntityMob(EntityAttributes.MELEE_DAMAGE_SLIME, 2f));

        RPGRegister.registerRPGEntity(EntityWolf.class, new RPGCommonEntityMob(EntityAttributes.MELEE_DAMAGE_STAB, 3f));
        RPGRegister.registerRPGEntity(EntityIronGolem.class, new RPGCommonEntityMob(EntityAttributes.MELEE_DAMAGE_STAB, 14f));
        RPGRegister.registerRPGEntity(EntityDragon.class, new RPGCommonEntityMob(EntityAttributes.MELEE_DAMAGE_STAB, 10f));
    }

    private static void loadEntities()
    {
        for (Object obj : EntityList.classToStringMapping.entrySet()) {
            Entry<Class, String> entry = (Entry<Class, String>) obj;
            if (entry.getKey() != null && entry.getValue() != null) {
                RPGableEntity.registerEntity(entry.getKey());
            }
        }

        RPGableEntity.registerEntity(EntityPlayer.class);

        for (Entry<Class<? extends EntityLivingBase>, EntityAttributesMap> data : rpgEntityRegistr.data.entrySet()) {
            RPGableEntity.registerEntityDefault(data.getKey(), data.getValue());
            data.getValue().rpgComponent.registerAttributes(data.getKey(), data.getValue());
            if (RPGConfig.entityAllEntityRPG || RPGConfig.entitySupportedRPGEntities.contains(EntityList.classToStringMapping.get(data.getKey()))) {
                rpgEntityRegistr.registr.add(data.getKey());
                DangerRPG.infoLog(Utils.toString("Register rpg entity: ", EntityList.classToStringMapping.get(data.getKey())));
            }
        }
        rpgEntityRegistr.registr.add(EntityPlayer.class);
    }

    private static void loadItems()
    {
        Iterator iterator = GameData.getItemRegistry().iterator();
        while(iterator.hasNext()) {
            RPGableItem.registerRPGItem((Item) iterator.next());
        }

        for (Entry<Item, ItemAttributesMap> data : lvlItemRegistr.data.entrySet()) {
            RPGableItem.registerParamsDefault(data.getKey(), data.getValue());
            data.getValue().rpgComponent.registerAttributes(data.getKey(), data.getValue());
            if (RPGConfig.itemAllItemsLvlable || RPGConfig.itemSupportedLvlItems.contains(data.getKey().unlocalizedName)) {
                lvlItemRegistr.registr.add(data.getKey());
                DangerRPG.infoLog(Utils.toString("Register lvlable item: ", data.getKey().unlocalizedName, " (mod supported: ", String.valueOf(data.getValue().isSupported), ")"));
            }
        }
    }

    public static class LvlItemRegistr
    {
        public Set<Item> registr = new HashSet<Item>();
        public HashMap<Item, ItemAttributesMap> data = new HashMap<Item, ItemAttributesMap>();

        private Set<Item> copy;

        private void createCloneSet()
        {
            copy = new HashSet<Item>(registr);
        }

        public Set<Item> getCloneSet()
        {
            return copy;
        }
    }

    public static class RPGEntityRegistr
    {
        public Set<Class<? extends EntityLivingBase>> registr = new HashSet<Class<? extends EntityLivingBase>>();
        public HashMap<Class<? extends EntityLivingBase>, EntityAttributesMap> data = new HashMap<Class<? extends EntityLivingBase>, EntityAttributesMap>();

        private Set<Class<? extends EntityLivingBase>> copy;

        private void createCloneSet()
        {
            copy = new HashSet<Class<? extends EntityLivingBase>>(registr);
        }

        public Set<Class<? extends EntityLivingBase>> getCloneSet()
        {
            return copy;
        }

        public boolean isRegistered(EntityLivingBase entity)
        {
            return entity instanceof EntityPlayer ?
                RPGCapability.rpgEntityRegistr.registr.contains(EntityPlayer.class) :
                RPGCapability.rpgEntityRegistr.registr.contains(entity.getClass());
        }

        public EntityAttributesMap getAttributesSet(EntityLivingBase entity)
        {
            return entity instanceof EntityPlayer ?
                RPGCapability.rpgEntityRegistr.data.get(EntityPlayer.class) :
                RPGCapability.rpgEntityRegistr.data.get(entity.getClass());
        }

        public void setAttributesSet(EntityLivingBase entity, EntityAttributesMap set)
        {
            data.put(entity instanceof EntityPlayer ? EntityPlayer.class : entity.getClass(), set);
        }
    }
}
