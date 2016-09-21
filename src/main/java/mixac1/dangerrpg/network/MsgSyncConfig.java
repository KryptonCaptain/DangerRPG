package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.init.RPGCapability;

public class MsgSyncConfig implements IMessage
{
    public MsgSyncConfig() {}

    @Override
    public void fromBytes(ByteBuf buf)
    {
        byte[] bytes;

        bytes = new byte[buf.readInt()];
        buf.readBytes(bytes);
        RPGCapability.rpgItemRegistr.extractTransferData(bytes);

        bytes = new byte[buf.readInt()];
        buf.readBytes(bytes);
        RPGCapability.rpgEntityRegistr.extractTransferData(bytes);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(RPGCapability.rpgItemRegistr.getTransferData().length);
        buf.writeBytes(RPGCapability.rpgItemRegistr.getTransferData());

        buf.writeInt(RPGCapability.rpgEntityRegistr.getTransferData().length);
        buf.writeBytes(RPGCapability.rpgEntityRegistr.getTransferData());
    }

    public static class Handler implements IMessageHandler<MsgSyncConfig, IMessage>
    {
        @Override
        public IMessage onMessage(MsgSyncConfig msg, MessageContext ctx)
        {
            return null;
        }
    }
}