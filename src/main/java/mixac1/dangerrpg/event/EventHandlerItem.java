package mixac1.dangerrpg.event;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.api.event.DealtDamageEvent;
import mixac1.dangerrpg.api.event.ItemStackEvent.EquipmentStackChange;
import mixac1.dangerrpg.api.event.ItemStackEvent.HitEntityEvent;
import mixac1.dangerrpg.capability.RPGableItem;
import mixac1.dangerrpg.capability.data.RPGUUID;
import mixac1.dangerrpg.capability.ea.PlayerAttributes;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

public class EventHandlerItem
{
    @SubscribeEvent
    public void onHitEntity(HitEntityEvent e)
    {
        if (e.attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.attacker;

            if (RPGableItem.isRPGable(e.stack)) {
                if (!e.isRangeed) {
                    float speed = ItemAttributes.MELEE_SPEED.getSafe(e.stack, player, 10f);
                    PlayerAttributes.SPEED_COUNTER.setValue(speed < 0 ? 0 : speed, player);
                }
                else {
                    e.newDamage += PlayerAttributes.STRENGTH.getValue(player) * ItemAttributes.STR_MUL.getSafe(e.stack, player, 0);
                }

                e.entity.hurtResistantTime = 0;
                e.knockback += ItemAttributes.KNOCKBACK.getSafe(e.stack, player, 0);
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemTooltipEvent e)
    {
        if (RPGableItem.isRPGable(e.itemStack)) {
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
    public void onBreakSpeed(BreakSpeed e)
    {
        if (ForgeHooks.canToolHarvestBlock(e.block, e.metadata, e.entityPlayer.inventory.getCurrentItem())) {
            e.newSpeed += PlayerAttributes.EFFICIENCY.getValue(e.entityPlayer);
        }
    }

    @SubscribeEvent
    public void onDealtDamage(DealtDamageEvent e)
    {
        if (e.damage > 0) {
            RPGableItem.upEquipment(e.player, e.target, e.stack, e.damage);
        }
    }

    @SubscribeEvent
    public void onEquipmentStackChange(EquipmentStackChange e)
    {
        if (e.slot == 0) {
            IAttributeInstance attr = e.player.getEntityAttribute(SharedMonsterAttributes.attackDamage);
            AttributeModifier mod = attr.getModifier(RPGUUID.ADDITIONAL_STR_DAMAGE);
            if (mod != null) {
                attr.removeModifier(mod);
            }
            if (e.stack != null &&RPGableItem.isRPGable(e.stack)) {
                AttributeModifier newMod = new AttributeModifier(RPGUUID.ADDITIONAL_STR_DAMAGE, "Strenght damage",
                        PlayerAttributes.STRENGTH.getValue(e.player) * ItemAttributes.STR_MUL.get(e.stack), 0).setSaved(true);
                attr.applyModifier(newMod);
            }
        }
    }
}
