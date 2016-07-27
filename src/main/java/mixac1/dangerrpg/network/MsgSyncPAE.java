package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.EntityAttributeE;
import mixac1.dangerrpg.capability.CommonEntityData;
import net.minecraft.entity.player.EntityPlayer;

public class MsgSyncPAE extends MsgSyncPA
{
    public int level;

    public MsgSyncPAE() {}

    public MsgSyncPAE(int hash, int level, float value)
    {
        super(hash, value);
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
    
    public static class Handler implements IMessageHandler<MsgSyncPAE, IMessage>
    {
        @Override
        public IMessage onMessage(MsgSyncPAE message, MessageContext ctx)
        {
            EntityPlayer player = DangerRPG.proxy.getPlayerFromMessageCtx(ctx);
            EntityAttributeE pa = CommonEntityData.get(player).getPlayerAttributeE(message.hash);
            pa.setLvl(message.level, player);
            pa.setValue(message.value, player, false);
            return null;
        }
    }
}
