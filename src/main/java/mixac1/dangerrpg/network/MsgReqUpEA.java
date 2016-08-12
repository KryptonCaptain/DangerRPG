package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.capability.EntityData;
import net.minecraft.entity.EntityLivingBase;

public class MsgReqUpEA implements IMessage
{
    public int hash;
    public int entityId;

    public MsgReqUpEA() {}

    public MsgReqUpEA(int hash, int entityId)
    {
        this.hash = hash;
        this.entityId = entityId;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.hash = buf.readInt();
        this.entityId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.hash);
        buf.writeInt(this.entityId);
    }

    public static class Handler implements IMessageHandler<MsgReqUpEA, IMessage>
    {
        @Override
        public IMessage onMessage(MsgReqUpEA message, MessageContext ctx)
        {
            EntityLivingBase entity = (EntityLivingBase) DangerRPG.proxy.getEntityByID(ctx, message.entityId);
            if (entity != null) {
                EntityAttribute ea = EntityData.get(entity).getEntityAttribute(message.hash);
                if (ea != null && ea.lvlProvider != null) {
                    ea.lvlProvider.tryUp(entity);
                }
            }
            return null;
        }
    }
}
