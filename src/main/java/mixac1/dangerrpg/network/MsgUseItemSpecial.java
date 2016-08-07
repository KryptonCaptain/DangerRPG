package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.GemableItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MsgUseItemSpecial implements IMessage
{
    public MsgUseItemSpecial() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<MsgUseItemSpecial, IMessage>
    {
        @Override
        public IMessage onMessage(MsgUseItemSpecial message, MessageContext ctx)
        {
            EntityPlayer player = DangerRPG.proxy.getPlayer(ctx);
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && GemableItem.isGemable(stack)) {
                GemableItem.onItemUseSpecial(stack, player.worldObj, player);
            }
            return null;
        }
    }
}
