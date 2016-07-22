package mixac1.dangerrpg.hook;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.Hook.ReturnValue;
import gloomyfolken.hooklib.asm.ReturnCondition;
import mixac1.dangerrpg.api.item.ILvlableItem;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemBow;
import mixac1.dangerrpg.capability.GemableItem;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.itemattr.ItemAttributes;
import mixac1.dangerrpg.event.ItemStackEvent.AddInformationEvent;
import mixac1.dangerrpg.event.ItemStackEvent.GetAttributeModifiers;
import mixac1.dangerrpg.event.ItemStackEvent.HitEntityEvent;
import mixac1.dangerrpg.event.ItemStackEvent.OnLeftClickEntityEvent;
import mixac1.dangerrpg.util.RPGCommonHelper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

public class RPGHooks
{
    /**
     * Hook to creating {@link ItemStack}
     * Add to stack lvlable and gemable parametres
     */
    @Hook(injectOnExit = true, targetMethod = "<init>")
    public static void ItemStack(ItemStack stack, Item item, int size, int metadata)
    {
        if (LvlableItem.itemsAttrebutes.containsKey(item)) {
            LvlableItem.createLvlableItem(stack);
            GemableItem.createGemableItem(stack);
        }
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static boolean onLeftClickEntity(Item item, ItemStack stack, EntityPlayer player, Entity entity, @ReturnValue boolean returnValue)
    {
        return !returnValue && MinecraftForge.EVENT_BUS.post(new OnLeftClickEntityEvent(stack, player, entity));
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static boolean hitEntity(ItemSword item, ItemStack stack, EntityLivingBase entity, EntityLivingBase attacker, @ReturnValue boolean returnValue)
    {
        return returnValue && MinecraftForge.EVENT_BUS.post(new HitEntityEvent(stack, entity, attacker));
    }

    @SideOnly(Side.CLIENT)
    @Hook
    public static void addInformation(Item item, ItemStack stack, EntityPlayer player, List list, boolean par)
    {
        MinecraftForge.EVENT_BUS.post(new AddInformationEvent(stack, player, list, par));
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static Multimap getAttributeModifiers(Item item, ItemStack stack, @ReturnValue Multimap returnValue)
    {
        if (ItemAttributes.MELEE_DAMAGE.hasIt(stack)) {
            returnValue.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
            returnValue.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"), "Weapon modifier",
                    ItemAttributes.MELEE_DAMAGE.get(stack), 0));
        }
        MinecraftForge.EVENT_BUS.post(new GetAttributeModifiers(stack, returnValue));
        return returnValue;
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static int getItemEnchantability(Item item, ItemStack stack, @ReturnValue int returnValue)
    {
        if (ItemAttributes.ENCHANTABILITY.hasIt(stack)) {
            return (int) ItemAttributes.ENCHANTABILITY.get(stack);
        }
        return returnValue;
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static int getMaxDamage(ItemStack stack, @ReturnValue int returnValue)
    {
        if (returnValue > 0 && ItemAttributes.MAX_DURABILITY.hasIt(stack)) {
            return (int) ItemAttributes.MAX_DURABILITY.get(stack);
        }
        return returnValue;
    }

    /**
     * Hook for {@link ItemArmor}
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static float applyArmorCalculations(EntityLivingBase entity, DamageSource source, float damage)
    {
        return RPGCommonHelper.applyArmorCalculations(entity, source, damage);
    }

    /**
     * Hook for {@link ItemBow}
     */
    @SideOnly(Side.CLIENT)
    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static IIcon getItemIcon(EntityPlayer player, ItemStack stack, int par, @ReturnValue IIcon returnValue)
    {
        if (player.getItemInUse() != null && stack.getItemUseAction() == EnumAction.bow && ItemAttributes.SHOT_SPEED.hasIt(stack)) {
            int ticks = stack.getMaxItemUseDuration() - player.getItemInUseCount();
            float speed = ItemAttributes.SHOT_SPEED.get(stack, player);
            if (ticks >= speed) {
                return ((ItemBow) stack.getItem()).getItemIconForUseDuration(2);
            }
            else if (ticks > speed / 2) {
                return ((ItemBow) stack.getItem()).getItemIconForUseDuration(1);
            }
            else if (ticks > 0) {
                return ((ItemBow) stack.getItem()).getItemIconForUseDuration(0);
            }
        }
        return returnValue;
    }

    /**
     * Hook for {@link ItemBow}
     */
    @SideOnly(Side.CLIENT)
    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static float getFOVMultiplier(EntityPlayerSP player)
    {
        float f = 1.0F;

        if (player.capabilities.isFlying) {
            f *= 1.1F;
        }

        IAttributeInstance attrInst = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        f = (float)(f * ((attrInst.getAttributeValue() / player.capabilities.getWalkSpeed() + 1.0D) / 2.0D));

        if (player.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f)) {
            f = 1.0F;
        }

        ItemStack stack;
        if (player.isUsingItem() && (stack = player.getItemInUse()).getItem() instanceof ItemBow) {
            int ticks = player.getItemInUseDuration();
            float speed = ItemAttributes.SHOT_SPEED.hasIt(stack) ? ItemAttributes.SHOT_SPEED.get(stack, player) : 20F;
            float f1 = ticks / speed;

            if (f1 > 1.0F) {
                f1 = 1.0F;
            }
            else {
                f1 *= f1;
            }

            f *= 1.0F - f1 * 0.15F;
        }

        return ForgeHooksClient.getOffsetFOV(player, f);
    }

    /**
     * Hook for {@link ItemBow}
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void onPlayerStoppedUsing(ItemBow bow, ItemStack stack, World world, EntityPlayer player, int par)
    {
        int useDuration = bow.getMaxItemUseDuration(stack) - par;
        ArrowLooseEvent event = new ArrowLooseEvent(player, stack, useDuration);
        MinecraftForge.EVENT_BUS.post(new ArrowLooseEvent(player, stack, useDuration));
        if (event.isCanceled()) {
            return;
        }
        useDuration = event.charge;

        ((ILvlableItemBow) (bow instanceof ILvlableItemBow ? bow : ILvlableItem.DEFAULT_BOW)).onStoppedUsing(stack, world, player, useDuration);
    }
}

