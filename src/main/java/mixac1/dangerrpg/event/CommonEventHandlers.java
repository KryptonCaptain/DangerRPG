package mixac1.dangerrpg.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mixac1.dangerrpg.capability.EntityData;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgSyncEntityData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class CommonEventHandlers
{
    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing e)
    {
        if (e.entity instanceof EntityLivingBase && EntityData.hasIt((EntityLivingBase) e.entity)) {
            EntityData.register((EntityLivingBase) e.entity);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent e)
    {
        if (e.entity instanceof EntityLivingBase && EntityData.hasIt((EntityLivingBase) e.entity)) {
            RPGNetwork.net.sendToServer(new MsgSyncEntityData((EntityLivingBase) e.entity));
        }
    }

    @SubscribeEvent
    public void onPlayerCloned(PlayerEvent.Clone e)
    {
        if (e.wasDeath) {
            EntityData.get(e.original).rebuildOnDeath();
        }
        NBTTagCompound nbt = new NBTTagCompound();
        EntityData.get(e.original).saveNBTData(nbt);
        EntityData.get(e.entityPlayer).loadNBTData(nbt);
    }
}
