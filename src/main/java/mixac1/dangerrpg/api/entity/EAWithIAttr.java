package mixac1.dangerrpg.api.entity;

import java.util.UUID;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.nbt.NBTTagCompound;

public class EAWithIAttr extends EAFloat
{
    public IAttribute attribute;

    public EAWithIAttr(String name)
    {
        this(name, new RPGAttribute(name));
    }

    public EAWithIAttr(String name, IAttribute attribute)
    {
        super(name);
        this.attribute = attribute;
    }

    @Override
    public void init(EntityLivingBase entity)
    {
        entity.getAttributeMap().registerAttribute(attribute);
        LvlEAProvider lvlProvider = getLvlProvider(entity);
        if (lvlProvider != null) {
            lvlProvider.init(entity);
        }
    }

    @Override
    @Deprecated
    public Float getValueRaw(EntityLivingBase entity)
    {
        return (float) entity.getEntityAttribute(attribute).getAttributeValue();
    }

    @Override
    @Deprecated
    public boolean setValueRaw(Float value, EntityLivingBase entity)
    {
        if (!value.equals(getValueRaw(entity)) && !entity.worldObj.isRemote) {
            entity.getEntityAttribute(attribute).setBaseValue(value);
            return true;
        }
        return false;
    }

    public Float getBaseValue(EntityLivingBase entity)
    {
        return (float) entity.getEntityAttribute(attribute).getBaseValue();
    }

    public Float getModifierValue(EntityLivingBase entity)
    {
        return getValue(entity) - getBaseValue(entity);
    }

    public Float getModifierValue(EntityLivingBase entity, UUID ID)
    {
        IAttributeInstance attr = entity.getEntityAttribute(attribute);
        AttributeModifier mod = attr.getModifier(ID);
        return mod == null ? 0 : (float) mod.getAmount();
    }

    public void setModificatorValue(Float value, EntityLivingBase entity, UUID ID)
    {
        if (!entity.worldObj.isRemote) {
            IAttributeInstance attr = entity.getEntityAttribute(attribute);
            AttributeModifier mod = attr.getModifier(ID);
            if (mod != null) {
                attr.removeModifier(mod);
            }
            AttributeModifier newMod = new AttributeModifier(ID, name.concat(ID.toString()), value, 0).setSaved(true);
            attr.applyModifier(newMod);
        }
    }

    public void removeModificator(EntityLivingBase entity, UUID ID)
    {
        if (!entity.worldObj.isRemote) {
            IAttributeInstance attr = entity.getEntityAttribute(attribute);
            AttributeModifier mod = attr.getModifier(ID);
            if (mod != null) {
                attr.removeModifier(mod);
            }
        }
    }

    @Override
    public String getDisplayValue(EntityLivingBase entity)
    {
        float mod = getModifierValue(entity);
        if (mod > 0f) {
            return String.format("%.2f + %.2f", getBaseValue(entity), mod);
        }
        else if (mod < 0f) {
            return String.format("%.2f - %.2f", getBaseValue(entity), mod);
        }
        return String.format("%.2f", getBaseValue(entity));
    }

    @Override
    public void toNBTforMsg(NBTTagCompound nbt, EntityLivingBase entity)
    {
        NBTTagCompound tmp = new NBTTagCompound();
        LvlEAProvider lvlProvider = getLvlProvider(entity);
        if (lvlProvider != null) {
            tmp.setInteger("lvl", lvlProvider.getLvl(entity));
        }
        nbt.setTag(name, tmp);
    }

    @Override
    public void fromNBTforMsg(NBTTagCompound nbt, EntityLivingBase entity)
    {
        NBTTagCompound tmp = (NBTTagCompound) nbt.getTag(name);
        if (tmp != null) {
            LvlEAProvider lvlProvider = getLvlProvider(entity);
            if (lvlProvider != null) {
                lvlProvider.setLvl(tmp.getInteger("lvl"), entity);
            }
        }
        else {
            serverInit(entity);
        }
    }

    /********************************************************************/

    public static class RPGAttribute extends RangedAttribute
    {
        public RPGAttribute(String name)
        {
            super(name, 0.0D, -Double.MAX_VALUE, Double.MAX_VALUE);
        }

        @Override
        public boolean getShouldWatch()
        {
            return true;
        }
    }

    public static class EAMotion extends EAWithIAttr
    {
        public EAMotion(String name)
        {
            super(name);
        }

        @Override
        public String getDisplayValue(EntityLivingBase entity)
        {
            return null;
        }
    }

    public static class EAPercent extends EAWithIAttr
    {
        public EAPercent(String name)
        {
            super(name);
        }

        @Override
        public String getDisplayValue(EntityLivingBase entity)
        {
            return String.format("%d%c", Math.round(getValue(entity) * 100), '%');
        }
    }
}
