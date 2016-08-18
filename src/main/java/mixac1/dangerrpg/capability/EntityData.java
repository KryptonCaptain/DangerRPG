package mixac1.dangerrpg.capability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.api.event.InitRPGEntityEvent;
import mixac1.dangerrpg.api.event.RegEAEvent;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import mixac1.dangerrpg.capability.ea.PlayerAttributes;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGConfig;
import mixac1.dangerrpg.init.RPGNetwork;
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

public class EntityData implements IExtendedEntityProperties
{
    protected static final String ID = "RPGCommonEntityData";

    public final EntityLivingBase entity;

    public HashMap<Integer, TypeStub> attributeMap = new HashMap<Integer, TypeStub>();
    public HashMap<Integer, Integer>  lvlMap       = new HashMap<Integer, Integer>();

    public EntityData(EntityLivingBase entity)
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
        entity.registerExtendedProperties(ID, new EntityData(entity));
    }

    public static EntityData get(EntityLivingBase entity)
    {
        return (EntityData) entity.getExtendedProperties(ID);
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
            int rand = DangerRPG.rand.nextInt(pas.size());
            pas.get(rand).up(entity, false);
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

    public ArrayList<EntityAttribute> getEntityAttributes()
    {
        return RPGCapability.getEntityAttributesSet(entity).attributes;
    }

    public ArrayList<LvlEAProvider> getLvlProviders()
    {
        return RPGCapability.getEntityAttributesSet(entity).lvlProviders;
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

    public static boolean hasIt(EntityLivingBase entity)
    {
        return RPGCapability.getEntityAttributesSet(entity) != null;
    }

    public static boolean registerEntity(Class entityClass)
    {
        if (EntityLivingBase.class.isAssignableFrom(entityClass) &&
           (RPGConfig.entityAllEntityRPG || RPGConfig.entitySupportedRPGEntities.contains(entityClass.getName()))) {
            EntityAttributesSet set = new EntityAttributesSet();
            registerEntityDefault(entityClass, set);
            if (EntityPlayer.class.isAssignableFrom(entityClass)) {
                registerEntityPlayer(entityClass, set);
            }
            else {
                registerEntityLiving(entityClass, set);
                if (EntityMob.class.isAssignableFrom(entityClass)) {
                    registerEntityMob(entityClass, set);
                }
            }
            RPGCapability.eaMap.put(entityClass, set);

            DangerRPG.infoLog("Register RPG entity: ".concat(entityClass.getName()));

            return true;
        }
        return false;
    }

    private static void registerEntityDefault(Class<? extends EntityLivingBase> entityClass, EntityAttributesSet set)
    {
        set.addEntityAttribute(EntityAttributes.LVL);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.DefaultEAEvent(entityClass, set));
    }

    private static void registerEntityLiving(Class<? extends EntityLiving> entityClass, EntityAttributesSet set)
    {
        set.addEntityAttribute(EntityAttributes.HEALTH);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.EntytyLivingEAEvent(entityClass, set));
    }

    private static void registerEntityMob(Class<? extends EntityMob> entityClass, EntityAttributesSet set)
    {
        set.addEntityAttribute(EntityAttributes.DAMAGE);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.EntytyMobEAEvent(entityClass, set));
    }

    private static void registerEntityPlayer(Class<? extends EntityPlayer> entityClass, EntityAttributesSet set)
    {
        set.addLvlableEntityAttribute(PlayerAttributes.HEALTH);
        set.addLvlableEntityAttribute(PlayerAttributes.MANA);
        set.addLvlableEntityAttribute(PlayerAttributes.STRENGTH);
        set.addLvlableEntityAttribute(PlayerAttributes.AGILITY);
        set.addLvlableEntityAttribute(PlayerAttributes.INTELLIGENCE);
        set.addLvlableEntityAttribute(PlayerAttributes.EFFICIENCY);
        set.addLvlableEntityAttribute(PlayerAttributes.MANA_REGEN);
        set.addLvlableEntityAttribute(PlayerAttributes.HEALTH_REGEN);

        set.addLvlableEntityAttribute(PlayerAttributes.MOVE_SPEED);
        set.addLvlableEntityAttribute(PlayerAttributes.SNEAK_SPEED);
        set.addLvlableEntityAttribute(PlayerAttributes.FLY_SPEED);
        set.addLvlableEntityAttribute(PlayerAttributes.SWIM_SPEED);
        set.addLvlableEntityAttribute(PlayerAttributes.JUMP_HEIGHT);
        set.addLvlableEntityAttribute(PlayerAttributes.JUMP_RANGE);

        set.addLvlableEntityAttribute(PlayerAttributes.STEEL_MUSC);
        set.addLvlableEntityAttribute(PlayerAttributes.STONESKIN);
        set.addLvlableEntityAttribute(PlayerAttributes.MAG_IMUN);

        set.addEntityAttribute(PlayerAttributes.CURR_MANA);
        set.addEntityAttribute(PlayerAttributes.SPEED_COUNTER);

        MinecraftForge.EVENT_BUS.post(new RegEAEvent.PlayerEAEvent(entityClass, set));
}

    public static class EntityAttributesSet
    {
        public ArrayList<EntityAttribute> attributes = new ArrayList<EntityAttribute>();
        public ArrayList<LvlEAProvider> lvlProviders = new ArrayList<LvlEAProvider>();

        public void addLvlableEntityAttribute(EntityAttribute attr)
        {
            addEntityAttribute(attr);
            lvlProviders.add(attr.lvlProvider);
        }

        public void addEntityAttribute(EntityAttribute attr)
        {
            attributes.add(attr);
        }
    }
}
