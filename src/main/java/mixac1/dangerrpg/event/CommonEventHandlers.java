package mixac1.dangerrpg.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mixac1.dangerrpg.capability.CommonEntityData;
import mixac1.dangerrpg.capability.EntityLivingData;
import mixac1.dangerrpg.capability.PlayerData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class CommonEventHandlers
{
    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing e)
    {
        if (e.entity instanceof EntityPlayer) {
            PlayerData.register((EntityPlayer) e.entity);
        }
        else if (e.entity instanceof EntityLivingBase)
        {
        	EntityLivingData.register((EntityLivingBase) e.entity);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent e)
    {
        if (e.entity instanceof EntityLivingBase) {
            CommonEntityData.get((EntityLivingBase) e.entity).request((EntityLivingBase) e.entity);
        }
    }

    @SubscribeEvent
    public void onPlayerCloned(PlayerEvent.Clone e)
    {
        if (e.wasDeath) {
            PlayerData.get(e.original).rebuildOnDeath();
        }
        NBTTagCompound nbt = new NBTTagCompound();
        PlayerData.get(e.original).saveNBTData(nbt);
        PlayerData.get(e.entityPlayer).loadNBTData(nbt);
    }
}
