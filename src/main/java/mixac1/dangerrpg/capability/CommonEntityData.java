package mixac1.dangerrpg.capability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.EntityAttributeE;
import mixac1.dangerrpg.capability.entityattr.EntityAttributes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class CommonEntityData implements IExtendedEntityProperties
{
    protected static final String ID = "RPGCommonEntityData";
    
    public final EntityLivingBase entity;
    
    public HashMap<Integer, EAEValues> attributeMapE = new HashMap<Integer, EAEValues>();
    public HashMap<Integer, EAValues>  attributeMap  = new HashMap<Integer, EAValues>();
    
    public static ArrayList<EntityAttributeE> entityAttributes = new ArrayList<EntityAttributeE>();    
    public static ArrayList<EntityAttribute>  workAttributes   = new ArrayList<EntityAttribute>();
    
    static
    {
    	workAttributes.add(EntityAttributes.LVL);
    }
    
    public CommonEntityData(EntityLivingBase entity)
    {
        this.entity = entity;
    }
    
    @Override
    public void init(Entity entity, World world)
    {
        for (EntityAttribute iter : getEntityAttributes()) {
            iter.init((EntityLivingBase) entity);
        }
        for (EntityAttribute iter : getWorkAttributes()) {
            iter.init((EntityLivingBase) entity);
        }
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
    
    public void sync(IMessage msg)
    {
    	/*if (isServerSide(entity)) {
            RPGNetwork.net.sendToAll(msg);
        }*/
    }
    
    public void request(IMessage msg)
    {

    }
    
    public void syncAll()
    {
        /*if (isServerSide(entity)) {
            RPGNetwork.net.sendToAll(new MsgSyncPlayerData(this));
        }*/
    }

    public void requestAll()
    {

    }
    
    @Override
    public void saveNBTData(NBTTagCompound nbt)
    {
        for (EntityAttributeE iter : getEntityAttributes()) {
            NBTTagCompound tmp = new NBTTagCompound();
            tmp.setInteger("lvl", iter.getLvl(entity));
            tmp.setFloat("value", iter.getValue(entity));
            nbt.setTag(iter.name, tmp);
        }
        for (EntityAttribute iter : getWorkAttributes()) {
            nbt.setFloat(iter.name, iter.getValue(entity));
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt)
    {
        for (EntityAttributeE iter : getEntityAttributes()) {
            if (nbt.hasKey(iter.name)) {
                NBTTagCompound tmp = (NBTTagCompound) nbt.getTag(iter.name);
                iter.setLvl(tmp.getInteger("lvl"), entity);
                iter.setValue(tmp.getFloat("value"), entity, false);
            }
        }
        for (EntityAttribute iter : getWorkAttributes()) {
            if (nbt.hasKey(iter.name)) {
                iter.setValue(nbt.getFloat(iter.name), entity, false);
            }
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
        return (EntityAttribute) getObject(hash, getWorkAttributes());
    }
    
    public EntityAttributeE getPlayerAttributeE(int hash)
    {
        return (EntityAttributeE) getObject(hash, getEntityAttributes());
    }
    
    public ArrayList<EntityAttribute> getWorkAttributes()
    {
    	return workAttributes;
    }
    
    public ArrayList<EntityAttributeE> getEntityAttributes()
    {
    	return entityAttributes;
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
