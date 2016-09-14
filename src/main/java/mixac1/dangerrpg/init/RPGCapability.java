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
import mixac1.dangerrpg.api.item.ILvlableItem;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.LvlableItem.ItemAttributesMap;
import mixac1.dangerrpg.capability.RPGEntityData;
import mixac1.dangerrpg.capability.RPGEntityData.EntityAttributesSet;
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
            if (item instanceof ILvlableItem) {
                RPGRegister.registerLvlableItem(item, (ILvlableItem) item);
            }
        }

        RPGRegister.registerLvlableItem(Items.wooden_hoe, ILvlableItem.DEFAULT_SWORD);
        RPGRegister.registerLvlableItem(Items.stone_hoe, ILvlableItem.DEFAULT_SWORD);
        RPGRegister.registerLvlableItem(Items.iron_hoe, ILvlableItem.DEFAULT_SWORD);
        RPGRegister.registerLvlableItem(Items.golden_hoe, ILvlableItem.DEFAULT_SWORD);
        RPGRegister.registerLvlableItem(Items.diamond_hoe, ILvlableItem.DEFAULT_SWORD);

        RPGRegister.registerLvlableItem(Items.wooden_axe, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.stone_axe, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.iron_axe, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.golden_axe, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.diamond_axe, ILvlableItem.DEFAULT_TOOL);

        RPGRegister.registerLvlableItem(Items.wooden_pickaxe, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.stone_pickaxe, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.iron_pickaxe, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.golden_pickaxe, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.diamond_pickaxe, ILvlableItem.DEFAULT_TOOL);

        RPGRegister.registerLvlableItem(Items.wooden_shovel, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.stone_shovel, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.iron_shovel, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.golden_shovel, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.diamond_shovel, ILvlableItem.DEFAULT_TOOL);

        RPGRegister.registerLvlableItem(Items.wooden_hoe, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.stone_hoe, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.iron_hoe, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.golden_hoe, ILvlableItem.DEFAULT_TOOL);
        RPGRegister.registerLvlableItem(Items.diamond_hoe, ILvlableItem.DEFAULT_TOOL);

        RPGRegister.registerLvlableItem(Items.leather_boots, ILvlableItem.DEFAULT_ARMOR);
        RPGRegister.registerLvlableItem(Items.leather_chestplate, ILvlableItem.DEFAULT_ARMOR);
        RPGRegister.registerLvlableItem(Items.leather_helmet, ILvlableItem.DEFAULT_ARMOR);
        RPGRegister.registerLvlableItem(Items.leather_leggings, ILvlableItem.DEFAULT_ARMOR);

        RPGRegister.registerLvlableItem(Items.chainmail_boots, ILvlableItem.DEFAULT_ARMOR);
        RPGRegister.registerLvlableItem(Items.chainmail_chestplate, ILvlableItem.DEFAULT_ARMOR);
        RPGRegister.registerLvlableItem(Items.chainmail_helmet, ILvlableItem.DEFAULT_ARMOR);
        RPGRegister.registerLvlableItem(Items.chainmail_leggings, ILvlableItem.DEFAULT_ARMOR);

        RPGRegister.registerLvlableItem(Items.iron_boots, ILvlableItem.DEFAULT_ARMOR);
        RPGRegister.registerLvlableItem(Items.iron_chestplate, ILvlableItem.DEFAULT_ARMOR);
        RPGRegister.registerLvlableItem(Items.iron_helmet, ILvlableItem.DEFAULT_ARMOR);
        RPGRegister.registerLvlableItem(Items.iron_leggings, ILvlableItem.DEFAULT_ARMOR);

        RPGRegister.registerLvlableItem(Items.golden_boots, ILvlableItem.DEFAULT_ARMOR);
        RPGRegister.registerLvlableItem(Items.golden_chestplate, ILvlableItem.DEFAULT_ARMOR);
        RPGRegister.registerLvlableItem(Items.golden_helmet, ILvlableItem.DEFAULT_ARMOR);
        RPGRegister.registerLvlableItem(Items.golden_leggings, ILvlableItem.DEFAULT_ARMOR);

        RPGRegister.registerLvlableItem(Items.diamond_boots, ILvlableItem.DEFAULT_ARMOR);
        RPGRegister.registerLvlableItem(Items.diamond_chestplate, ILvlableItem.DEFAULT_ARMOR);
        RPGRegister.registerLvlableItem(Items.diamond_helmet, ILvlableItem.DEFAULT_ARMOR);
        RPGRegister.registerLvlableItem(Items.diamond_leggings, ILvlableItem.DEFAULT_ARMOR);

        RPGRegister.registerLvlableItem(Items.bow, ILvlableItem.DEFAULT_BOW);
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
                RPGEntityData.registerEntity(entry.getKey());
            }
        }

        RPGEntityData.registerEntity(EntityPlayer.class);

        for (Entry<Class<? extends EntityLivingBase>, EntityAttributesSet> data : rpgEntityRegistr.data.entrySet()) {
            RPGEntityData.registerEntityDefault(data.getKey(), data.getValue());
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
            LvlableItem.registerLvlableItem((Item) iterator.next());
        }

        for (Entry<Item, ItemAttributesMap> data : lvlItemRegistr.data.entrySet()) {
            LvlableItem.registerParamsDefault(data.getKey(), data.getValue());
            data.getValue().lvlComponent.registerAttributes(data.getKey(), data.getValue());
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
        public HashMap<Class<? extends EntityLivingBase>, EntityAttributesSet> data = new HashMap<Class<? extends EntityLivingBase>, EntityAttributesSet>();

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

        public EntityAttributesSet getAttributesSet(EntityLivingBase entity)
        {
            return entity instanceof EntityPlayer ?
                RPGCapability.rpgEntityRegistr.data.get(EntityPlayer.class) :
                RPGCapability.rpgEntityRegistr.data.get(entity.getClass());
        }

        public void setAttributesSet(EntityLivingBase entity, EntityAttributesSet set)
        {
            data.put(entity instanceof EntityPlayer ? EntityPlayer.class : entity.getClass(), set);
        }
    }
}
