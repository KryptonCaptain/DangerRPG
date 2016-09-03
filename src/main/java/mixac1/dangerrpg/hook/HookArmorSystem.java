package mixac1.dangerrpg.hook;

import java.util.ArrayList;
import java.util.Arrays;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import mixac1.dangerrpg.init.RPGOther.RPGDamageSource;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

public class HookArmorSystem
{
    public static final float MAX_PHISICAL_ARMOR = 40;

    @SideOnly(Side.CLIENT)
    public static Minecraft mc = Minecraft.getMinecraft();

    public static float convertPhisicArmor(float armor)
    {
        return Utils.alignment(armor * 100 / MAX_PHISICAL_ARMOR, 0, 100);
    }

    private static float getPhisicArmor(ItemStack stack)
    {
        if (stack != null && stack.getItem() instanceof ItemArmor) {
            if (ItemAttributes.PHISIC_ARMOR.hasIt(stack)) {
                return ItemAttributes.PHISIC_ARMOR.get(stack);
            }
            else {
                return convertPhisicArmor(((ItemArmor) stack.getItem()).damageReduceAmount);
            }
        }
        return 0;
    }

    private static float getMagicArmor(ItemStack stack)
    {
        if (stack != null
                && stack.getItem() instanceof ItemArmor
                && ItemAttributes.MAGIC_ARMOR.hasIt(stack)) {
            return ItemAttributes.MAGIC_ARMOR.get(stack);
        }
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public static float getTotalArmor(DamageSource source)
    {
        float value = 0;
        for (ArmorProperties prop : getArrayArmorProperties(mc.thePlayer, mc.thePlayer.inventory.armorInventory, source, 5)) {
            value += prop.AbsorbRatio;
        }
        return value * 100;
    }

    @SideOnly(Side.CLIENT)
    public static float getTotalPhisicArmor()
    {
        return getTotalArmor(RPGDamageSource.phisic);
    }

    @SideOnly(Side.CLIENT)
    public static float getTotalMagicArmor()
    {
        return getTotalArmor(RPGDamageSource.magic);
    }

    @SideOnly(Side.CLIENT)
    public static float getArmor(ItemStack stack, DamageSource source)
    {
        return (float) (getArmorProperties(mc.thePlayer, stack, 0, source, 5).AbsorbRatio * 100);
    }

    private static ArmorProperties getArmorProperties(EntityLivingBase entity, ItemStack stack, int slot, DamageSource source, double damage)
    {
        ArmorProperties prop = null;

        if (stack.getItem() instanceof ISpecialArmor) {
            ISpecialArmor armor = (ISpecialArmor)stack.getItem();
            prop = armor.getProperties(entity, stack, source, damage, slot).copy();
        }
        else if (LvlableItem.isLvlable(stack)) {
            if (stack.getItem() instanceof ItemArmor && !source.isUnblockable()) {
                float armorValue;
                if (source.isMagicDamage()) {
                    armorValue = getMagicArmor(stack);
                }
                else {
                    armorValue = getPhisicArmor(stack);
                }
                prop = new ArmorProperties(0, armorValue / 100, ((ItemArmor) stack.getItem()).getMaxDamage() + 1 - stack.getItemDamage());
            }
        }
        else {
            if (stack.getItem() instanceof ItemArmor && !source.isUnblockable()) {
                float armorValue;
                if (source.isMagicDamage()) {
                    armorValue = 0;
                }
                else {
                    armorValue = ((ItemArmor) stack.getItem()).damageReduceAmount / MAX_PHISICAL_ARMOR;
                }
                prop = new ArmorProperties(0, armorValue, ((ItemArmor) stack.getItem()).getMaxDamage() + 1 - stack.getItemDamage());
            }
        }

        return prop;
    }

    private static ArrayList<ArmorProperties> getArrayArmorProperties(EntityLivingBase entity, ItemStack[] inventory, DamageSource source, double damage)
    {
        ArrayList<ArmorProperties> dmgVals = new ArrayList<ArmorProperties>();

        for (int x = 0; x < inventory.length; x++) {
            ItemStack stack = inventory[x];
            if (stack == null) {
                continue;
            }

            ArmorProperties prop = getArmorProperties(entity, inventory[x], x, source, damage);

            if (prop != null) {
                prop.Slot = x;
                dmgVals.add(prop);
            }
        }

        return dmgVals;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static float ApplyArmor(ArmorProperties nullObj, EntityLivingBase entity, ItemStack[] inventory, DamageSource source, double damage)
    {
        ArrayList<ArmorProperties> dmgVals = getArrayArmorProperties(entity, inventory, source, damage);
        damage *= MAX_PHISICAL_ARMOR;

        if (dmgVals.size() > 0) {
            ArmorProperties[] props = dmgVals.toArray(new ArmorProperties[dmgVals.size()]);
            standardizeList(props, damage);
            int level = props[0].Priority;
            double ratio = 0;
            for (ArmorProperties prop : props) {
                if (level != prop.Priority) {
                    damage -= (damage * ratio);
                    ratio = 0;
                    level = prop.Priority;
                }
                ratio += prop.AbsorbRatio;

                double absorb = damage * prop.AbsorbRatio;
                if (absorb > 0) {
                    ItemStack stack = inventory[prop.Slot];
                    int itemDamage = (int)(absorb / MAX_PHISICAL_ARMOR < 1 ? 1 : absorb / MAX_PHISICAL_ARMOR);
                    if (stack.getItem() instanceof ISpecialArmor) {
                        ((ISpecialArmor)stack.getItem()).damageArmor(entity, stack, source, itemDamage, prop.Slot);
                    }
                    else {
                        stack.damageItem(itemDamage, entity);
                    }
                    if (stack.stackSize <= 0) {
                        inventory[prop.Slot] = null;
                    }
                }
            }
            damage -= (damage * ratio);
        }
        return (float)(damage / MAX_PHISICAL_ARMOR);
    }

    private static void standardizeList(ArmorProperties[] armor, double damage)
    {
        Arrays.sort(armor);

        int     start     = 0;
        double  total     = 0;
        int     priority  = armor[0].Priority;
        int     pStart    = 0;
        boolean pChange   = false;
        boolean pFinished = false;

        for (int x = 0; x < armor.length; x++) {
            total += armor[x].AbsorbRatio;
            if (x == armor.length - 1 || armor[x].Priority != priority) {
                if (armor[x].Priority != priority) {
                    total -= armor[x].AbsorbRatio;
                    x--;
                    pChange = true;
                }
                if (total > 1) {
                    for (int y = start; y <= x; y++) {
                        double newRatio = armor[y].AbsorbRatio / total;
                        if (newRatio * damage > armor[y].AbsorbMax) {
                            armor[y].AbsorbRatio = armor[y].AbsorbMax / damage;
                            total = 0;
                            for (int z = pStart; z <= y; z++) {
                                total += armor[z].AbsorbRatio;
                            }
                            start = y + 1;
                            x = y;
                            break;
                        }
                        else {
                            armor[y].AbsorbRatio = newRatio;
                            pFinished = true;
                        }
                    }
                    if (pChange && pFinished) {
                        damage -= (damage * total);
                        total = 0;
                        start = x + 1;
                        priority = armor[start].Priority;
                        pStart = start;
                        pChange = false;
                        pFinished = false;
                        if (damage <= 0) {
                            for (int y = x + 1; y < armor.length; y++) {
                                armor[y].AbsorbRatio = 0;
                            }
                            break;
                        }
                    }
                }
                else {
                    for (int y = start; y <= x; y++) {
                        total -= armor[y].AbsorbRatio;
                        if (damage * armor[y].AbsorbRatio > armor[y].AbsorbMax) {
                            armor[y].AbsorbRatio = armor[y].AbsorbMax / damage;
                        }
                        total += armor[y].AbsorbRatio;
                    }
                    damage -= (damage * total);
                    total = 0;
                    if (x != armor.length - 1) {
                        start = x + 1;
                        priority = armor[start].Priority;
                        pStart = start;
                        pChange = false;
                        if (damage <= 0) {
                            for (int y = x + 1; y < armor.length; y++) {
                                armor[y].AbsorbRatio = 0;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
