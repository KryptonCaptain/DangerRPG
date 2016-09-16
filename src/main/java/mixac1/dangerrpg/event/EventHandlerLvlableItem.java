package mixac1.dangerrpg.event;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.api.event.ItemStackEvent.HitEntityEvent;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.ea.PlayerAttributes;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

public class EventHandlerLvlableItem
{
    @SubscribeEvent
    public void hitEntity(HitEntityEvent e)
    {
        if (e.attacker instanceof EntityPlayer && LvlableItem.isLvlable(e.stack)) {
            EntityPlayer player = (EntityPlayer) e.attacker;

            if (!e.isRangeed) {
                float speed = ItemAttributes.MELEE_SPEED.hasIt(e.stack) ? ItemAttributes.MELEE_SPEED.get(e.stack, player) : 10f;
                PlayerAttributes.SPEED_COUNTER.setValue(speed < 0 ? 0 : speed, player);
            }

            e.entity.hurtResistantTime = 0;

            if (ItemAttributes.KNOCKBACK.hasIt(e.stack)) {
                e.knockback += ItemAttributes.KNOCKBACK.get(e.stack, player);
            }

            if (ItemAttributes.STR_MUL.hasIt(e.stack)) {
                e.damage += PlayerAttributes.STRENGTH.getValue(player) * ItemAttributes.STR_MUL.get(e.stack);
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemTooltipEvent e)
    {
        if (LvlableItem.isLvlable(e.itemStack)) {
            e.toolTip.add("");
            e.toolTip.add(Utils.toString(EnumChatFormatting.GOLD,
                       ItemAttributes.LEVEL.getDispayName(), ": ", (int) ItemAttributes.LEVEL.get(e.itemStack)));
            e.toolTip.add(Utils.toString(EnumChatFormatting.GRAY,
                       ItemAttributes.CURR_EXP.getDispayName(), ": ",
                       (int) ItemAttributes.CURR_EXP.get(e.itemStack), "/", (int) ItemAttributes.MAX_EXP.get(e.itemStack)));
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerTickClient(TickEvent.PlayerTickEvent e)
    {
        Minecraft m;
        if (e.phase == TickEvent.Phase.END) {
            if (e.player.swingProgressInt == 1) {
                ItemStack stack = e.player.getCurrentEquippedItem();
                if (stack != null && ItemAttributes.REACH.hasIt(stack)) {
                    MovingObjectPosition object = RPGHelper.getMouseOver(0, ItemAttributes.REACH.get(stack) + 4);

                    if (object != null && object.entityHit != null && object.entityHit != e.player && object.entityHit.hurtResistantTime == 0) {
                        FMLClientHandler.instance().getClient().playerController.attackEntity(e.player, object.entityHit);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void breakSpeed(BreakSpeed e)
    {
        if (ForgeHooks.canToolHarvestBlock(e.block, e.metadata, e.entityPlayer.inventory.getCurrentItem())) {
            e.newSpeed += PlayerAttributes.EFFICIENCY.getValue(e.entityPlayer);
        }
    }
}
