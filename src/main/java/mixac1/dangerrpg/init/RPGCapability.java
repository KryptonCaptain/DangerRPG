package mixac1.dangerrpg.init;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameData;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.RPGRegister;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.IRPGEntity;
import mixac1.dangerrpg.api.entity.IRPGEntity.RPGCommonEntityMob;
import mixac1.dangerrpg.api.entity.IRPGEntity.RPGEntityRangeMob;
import mixac1.dangerrpg.api.item.IRPGItem;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.RPGableEntity;
import mixac1.dangerrpg.capability.RPGableEntity.EntityData;
import mixac1.dangerrpg.capability.RPGableItem;
import mixac1.dangerrpg.capability.RPGableItem.ItemData;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import mixac1.dangerrpg.init.RPGCapability.RPGDataRegister.ElementData;
import mixac1.dangerrpg.util.Tuple.Pair;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public abstract class RPGCapability
{
    public static RPGItemRegister   rpgItemRegistr   = new RPGItemRegister();
    public static RPGEntityRegister rpgEntityRegistr = new RPGEntityRegister();

    public static HashMap<Integer, ItemAttribute>   mapIntToItemAttribute   = new HashMap<Integer, ItemAttribute>();
    public static HashMap<Integer, EntityAttribute> mapIntToEntityAttribute = new HashMap<Integer, EntityAttribute>();

    public static void preLoad(FMLPostInitializationEvent e)
    {
        registerDefaultRPGItems();

        registerDefaultRPGEntities();
    }

    public static void load(FMLPostInitializationEvent e)
    {
        loadEntities();

        loadItems();

        rpgItemRegistr.createTransferData();
        rpgEntityRegistr.createTransferData();
    }

    private static void registerDefaultRPGItems()
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
        RPGRegister.registerRPGEntity(EntityBat.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntitySquid.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntityChicken.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntitySnowman.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntityCow.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntityPig.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntityHorse.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntityOcelot.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntitySheep.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntityMooshroom.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntityVillager.class, IRPGEntity.DEFAULT_LIVING);

        RPGRegister.registerRPGEntity(EntitySpider.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntityZombie.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntityCreeper.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntityCaveSpider.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntityWitch.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntityPigZombie.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntitySilverfish.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntityEnderman.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntityGiantZombie.class, IRPGEntity.DEFAULT_MOB);

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

        for (Entry<Class<? extends EntityLivingBase>, EntityData> it : rpgEntityRegistr.entrySet()) {
            RPGableEntity.registerEntityDefault(it.getKey(), it.getValue());
            it.getValue().rpgComponent.registerAttributes(it.getKey(), it.getValue());
            if (RPGConfig.EntityConfig.isAllEntitiesRPGable || RPGConfig.EntityConfig.supportedRPGEntities.contains(EntityList.classToStringMapping.get(it.getKey()))) {
                rpgEntityRegistr.get(it.getKey()).isActivated = true;
                DangerRPG.infoLog(String.format("Register RPG entity (sup from mod: %s): %s",
                                  it.getValue().isSupported ? " true" : "false", EntityList.classToStringMapping.get(it.getKey())));
            }
        }

        rpgEntityRegistr.get(EntityPlayer.class).isActivated = true;
    }

    private static void loadItems()
    {
        Iterator iterator = GameData.getItemRegistry().iterator();
        while(iterator.hasNext()) {
            RPGableItem.registerRPGItem((Item) iterator.next());
        }

        for (Entry<Item, ItemData> it : rpgItemRegistr.entrySet()) {
            RPGableItem.registerParamsDefault(it.getKey(), it.getValue());
            it.getValue().rpgComponent.registerAttributes(it.getKey(), it.getValue());
            if (RPGConfig.ItemConfig.isAllItemsRPGable || RPGConfig.ItemConfig.supportedRPGItems.contains(it.getKey().delegate.name())) {
                rpgItemRegistr.get(it.getKey()).isActivated = true;
                DangerRPG.infoLog(String.format("Register RPG item (sup from mod: %s): %s",
                                  it.getValue().isSupported ? " true" : "false", it.getKey().delegate.name()));
            }
        }
    }

    public static abstract class RPGDataRegister<Key, Data extends ElementData<TransferData>, TransferKey, TransferData> extends HashMap<Key, Data>
    {
        private byte[] tranferData;

        public boolean isActivated(Key key)
        {
            return containsKey(key) && get(key).isActivated;
        }

        public boolean isSupported(Key key)
        {
            return containsKey(key) && get(key).isSupported;
        }

        public HashMap<Key, Data> getActiveElements()
        {
            HashMap<Key, Data> map = new HashMap<Key, Data>();
            for (Entry<Key, Data> entry : entrySet()) {
                if (entry.getValue().isActivated) {
                    map.put(entry.getKey(), entry.getValue());
                }
            }
            return map;
        }

        protected abstract TransferKey codingKey(Key key);

        protected abstract Key decodingKey(TransferKey key);

        public void createTransferData()
        {
            LinkedList<Pair<TransferKey, TransferData>> list = new LinkedList<Pair<TransferKey, TransferData>>();
            for (Entry<Key, Data> entry : getActiveElements().entrySet()) {
                TransferKey key = codingKey(entry.getKey());
                if (key != null) {
                    list.add(new Pair<TransferKey, TransferData>(key, entry.getValue().getTransferData()));
                }
            }

            tranferData = Utils.serialize(list);
        }

        public byte[] getTransferData()
        {
            return tranferData;
        }

        public void extractTransferData(byte[] tranferData)
        {
            for (Entry<Key, Data> entry : entrySet()) {
                entry.getValue().isActivated = false;
            }

            LinkedList<Pair<TransferKey, TransferData>> list = Utils.deserialize(tranferData);
            for (Pair<TransferKey, TransferData> data : list) {
                Key key = decodingKey(data.value1);
                if (key != null) {
                    get(key).unpackTransferData(data.value2);
                    get(key).isActivated = true;
                }
            }
        }

        public static abstract class ElementData<TransferData>
        {
            public boolean isActivated;
            public boolean isSupported;

            public abstract TransferData getTransferData();

            public abstract void unpackTransferData(TransferData data);
        }
    }

    public static class RPGItemRegister extends RPGDataRegister<Item, ItemData, Integer, HashMap<Integer, Float>>
    {
        @Override
        protected Integer codingKey(Item key)
        {
            return Item.getIdFromItem(key);
        }

        @Override
        protected Item decodingKey(Integer key)
        {
            return Item.getItemById(key);
        }
    }

    public static class RPGEntityRegister extends RPGDataRegister<Class<? extends EntityLivingBase>, EntityData, String, Object>
    {
        public Class<? extends EntityLivingBase> getClass(EntityLivingBase entity)
        {
            return entity instanceof EntityPlayer ? EntityPlayer.class : entity.getClass();
        }

        public boolean isActivated(EntityLivingBase entity)
        {
            return super.isActivated(getClass(entity));
        }

        public EntityData get(EntityLivingBase entity)
        {
            return super.get(getClass(entity));
        }

        public void put(EntityLivingBase entity, EntityData data)
        {
            super.put(getClass(entity), data);
        }

        @Override
        protected String codingKey(Class<? extends EntityLivingBase> key)
        {
            return (String) (EntityList.classToStringMapping.containsKey(key) ?
                    EntityList.classToStringMapping.get(key) : EntityPlayer.class.isAssignableFrom(key) ?
                            "player" : null);
        }

        @Override
        protected Class<? extends EntityLivingBase> decodingKey(String key)
        {
            return (Class<? extends EntityLivingBase>) (EntityList.stringToClassMapping.containsKey(key) ?
                    EntityList.stringToClassMapping.get(key) : "player".equals(key) ?
                            EntityPlayer.class : null);
        }
    }
}
