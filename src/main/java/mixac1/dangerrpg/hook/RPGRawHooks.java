package mixac1.dangerrpg.hook;

import java.io.IOException;

import org.apache.logging.log4j.Level;

import com.google.common.base.Throwables;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.ReflectionHelper;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.MathHelper;

public class RPGRawHooks
{
    /*@Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS, targetMethod = "<init>")
    public static void fixCutEntityMotion(S12PacketEntityVelocity packet, int id, double motionX, double motionY, double motionZ)
    {
        ReflectionHelper.setPrivateValue(S12PacketEntityVelocity.class, packet, id,                        "field_149417_a");
        ReflectionHelper.setPrivateValue(S12PacketEntityVelocity.class, packet, (int) (motionX * 8000.0D), "field_149415_b");
        ReflectionHelper.setPrivateValue(S12PacketEntityVelocity.class, packet, (int) (motionY * 8000.0D), "field_149416_c");
        ReflectionHelper.setPrivateValue(S12PacketEntityVelocity.class, packet, (int) (motionZ * 8000.0D), "field_149414_d");
    }*/

    @Hook(returnCondition = ReturnCondition.ALWAYS, targetMethod = "toBytes")
    public static void fixCutEntityMotion(FMLMessage.EntitySpawnMessage message, ByteBuf buf)
    {
        Entity entity = ReflectionHelper.getPrivateValue(FMLMessage.EntityMessage.class, message, "entity");

        String modId = ReflectionHelper.getPrivateValue(FMLMessage.EntitySpawnMessage.class, message, "modId");
        int modEntityTypeId = ReflectionHelper.getPrivateValue(FMLMessage.EntitySpawnMessage.class, message, "modEntityTypeId");

        //T0D0: super method
        buf.writeInt(entity.getEntityId());

        //T0D0: this method
        ByteBufUtils.writeUTF8String(buf, modId);
        buf.writeInt(modEntityTypeId);

        buf.writeInt(MathHelper.floor_double(entity.posX * 32D));
        buf.writeInt(MathHelper.floor_double(entity.posY * 32D));
        buf.writeInt(MathHelper.floor_double(entity.posZ * 32D));

        buf.writeByte((byte)(entity.rotationYaw * 256.0F / 360.0F));
        buf.writeByte((byte) (entity.rotationPitch * 256.0F / 360.0F));

        if (entity instanceof EntityLivingBase) {
            buf.writeByte((byte) (((EntityLivingBase)entity).rotationYawHead * 256.0F / 360.0F));
        }
        else {
            buf.writeByte(0);
        }

        ByteBuf tmpBuf = Unpooled.buffer();
        PacketBuffer pb = new PacketBuffer(tmpBuf);

        try {
            entity.getDataWatcher().func_151509_a(pb);
        }
        catch (IOException e) {
            FMLLog.log(Level.FATAL,e,"Encountered fatal exception trying to send entity spawn data watchers");
            throw Throwables.propagate(e);
        }
        buf.writeBytes(tmpBuf);

        if (entity instanceof IThrowableEntity) {
            Entity owner = ((IThrowableEntity)entity).getThrower();
            buf.writeInt(owner == null ? entity.getEntityId() : owner.getEntityId());
            buf.writeInt((int)(entity.motionX * 8000D));
            buf.writeInt((int)(entity.motionY * 8000D));
            buf.writeInt((int)(entity.motionZ * 8000D));
        }
        else {
            buf.writeInt(0);
        }

        if (entity instanceof IEntityAdditionalSpawnData) {
            tmpBuf = Unpooled.buffer();
            ((IEntityAdditionalSpawnData)entity).writeSpawnData(tmpBuf);
            buf.writeBytes(tmpBuf);
        }
    }

