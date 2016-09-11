package mixac1.dangerrpg.capability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.IRPGEntity;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.api.event.InitRPGEntityEvent;
import mixac1.dangerrpg.api.event.RegEAEvent;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import mixac1.dangerrpg.capability.ea.PlayerAttributes;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGConfig;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.network.MsgSyncEntityData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;

public class RPGEntityData implements IExtendedEntityProperties
{
    protected static final String ID = "RPGCommonEntityData";

    public final EntityLivingBase entity;

    public HashMap<Integer, TypeStub> attributeMap = new HashMap<Integer, TypeStub>();
    public HashMap<Integer, Integer>  lvlMap       = new HashMap<Integer, Integer>();

    public RPGEntityData(EntityLivingBase entity)
    {
        this.entity = entity;
    }

    @Override
    public void init(Entity entity, World world)
    {
        for (EntityAttribute iter : getEntityAttributes()) {
            iter.init((EntityLivingBase) entity);
        }
    }

    public void serverInit()
    {
        if (isServerSide(entity) && !EntityAttributes.LVL.isInitedEntity(entity)) {
            for (EntityAttribute iter : getEntityAttributes()) {
                iter.serverInit(entity);
            }

            if (!(entity instanceof EntityPlayer)) {
                MinecraftForge.EVENT_BUS.post(new InitRPGEntityEvent(entity));
            }
        }
    }

    public static void register(EntityLivingBase entity)
    {
        entity.registerExtendedProperties(ID, new RPGEntityData(entity));
    }

    public static RPGEntityData get(EntityLivingBase entity)
    {
        return (RPGEntityData) entity.getExtendedProperties(ID);
    }

    public static boolean isServerSide(EntityLivingBase entity)
    {
        return !entity.worldObj.isRemote;
    }

    public boolean checkValid()
    {
        boolean result = EntityAttributes.LVL.isInitedEntity(entity);
        if (!result) {
            if (isServerSide(entity)) {
                init(entity, entity.worldObj);
                RPGNetwork.net.sendToAll(new MsgSyncEntityData(entity, this));
            }
            else {
                RPGNetwork.net.sendToServer(new MsgSyncEntityData(entity));
            }
        }
        return result;
    }

    public void rebuildOnDeath()
    {
        int count = 0;
        int lvl;

        ArrayList<LvlEAProvider> pas = new ArrayList<LvlEAProvider>();
        for (LvlEAProvider it : getLvlProviders()) {
            if ((lvl = it.getLvl(entity)) > 1) {
                pas.add(it);
                count += lvl - 1;
            }
        }

        if (count > RPGConfig.playerLoseLvlCount) {
            count = RPGConfig.playerLoseLvlCount;
        }

        for (int i = 0; i < count; ++i) {
            int rand = RPGOther.rand.nextInt(pas.size());
            pas.get(rand).up(entity, null, false);
            if (pas.get(rand).getLvl(entity) <= 1) {
                pas.remove(rand);
            }
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt)
    {
        NBTTagCompound tmp = new NBTTagCompound();
        for (EntityAttribute iter : getEntityAttributes()) {
            iter.toNBT(tmp, entity);
        }
        nbt.setTag(ID, tmp);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt)
    {
        NBTTagCompound tmp = (NBTTagCompound) nbt.getTag(ID);
        if (tmp != null) {
            for (EntityAttribute iter : getEntityAttributes()) {
                iter.fromNBT(tmp, entity);
            }
        }
    }

    private Object getObject(int hash, Collection list)
    {
        for (Object it : list) {
            if (it.hashCode() == hash) {
                return it;
            }
        }
        return null;
    }

    public EntityAttribute getEntityAttribute(int hash)
    {
        return (EntityAttribute) getObject(hash, getEntityAttributes());
    }

    public LvlEAProvider getLvlProvider(int hash)
    {
        return (LvlEAProvider) getObject(hash, getLvlProviders());
    }

    public Set<EntityAttribute> getEntityAttributes()
    {
        return RPGCapability.rpgEntityRegistr.getAttributesSet(entity).attributes.keySet();
    }

    public List<LvlEAProvider> getLvlProviders()
    {
        return RPGCapability.rpgEntityRegistr.getAttributesSet(entity).lvlProviders;
    }

    /*************************************************************************************/

    public static class TypeStub<Type>
    {
        public Type value;

        public TypeStub(Type value)
        {
            this.value = value;
        }
    }

    public static boolean isRPGEntity(EntityLivingBase entity)
    {
        return RPGCapability.rpgEntityRegistr.isRegistered(entity);
    }

    public static boolean registerEntity(Class entityClass)
    {
        if (EntityLivingBase.class.isAssignableFrom(entityClass)) {
            if (RPGCapability.rpgEntityRegistr.data.containsKey(entityClass)) {
                return true;
            }

            IRPGEntity iRPG = EntityPlayer.class.isAssignableFrom(entityClass) ? IRPGEntity.DEFAULT_PLAYER:
                              EntityMob.class.isAssignableFrom(entityClass)    ? IRPGEntity.DEFAULT_MOB:
                                                                                 IRPGEntity.DEFAULT_LIVING;

            RPGCapability.rpgEntityRegistr.data.put(entityClass, new EntityAttributesSet(iRPG, false));
            return true;
        }
        return false;
    }

    public static void registerEntityDefault(Class<? extends EntityLivingBase> entityClass, EntityAttributesSet set)
    {
        set.addEntityAttribute(EntityAttributes.LVL, 0);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.DefaultEAEvent(entityClass, set));
    }

