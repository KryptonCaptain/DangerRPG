package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.PlayerData;
import net.minecraft.entity.player.EntityPlayer;

public class MsgReqUpPA implements IMessage {

    public int index;

    public MsgReqUpPA() {}

    public MsgReqUpPA(int index)
    {
        this.index = index;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.index = buf.readInt();
       
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.index);
    }
    
    public static class Handler implements IMessageHandler<MsgReqUpPA, IMessage>
    {
        @Override
        public IMessage onMessage(MsgReqUpPA message, MessageContext ctx)
        {
            EntityPlayer player = DangerRPG.proxy.getPlayerFromMessageCtx(ctx);
            PlayerData.get(player).playerAttributes.get(message.index).tryUp(player);
            return null;
        }
    }
}
