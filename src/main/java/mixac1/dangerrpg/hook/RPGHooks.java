package mixac1.dangerrpg.hook;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.Level;

import com.google.common.base.Throwables;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.Hook.ReturnValue;
import gloomyfolken.hooklib.asm.ReturnCondition;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

public class RPGHooks
{
    /**
     * Hook to creating ItemStack
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
     * Hook for ItemArmor
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static float applyArmorCalculations(EntityLivingBase entity, DamageSource source, float damage)
    {
        return RPGCommonHelper.applyArmorCalculations(entity, source, damage);
    }


    /**
     * Hook for ItemBow
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
     * Hook for ItemBow
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
     * Hook for ItemBow
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

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS, targetMethod = "<init>")
    public static void fixCutEntityMotion(S12PacketEntityVelocity packet, int id, double motionX, double motionY, double motionZ)
    {
        ReflectionHelper.setPrivateValue(S12PacketEntityVelocity.class, packet, (int) (motionX * 8000.0D), "field_149415_b");
        ReflectionHelper.setPrivateValue(S12PacketEntityVelocity.class, packet, (int) (motionY * 8000.0D), "field_149416_c");
        ReflectionHelper.setPrivateValue(S12PacketEntityVelocity.class, packet, (int) (motionZ * 8000.0D), "field_149414_d");
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS, targetMethod = "<init>")
    public static void fixCutEntityMotion(S0EPacketSpawnObject packet, Entity entity, int par1, int par2)
    {
        if (par2 > 0) {
            packet.func_149003_d((int) (entity.motionX * 8000.0D));
            packet.func_149000_e((int) (entity.motionY * 8000.0D));
            packet.func_149007_f((int) (entity.motionZ * 8000.0D));
        }
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS, targetMethod = "<init>")
    public static void fixCutEntityMotion(S0FPacketSpawnMob packet, EntityLivingBase entity)
    {
        ReflectionHelper.setPrivateValue(S0FPacketSpawnMob.class, packet, (int) (entity.motionX * 8000.0D), "field_149036_f");
        ReflectionHelper.setPrivateValue(S0FPacketSpawnMob.class, packet, (int) (entity.motionY * 8000.0D), "field_149037_g");
        ReflectionHelper.setPrivateValue(S0FPacketSpawnMob.class, packet, (int) (entity.motionZ * 8000.0D), "field_149047_h");
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS, targetMethod = "toBytes")
    public static void fixCutEntityMotion(FMLMessage.EntitySpawnMessage message, ByteBuf buf)
    {
        Entity entity = ReflectionHelper.getPrivateValue(FMLMessage.EntityMessage.class, message, "entity");

        String modId = ReflectionHelper.getPrivateValue(FMLMessage.EntitySpawnMessage.class, message, "modId");
        int modEntityTypeId = ReflectionHelper.getPrivateValue(FMLMessage.EntitySpawnMessage.class, message, "modEntityTypeId");

        //T0D0: super method
        buf.writeInt(entity.getEntityId());

        //T0D0: this method
        ByteBufUtils.writeUTF8String(buf, modId);
        buf.writeInt(modEntityTypeId);

        buf.writeInt(MathHelper.floor_double(entity.posX * 32D));
        buf.writeInt(MathHelper.floor_double(entity.posY * 32D));
        buf.writeInt(MathHelper.floor_double(entity.posZ * 32D));

        buf.writeByte((byte)(entity.rotationYaw * 256.0F / 360.0F));
        buf.writeByte((byte) (entity.rotationPitch * 256.0F / 360.0F));

        if (entity instanceof EntityLivingBase) {
            buf.writeByte((byte) (((EntityLivingBase)entity).rotationYawHead * 256.0F / 360.0F));
        }
        else {
            buf.writeByte(0);
        }

        ByteBuf tmpBuf = Unpooled.buffer();
        PacketBuffer pb = new PacketBuffer(tmpBuf);

        try {
            entity.getDataWatcher().func_151509_a(pb);
        }
        catch (IOException e) {
            FMLLog.log(Level.FATAL,e,"Encountered fatal exception trying to send entity spawn data watchers");
            throw Throwables.propagate(e);
        }
        buf.writeBytes(tmpBuf);

        if (entity instanceof IThrowableEntity) {
            Entity owner = ((IThrowableEntity)entity).getThrower();
            buf.writeInt(owner == null ? entity.getEntityId() : owner.getEntityId());
            buf.writeInt((int)(entity.motionX * 8000D));
            buf.writeInt((int)(entity.motionY * 8000D));
            buf.writeInt((int)(entity.motionZ * 8000D));
        }
        else {
            buf.writeInt(0);
        }

        if (entity instanceof IEntityAdditionalSpawnData) {
            tmpBuf = Unpooled.buffer();
            ((IEntityAdditionalSpawnData)entity).writeSpawnData(tmpBuf);
            buf.writeBytes(tmpBuf);
        }
    }
}

