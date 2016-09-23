package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EAWithIAttr;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

public class EAMana extends EAWithIAttr
{
    public EAMana(String name)
    {
        super(name);
    }

    @Override
    public void setValue(Float value, EntityLivingBase entity)
    {
        if (isValid(value, entity)) {
            float max = getValue(entity);
            setValueRaw(value, entity);
            sync(entity);
            PlayerAttributes.CURR_MANA.setValue(value * PlayerAttributes.CURR_MANA.getValue(entity) / max, entity);
        }
    }

    @Override
    public void toNBTforMsg(NBTTagCompound nbt, EntityLivingBase entity)
    {
        toNBT(nbt, entity);
    }

    @Override
    public void fromNBTforMsg(NBTTagCompound nbt, EntityLivingBase entity)
    {
        fromNBT(nbt, entity);
    }
}
