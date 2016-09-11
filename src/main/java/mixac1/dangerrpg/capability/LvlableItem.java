package mixac1.dangerrpg.capability;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import mixac1.dangerrpg.api.event.RegIAEvent;
import mixac1.dangerrpg.api.event.UpEquipmentEvent;
import mixac1.dangerrpg.api.item.IADynamic;
import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.api.item.ILvlableItem;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemArmor;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemBow;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemGun;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemMod;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemStaff;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemTool;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import mixac1.dangerrpg.hook.HookArmorSystem;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGConfig;
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
import net.minecraft.entity.EntityLivingBase;
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

public abstract class LvlableItem
{
    public static final String IS_LVLABLE = "rpg_lvlable";

    public static final IMultiplier<Float> EXP_MUL = new IMultiplier<Float>()
    {
        @Override
        public Float up(Float value, Object... meta)
        {
            return value * RPGConfig.itemExpMul;
        }
    };

    public static final IMultiplier<Float> DUR_MUL = new IMultiplier<Float>()
    {
        @Override
        public Float up(Float value, Object... meta)
        {
            ItemStack stack = (ItemStack) meta[0];
            int lvl = (int) ItemAttributes.LEVEL.get(stack);
            if (lvl >= 15) {
                stack.getTagCompound().setBoolean("Unbreakable", true);
                return value;
            }
            else {
                return value + 25 * (lvl - 1);
            }
        }
    };

    public static boolean registerLvlableItem(Item item)
    {
        if (item != null && !(item instanceof ItemBlock)) {
            if (RPGCapability.lvlItemRegistr.data.containsKey(item)) {
                return true;
            }

            ILvlableItem iLvl = item instanceof ItemSword ? ILvlableItem.DEFAULT_SWORD :
                                item instanceof ItemTool  ? ILvlableItem.DEFAULT_TOOL  :
                                item instanceof ItemHoe   ? ILvlableItem.DEFAULT_TOOL  :
                                item instanceof ItemArmor ? ILvlableItem.DEFAULT_ARMOR :
                                item instanceof ItemBow   ? ILvlableItem.DEFAULT_BOW   :
                                null;

            if (iLvl != null) {
                RPGCapability.lvlItemRegistr.data.put(item, new ItemAttributesMap(iLvl, false));
                return true;
            }
        }
        return false;
    }

    public static void registerParamsDefault(Item item, ItemAttributesMap map)
    {
        map.addDynamicItemAttribute(ItemAttributes.MAX_EXP, RPGConfig.itemStartMaxExp, EXP_MUL);
        MinecraftForge.EVENT_BUS.post(new RegIAEvent.DefaultIAEvent(item, map));
    }

