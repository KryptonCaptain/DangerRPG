package mixac1.dangerrpg.api.item;

import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.LvlableItem.ItemAttributesMap;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import mixac1.dangerrpg.entity.projectile.EntityArrowRPG;
import mixac1.dangerrpg.entity.projectile.EntityMaterial;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.item.RPGArmorMaterial;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGArmorComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGBowComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGGunComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGICWithoutTM;
import mixac1.dangerrpg.item.RPGItemComponent.RPGStaffComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.util.RPGCommonHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
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
    public void registerAttributes(Item item, ItemAttributesMap map);

    public RPGItemComponent getItemComponent(Item item);

    public interface ILvlableItemMod extends ILvlableItem {}

    public interface ILvlableItemTool extends ILvlableItemMod
    {
        @Override
        public RPGToolComponent getItemComponent(Item item);

        public RPGToolMaterial getToolMaterial(Item item);
    }

    public interface ILvlableItemArmor extends ILvlableItem
    {
        @Override
        public RPGArmorComponent getItemComponent(Item item);

        public RPGArmorMaterial getArmorMaterial(Item item);
    }

    public interface ILvlableItemGun extends ILvlableItemTool
    {
        @Override
        public RPGGunComponent getItemComponent(Item item);
    }

    public interface ILvlableItemStaff extends ILvlableItemGun
    {
        @Override
        public RPGStaffComponent getItemComponent(Item item);
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
        public void registerAttributes(Item item, ItemAttributesMap map) {}

        @Override
        public RPGItemComponent getItemComponent(Item item)
        {
            return null;
        }
    };

    public static final ILvlableItem DEFAULT_ITEM_MOD = new ILvlableItemMod()
    {
        @Override
        public void registerAttributes(Item item, ItemAttributesMap map)
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
        public void registerAttributes(Item item, ItemAttributesMap map)
        {
            LvlableItem.registerParamsItemSword(item, map);
        }

        @Override
        public RPGToolComponent getItemComponent(Item item)
        {
            return RPGToolComponent.itemComponentHook(item, RPGItemComponent.SWORD);
        }

        @Override
        public RPGToolMaterial getToolMaterial(Item item)
        {
            return RPGToolMaterial.toolMaterialHook(((ItemSword) item).field_150933_b);
        }
    };

    public static final ILvlableItemTool DEFAULT_TOOL = new ILvlableItemTool()
    {
        @Override
        public void registerAttributes(Item item, ItemAttributesMap map)
        {
            LvlableItem.registerParamsItemTool(item, map);
        }

        @Override
        public RPGToolComponent getItemComponent(Item item)
        {
            if (item instanceof ItemAxe) {
                return RPGToolComponent.itemComponentHook(item, RPGItemComponent.AXE);
            }
            else if (item instanceof ItemHoe) {
                return RPGToolComponent.itemComponentHook(item, RPGItemComponent.HOE);
            }
            else if (item instanceof ItemSpade) {
                return RPGToolComponent.itemComponentHook(item, RPGItemComponent.SHOVEL);
            }
            else if (item instanceof ItemPickaxe) {
                return RPGToolComponent.itemComponentHook(item, RPGItemComponent.PICKAXE);
            }
            return RPGToolComponent.itemComponentHook(item, RPGItemComponent.AXE);
        }

        @Override
        public RPGToolMaterial getToolMaterial(Item item)
        {
            if (item instanceof ItemTool) {
                return RPGToolMaterial.toolMaterialHook(((ItemTool) item).func_150913_i());
            }
            else if (item instanceof ItemHoe) {
                return RPGToolMaterial.toolMaterialHook(((ItemHoe) item).theToolMaterial);
            }
            return null;
        }
    };

    public static final ILvlableItemArmor DEFAULT_ARMOR = new ILvlableItemArmor()
    {
        @Override
        public void registerAttributes(Item item, ItemAttributesMap map)
        {
            LvlableItem.registerParamsItemArmor(item, map);
        }

        @Override
        public RPGArmorComponent getItemComponent(Item item)
        {
            return RPGToolComponent.itemComponentHook(item, RPGArmorComponent.ARMOR);
        }

        @Override
        public RPGArmorMaterial getArmorMaterial(Item item)
        {
            return RPGArmorMaterial.armorMaterialHook(((ItemArmor) item).getArmorMaterial());
        }
    };

    public static final ILvlableItemBow DEFAULT_BOW = new ILvlableItemBow()
    {
        @Override
        public void registerAttributes(Item item, ItemAttributesMap map)
        {
            LvlableItem.registerParamsItemBow(item, map);
        }

        @Override
        public RPGBowComponent getItemComponent(Item item)
        {
            return RPGToolComponent.itemComponentHook(item, RPGItemComponent.BOW);
        }

        @Override
        public RPGToolMaterial getToolMaterial(Item item)
        {
            return null;
        }

        @Override
        public void onStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useDuration)
        {
            boolean flag = player.capabilities.isCreativeMode
                     || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

            if (flag || player.inventory.hasItem(Items.arrow)) {
                float power = RPGCommonHelper.getUsePower(player, stack, useDuration, 20f, 0.3f);
                if (power < 0) {
                    return;
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
                                        1.0F / (RPGOther.rand.nextFloat() * 0.4F + 1.2F) + power * 0.5F);

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
