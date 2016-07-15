package mixac1.dangerrpg.capability;

import java.util.HashMap;
import java.util.Set;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.RPGApi;
import mixac1.dangerrpg.api.item.ILvlableItem;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemArmor;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemBow;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemMod;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemTool;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.itemattr.ItemAttributes;
import mixac1.dangerrpg.event.RegIAEvent;
import mixac1.dangerrpg.init.RPGConfig;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.item.RPGItemComponent.IWithoutToolMaterial;
import mixac1.dangerrpg.item.RPGItemComponent.RPGBowComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
import mixac1.dangerrpg.util.Multiplier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

public class LvlableItem
{
    public static final String IS_LVLABLE = "rpg_lvlable";

    public static HashMap<Item, HashMap<ItemAttribute, ItemAttrParams>> itemsAttrebutes = new HashMap<Item, HashMap<ItemAttribute, ItemAttrParams>>();

    public static final Multiplier EXP_MUL = new Multiplier()
    {
        @Override
        public float up(float value)
        {
            return value * RPGConfig.itemExpMul;
        }
    };

    public static final Multiplier DUR_MUL = new Multiplier()
    {
        @Override
        public float up(float value)
        {
            return value + 50;
        }
    };


    public static boolean isLvlable(ItemStack stack)
    {
        if (stack != null &&
            stack.stackTagCompound != null &&
            stack.stackTagCompound.hasKey(IS_LVLABLE)) {
            return true;
        }
        return false;
    }

