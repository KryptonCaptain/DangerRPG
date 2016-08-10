package mixac1.dangerrpg.api.entity;

import java.util.ArrayList;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.CommonEntityData;
import mixac1.dangerrpg.capability.CommonEntityData.TypeStub;
import mixac1.dangerrpg.capability.LvlEAProvider;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgSyncEA;
import mixac1.dangerrpg.util.ITypeProvider;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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

    public boolean isValid(Type value)
    {
        return typeProvider.isValid(value);
    }

    public boolean isValid(Type value, EntityLivingBase entity)
    {
        return isValid(value);
    }

    public CommonEntityData getEntityData(EntityLivingBase entity)
    {
        return CommonEntityData.get(entity);
    }

    @Deprecated
    public Type getValueRaw(EntityLivingBase entity)
    {
        CommonEntityData data = getEntityData(entity);
        ArrayList<EntityAttribute> list = data.getEntityAttributes();
        return (Type) data.attributeMap.get(hash).value;
    }

    @Deprecated
    public void setValueRaw(Type value, EntityLivingBase entity)
    {
        getEntityData(entity).attributeMap.get(hash).value = value;
        apply(entity);
    }

    public Type getValue(EntityLivingBase entity)
    {
        Type value = getValueRaw(entity);
        if (!isValid(value, entity)) {
            init(entity);
            value = getValueRaw(entity);
        }
        return value;
    }

    public void setValue(Type value, EntityLivingBase entity)
    {
        if (isValid(value, entity)) {
            setValueRaw(value, entity);
            sync(entity);
        }
    }

    public void addValue(Type value, EntityLivingBase entity)
    {
        setValue((Type) typeProvider.concat(getValue(entity), value), entity);
    }

    public void init(Type value, EntityLivingBase entity)
    {
        getEntityData(entity).attributeMap.put(hash, new TypeStub(value));
        if (lvlProvider != null) {
            lvlProvider.init(entity);
        }
    }

    public void init(EntityLivingBase entity)
    {
        init(startValue, entity);
    }

    public void sync(EntityLivingBase entity)
    {
        if (CommonEntityData.isServerSide(entity)) {
            RPGNetwork.net.sendToAll(new MsgSyncEA(this, entity));
        }
    }

    public void apply(EntityLivingBase entity)
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

    public Type displayValue(EntityLivingBase entity)
    {
        return (Type) typeProvider.toString(getValue(entity));
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
    public int hashCode()
    {
        return hash;
    }

    /********************************************************************/

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
