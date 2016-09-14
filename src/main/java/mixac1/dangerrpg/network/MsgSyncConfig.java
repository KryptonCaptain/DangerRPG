package mixac1.dangerrpg.network;

import java.util.ArrayList;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.util.RPGHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class MsgSyncConfig implements IMessage
{
    public MsgSyncConfig() {}

    @Override
    public void fromBytes(ByteBuf buf)
    {
        RPGCapability.lvlItemRegistr.registr.clear();
        int size = buf.readInt();
        for (int i = 0; i < size; ++i) {
            RPGCapability.lvlItemRegistr.registr.add(Item.getItemById(buf.readInt()));
        }

        RPGCapability.rpgEntityRegistr.registr.clear();
        size = buf.readInt();
        String s;
        for (int i = 0; i < size; ++i) {
            s = ByteBufUtils.readUTF8String(buf);
            if (EntityList.stringToClassMapping.containsKey(s)) {
                Class entityClass = (Class) EntityList.stringToClassMapping.get(s);
                if (EntityLivingBase.class.isAssignableFrom(entityClass)) {
                    RPGCapability.rpgEntityRegistr.registr.add(entityClass);
                }
            }
        }
        RPGCapability.rpgEntityRegistr.registr.add(EntityPlayer.class);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(RPGCapability.lvlItemRegistr.getCloneSet().size());
        for (Item item : RPGCapability.lvlItemRegistr.getCloneSet()) {
            buf.writeInt(item.getIdFromItem(item));
        }

        ArrayList<String> entityNames = RPGHelper.getEntityNames(RPGCapability.rpgEntityRegistr.getCloneSet(), false);
        buf.writeInt(entityNames.size());
        for (String str : entityNames) {
            ByteBufUtils.writeUTF8String(buf, str);
        }
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