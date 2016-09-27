package mixac1.dangerrpg.capability;

import java.util.ArrayList;
import java.util.Set;

import mixac1.dangerrpg.api.event.RegIAEvent;
import mixac1.dangerrpg.api.event.UpEquipmentEvent;
import mixac1.dangerrpg.api.item.IRPGItem;
import mixac1.dangerrpg.api.item.IRPGItem.IRPGItemArmor;
import mixac1.dangerrpg.api.item.IRPGItem.IRPGItemBow;
import mixac1.dangerrpg.api.item.IRPGItem.IRPGItemGun;
import mixac1.dangerrpg.api.item.IRPGItem.IRPGItemMod;
import mixac1.dangerrpg.api.item.IRPGItem.IRPGItemStaff;
import mixac1.dangerrpg.api.item.IRPGItem.IRPGItemTool;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import mixac1.dangerrpg.hook.HookArmorSystem;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGConfig.ItemConfig;
import mixac1.dangerrpg.item.RPGArmorMaterial;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.item.RPGItemComponent.IWithoutToolMaterial;
import mixac1.dangerrpg.item.RPGItemComponent.RPGArmorComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGBowComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGGunComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGStaffComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.util.IMultiplier;
import mixac1.dangerrpg.util.IMultiplier.IMulConfigurable;
import mixac1.dangerrpg.util.IMultiplier.MultiplierMul;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

public abstract class RPGableItem
{
    public static final MultiplierMul EXP_MUL = new MultiplierMul(ItemConfig.d.expMul);

    public static final IMulConfigurable DUR_MUL = new IMulConfigurable()
    {
        @Override
        public Float down(Float value, Object... objs)
        {
            return value;
        }

        @Override
        public Float up(Float value, Object... objs)
        {
            return value > 500 ? value * 1.1f : value + 50;
        }

        @Override
        public String toString()
        {
            return MulType.HARD.toString();
        }
    };

    public static boolean registerRPGItem(Item item)
    {
        if (item != null && !(item instanceof ItemBlock) && item.unlocalizedName != null) {
            if (RPGCapability.rpgItemRegistr.containsKey(item)) {
                return true;
            }

            IRPGItem iRPG = item instanceof ItemSword ? IRPGItem.DEFAULT_SWORD :
                            item instanceof ItemTool  ? IRPGItem.DEFAULT_TOOL  :
                            item instanceof ItemHoe   ? IRPGItem.DEFAULT_TOOL  :
                            item instanceof ItemArmor ? IRPGItem.DEFAULT_ARMOR :
                            item instanceof ItemBow   ? IRPGItem.DEFAULT_BOW   :
                            null;

            if (iRPG != null) {
                RPGCapability.rpgItemRegistr.put(item, new RPGItemData(iRPG, false));
                return true;
            }
        }
        return false;
    }

    public static void registerParamsDefault(Item item, RPGItemData map)
    {
        map.addDynamicItemAttribute(ItemAttributes.MAX_EXP, ItemConfig.d.startMaxExp, EXP_MUL);
        MinecraftForge.EVENT_BUS.post(new RegIAEvent.DefaultIAEvent(item, map));
    }

