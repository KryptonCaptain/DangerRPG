package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.EntityAttributeE;
import mixac1.dangerrpg.capability.CommonEntityData;
import net.minecraft.entity.player.EntityPlayer;

public class MsgReqUpPA implements IMessage
{
    public int hash;

    public MsgReqUpPA() {}

    public MsgReqUpPA(int hash)
    {
        this.hash = hash;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.hash = buf.readInt();
       
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.hash);
    }
    
    public static class Handler implements IMessageHandler<MsgReqUpPA, IMessage>
    {
        @Override
        public IMessage onMessage(MsgReqUpPA message, MessageContext ctx)
        {
            EntityPlayer player = DangerRPG.proxy.getPlayerFromMessageCtx(ctx);
            EntityAttributeE pa = CommonEntityData.get(player).getPlayerAttributeE(message.hash);
            pa.tryUp(player);
            return null;
        }
    }
}