    public static void registerEntityLiving(Class<? extends EntityLiving> entityClass, EntityAttributesSet set)
    {
        set.addEntityAttribute(EntityAttributes.HEALTH, 0f);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.EntytyLivingEAEvent(entityClass, set));
    }

    public static void registerEntityMob(Class<? extends EntityMob> entityClass, EntityAttributesSet set)
    {
        set.addEntityAttribute(EntityAttributes.MELEE_DAMAGE, 0f);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.EntytyMobEAEvent(entityClass, set));
    }

    public static void registerEntityPlayer(Class<? extends EntityPlayer> entityClass, EntityAttributesSet set)
    {
        set.addLvlableEntityAttribute(PlayerAttributes.HEALTH,       0f,  new LvlEAProvider<Float>(2, 1000, PlayerAttributes.ADD_2,     PlayerAttributes.MUL_1));
        set.addLvlableEntityAttribute(PlayerAttributes.MANA,         10f, new LvlEAProvider<Float>(2, 1000, PlayerAttributes.ADD_2,     PlayerAttributes.MUL_1));
        set.addLvlableEntityAttribute(PlayerAttributes.STRENGTH,     0f,  new LvlEAProvider<Float>(2, 1000, PlayerAttributes.ADD_1,     PlayerAttributes.MUL_1));
        set.addLvlableEntityAttribute(PlayerAttributes.AGILITY,      0f,  new LvlEAProvider<Float>(2, 1000, PlayerAttributes.ADD_1,     PlayerAttributes.MUL_1));
        set.addLvlableEntityAttribute(PlayerAttributes.INTELLIGENCE, 0f,  new LvlEAProvider<Float>(2, 1000, PlayerAttributes.ADD_1,     PlayerAttributes.MUL_1));
        set.addLvlableEntityAttribute(PlayerAttributes.EFFICIENCY,   0f,  new LvlEAProvider<Float>(2, 1000, PlayerAttributes.ADD_2,     PlayerAttributes.MUL_1));
        set.addLvlableEntityAttribute(PlayerAttributes.MANA_REGEN,   1f,  new LvlEAProvider<Float>(2, 1000, PlayerAttributes.ADD_0d2,   PlayerAttributes.MUL_1));
        set.addLvlableEntityAttribute(PlayerAttributes.HEALTH_REGEN, 0f,  new LvlEAProvider<Float>(2, 1000, PlayerAttributes.ADD_0d2,   PlayerAttributes.MUL_1));

        set.addLvlableEntityAttribute(PlayerAttributes.MOVE_SPEED,  0f,   new LvlEAProvider<Float>(2, 20,   PlayerAttributes.ADD_0d001, PlayerAttributes.MUL_1));
        set.addLvlableEntityAttribute(PlayerAttributes.SNEAK_SPEED, 0f,   new LvlEAProvider<Float>(2, 20,   PlayerAttributes.ADD_0d001, PlayerAttributes.MUL_1));
        set.addLvlableEntityAttribute(PlayerAttributes.FLY_SPEED,   0f,   new LvlEAProvider<Float>(2, 20,   PlayerAttributes.ADD_0d001, PlayerAttributes.MUL_1));
        set.addLvlableEntityAttribute(PlayerAttributes.SWIM_SPEED,  0f,   new LvlEAProvider<Float>(2, 20,   PlayerAttributes.ADD_0d001, PlayerAttributes.MUL_1));
        set.addLvlableEntityAttribute(PlayerAttributes.JUMP_HEIGHT, 0f,   new LvlEAProvider<Float>(2, 20,   PlayerAttributes.ADD_0d014, PlayerAttributes.MUL_1));
        set.addLvlableEntityAttribute(PlayerAttributes.JUMP_RANGE,  0f,   new LvlEAProvider<Float>(2, 20,   PlayerAttributes.ADD_0d001, PlayerAttributes.MUL_1));

        set.addLvlableEntityAttribute(PlayerAttributes.STEEL_MUSC,  0f,   new LvlEAProvider<Float>(2, 20,   PlayerAttributes.ADD_0d5,   PlayerAttributes.MUL_1));
        set.addLvlableEntityAttribute(PlayerAttributes.STONESKIN,   0f,   new LvlEAProvider<Float>(2, 20,   PlayerAttributes.ADD_1,     PlayerAttributes.MUL_1));
        set.addLvlableEntityAttribute(PlayerAttributes.MAG_IMUN,    0f,   new LvlEAProvider<Float>(2, 20,   PlayerAttributes.ADD_1,     PlayerAttributes.MUL_1));

        set.addEntityAttribute(PlayerAttributes.CURR_MANA, 0f);
        set.addEntityAttribute(PlayerAttributes.SPEED_COUNTER, 0f);

        MinecraftForge.EVENT_BUS.post(new RegEAEvent.PlayerEAEvent(entityClass, set));
}

    public static class EntityAttributesSet
    {
        public Map<EntityAttribute, EntityAttrParams> attributes = new LinkedHashMap<EntityAttribute, EntityAttrParams>();
        public List<LvlEAProvider> lvlProviders = new LinkedList<LvlEAProvider>();
        public IRPGEntity rpgComponent;
        public boolean isSupported;

        public EntityAttributesSet(IRPGEntity rpgComponent, boolean isSupported)
        {
            this.rpgComponent = rpgComponent;
            this.isSupported = isSupported;
        }

        public <T> void addLvlableEntityAttribute(EntityAttribute<T> attr, T startvalue, LvlEAProvider<T> lvlProvider)
        {
            lvlProvider.attr = attr;
            attributes.put(attr, new EntityAttrParams(startvalue, lvlProvider));
            lvlProviders.add(lvlProvider);
        }

        public <T> void addEntityAttribute(EntityAttribute<T> attr, T startvalue)
        {
            attributes.put(attr, new EntityAttrParams(startvalue, null));
        }

        public static class EntityAttrParams<Type>
        {
            public Type startValue;
            public LvlEAProvider<Type> lvlProvider;

            public EntityAttrParams(Type startValue, LvlEAProvider<Type> lvlProvider)
            {
                this.startValue = startValue;
                this.lvlProvider = lvlProvider;
            }
        }
    }
}
