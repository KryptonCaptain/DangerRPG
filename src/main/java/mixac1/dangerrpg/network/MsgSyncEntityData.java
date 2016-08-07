package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.CommonEntityData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class MsgSyncEntityData implements IMessage
{
    public NBTTagCompound data;
    public int entityId = -1;

    public MsgSyncEntityData () {}

    public MsgSyncEntityData(int entityId)
    {
        init(entityId);
    }

    public MsgSyncEntityData(CommonEntityData entityData)
    {
        init(entityData);
    }

    public MsgSyncEntityData(CommonEntityData entityData, int entityId)
    {
        init(entityId);
        init(entityData);
    }

    private void init(int entityId)
    {
        this.entityId = entityId;
    }

    private void init(CommonEntityData entityData)
    {
        this.data = new NBTTagCompound();
        entityData.saveNBTData(this.data);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.data = ByteBufUtils.readTag(buf);
        this.entityId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf, this.data);
        buf.writeInt(this.entityId);
    }

    public static class HandlerClient implements IMessageHandler<MsgSyncEntityData, IMessage>
    {
        @Override
        public IMessage onMessage(MsgSyncEntityData message, MessageContext ctx)
        {
            CommonEntityData.get(DangerRPG.proxy.getPlayer(ctx)).handle(message, ctx);
            return null;
        }
    }

    public static class HandlerServer implements IMessageHandler<MsgSyncEntityData, IMessage>
    {
        @Override
        public IMessage onMessage(MsgSyncEntityData message, MessageContext ctx)
        {
            EntityPlayerMP player = (EntityPlayerMP) DangerRPG.proxy.getPlayer(ctx);
            CommonEntityData.get(player).sync(player, message.entityId);
            return null;
        }
    }
}