    public static void registerParamsItemMod(Item item, RPGItemData map)
    {
        float durab, ench;
        RPGItemComponent comp;
        if (item instanceof IRPGItemMod &&
           (comp = ((IRPGItemMod) item).getItemComponent(item)) instanceof IWithoutToolMaterial) {
            ench  = ((IWithoutToolMaterial) comp).getEnchantability();
            durab = ((IWithoutToolMaterial) comp).getMaxDurability();
        }
        else {
            ench  = item.getItemEnchantability();
            durab = item.isDamageable() ? item.getMaxDamage() : -1;
        }

        map.addDynamicItemAttribute(ItemAttributes.ENCHANTABILITY, ench, IMultiplier.ADD_1);
        if (durab != -1) {
            map.addDynamicItemAttribute(ItemAttributes.MAX_DURABILITY, durab, DUR_MUL);
        }

        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemModIAEvent(item, map));
    }

    public static void registerParamsItemSword(Item item, RPGItemData map)
    {
        registerParamsItemMod(item, map);
        IRPGItemTool iRPG = (IRPGItemTool) (item instanceof IRPGItemTool ? item : IRPGItem.DEFAULT_SWORD);
        RPGToolComponent comp = iRPG.getItemComponent(item);
        RPGToolMaterial mat = iRPG.getToolMaterial(item);

        map.addStaticItemAttribute(ItemAttributes.MELEE_DAMAGE, comp.meleeDamage + mat.material.getDamageVsEntity() * comp.strMul * 2);
        map.addStaticItemAttribute(ItemAttributes.MELEE_SPEED,  comp.meleeSpeed);
        map.addStaticItemAttribute(ItemAttributes.STR_MUL,      comp.strMul);
        map.addStaticItemAttribute(ItemAttributes.AGI_MUL,      comp.agiMul);
        map.addStaticItemAttribute(ItemAttributes.INT_MUL,      comp.intMul);
        map.addStaticItemAttribute(ItemAttributes.KNOCKBACK,    comp.knBack);
        map.addStaticItemAttribute(ItemAttributes.KNBACK_MUL,   comp.knbMul);
        map.addStaticItemAttribute(ItemAttributes.REACH,        comp.reach);

        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemSwordIAEvent(item, map));
    }

    public static void registerParamsItemTool(Item item, RPGItemData map)
    {
        registerParamsItemMod(item, map);
        IRPGItemTool iRPG = (IRPGItemTool) (item instanceof IRPGItemTool ? item : IRPGItem.DEFAULT_TOOL);
        RPGToolComponent comp = iRPG.getItemComponent(item);
        RPGToolMaterial mat = iRPG.getToolMaterial(item);

        map.addStaticItemAttribute(ItemAttributes.MELEE_DAMAGE, comp.meleeDamage + mat.material.getDamageVsEntity() * comp.strMul * 2);
        map.addStaticItemAttribute(ItemAttributes.MELEE_SPEED,  comp.meleeSpeed);
        map.addStaticItemAttribute(ItemAttributes.STR_MUL,      comp.strMul);
        map.addStaticItemAttribute(ItemAttributes.AGI_MUL,      comp.agiMul);
        map.addStaticItemAttribute(ItemAttributes.INT_MUL,      comp.intMul);
        map.addStaticItemAttribute(ItemAttributes.KNOCKBACK,    comp.knBack);
        map.addStaticItemAttribute(ItemAttributes.KNBACK_MUL,   comp.knbMul);
        map.addStaticItemAttribute(ItemAttributes.REACH,        comp.reach);

        map.addDynamicItemAttribute(ItemAttributes.EFFICIENCY,  mat.material.getEfficiencyOnProperMaterial(), IMultiplier.ADD_1);

        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemToolIAEvent(item, map));
    }

    public static void registerParamsItemArmor(Item item, RPGItemData map)
    {
        registerParamsItemMod(item, map);
        IRPGItemArmor iRPG = (IRPGItemArmor) (item instanceof IRPGItemArmor ? item : IRPGItem.DEFAULT_ARMOR);
        RPGArmorMaterial mat = iRPG.getArmorMaterial(item);
        RPGArmorComponent com = iRPG.getItemComponent(item);

        float armor = mat.material.getDamageReductionAmount(((ItemArmor) item).armorType) * com.phisicalResMul;
        map.addStaticItemAttribute(ItemAttributes.PHISIC_ARMOR, HookArmorSystem.convertPhisicArmor(armor));
        map.addStaticItemAttribute(ItemAttributes.MAGIC_ARMOR,  mat.magicRes * com.magicResMul);

        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemArmorIAEvent(item, map));
    }

    public static void registerParamsItemBow(Item item, RPGItemData map)
    {
        registerParamsItemMod(item, map);
        IRPGItemBow iRPG = (IRPGItemBow) (item instanceof IRPGItemBow ? item : IRPGItem.DEFAULT_BOW);
        RPGBowComponent comp = iRPG.getItemComponent(item);

        map.addStaticItemAttribute(ItemAttributes.MELEE_DAMAGE,   comp.meleeDamage);
        map.addStaticItemAttribute(ItemAttributes.MELEE_SPEED,    comp.meleeSpeed);
        map.addStaticItemAttribute(ItemAttributes.STR_MUL,        comp.strMul);
        map.addStaticItemAttribute(ItemAttributes.AGI_MUL,        comp.agiMul);
        map.addStaticItemAttribute(ItemAttributes.INT_MUL,        comp.intMul);
        map.addStaticItemAttribute(ItemAttributes.KNOCKBACK,      comp.knBack);
        map.addStaticItemAttribute(ItemAttributes.KNBACK_MUL,     comp.knbMul);

        map.addStaticItemAttribute(ItemAttributes.SHOT_DAMAGE,    comp.shotDamage);
        map.addStaticItemAttribute(ItemAttributes.SHOT_POWER,     comp.shotPower);
        map.addStaticItemAttribute(ItemAttributes.MIN_CUST_TIME,  comp.shotMinCastTime);
        map.addStaticItemAttribute(ItemAttributes.SHOT_SPEED,     comp.shotSpeed);

        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemBowIAEvent(item, map));
    }

    public static void registerParamsItemGun(Item item, RPGItemData map)
    {
        registerParamsItemMod(item, map);
        IRPGItemGun iRPG = (IRPGItemGun) item;
        RPGGunComponent comp = iRPG.getItemComponent(item);
        RPGToolMaterial mat = iRPG.getToolMaterial(item);

        map.addStaticItemAttribute(ItemAttributes.MELEE_DAMAGE, comp.meleeDamage + mat.material.getDamageVsEntity() * comp.strMul * 2);
        map.addStaticItemAttribute(ItemAttributes.MELEE_SPEED,  comp.meleeSpeed);
        map.addStaticItemAttribute(ItemAttributes.STR_MUL,      comp.strMul);
        map.addStaticItemAttribute(ItemAttributes.AGI_MUL,      comp.agiMul);
        map.addStaticItemAttribute(ItemAttributes.INT_MUL,      comp.intMul);
        map.addStaticItemAttribute(ItemAttributes.KNOCKBACK,    comp.knBack);
        map.addStaticItemAttribute(ItemAttributes.KNBACK_MUL,   comp.knbMul);
        map.addStaticItemAttribute(ItemAttributes.REACH,        comp.reach);

        map.addStaticItemAttribute(ItemAttributes.SHOT_DAMAGE,    comp.shotDamage + mat.material.getDamageVsEntity() * comp.intMul * 2);
        map.addStaticItemAttribute(ItemAttributes.MIN_CUST_TIME,  comp.shotMinCastTime);
        map.addStaticItemAttribute(ItemAttributes.SHOT_SPEED,     comp.shotSpeed);

        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemGunIAEvent(item, map));
    }

    public static void registerParamsItemStaff(Item item, RPGItemData map)
    {
        registerParamsItemGun(item, map);
        IRPGItemStaff iRPG = (IRPGItemStaff) item;
        RPGStaffComponent comp = iRPG.getItemComponent(item);
        RPGToolMaterial mat = iRPG.getToolMaterial(item);

        map.addStaticItemAttribute(ItemAttributes.MANA_COST, comp.needMana);

        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemStaffIAEvent(item, map));
    }

    public static boolean isRPGable(ItemStack stack)
    {
        return RPGCapability.rpgItemRegistr.isActivated(stack.getItem());
    }

    public static Set<ItemAttribute> getAttributeValues(ItemStack stack)
    {
        return RPGCapability.rpgItemRegistr.get(stack.getItem()).attributes.keySet();
    }

    public static void initRPGItem(ItemStack stack)
    {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        initParams(stack);
    }

    public static void reinitRPGItem(ItemStack stack)
    {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        reinitParams(stack);
    }

    public static void initParams(ItemStack stack)
    {
        ItemAttributes.LEVEL.set(stack, 1);
        ItemAttributes.CURR_EXP.set(stack, 0);
        ItemAttributes.MAX_EXP.init(stack);

        Set<ItemAttribute> itemAttributes = getAttributeValues(stack);
        for (ItemAttribute it : itemAttributes) {
            it.init(stack);
        }
    }

    public static void reinitParams(ItemStack stack)
    {
        if (!ItemAttributes.LEVEL.hasIt(stack)) {
            ItemAttributes.LEVEL.set(stack, 1);
        }
        if (!ItemAttributes.CURR_EXP.hasIt(stack)) {
            ItemAttributes.CURR_EXP.set(stack, 0);
        }
        if (!ItemAttributes.MAX_EXP.hasIt(stack)) {
            ItemAttributes.MAX_EXP.init(stack);
        }

        Set<ItemAttribute> itemAttributes = getAttributeValues(stack);
        for (ItemAttribute it : itemAttributes) {
            if (!it.hasIt(stack)) {
                it.init(stack);
            }
        }
    }

    public static void instantLvlUp(ItemStack stack)
    {
        if (isRPGable(stack)) {
            ItemAttributes.LEVEL.add(stack, 1);
            ItemAttributes.MAX_EXP.lvlUp(stack);
            ItemAttributes.CURR_EXP.set(stack, 0F);

            Set<ItemAttribute> itemAttributes = getAttributeValues(stack);
            for (ItemAttribute iterator : itemAttributes) {
                iterator.lvlUp(stack);
            }
        }
    }

    public static void addExp(ItemStack stack, float value)
    {
        if (isRPGable(stack)) {
            if (value <= 0) {
                return;
            }
            int level = (int) ItemAttributes.LEVEL.get(stack);

            if (level < ItemConfig.d.maxLevel) {
                float currEXP = ItemAttributes.CURR_EXP.get(stack);
                float maxEXP  = ItemAttributes.MAX_EXP.get(stack);

                currEXP += value;

                while (currEXP >= maxEXP) {
                    instantLvlUp(stack);
                    if (++level < ItemConfig.d.maxLevel) {
                        currEXP -= maxEXP;
                    } else {
                        currEXP = maxEXP;
                        break;
                    }
                }
                ItemAttributes.CURR_EXP.set(stack, currEXP);
            }
        }
    }

    public static void upEquipment(EntityPlayer player, ItemStack stack, float points, boolean onlyCurr)
    {
        UpEquipmentEvent e = new UpEquipmentEvent(player, stack, points);
        MinecraftForge.EVENT_BUS.post(e);

        if (e.points > 0) {
            ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();

            if (e.needUp[0]) {
                if (stack == null) {
                    stack = player.getCurrentEquippedItem();
                }

                if (stack != null && isRPGable(stack)) {
                    stacks.add(stack);
                }
            }

            if (!onlyCurr) {
                ItemStack[] armors = player.inventory.armorInventory;
                for (int i = 0; i < armors.length; ++i) {
                    if (e.needUp[i + 1] && armors[i] != null && isRPGable(armors[i])) {
                        stacks.add(armors[i]);
                    }
                }
            }

            e.points = e.points / stacks.size();
            for (ItemStack tmp : stacks) {
                addExp(tmp, e.points);
            }
        }
    }
}