    public static void registerParamsItemMod(Item item, ItemAttributesMap map)
    {
        float durab, ench;
        RPGItemComponent comp;
        if (item instanceof ILvlableItemMod &&
           (comp = ((ILvlableItemMod) item).getItemComponent(item)) instanceof IWithoutToolMaterial) {
            ench  = ((IWithoutToolMaterial) comp).getEnchantability();
            durab = ((IWithoutToolMaterial) comp).getMaxDurability();
        }
        else {
            ench  = item.getItemEnchantability();
            durab = item.getMaxDamage();
        }

        map.addDynamicItemAttribute(ItemAttributes.ENCHANTABILITY, ench, IMultiplier.ADD_1);
        map.addDynamicItemAttribute(ItemAttributes.MAX_DURABILITY, durab, DUR_MUL);
        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemModIAEvent(item, map));
    }

    public static void registerParamsItemSword(Item item, ItemAttributesMap map)
    {
        registerParamsItemMod(item, map);
        ILvlableItemTool iLvl = (ILvlableItemTool) (item instanceof ILvlableItemTool ? item : ILvlableItem.DEFAULT_SWORD);
        RPGToolComponent comp = iLvl.getItemComponent(item);
        RPGToolMaterial mat = iLvl.getToolMaterial(item);

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

    public static void registerParamsItemTool(Item item, ItemAttributesMap map)
    {
        registerParamsItemMod(item, map);
        ILvlableItemTool iLvl = (ILvlableItemTool) (item instanceof ILvlableItemTool ? item : ILvlableItem.DEFAULT_TOOL);
        RPGToolComponent comp = iLvl.getItemComponent(item);
        RPGToolMaterial mat = iLvl.getToolMaterial(item);

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

    public static void registerParamsItemArmor(Item item, ItemAttributesMap map)
    {
        registerParamsItemMod(item, map);
        ILvlableItemArmor iLvl = (ILvlableItemArmor) (item instanceof ILvlableItemArmor ? item : ILvlableItem.DEFAULT_ARMOR);
        RPGArmorMaterial mat = iLvl.getArmorMaterial(item);
        RPGArmorComponent com = iLvl.getItemComponent(item);

        float armor = mat.material.getDamageReductionAmount(((ItemArmor) item).armorType) * com.phisicalResMul;
        map.addStaticItemAttribute(ItemAttributes.PHISIC_ARMOR, HookArmorSystem.convertPhisicArmor(armor));
        map.addStaticItemAttribute(ItemAttributes.MAGIC_ARMOR,  mat.magicRes * com.magicResMul);

        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemArmorIAEvent(item, map));
    }

    public static void registerParamsItemBow(Item item, ItemAttributesMap map)
    {
        registerParamsItemMod(item, map);
        ILvlableItemBow iLvl = (ILvlableItemBow) (item instanceof ILvlableItemBow ? item : ILvlableItem.DEFAULT_BOW);
        RPGBowComponent comp = iLvl.getItemComponent(item);

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

    public static void registerParamsItemGun(Item item, ItemAttributesMap map)
    {
        registerParamsItemMod(item, map);
        ILvlableItemGun iLvl = (ILvlableItemGun) item;
        RPGGunComponent comp = iLvl.getItemComponent(item);
        RPGToolMaterial mat = iLvl.getToolMaterial(item);

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

    public static void registerParamsItemStaff(Item item, ItemAttributesMap map)
    {
        registerParamsItemGun(item, map);
        ILvlableItemStaff iLvl = (ILvlableItemStaff) item;
        RPGStaffComponent comp = iLvl.getItemComponent(item);
        RPGToolMaterial mat = iLvl.getToolMaterial(item);

        map.addStaticItemAttribute(ItemAttributes.MANA_COST, comp.needMana);

        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemStaffIAEvent(item, map));
    }

    public static boolean isLvlable(ItemStack stack)
    {
        return RPGCapability.lvlItemRegistr.registr.contains(stack.getItem());
    }

    public static Set<ItemAttribute> getAttributeValues(ItemStack stack)
    {
        return RPGCapability.lvlItemRegistr.data.get(stack.getItem()).map.keySet();
    }

    public static void createLvlableItem(ItemStack stack)
    {
        if (isLvlable(stack)) {
            if (stack.stackTagCompound == null) {
                stack.stackTagCompound = new NBTTagCompound();
            }
            setStartParams(stack);
        }
    }

    public static void setStartParams(ItemStack stack)
    {
        if (isLvlable(stack)) {
            ItemAttributes.LEVEL.set(stack, 1);
            ItemAttributes.CURR_EXP.set(stack, 0);
            ItemAttributes.MAX_EXP.init(stack);

            Set<ItemAttribute> itemAttributes = getAttributeValues(stack);
            for (ItemAttribute iterator : itemAttributes) {
                iterator.init(stack);
            }
        }
    }

    public static void instantLvlUp(ItemStack stack)
    {
        if (isLvlable(stack)) {
            ItemAttributes.LEVEL.add(stack, 1);
            ItemAttributes.MAX_EXP.lvlUp(stack);
            ItemAttributes.CURR_EXP.set(stack, 0F);

            Set<ItemAttribute> itemAttributes = getAttributeValues(stack);
            for (ItemAttribute iterator : itemAttributes) {
                iterator.lvlUp(stack);
            }
        }
    }

    public static void addExp(ItemStack stack, int value)
    {
        if (isLvlable(stack)) {
            if (value <= 0) {
                return;
            }
            int level = (int) ItemAttributes.LEVEL.get(stack);

            if (level < RPGConfig.itemMaxLevel) {
                long currEXP = (long) ItemAttributes.CURR_EXP.get(stack);
                int maxEXP  = (int) ItemAttributes.MAX_EXP.get(stack);

                currEXP += value;

                while (currEXP >= maxEXP) {
                    instantLvlUp(stack);
                    if (++level < RPGConfig.itemMaxLevel) {
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

    public static void upEquipment(EntityPlayer player, EntityLivingBase target, ItemStack stack, float points)
    {
        UpEquipmentEvent e = new UpEquipmentEvent(player, target, stack, points);
        MinecraftForge.EVENT_BUS.post(e);

        if (e.points > 0) {
            ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();

            stack = player.getCurrentEquippedItem();
            if (e.needUp[0] && stack != null && isLvlable(stack)) {
                stacks.add(stack);
            }

            ItemStack[] armors = player.inventory.armorInventory;
            for (int i = 0; i < armors.length; ++i) {
                if (e.needUp[i + 1] && armors[i] != null && isLvlable(armors[i])) {
                    stacks.add(armors[i]);
                }
            }

            e.points = e.points / stacks.size();
            for (ItemStack tmp : stacks) {
                addExp(tmp, (int) e.points);
            }
        }
    }

    public static class ItemAttributesMap
    {
        public Map<ItemAttribute, ItemAttrParams> map = new LinkedHashMap<ItemAttribute, ItemAttrParams>();
        public ILvlableItem lvlComponent;
        public boolean isSupported;

        public ItemAttributesMap(ILvlableItem lvlComponent, boolean isSupported)
        {
            this.lvlComponent = lvlComponent;
            this.isSupported = isSupported;
        }

        public void addStaticItemAttribute(IAStatic attr, float value)
        {
            map.put(attr, new ItemAttrParams(value, null));
        }

        public void addDynamicItemAttribute(IADynamic attr, float value, IMultiplier mul)
        {
            map.put(attr, new ItemAttrParams(value, mul));
        }

        public static class ItemAttrParams
        {
            public float value;
            public IMultiplier<Float> mul;

            public ItemAttrParams(float value, IMultiplier<Float> mul)
            {
                this.value = value;
                this.mul = mul;
            }

            public float up(float value, ItemStack stack)
            {
                return mul.up(value, stack);
            }
        }
    }
}
