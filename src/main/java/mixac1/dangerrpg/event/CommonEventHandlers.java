package mixac1.dangerrpg.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mixac1.dangerrpg.capability.PlayerData;
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
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent e)
	{
	    if (e.entity instanceof EntityPlayer) {
	        PlayerData.get((EntityPlayer) e.entity).requestSyncAll();;
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
