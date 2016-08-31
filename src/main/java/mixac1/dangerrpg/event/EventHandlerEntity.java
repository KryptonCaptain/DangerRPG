package mixac1.dangerrpg.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.event.InitRPGEntityEvent;
import mixac1.dangerrpg.capability.EntityData;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import mixac1.dangerrpg.capability.ea.PlayerAttributes;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgSyncEntityData;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EventHandlerEntity
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
            if (e.entity.worldObj.isRemote) {
                RPGNetwork.net.sendToServer(new MsgSyncEntityData((EntityLivingBase) e.entity));
            }
            else {
                EntityData.get((EntityLivingBase) e.entity).serverInit();
            }
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

    @SubscribeEvent
    public void onInitRPGEntity(InitRPGEntityEvent e)
    {
        ChunkCoordinates spawn = e.entity.worldObj.getSpawnPoint();
        double distance = Utils.getDiagonal(e.entity.posX - spawn.posX, e.entity.posZ - spawn.posZ);

        int lvl = (int) (distance / 50);
        if (EntityAttributes.LVL.hasIt(e.entity)) {
            EntityAttributes.LVL.setValue(lvl + 1, e.entity);
        }

        if (EntityAttributes.HEALTH.hasIt(e.entity)) {
            float health = e.entity.getHealth() / 5;
            EntityAttributes.HEALTH.setValue(health * lvl, e.entity);
        }
        if (EntityAttributes.DAMAGE.hasIt(e.entity)) {
            EntityAttributes.DAMAGE.setValue(0.5f * lvl, e.entity);
        }
    }

    @SubscribeEvent
    public void onLivingJump(LivingJumpEvent e)
    {
        if (e.entityLiving instanceof EntityPlayer) {
            e.entityLiving.motionY += PlayerAttributes.JUMP_HEIGHT.getValue(e.entityLiving);
        }
    }

    @SubscribeEvent
    public void onLivingFall(LivingFallEvent e)
    {
        if (e.entityLiving instanceof EntityPlayer) {
            e.distance -= PlayerAttributes.STEEL_MUSC.getValue(e.entityLiving);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e)
    {
        if (e.phase == TickEvent.Phase.START) {
            DangerRPG.proxy.fireTick(e.side);

            float tmp1, tmp2;
            if (!e.player.worldObj.isRemote) {
                if (e.player != null && (tmp1 = PlayerAttributes.SPEED_COUNTER.getValue(e.player)) > 0) {
                    PlayerAttributes.SPEED_COUNTER.setValue(tmp1 - 1, e.player);
                }

                if (DangerRPG.proxy.getTick(e.side) % 20 == 0) {
                    if ((tmp1 = PlayerAttributes.CURR_MANA.getValue(e.player)) < PlayerAttributes.MANA.getValue(e.player) &&
                        (tmp2 = PlayerAttributes.MANA_REGEN.getValue(e.player)) != 0) {
                        PlayerAttributes.CURR_MANA.setValue(tmp1 + tmp2, e.player);
                    }
                    e.player.heal(PlayerAttributes.HEALTH_REGEN.getValue(e.player));
                }
            }
            else {

            }
        }
    }
}
