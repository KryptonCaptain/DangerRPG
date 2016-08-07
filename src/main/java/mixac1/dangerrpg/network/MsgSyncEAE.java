package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.CommonEntityData;
import net.minecraft.entity.EntityLivingBase;

public class MsgSyncEAE extends MsgSyncEA
{
    public int level;

    public MsgSyncEAE() {}

    public MsgSyncEAE(int hash, int level, float value, int entityId)
    {
        super(hash, value, entityId);
        this.level = level;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        super.fromBytes(buf);
        this.level = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        super.toBytes(buf);
        buf.writeInt(this.level);
    }

    public static class Handler implements IMessageHandler<MsgSyncEAE, IMessage>
    {
        @Override
        public IMessage onMessage(MsgSyncEAE message, MessageContext ctx)
        {
            EntityLivingBase entity = (EntityLivingBase) DangerRPG.proxy.getEntityByID(ctx, message.entityId);
            CommonEntityData.get(entity).getPlayerAttributeE(message.hash).handle(entity, message);
            return null;
        }
    }
}
