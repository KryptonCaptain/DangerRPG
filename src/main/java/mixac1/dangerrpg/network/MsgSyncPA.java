package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.player.PlayerAttribute;
import mixac1.dangerrpg.capability.PlayerData;
import net.minecraft.entity.player.EntityPlayer;

public class MsgSyncPA implements IMessage
{
    public int hash;
    public float value;

    public MsgSyncPA() {}

    public MsgSyncPA(int hash, float value)
    {
        this.hash = hash;
        this.value = value;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.hash = buf.readInt();
        this.value = buf.readFloat();     
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.hash);
        buf.writeFloat(this.value);
    }
    
    public static class Handler implements IMessageHandler<MsgSyncPA, IMessage>
    {
        @Override
        public IMessage onMessage(MsgSyncPA message, MessageContext ctx)
        {
            EntityPlayer player = DangerRPG.proxy.getPlayerFromMessageCtx(ctx);
            PlayerAttribute pa = PlayerData.get(player).getPlayerAttribute(message.hash);
            pa.setValue(message.value, player, false);
            return null;
        }
    }
}

