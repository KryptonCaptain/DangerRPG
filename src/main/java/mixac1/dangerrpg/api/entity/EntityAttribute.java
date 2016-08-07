package mixac1.dangerrpg.api.entity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.CommonEntityData;
import mixac1.dangerrpg.capability.CommonEntityData.EAValues;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgSyncEA;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

public class EntityAttribute
{
    public final String name;
    public final int    hash;

    protected float startValue = 0F;

    public EntityAttribute(String name)
    {
        this.name = name;
        hash = name.hashCode();
    }

    public EntityAttribute(String name, float startValue)
    {
        this(name);
        this.startValue = startValue;
    }

    public boolean isValid(float value)
    {
        return value >= 0F;
    }

    public boolean isValid(EntityLivingBase entity, float value)
    {
        return isValid(value);
    }

    public CommonEntityData getEntityData(EntityLivingBase entity)
    {
    	return CommonEntityData.get(entity);
    }

    public float getValue(EntityLivingBase entity)
    {
        float value = getEntityData(entity).attributeMap.get(hash).value;
        if (!isValid(entity, value)) {
            init(entity);
            value = getEntityData(entity).attributeMap.get(hash).value;
        }
        return value;
    }

    public void setValue(float value, EntityLivingBase entity, boolean needSync)
    {
        if (isValid(entity, value)) {
        	getEntityData(entity).attributeMap.get(hash).value = value;
            apply(entity);
            if (needSync) {
                sync(entity);
            }
        }
    }

    public void setValue(float value, EntityLivingBase entity)
    {
        setValue(value, entity, true);
    }

    public void addValue(float value, EntityLivingBase entity, boolean needSync)
    {
        setValue(getValue(entity) + value, entity, needSync);
    }

    public void addValue(float value, EntityLivingBase entity)
    {
        addValue(value, entity, true);
    }

    public void init(float value, EntityLivingBase entity)
    {
    	getEntityData(entity).attributeMap.put(hash, new EAValues(value));
    }

    public void init(EntityLivingBase entity)
    {
        init(startValue, entity);
    }

    public void sync(EntityLivingBase entity)
    {
    	if (CommonEntityData.isServerSide(entity)) {
    	    RPGNetwork.net.sendToAll(new MsgSyncEA(hash, getValue(entity), entity.getEntityId()));
    	}
    }

    public void handle(EntityLivingBase entity, IMessage msg)
    {
        if (!CommonEntityData.isServerSide(entity)) {
            setValue(((MsgSyncEA) msg).value, entity, false);
        }
    }

    public void apply(EntityLivingBase entity)
    {

    }

    public float displayValue(EntityLivingBase entity)
    {
        return getValue(entity);
    }

    public String getDispayName()
    {
        return DangerRPG.trans("pl_attr.".concat(name));
    }

    @Override
    public int hashCode()
    {
        return hash;
    }

    public void saveToNBT(NBTTagCompound nbt, EntityLivingBase entity)
    {
        nbt.setFloat(name, getValue(entity));
    }

    public void loadFromNBT(NBTTagCompound nbt, EntityLivingBase entity)
    {
        if (nbt.hasKey(name)) {
            setValue(nbt.getFloat(name), entity, false);
        }
    }
}
