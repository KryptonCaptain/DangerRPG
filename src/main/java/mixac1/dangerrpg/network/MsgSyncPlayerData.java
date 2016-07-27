package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.CommonEntityData;
import net.minecraft.nbt.NBTTagCompound;

public class MsgSyncPlayerData implements IMessage
{
    private NBTTagCompound data;

    public MsgSyncPlayerData () {}

    public MsgSyncPlayerData(CommonEntityData entityData)
    {
        this.data = new NBTTagCompound();
        entityData.saveNBTData(this.data);
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
            CommonEntityData.get(DangerRPG.proxy.getPlayerFromMessageCtx(ctx)).loadNBTData(message.data);
            return null;
        }
    }
    
    public static class HandlerServer implements IMessageHandler<MsgSyncPlayerData, IMessage>
    {
        @Override
        public IMessage onMessage(MsgSyncPlayerData message, MessageContext ctx)
        {
            CommonEntityData.get(DangerRPG.proxy.getPlayerFromMessageCtx(ctx)).syncAll();
            return null;
        }
    }
    
    /*public static class MsgSyncEntityData extends MsgSyncPlayerData
    {
    	private int entityId;

        public MsgSyncEntityData () {}

        public MsgSyncEntityData(CommonEntityData entityData, int entityId)
        {
            super(entityData);
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
        	super.fromBytes(buf);
        	entityId = buf.readInt();
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
        	super.toBytes(buf);
            buf.writeInt(entityId);
        }
        
        public static class HandlerClient implements IMessageHandler<MsgSyncEntityData, IMessage>
        {
            @Override
            public IMessage onMessage(MsgSyncEntityData message, MessageContext ctx)
            {
                CommonEntityData.get(DangerRPG.proxy.getPlayerFromMessageCtx(ctx)).loadNBTData(message.data);
                return null;
            }
        }
        
        public static class HandlerServer implements IMessageHandler<MsgSyncEntityData, IMessage>
        {
            @Override
            public IMessage onMessage(MsgSyncEntityData message, MessageContext ctx)
            {
                CommonEntityData.get(DangerRPG.proxy.getPlayerFromMessageCtx(ctx)).syncAll();
                return null;
            }
        }
    }*/
}
