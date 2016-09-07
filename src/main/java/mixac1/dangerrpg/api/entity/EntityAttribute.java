package mixac1.dangerrpg.api.entity;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.EntityData;
import mixac1.dangerrpg.capability.EntityData.TypeStub;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgSyncEA;
import mixac1.dangerrpg.util.ITypeProvider;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Default entity attribute. It supports any Type, but you must create {@link ITypeProvider} for this Type. <br>
 * {@link LvlEAProvider} allows make this attribute lvlable.
 */
public class EntityAttribute<Type>
{
    public final String name;
    public final int    hash;
    public final ITypeProvider<? super Type> typeProvider;
    public final LvlEAProvider<Type> lvlProvider;

    protected Type startValue;

    public EntityAttribute(ITypeProvider<? super Type> typeProvider, String name, Type startValue, LvlEAProvider<Type> lvlProvider)
    {
        this.name = name;
        hash = name.hashCode();
        this.typeProvider = typeProvider;
        this.startValue = startValue;
        this.lvlProvider = lvlProvider;
        if (lvlProvider != null) {
            lvlProvider.attr = this;
        }
    }

    public void init(EntityLivingBase entity)
    {
        getEntityData(entity).attributeMap.put(hash, new TypeStub<Type>((Type) typeProvider.getEmpty()));
        if (lvlProvider != null) {
            lvlProvider.init(entity);
        }
    }

    public void serverInit(EntityLivingBase entity)
    {
        setValueRaw(startValue, entity);
    }

    public boolean hasIt(EntityLivingBase entity)
    {
        return RPGCapability.rpgEntityRegistr.isRegistered(entity)
                && RPGCapability.rpgEntityRegistr.getAttributesSet(entity).attributes.contains(this);
    }

    public boolean isValid(Type value)
    {
        return typeProvider.isValid(value);
    }

    public boolean isValid(Type value, EntityLivingBase entity)
    {
        return isValid(value);
    }

    public EntityData getEntityData(EntityLivingBase entity)
    {
        return EntityData.get(entity);
    }

    /**
     * Get value without check<br>
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before use this method
     */
    @Deprecated
    public Type getValueRaw(EntityLivingBase entity)
    {
        return (Type) getEntityData(entity).attributeMap.get(hash).value;
    }

    /**
     * Set value without check<br>
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before use this method
     */
    @Deprecated
    public boolean setValueRaw(Type value, EntityLivingBase entity)
    {
        if (!value.equals(getValueRaw(entity))) {
            getEntityData(entity).attributeMap.get(hash).value = value;
            apply(entity, value);
            return true;
        }
        return false;
    }

    /**
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before use this method
     */
    public Type getValue(EntityLivingBase entity)
    {
        Type value = getValueRaw(entity);
        if (!isValid(value, entity)) {
            serverInit(entity);
            value = getValueRaw(entity);
        }
        return value;
    }

    /**
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before use this method
     */
    public void setValue(Type value, EntityLivingBase entity)
    {
        if (isValid(value, entity)) {
            if (setValueRaw(value, entity)) {
                sync(entity);
            }
        }
    }

    /**
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before use this method
     */
    public void addValue(Type value, EntityLivingBase entity)
    {
        setValue((Type) typeProvider.concat(getValue(entity), value), entity);
    }

    public void sync(EntityLivingBase entity)
    {
        if (EntityData.isServerSide(entity)) {
            RPGNetwork.net.sendToAll(new MsgSyncEA(this, entity));
        }
    }

    /**
     * Used when was set value
     */
    public void apply(EntityLivingBase entity, Type addedValue)
    {

    }

    public void toNBT(NBTTagCompound nbt, EntityLivingBase entity)
    {
        NBTTagCompound tmp = new NBTTagCompound();
        typeProvider.toNBT(getValue(entity), "value", tmp);
        if (lvlProvider != null) {
            tmp.setInteger("lvl", lvlProvider.getLvl(entity));
        }
        nbt.setTag(name, tmp);
    }

    public void fromNBT(NBTTagCompound nbt, EntityLivingBase entity)
    {
        NBTTagCompound tmp = (NBTTagCompound) nbt.getTag(name);
        setValueRaw((Type) typeProvider.fromNBT("value", tmp), entity);
        if (lvlProvider != null) {
            lvlProvider.setLvl(tmp.getInteger("lvl"), entity);
        }
    }

    public String getDisplayValue(EntityLivingBase entity)
    {
        return typeProvider.toString(getValue(entity));
    }

    public String getDisplayName()
    {
        return DangerRPG.trans("ea.".concat(name));
    }

    public String getInfo()
    {
        return DangerRPG.trans(Utils.toString("ea.", name, ".info"));
    }

    @Override
    public final int hashCode()
    {
        return hash;
    }

    /********************************************************************/
    //T0D0: implements of EntityAttribute for default types

    public static class EABoolean extends EntityAttribute<Boolean>
    {
        public EABoolean(String name, boolean startValue, LvlEAProvider<Boolean> lvlProvider)
        {
            super(ITypeProvider.BOOLEAN, name, startValue, lvlProvider);
        }
    }

    public static class EAInteger extends EntityAttribute<Integer>
    {
        public EAInteger(String name, int startValue, LvlEAProvider<Integer> lvlProvider)
        {
            super(ITypeProvider.INTEGER, name, startValue, lvlProvider);
        }
    }

    public static class EAFloat extends EntityAttribute<Float>
    {
        public EAFloat(String name, float startValue, LvlEAProvider<Float> lvlProvider)
        {
            super(ITypeProvider.FLOAT, name, startValue, lvlProvider);
        }
    }

    public static class EAString extends EntityAttribute<String>
    {
        public EAString(String name, String startValue, LvlEAProvider<String> lvlProvider)
        {
            super(ITypeProvider.STRING, name, startValue, lvlProvider);
        }
    }

    public static class EANBT extends EntityAttribute<NBTTagCompound>
    {
        public EANBT(String name, NBTTagCompound startValue, LvlEAProvider<NBTTagCompound> lvlProvider)
        {
            super(ITypeProvider.NBT_TAG, name, startValue, lvlProvider);
        }
    }

    public static class EAItemStack extends EntityAttribute<ItemStack>
    {
        public EAItemStack(String name, ItemStack startValue, LvlEAProvider<ItemStack> lvlProvider)
        {
            super(ITypeProvider.ITEM_STACK, name, startValue, lvlProvider);
        }
    }
}
