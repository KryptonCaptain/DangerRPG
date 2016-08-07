package mixac1.dangerrpg.capability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.EntityAttributeE;
import mixac1.dangerrpg.capability.entityattr.EntityAttributes;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgSyncEntityData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class CommonEntityData implements IExtendedEntityProperties
{
    protected static final String ID = "RPGCommonEntityData";

    public final EntityLivingBase entity;

    public HashMap<Integer, EAEValues> attributeMapE = new HashMap<Integer, EAEValues>();
    public HashMap<Integer, EAValues>  attributeMap  = new HashMap<Integer, EAValues>();

    public static ArrayList<EntityAttributeE> lvlableAttributes = new ArrayList<EntityAttributeE>();
    public static ArrayList<EntityAttribute>  staticAttributes  = new ArrayList<EntityAttribute>();

    static
    {
        staticAttributes.add(EntityAttributes.IS_INIT);
        staticAttributes.add(EntityAttributes.LVL);
    }

    public CommonEntityData(EntityLivingBase entity)
    {
        this.entity = entity;
    }

    @Override
    public void init(Entity entity, World world)
    {
        for (EntityAttribute iter : getLvlableAttributes()) {
            iter.init((EntityLivingBase) entity);
        }
        for (EntityAttribute iter : getStaticAttributes()) {
            iter.init((EntityLivingBase) entity);
        }
    }

    public void serverInit()
    {
        EntityAttributes.IS_INIT.setValue(1f, entity, false);
    }

    public static void register(EntityLivingBase entity)
    {
    	entity.registerExtendedProperties(ID, new CommonEntityData(entity));
    }

    public static CommonEntityData get(EntityLivingBase entity)
    {
        return (CommonEntityData) entity.getExtendedProperties(ID);
    }

    public static boolean isServerSide(EntityLivingBase entity)
    {
        return !entity.worldObj.isRemote;
    }

    public boolean checkValid()
    {
        boolean result = EntityAttributes.IS_INIT.getValue(entity) != 0f;
        if (!result) {
            if (isServerSide(entity)) {
                init(entity, entity.worldObj);
                RPGNetwork.net.sendToAll(new MsgSyncEntityData(this, entity.getEntityId()));
            }
            else {
                request(entity);
            }
        }
        return result;
    }

    public void sync(EntityPlayerMP player, int entityId)
    {
        if (isServerSide(entity)) {
            if (entityId == -1) {
                RPGNetwork.net.sendTo(new MsgSyncEntityData(get(entity)), player);
            }
            else {
                EntityLivingBase target = (EntityLivingBase) entity.worldObj.getEntityByID(entityId);
                if (target != null) {
                    RPGNetwork.net.sendTo(new MsgSyncEntityData(get(target), entityId), player);
                }
            }
        }
    }

    public void request(EntityLivingBase entityTarget)
    {
        if (!isServerSide(entity)) {
            if (entityTarget == DangerRPG.proxy.getClientPlayer()) {
                RPGNetwork.net.sendToServer(new MsgSyncEntityData());
            }
            else {
                RPGNetwork.net.sendToServer(new MsgSyncEntityData(entityTarget.getEntityId()));
            }
        }
    }

    public void handle(MsgSyncEntityData msg, MessageContext ctx)
    {
        EntityLivingBase entity;
        if (msg.entityId == -1) {
            entity = DangerRPG.proxy.getPlayer(ctx);
        }
        else {
            entity = (EntityLivingBase) DangerRPG.proxy.getEntityByID(ctx, msg.entityId);
        }

        if (entity != null) {
            get(entity).loadNBTData(msg.data);
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt)
    {
        for (EntityAttributeE iter : getLvlableAttributes()) {
            iter.saveToNBT(nbt, entity);
        }
        for (EntityAttribute iter : getStaticAttributes()) {
            iter.saveToNBT(nbt, entity);
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt)
    {
        for (EntityAttributeE iter : getLvlableAttributes()) {
            iter.loadFromNBT(nbt, entity);
        }
        for (EntityAttribute iter : getStaticAttributes()) {
            iter.loadFromNBT(nbt, entity);
        }
    }

    private Object getObject(int hash, List list)
    {
        for (Object it : list) {
            if (it.hashCode() == hash) {
                return it;
            }
        }
        return null;
    }

    public EntityAttribute getPlayerAttribute(int hash)
    {
        return (EntityAttribute) getObject(hash, getStaticAttributes());
    }

    public EntityAttributeE getPlayerAttributeE(int hash)
    {
        return (EntityAttributeE) getObject(hash, getLvlableAttributes());
    }

    public ArrayList<EntityAttribute> getStaticAttributes()
    {
    	return staticAttributes;
    }

    public ArrayList<EntityAttributeE> getLvlableAttributes()
    {
    	return lvlableAttributes;
    }

    public static class EAValues
    {
        public float value;

        public EAValues(float value)
        {
            this.value = value;
        }
    }

    public static class EAEValues extends EAValues
    {
        public int lvl;

        public EAEValues(int lvl, float value)
        {
            super(value);
            this.lvl = lvl;
        }
    }
}
