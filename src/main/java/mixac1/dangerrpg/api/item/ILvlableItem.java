package mixac1.dangerrpg.api.item;

import java.util.HashMap;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.ItemAttrParams;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.itemattr.ItemAttributes;
import mixac1.dangerrpg.entity.projectile.EntityArrowRPG;
import mixac1.dangerrpg.entity.projectile.EntityMaterial;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGBowComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGGunComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGICWithoutTM;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
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

/**
 * Implements this interface for creating LvlableItem
 */
public interface ILvlableItem
{
    public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map);

    public RPGItemComponent getItemComponent(Item item);

    public interface ILvlableItemMod extends ILvlableItem {}

    public interface ILvlableItemTool extends ILvlableItemMod
    {
        @Override
        public RPGToolComponent getItemComponent(Item item);

        public ToolMaterial getToolMaterial(Item item);
    }

    public interface ILvlableItemArmor extends ILvlableItem
    {
        public ArmorMaterial getArmorMaterial(Item item);
    }

    public interface ILvlableItemGun extends ILvlableItemTool
    {
        @Override
        public RPGGunComponent getItemComponent(Item item);
    }

    public interface ILvlableItemBow extends ILvlableItemGun
    {
        /**
         * Don't use onPlayerStoppedUsing method.
         */
        public void onStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useDuration);

        @Override
        public RPGBowComponent getItemComponent(Item item);
    }

    /**
     * Start DEFAULT Lvlable items
     */
    public static final ILvlableItem DEFAULT_ITEM = new ILvlableItem()
    {
        @Override
        public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map) {}

        @Override
        public RPGItemComponent getItemComponent(Item item)
        {
            return null;
        }
    };

    public static final ILvlableItem DEFAULT_ITEM_MOD = new ILvlableItemMod()
    {
        @Override
        public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
        {
            LvlableItem.registerParamsItemMod(item, map);
        }

        @Override
        public RPGICWithoutTM getItemComponent(Item item)
        {
            return null;
        }
    };

    public static final ILvlableItemTool DEFAULT_SWORD = new ILvlableItemTool()
    {   	
        @Override
        public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
        {
            LvlableItem.registerParamsItemSword(item, map);
        }

        @Override
        public RPGToolComponent getItemComponent(Item item)
        {
            return RPGItemComponent.SWORD;
        }

        @Override
        public ToolMaterial getToolMaterial(Item item)
        {
            return ((ItemSword) item).field_150933_b;
        }
    };

    public static final ILvlableItemTool DEFAULT_TOOL = new ILvlableItemTool()
    {
        @Override
        public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
        {
            LvlableItem.registerParamsItemTool(item, map);
        }

        @Override
        public RPGToolComponent getItemComponent(Item item)
        {
            if (item instanceof ItemAxe) {
                return RPGItemComponent.AXE;
            }
            else if (item instanceof ItemHoe) {
                return RPGItemComponent.HOE;
            }
            else if (item instanceof ItemSpade) {
                return RPGItemComponent.SHOVEL;
            }
            else if (item instanceof ItemPickaxe) {
                return RPGItemComponent.PICKAXE;
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
                return ((ItemHoe) item).theToolMaterial;
            }
            return null;
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
        public RPGItemComponent getItemComponent(Item item)
        {
            return null;
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
        public RPGBowComponent getItemComponent(Item item)
        {
            return RPGItemComponent.BOW;
        }

        @Override
        public ToolMaterial getToolMaterial(Item item)
        {
            return null;
        }

        @Override
        public void onStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useDuration)
        {
            boolean flag = player.capabilities.isCreativeMode
                     || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

            if (flag || player.inventory.hasItem(Items.arrow)) {
                float power = useDuration / (ItemAttributes.SHOT_SPEED.hasIt(stack) ?
                                             ItemAttributes.SHOT_SPEED.get(stack, player) : 20F);
                power = (power * power + power * 2.0F) / 3.0F;

                if (power < 0.1D) {
                    return;
                }
                else if (power > 1.0F) {
                    power = 1.0F;
                }

                float powerMul = ItemAttributes.SHOT_POWER.hasIt(stack) ?
                                 ItemAttributes.SHOT_POWER.get(stack, player) : 1F;
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
    };
}