    /*@Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void sendLocationToAllClients(EntityTrackerEntry tracker, List list)
    {
        boolean isDataInitialized = ReflectionHelper.getPrivateValue(EntityTrackerEntry.class, tracker, "isDataInitialized");

        double posX = ReflectionHelper.getPrivateValue(EntityTrackerEntry.class, tracker, "posX");
        double posY = ReflectionHelper.getPrivateValue(EntityTrackerEntry.class, tracker, "posY");
        double posZ = ReflectionHelper.getPrivateValue(EntityTrackerEntry.class, tracker, "posZ");

        Entity field_85178_v = ReflectionHelper.getPrivateValue(EntityTrackerEntry.class, tracker, "field_85178_v");

        //T0D0: this method

        tracker.playerEntitiesUpdated = false;

        if (!isDataInitialized || tracker.myEntity.getDistanceSq(posX, posY, posZ) > 16.0D) {
            ReflectionHelper.setPrivateValue(EntityTrackerEntry.class, tracker, tracker.myEntity.posX, "posX");
            ReflectionHelper.setPrivateValue(EntityTrackerEntry.class, tracker, tracker.myEntity.posY, "posY");
            ReflectionHelper.setPrivateValue(EntityTrackerEntry.class, tracker, tracker.myEntity.posZ, "posZ");
            ReflectionHelper.setPrivateValue(EntityTrackerEntry.class, tracker, true, "isDataInitialized");
            tracker.playerEntitiesUpdated = true;
            tracker.sendEventsToPlayers(list);
        }

        if (field_85178_v != tracker.myEntity.ridingEntity || tracker.myEntity.ridingEntity != null && tracker.ticks % 60 == 0) {
            ReflectionHelper.setPrivateValue(EntityTrackerEntry.class, tracker, tracker.myEntity.ridingEntity, "field_85178_v");
            tracker.func_151259_a(new S1BPacketEntityAttach(0, tracker.myEntity, tracker.myEntity.ridingEntity));
        }

        if (tracker.myEntity instanceof EntityItemFrame && tracker.ticks % 10 == 0) {
            EntityItemFrame entityitemframe = (EntityItemFrame)tracker.myEntity;
            ItemStack itemstack = entityitemframe.getDisplayedItem();

            if (itemstack != null && itemstack.getItem() instanceof ItemMap) {
                MapData mapdata = Items.filled_map.getMapData(itemstack, tracker.myEntity.worldObj);
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    EntityPlayer entityplayer = (EntityPlayer)iterator.next();
                    EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
                    mapdata.updateVisiblePlayers(entityplayermp, itemstack);
                    Packet packet = Items.filled_map.func_150911_c(itemstack, tracker.myEntity.worldObj, entityplayermp);

                    if (packet != null) {
                        entityplayermp.playerNetServerHandler.sendPacket(packet);
                    }
                }
            }

            try {
                ReflectionHelper.findMethod(EntityTrackerEntry.class, tracker, new String[] {"sendMetadataToAllAssociatedPlayers"},
                                            (Class<?>) void.class).invoke(tracker, (Class<?>) void.class);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        else if (tracker.ticks % tracker.updateFrequency == 0 || tracker.myEntity.isAirBorne || tracker.myEntity.getDataWatcher().hasChanges()) {
            int i;
            int j;

            if (tracker.myEntity.ridingEntity == null) {
                int ticksSinceLastForcedTeleport = (int) ReflectionHelper.getPrivateValue(EntityTrackerEntry.class, tracker, "ticksSinceLastForcedTeleport") + 1;
                ++ticksSinceLastForcedTeleport;
                i = tracker.myEntity.myEntitySize.multiplyBy32AndRound(tracker.myEntity.posX);
                j = MathHelper.floor_double(tracker.myEntity.posY * 32.0D);
                int k = tracker.myEntity.myEntitySize.multiplyBy32AndRound(tracker.myEntity.posZ);
                int l = MathHelper.floor_float(tracker.myEntity.rotationYaw * 256.0F / 360.0F);
                int i1 = MathHelper.floor_float(tracker.myEntity.rotationPitch * 256.0F / 360.0F);
                int j1 = i - tracker.lastScaledXPosition;
                int k1 = j - tracker.lastScaledYPosition;
                int l1 = k - tracker.lastScaledZPosition;
                Object object = null;
                boolean flag = Math.abs(j1) >= 4 || Math.abs(k1) >= 4 || Math.abs(l1) >= 4 || tracker.ticks % 60 == 0;
                boolean flag1 = Math.abs(l - tracker.lastYaw) >= 4 || Math.abs(i1 - tracker.lastPitch) >= 4;

                if (tracker.ticks > 0 || tracker.myEntity instanceof EntityArrow) {
                    if (j1 >= -128 && j1 < 128 && k1 >= -128 && k1 < 128 && l1 >= -128 && l1 < 128 && ticksSinceLastForcedTeleport <= 400 && !(boolean) ReflectionHelper.getPrivateValue(EntityTrackerEntry.class, tracker, "ridingEntity")) {
                        if (flag && flag1) {
                            object = new S14PacketEntity.S17PacketEntityLookMove(tracker.myEntity.getEntityId(), (byte)j1, (byte)k1, (byte)l1, (byte)l, (byte)i1);
                        }
                        else if (flag) {
                            object = new S14PacketEntity.S15PacketEntityRelMove(tracker.myEntity.getEntityId(), (byte)j1, (byte)k1, (byte)l1);
                        }
                        else if (flag1) {
                            object = new S14PacketEntity.S16PacketEntityLook(tracker.myEntity.getEntityId(), (byte)l, (byte)i1);
                        }
                    }
                    else {
                        ticksSinceLastForcedTeleport = 0;
                        object = new S18PacketEntityTeleport(tracker.myEntity.getEntityId(), i, j, k, (byte)l, (byte)i1);
                    }
                }

                ReflectionHelper.setPrivateValue(EntityTrackerEntry.class, tracker, ticksSinceLastForcedTeleport, "ticksSinceLastForcedTeleport");

                if (ReflectionHelper.getPrivateValue(EntityTrackerEntry.class, tracker, "sendVelocityUpdates")) {
                    double d0 = tracker.myEntity.motionX - tracker.motionX;
                    double d1 = tracker.myEntity.motionY - tracker.motionY;
                    double d2 = tracker.myEntity.motionZ - tracker.motionZ;
                    double d3 = 0.02D;
                    double d4 = d0 * d0 + d1 * d1 + d2 * d2;

                    if (d4 > d3 * d3 || d4 > 0.0D && tracker.myEntity.motionX == 0.0D && tracker.myEntity.motionY == 0.0D && tracker.myEntity.motionZ == 0.0D) {
                        tracker.motionX = tracker.myEntity.motionX;
                        tracker.motionY = tracker.myEntity.motionY;
                        tracker.motionZ = tracker.myEntity.motionZ;
                        tracker.func_151259_a(new S12PacketEntityVelocityMy(tracker.myEntity.getEntityId(), tracker.motionX, tracker.motionY, tracker.motionZ));
                    }
                }

                if (object != null) {
                    tracker.func_151259_a((Packet)object);
                }

                try {
                    ReflectionHelper.findMethod(EntityTrackerEntry.class, tracker, new String[] {"sendMetadataToAllAssociatedPlayers"},
                            (Class<?>) void.class).invoke(tracker, (Class<?>) void.class);
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                if (flag) {
                    tracker.lastScaledXPosition = i;
                    tracker.lastScaledYPosition = j;
                    tracker.lastScaledZPosition = k;
                }

                if (flag1) {
                    tracker.lastYaw = l;
                    tracker.lastPitch = i1;
                }

                ReflectionHelper.setPrivateValue(EntityTrackerEntry.class, tracker, false, "ridingEntity");
            }
            else {
                i = MathHelper.floor_float(tracker.myEntity.rotationYaw * 256.0F / 360.0F);
                j = MathHelper.floor_float(tracker.myEntity.rotationPitch * 256.0F / 360.0F);
                boolean flag2 = Math.abs(i - tracker.lastYaw) >= 4 || Math.abs(j - tracker.lastPitch) >= 4;

                if (flag2) {
                    tracker.func_151259_a(new S14PacketEntity.S16PacketEntityLook(tracker.myEntity.getEntityId(), (byte)i, (byte)j));
                    tracker.lastYaw = i;
                    tracker.lastPitch = j;
                }

                tracker.lastScaledXPosition = tracker.myEntity.myEntitySize.multiplyBy32AndRound(tracker.myEntity.posX);
                tracker.lastScaledYPosition = MathHelper.floor_double(tracker.myEntity.posY * 32.0D);
                tracker.lastScaledZPosition = tracker.myEntity.myEntitySize.multiplyBy32AndRound(tracker.myEntity.posZ);
                try {
                    ReflectionHelper.findMethod(EntityTrackerEntry.class, tracker, new String[] {"sendMetadataToAllAssociatedPlayers"},
                            (Class<?>) void.class).invoke(tracker, (Class<?>) void.class);
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                ReflectionHelper.setPrivateValue(EntityTrackerEntry.class, tracker, true, "ridingEntity");
            }

            i = MathHelper.floor_float(tracker.myEntity.getRotationYawHead() * 256.0F / 360.0F);

            if (Math.abs(i - tracker.lastHeadMotion) >= 4)
            {
                tracker.func_151259_a(new S19PacketEntityHeadLook(tracker.myEntity, (byte)i));
                tracker.lastHeadMotion = i;
            }

            tracker.myEntity.isAirBorne = false;
        }

        ++tracker.ticks;

        if (tracker.myEntity.velocityChanged)
        {
            tracker.func_151261_b(new S12PacketEntityVelocity(tracker.myEntity));
            tracker.myEntity.velocityChanged = false;
        }
    }*/

    public static class S12PacketEntityVelocityMy extends S12PacketEntityVelocity
    {
        public S12PacketEntityVelocityMy(int id, double motionX, double motionY, double motionZ)
        {
            ReflectionHelper.setPrivateValue(S12PacketEntityVelocity.class, this, id,                        "field_149417_a");
            ReflectionHelper.setPrivateValue(S12PacketEntityVelocity.class, this, (int) (motionX * 8000.0D), "field_149415_b");
            ReflectionHelper.setPrivateValue(S12PacketEntityVelocity.class, this, (int) (motionY * 8000.0D), "field_149416_c");
            ReflectionHelper.setPrivateValue(S12PacketEntityVelocity.class, this, (int) (motionZ * 8000.0D), "field_149414_d");
        }
    }
}
