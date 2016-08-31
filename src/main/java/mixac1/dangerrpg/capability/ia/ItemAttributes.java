package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.IADynamic;
import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.hook.HookArmorSystem;
import mixac1.dangerrpg.init.RPGOther;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemAttributes
{
    public static final IADynamic LEVEL = new IALevel("lvl");
    public static final IADynamic CURR_EXP = new IACurrExp("curr_exp");
    public static final IADynamic MAX_EXP = new IADynamic("max_exp");

    public static final IAStatic MELEE_DAMAGE = new IADamage("melee_damage")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringPlus(get(stack, player));
        }
    };

    public static final IAStatic SHOT_DAMAGE = new IADamage("shot_damage")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            float value = get(stack, player);
            float power;
            if (ItemAttributes.SHOT_POWER.hasIt(stack) && (power = ItemAttributes.SHOT_POWER.get(stack, player)) != 1F) {
                if (power > 1) {
                    return String.format("%.2f - %.2f", value, value * power);
                }
                else {
                    return String.format("%.2f - %.2f", value * power, value);
                }
            }
            else {
                return getStringPlus(get(stack, player));
            }
        }
    };

    public static final IAStatic SHOT_POWER = new IAStatic("shot_power");

    public static final IAStatic MELEE_SPEED = new IASpeed("melee_speed", 10F)
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringSpeed(get(stack, player), normalValue);
        }
    };

    public static final IAStatic SHOT_SPEED = new IASpeed("shot_speed", 20F)
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringSpeed(get(stack, player), normalValue);
        }
    };

    public static final IAStatic MIN_CUST_TIME = new IAStatic("min_cust_time")
    {
        @Override
        public boolean isVisibleInInfoBook(ItemStack stack)
        {
            return false;
        }
    };

    public static final IAStatic REACH = new IAStatic("reach")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringPlus(get(stack, player));
        }
    };

    public static final IAStatic KNOCKBACK = new IAKnockback("knockback")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringPlus(get(stack, player));
        }
    };

    public static final IAStatic MANA_COST = new IAStatic("mana_cost");

    public static final IAStatic PHISIC_ARMOR = new IAStatic("phisic_armor")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return String.format("+%d%c", (int) HookArmorSystem.getArmor(stack, RPGOther.phisic), '%');
        }
    };

    public static final IAStatic MAGIC_ARMOR = new IAStatic("magic_armor")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return String.format("+%d%c", (int) HookArmorSystem.getArmor(stack, RPGOther.magic), '%');
        }
    };

    /*********************************************************************************/

    public static final IAStatic STR_MUL = new IAStatic("str_mul")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringProcentage(get(stack, player));
        }
    };

    public static final IAStatic AGI_MUL = new IAStatic("agi_mul")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringProcentage(get(stack, player));
        }
    };

    public static final IAStatic INT_MUL = new IAStatic("int_mul")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringProcentage(get(stack, player));
        }
    };

    public static final IAStatic KNBACK_MUL = new IAStatic("knb_mul")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringProcentage(get(stack, player));
        }
    };

    /*********************************************************************************/

    public static final IADynamic ENCHANTABILITY = new IADynamic("ench")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringInteger(get(stack, player));
        }
    };

    public static final IAStatic DURABILITY = new IADurability("durab")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringInteger(get(stack, player));
        }
    };

    public static final IADynamic MAX_DURABILITY = new IADynamic("max_durab")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringInteger(get(stack, player));
        }
    };

    public static final IADynamic EFFICIENCY = new IAEfficiency("effic")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringInteger(get(stack, player));
        }
    };

    /*********************************************************************************/

    private static String getStringPlus(float value)
    {
        return String.format("+%.2f", value);
    }

    private static String getStringInteger(float value)
    {
        return String.format("%d", (int) value);
    }

    private static String getStringProcentage(float value)
    {
        return String.format("%d%c", (int) (value * 100), '%');
    }

    private static String getStringSpeed(float value, float normalValue)
    {
        value = - (value - normalValue);
        if (value > 0) {
            return String.format("+%.2f", value);
        }
        else if (value == 0) {
            return "+0.00";
        }
        return String.format("%.2f", value);
    }
}
