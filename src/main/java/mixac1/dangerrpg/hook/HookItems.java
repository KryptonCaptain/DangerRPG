package mixac1.dangerrpg.hook;

import java.util.UUID;

import com.google.common.collect.Multimap;

import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.Hook.ReturnValue;
import gloomyfolken.hooklib.asm.ReturnCondition;
import mixac1.dangerrpg.api.event.ItemStackEvent.AddAttributeModifiers;
import mixac1.dangerrpg.capability.GemableItem;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.ea.PlayerAttributes;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import mixac1.dangerrpg.init.RPGOther.RPGItemRarity;
import mixac1.dangerrpg.item.IMaterialSpecial;
import mixac1.dangerrpg.util.RPGHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class HookItems
{
    /**
     * Hook to creating {@link ItemStack}
     * Add to stack lvlable and gemable parametres
     */
    @Hook(injectOnExit = true, targetMethod = "<init>")
    public static void ItemStack(ItemStack stack, Item item, int size, int metadata)
    {
        if (LvlableItem.isLvlable(stack)) {
            LvlableItem.createLvlableItem(stack);
            GemableItem.createGemableItem(stack);
        }
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static Multimap getAttributeModifiers(Item item, ItemStack stack, @ReturnValue Multimap returnValue)
    {
        if (LvlableItem.isLvlable(stack)) {
            if (ItemAttributes.MELEE_DAMAGE.hasIt(stack)) {
                returnValue.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
                returnValue.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"), "Weapon modifier",
                        ItemAttributes.MELEE_DAMAGE.get(stack), 0));
            }
            MinecraftForge.EVENT_BUS.post(new AddAttributeModifiers(stack, returnValue));
        }
        return returnValue;
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static int getItemEnchantability(Item item, ItemStack stack, @ReturnValue int returnValue)
    {
        if (LvlableItem.isLvlable(stack)) {
            if (ItemAttributes.ENCHANTABILITY.hasIt(stack)) {
                return (int) ItemAttributes.ENCHANTABILITY.get(stack);
            }
        }
        return returnValue;
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static int getMaxDamage(ItemStack stack, @ReturnValue int returnValue)
    {
        if (LvlableItem.isLvlable(stack)) {
            if (returnValue > 0 && ItemAttributes.MAX_DURABILITY.hasIt(stack)) {
                return (int) ItemAttributes.MAX_DURABILITY.get(stack);
            }
        }
        return returnValue;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean onEntitySwing(Item item, EntityLivingBase entity, ItemStack stack)
    {
        if (entity instanceof EntityPlayer) {
            return PlayerAttributes.SPEED_COUNTER.getValue(entity) != 0;
        }
        return false;
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static EnumRarity getRarity(Item item, ItemStack stack, @ReturnValue EnumRarity returnValue)
    {
        if (LvlableItem.isLvlable(stack)
                && (returnValue == EnumRarity.common
                || stack.isItemEnchanted() && returnValue == EnumRarity.rare)) {
            IMaterialSpecial mat = RPGHelper.getMaterialSpecial(stack);
            if (mat != null) {
                return mat.getItemRarity();
            }
        }

        if (returnValue == EnumRarity.uncommon) {
            return RPGItemRarity.uncommon;
        }
        else if (returnValue == EnumRarity.rare) {
            return RPGItemRarity.rare;
        }
        else if (returnValue == EnumRarity.epic) {
            return RPGItemRarity.epic;
        }
        return returnValue;
    }
}

