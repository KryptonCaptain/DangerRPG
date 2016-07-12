package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.PlayerData;
import net.minecraft.nbt.NBTTagCompound;

public class MsgSyncPlayerData implements IMessage
{
    private NBTTagCompound data;

    public MsgSyncPlayerData () {}

    public MsgSyncPlayerData(PlayerData playerData)
    {
        this.data = new NBTTagCompound();
        playerData.saveNBTData(this.data);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf, this.data);
    }
    
    public static class HandlerClient implements IMessageHandler<MsgSyncPlayerData, IMessage>
    {
		@Override
		public IMessage onMessage(MsgSyncPlayerData message, MessageContext ctx)
		{
			PlayerData.get(DangerRPG.proxy.getPlayerFromMessageCtx(ctx)).loadNBTData(message.data);
			return null;
		}
    }
    
    public static class HandlerServer implements IMessageHandler<MsgSyncPlayerData, IMessage>
    {
		@Override
		public IMessage onMessage(MsgSyncPlayerData message, MessageContext ctx)
		{
			PlayerData.get(DangerRPG.proxy.getPlayerFromMessageCtx(ctx)).syncAll();
			return null;
		}
    }
}
