package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.CommonEntityData;
import net.minecraft.entity.EntityLivingBase;

public class MsgSyncEA implements IMessage
{
    public int hash;
    public float value;
    public int entityId;

    public MsgSyncEA() {}

    public MsgSyncEA(int hash, float value, int entityId)
    {
        this.hash = hash;
        this.value = value;
        this.entityId = entityId;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.hash = buf.readInt();
        this.value = buf.readFloat();
        this.entityId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.hash);
        buf.writeFloat(this.value);
        buf.writeInt(this.entityId);
    }

    public static class Handler implements IMessageHandler<MsgSyncEA, IMessage>
    {
        @Override
        public IMessage onMessage(MsgSyncEA message, MessageContext ctx)
        {
            EntityLivingBase entity = (EntityLivingBase) DangerRPG.proxy.getEntityByID(ctx, message.entityId);
            CommonEntityData.get(entity).getPlayerAttribute(message.hash).handle(entity, message);
            return null;
        }
    }
}

