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
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.MathHelper;

public class RPGRawHooks
{
    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS, targetMethod = "<init>")
    public static void fixCutEntityMotion(S12PacketEntityVelocity packet, int id, double motionX, double motionY, double motionZ)
    {
        ReflectionHelper.setPrivateValue(S12PacketEntityVelocity.class, packet, (int) (motionX * 8000.0D), "field_149415_b");
        ReflectionHelper.setPrivateValue(S12PacketEntityVelocity.class, packet, (int) (motionY * 8000.0D), "field_149416_c");
        ReflectionHelper.setPrivateValue(S12PacketEntityVelocity.class, packet, (int) (motionZ * 8000.0D), "field_149414_d");
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS, targetMethod = "<init>")
    public static void fixCutEntityMotion(S0EPacketSpawnObject packet, Entity entity, int par1, int par2)
    {
        if (par2 > 0) {
            packet.func_149003_d((int) (entity.motionX * 8000.0D));
            packet.func_149000_e((int) (entity.motionY * 8000.0D));
            packet.func_149007_f((int) (entity.motionZ * 8000.0D));
        }
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS, targetMethod = "<init>")
    public static void fixCutEntityMotion(S0FPacketSpawnMob packet, EntityLivingBase entity)
    {
        ReflectionHelper.setPrivateValue(S0FPacketSpawnMob.class, packet, (int) (entity.motionX * 8000.0D), "field_149036_f");
        ReflectionHelper.setPrivateValue(S0FPacketSpawnMob.class, packet, (int) (entity.motionY * 8000.0D), "field_149037_g");
        ReflectionHelper.setPrivateValue(S0FPacketSpawnMob.class, packet, (int) (entity.motionZ * 8000.0D), "field_149047_h");
    }

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
}
