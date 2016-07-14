package mixac1.dangerrpg.api.item;

import java.util.HashMap;

import cpw.mods.fml.relauncher.ReflectionHelper;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.ItemAttrParams;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.itemattr.ItemAttributes;
import mixac1.dangerrpg.entity.projectile.EntityArrowRPG;
import mixac1.dangerrpg.entity.projectile.EntityMaterial;
import mixac1.dangerrpg.item.RPGToolComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;

public interface ILvlableItem
{
    public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map);

    public interface ILvlableItemTool extends ILvlableItem
    {
        public RPGToolComponent getToolComponent(Item item);

        public ToolMaterial getToolMaterial(Item item);
    }

    public interface ILvlableItemArmor extends ILvlableItem
    {
        public ArmorMaterial getArmorMaterial(Item item);
    }

    public interface ILvlableItemShoot extends ILvlableItemTool
    {
        public float getMaxCharge(ItemStack stack, EntityPlayer player);

        public float getPoweMul();
    }

    public interface ILvlableItemBow extends ILvlableItemShoot
    {
        /**
         * Don't use onPlayerStoppedUsing method.
         */
        public void onStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useDuration);
    }

    /**
     * Start DEFAULT Lvlable items
     */
    public static final ILvlableItem DEFAULT_ITEM = new ILvlableItem()

    {

        @Override
        public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
        {
        }
    };

    public static final ILvlableItem DEFAULT_ITEM_MOD = new ILvlableItem()
    {
        @Override
        public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
        {
            LvlableItem.registerParamsItemMod(item, map);
        }
    };

    public static final ILvlableItemTool DEFAULT_SWORD = new ILvlableItemTool()
    {
        @Override
        public RPGToolComponent getToolComponent(Item item)
        {
            return RPGToolComponent.SWORD;
        }

        @Override
        public ToolMaterial getToolMaterial(Item item)
        {
            try {
                return ReflectionHelper.getPrivateValue(ItemSword.class, (ItemSword) item, "field_150933_b");
            }
            catch (Exception e) {
                DangerRPG.logger.warn(e);
            };
            return null;
        }

        @Override
        public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
        {
            LvlableItem.registerParamsItemSword(item, map);
        }
    };

    public static final ILvlableItemTool DEFAULT_TOOL = new ILvlableItemTool()
    {
        @Override
        public RPGToolComponent getToolComponent(Item item)
        {
            if (item instanceof ItemAxe) {
                return RPGToolComponent.AXE;
            }
            else if (item instanceof ItemHoe) {
                return RPGToolComponent.HOE;
            }
            else if (item instanceof ItemSpade) {
                return RPGToolComponent.SHOVEL;
            }
            else if (item instanceof ItemPickaxe) {
                return RPGToolComponent.PICKAXE;
            }
            return null;
        }

        @Override
        public ToolMaterial getToolMaterial(Item item)
        {
            if (item instanceof ItemTool) {
                return ((ItemTool) item).func_150913_i();
            }
            else if (item instanceof ItemHoe) {
                try {
                    return ReflectionHelper.getPrivateValue(ItemHoe.class, (ItemHoe) item, "theToolMaterial");
                }
                catch (Exception e) {
                    DangerRPG.logger.warn(e);
                }
            }
            return null;
        }

        @Override
        public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
        {
            LvlableItem.registerParamsItemTool(item, map);
        }
    };

    public static final ILvlableItemArmor DEFAULT_ARMOR = new ILvlableItemArmor()
    {
        @Override
        public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
        {
            LvlableItem.registerParamsItemArmor(item, map);
        }

        @Override
        public ArmorMaterial getArmorMaterial(Item item)
        {
            return ((ItemArmor) item).getArmorMaterial();
        }
    };

    public static final ILvlableItemBow DEFAULT_BOW = new ILvlableItemBow()
    {
        @Override
        public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
        {
            LvlableItem.registerParamsItemBow(item, map);
        }

        @Override
        public RPGToolComponent getToolComponent(Item item)
        {
            return RPGToolComponent.BOW;
        }

        @Override
        public void onStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useDuration)
        {
            boolean flag = player.capabilities.isCreativeMode
                     || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

            if (flag || player.inventory.hasItem(Items.arrow)) {
                float power = useDuration / getMaxCharge(stack, player);
                power = (power * power + power * 2.0F) / 3.0F;

                if (power < 0.1D) {
                    return;
                }
                else if (power > 1.0F) {
                    power = 1.0F;
                }

                float powerMul = ItemAttributes.SHOT_POWER.hasIt(stack) ?
                                 ItemAttributes.SHOT_POWER.get(stack, player) : getPoweMul();
                EntityArrowRPG entity = new EntityArrowRPG(world, player, power * powerMul, 1F);

                entity.phisicDamage = ItemAttributes.SHOT_DAMAGE.hasIt(stack) ?
                                      ItemAttributes.SHOT_DAMAGE.get(stack, player) : 2F;

                int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
                if (k > 0) {
                    entity.phisicDamage += k * 0.5F + 0.5F;
                }

                if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
                    entity.setFire(100);
                }

                stack.damageItem(1, player);
                world.playSoundAtEntity(player, "random.bow", 1.0F,
                                        1.0F / (DangerRPG.rand.nextFloat() * 0.4F + 1.2F) + power * 0.5F);

                if (flag) {
                    entity.pickupMode = EntityMaterial.PICKUP_CREATIVE;
                }
                else {
                    player.inventory.consumeInventoryItem(Items.arrow);
                }

                if (!world.isRemote) {
                    world.spawnEntityInWorld(entity);
                }
            }
        }

        @Deprecated
        @Override
        public ToolMaterial getToolMaterial(Item item)
        {
            return null;
        }

        @Override
        public float getMaxCharge(ItemStack stack, EntityPlayer player)
        {
            return ItemAttributes.SHOT_SPEED.hasIt(stack) ?
                   ItemAttributes.SHOT_SPEED.get(stack, player) : 20F;
        }

        @Override
        public float getPoweMul()
        {
            return 3F;
        }
    };
}
