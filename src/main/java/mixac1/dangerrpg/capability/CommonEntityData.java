package mixac1.dangerrpg.capability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgSyncEntityData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class CommonEntityData implements IExtendedEntityProperties
{
    protected static final String ID = "RPGCommonEntityData";

    public final EntityLivingBase entity;

    public HashMap<Integer, TypeStub> attributeMap = new HashMap<Integer, TypeStub>();
    public HashMap<Integer, Integer>  lvlMap       = new HashMap<Integer, Integer>();

    public static ArrayList<LvlEAProvider>   lvlProviders     = new ArrayList<LvlEAProvider>();
    public static ArrayList<EntityAttribute> entityAttributes = new ArrayList<EntityAttribute>();

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

        if (isServerSide((EntityLivingBase) entity)) {
            EntityAttributes.IS_INIT.setValueRaw(true, (EntityLivingBase) entity);
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

    public boolean checkValid()
    {
        boolean result = EntityAttributes.IS_INIT.getValue(entity);
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

    @Override
    public void saveNBTData(NBTTagCompound nbt)
    {
        for (EntityAttribute iter : getEntityAttributes()) {
            iter.toNBT(nbt, entity);
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt)
    {
        for (EntityAttribute iter : getEntityAttributes()) {
            iter.fromNBT(nbt, entity);
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

    public EntityAttribute getEntityAttribute(int hash)
    {
        return (EntityAttribute) getObject(hash, getEntityAttributes());
    }

    public ArrayList<EntityAttribute> getEntityAttributes()
    {
        return entityAttributes;
    }

    public ArrayList<LvlEAProvider> getLvlProviders()
    {
        return lvlProviders;
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
