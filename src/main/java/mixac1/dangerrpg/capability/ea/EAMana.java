package mixac1.dangerrpg.capability.ea;

import java.util.UUID;

import mixac1.dangerrpg.api.entity.EAWithIAttr;
import mixac1.dangerrpg.capability.PlayerAttributes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

public class EAMana extends EAWithIAttr
{
    public EAMana(String name)
    {
        super(name);
    }

    @Override
    @Deprecated
    public boolean setValueRaw(Float value, EntityLivingBase entity)
    {
        float tmp = PlayerAttributes.CURR_MANA.getValue(entity) / getValue(entity);
        if (super.setValueRaw(value, entity)) {
            PlayerAttributes.CURR_MANA.setValue(getValue(entity) * tmp, entity);
            return true;
        }
        return false;
    }

    @Override
    public void setModificatorValue(Float value, EntityLivingBase entity, UUID ID)
    {
        if (!entity.worldObj.isRemote) {
            float tmp = PlayerAttributes.CURR_MANA.getValue(entity) / getValue(entity);
            super.setModificatorValue(value, entity, ID);
            PlayerAttributes.CURR_MANA.setValue(getValue(entity) * tmp, entity);
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