    public static boolean registerLvlableItem(Item item)
    {
        if (item != null && !(item instanceof ItemBlock)) {
            HashMap<ItemAttribute, ItemAttrParams> map = new HashMap<ItemAttribute, ItemAttrParams>();

            if (item instanceof ILvlableItem) {
                registerParamsDefault(item, map);
                ((ILvlableItem) item).registerAttributes(item, map);
                itemsAttrebutes.put(item, map);
            }
            else if (RPGConfig.itemAllItemsLvlable || RPGConfig.itemSupportedLvlItems.contains(item.getUnlocalizedName())) {
                ILvlableItem iLvl = item instanceof ItemSword ? ILvlableItem.DEFAULT_SWORD :
                                    item instanceof ItemTool  ? ILvlableItem.DEFAULT_TOOL  :
                                    item instanceof ItemHoe   ? ILvlableItem.DEFAULT_TOOL  :
                                    item instanceof ItemArmor ? ILvlableItem.DEFAULT_ARMOR :
                                    item instanceof ItemBow   ? ILvlableItem.DEFAULT_BOW   :
                                    null;
                if (iLvl != null) {
                    registerParamsDefault(item, map);
                    iLvl.registerAttributes(item, map);
                    itemsAttrebutes.put(item, map);
                    DangerRPG.logger.info("Register lvlable item: ".concat(item.getUnlocalizedName()));

                    if (RPGConfig.itemAllItemsLvlable) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void registerParamsDefault(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
    {
        RPGApi.registerDynamicItemAttribute(map, ItemAttributes.MAX_EXP, RPGConfig.itemStartMaxExp, EXP_MUL);
        MinecraftForge.EVENT_BUS.post(new RegIAEvent.DefaultIAEvent(item, map));
    }

    public static void registerParamsItemMod(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
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

        RPGApi.registerDynamicItemAttribute(map, ItemAttributes.ENCHANTABILITY, ench, Multiplier.ADD_1);
        RPGApi.registerDynamicItemAttribute(map, ItemAttributes.MAX_DURABILITY, durab, DUR_MUL);
        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemModIAEvent(item, map));
    }

    public static void registerParamsItemSword(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
    {
        registerParamsItemMod(item, map);
        ILvlableItemTool iLvl = (ILvlableItemTool) (item instanceof ILvlableItemTool ? item : ILvlableItem.DEFAULT_SWORD);
        RPGToolComponent comp = iLvl.getItemComponent(item);
        Item.ToolMaterial mat = iLvl.getToolMaterial(item);

        RPGApi.registerStaticItemAttribute(map, ItemAttributes.MELEE_DAMAGE, comp.meleeDamage + mat.getDamageVsEntity() * comp.strMul * 2);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.MELEE_SPEED,  comp.meleeSpeed);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.MAGIC,        comp.magic);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.STR_MUL,      comp.strMul);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.AGI_MUL,      comp.agiMul);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.INT_MUL,      comp.intMul);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.KNOCKBACK,    comp.knBack);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.REACH,        comp.reach);

        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemSwordIAEvent(item, map));
    }

    public static void registerParamsItemTool(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
    {
        registerParamsItemMod(item, map);
        ILvlableItemTool iLvl = (ILvlableItemTool) (item instanceof ILvlableItemTool ? item : ILvlableItem.DEFAULT_TOOL);
        RPGToolComponent comp = iLvl.getItemComponent(item);
        Item.ToolMaterial mat = iLvl.getToolMaterial(item);

        RPGApi.registerStaticItemAttribute(map, ItemAttributes.MELEE_DAMAGE, comp.meleeDamage + mat.getDamageVsEntity() * comp.strMul * 2);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.MELEE_SPEED,  comp.meleeSpeed);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.MAGIC,        comp.magic);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.STR_MUL,      comp.strMul);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.AGI_MUL,      comp.agiMul);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.INT_MUL,      comp.intMul);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.KNOCKBACK,    comp.knBack);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.REACH,        comp.reach);
        RPGApi.registerDynamicItemAttribute(map, ItemAttributes.EFFICIENCY,  mat.getEfficiencyOnProperMaterial(), Multiplier.ADD_1);

        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemToolIAEvent(item, map));
    }

    public static void registerParamsItemArmor(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
    {
        registerParamsItemMod(item, map);
        ILvlableItemArmor iLvl = (ILvlableItemArmor) (item instanceof ILvlableItemArmor ? item : ILvlableItem.DEFAULT_ARMOR);
        ArmorMaterial mat = iLvl.getArmorMaterial(item);

        RPGApi.registerStaticItemAttribute(map,  ItemAttributes.PHISIC_ARMOR, mat.getDamageReductionAmount(((ItemArmor) item).armorType));
        RPGApi.registerDynamicItemAttribute(map, ItemAttributes.MAGIC_ARMOR,  RPGConfig.itemStartMagicArmor, Multiplier.ADD_1);

        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemArmorIAEvent(item, map));
    }

    public static void registerParamsItemBow(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
    {
        registerParamsItemMod(item, map);
        ILvlableItemBow iLvl = (ILvlableItemBow) (item instanceof ILvlableItemBow ? item : ILvlableItem.DEFAULT_BOW);
        RPGBowComponent comp = iLvl.getItemComponent(item);

        RPGApi.registerStaticItemAttribute(map, ItemAttributes.MELEE_DAMAGE, comp.meleeDamage);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.MELEE_SPEED,  comp.meleeSpeed);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.SHOT_DAMAGE,  comp.shotDamage);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.SHOT_POWER,   comp.shotPower);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.SHOT_SPEED,   comp.shotSpeed);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.MAGIC,        comp.magic);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.STR_MUL,      comp.strMul);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.AGI_MUL,      comp.agiMul);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.INT_MUL,      comp.intMul);
        RPGApi.registerStaticItemAttribute(map, ItemAttributes.KNOCKBACK,    comp.knBack);

        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemBowIAEvent(item, map));
    }

    public static Set<ItemAttribute> getAttributeValues(ItemStack stack)
    {
        return itemsAttrebutes.get(stack.getItem()).keySet();
    }

    public static void createLvlableItem(ItemStack stack)
    {
        if (itemsAttrebutes.containsKey(stack.getItem())) {
            if (stack.stackTagCompound == null) {
                stack.stackTagCompound = new NBTTagCompound();
            }
            stack.stackTagCompound.setBoolean(IS_LVLABLE, true);
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
}
