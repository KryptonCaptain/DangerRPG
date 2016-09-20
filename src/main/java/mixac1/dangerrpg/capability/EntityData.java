package mixac1.dangerrpg.capability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import cpw.mods.fml.relauncher.Side;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.api.event.InitRPGEntityEvent;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGConfig;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.network.MsgSyncEntityData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
            else if (DangerRPG.proxy.getTick(Side.CLIENT) % 100 == 0) {
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

        if (count > RPGConfig.MainConfig.playerLoseLvlCount) {
            count = RPGConfig.MainConfig.playerLoseLvlCount;
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

    public static class TypeStub<Type>
    {
        public Type value;

        public TypeStub(Type value)
        {
            this.value = value;
        }
    }
}
