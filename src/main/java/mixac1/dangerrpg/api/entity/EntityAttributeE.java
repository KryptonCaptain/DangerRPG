package mixac1.dangerrpg.api.entity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.CommonEntityData;
import mixac1.dangerrpg.capability.CommonEntityData.EAEValues;
import mixac1.dangerrpg.capability.entityattr.EntityAttributes;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgReqUpPA;
import mixac1.dangerrpg.network.MsgSyncEAE;
import mixac1.dangerrpg.util.Multiplier;
import mixac1.dangerrpg.util.Multiplier.MultiplierE;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class EntityAttributeE extends EntityAttribute
{
    protected MultiplierE mulValue;
    protected float maxLvl;
    protected float startExpCost;
    protected Multiplier mulExpCost;

    public EntityAttributeE(String name, float startValue, float startExpCost, float maxLvl, MultiplierE mulValue, Multiplier mulExpCost)
    {
        super(name);
        this.startValue = startValue;
        this.mulValue = mulValue;
        this.maxLvl = maxLvl;
        this.startExpCost = startExpCost;
        this.mulExpCost = mulExpCost;
    }

    public EAEValues getValues(EntityLivingBase entity)
    {
        return getEntityData(entity).attributeMapE.get(hash);
    }

    public int getLvl(EntityLivingBase entity)
    {
        return getValues(entity).lvl;
    }

    @Deprecated
    public void setLvl(int lvl, EntityLivingBase entity)
    {
    	getValues(entity).lvl = lvl;
    }

    @Override
    public float getValue(EntityLivingBase entity)
    {
        float value = getValues(entity).value;
        if (!isValid(entity, value)) {
            init(entity);
            value = getValues(entity).value;
        }
        return value;
    }

    @Deprecated
    @Override
    public void setValue(float value, EntityLivingBase entity, boolean needSync)
    {
        if (isValid(entity, value)) {
        	getValues(entity).value = value;
            apply(entity);
            if (needSync) {
                sync(entity);
            }
        }
    }

    @Override
    public void init(float value, EntityLivingBase entity)
    {
        getEntityData(entity).attributeMapE.put(hash, new EAEValues(1, value));
    }

    public int getExpUp(EntityLivingBase entity)
    {
        return (int) (startExpCost + mulExpCost.up(getLvl(entity)));
    }

    public boolean canUp(EntityLivingBase entity)
    {
    	if (entity instanceof EntityPlayer) {
    		return ((EntityPlayer) entity).capabilities.isCreativeMode ||
    			   ((EntityPlayer) entity).experienceLevel >= getExpUp(entity);
    	}
    	return true;
    }

    /**
     * @param flag - if true, then lvl up, else down
     */
    @Deprecated
    public boolean up(EntityLivingBase entity, boolean flag)
    {
    	if (CommonEntityData.isServerSide(entity)) {
	        EAEValues tmp = getValues(entity);
	        if (flag) {
	            if (tmp.lvl < maxLvl) {
	                tmp.lvl += 1;
	                EntityAttributes.LVL.addValue(1, entity);
	                setValue(mulValue.up(tmp.value), entity);
	                return true;
	            }
	        }
	        else if (tmp.lvl > 1) {
	        	tmp.lvl -= 1;
	        	EntityAttributes.LVL.addValue(-1, entity);
	            setValue(mulValue.down(tmp.value), entity);
	            return true;
	        }
	        return false;
    	}
    	else {
    		RPGNetwork.net.sendToServer(new MsgReqUpPA(hash));
    		return true;
    	}
    }

    public boolean tryUp(EntityLivingBase entity)
    {
        if (entity instanceof EntityPlayer) {
        	if (getLvl(entity) < maxLvl) {
        		EntityPlayer player = (EntityPlayer) entity;
                if (player.capabilities.isCreativeMode) {
                    return up(player, true);
                }
                else {
                    int exp = getExpUp(player);
                    if (exp >= getExpUp(player)) {
                        if (CommonEntityData.isServerSide(entity)) {
                        	player.addExperienceLevel(-exp);
                        }
                        return up(player, true);
                    }
                }
        	}
        	return false;
        }
        return true;
    }

    @Override
    public void sync(EntityLivingBase entity)
    {
    	if (CommonEntityData.isServerSide(entity)) {
    	    RPGNetwork.net.sendToAll(new MsgSyncEAE(hash, getLvl(entity), getValue(entity), entity.getEntityId()));
    	}
    }

    @Override
    public void handle(EntityLivingBase entity, IMessage msg)
    {
        if (!CommonEntityData.isServerSide(entity)) {
            setLvl(((MsgSyncEAE) msg).level, entity);
            setValue(((MsgSyncEAE) msg).value, entity, false);
        }
    }

    public String getInfo(EntityLivingBase entity)
    {
        return DangerRPG.trans("pl_attr.".concat(name).concat(".info"));
    }

    @Override
    public void saveToNBT(NBTTagCompound nbt, EntityLivingBase entity)
    {
        NBTTagCompound tmp = new NBTTagCompound();
        tmp.setInteger("lvl", getLvl(entity));
        tmp.setFloat("value", getValue(entity));
        nbt.setTag(name, tmp);
    }

    @Override
    public void loadFromNBT(NBTTagCompound nbt, EntityLivingBase entity)
    {
        if (nbt.hasKey(name)) {
            NBTTagCompound tmp = (NBTTagCompound) nbt.getTag(name);
            setLvl(tmp.getInteger("lvl"), entity);
            setValue(tmp.getFloat("value"), entity, false);
        }
    }
}
