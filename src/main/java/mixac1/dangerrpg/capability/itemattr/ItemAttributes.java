package mixac1.dangerrpg.capability.itemattr;

import mixac1.dangerrpg.api.item.IADynamic;
import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.util.RPGCommonHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemAttributes
{
	public static final ItemAttribute LEVEL = new IALevel("lvl");
	public static final ItemAttribute CURR_EXP = new IACurrExp("curr_exp");	
	public static final ItemAttribute MAX_EXP = new IADynamic("max_exp");
	
	public static final ItemAttribute MELEE_DAMAGE = new IADamage("melee_damage")
	{
		@Override
		public String getDispayValue(ItemStack stack, EntityPlayer player)
		{
			return getStringPlus(get(stack, player));
		}
	};
	
	public static final ItemAttribute SHOT_DAMAGE = new IADamage("shot_damage")
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
    
    public static final ItemAttribute SHOT_POWER = new IAStatic("shot_power")
    {
        @Override
        public boolean isVisibleInInfoBook(ItemStack stack)
        {
            return false;
        }
    };
    
    
    
	public static final ItemAttribute MELEE_SPEED = new IASpeed("melee_speed", 10F)
	{
		@Override
		public String getDispayValue(ItemStack stack, EntityPlayer player)
		{
			return getStringSpeed(get(stack, player), normalValue);
		}
	};
	
	public static final ItemAttribute SHOT_SPEED = new IASpeed("shot_speed", 20F)
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringSpeed(get(stack, player), normalValue);
        }
    };
	
	public static final ItemAttribute MAGIC = new IAMagic("magic")
	{
		@Override
		public String getDispayValue(ItemStack stack, EntityPlayer player)
		{
			return getStringPlus(get(stack, player));
		}
	};
	
	public static final ItemAttribute REACH = new IAStatic("reach")
	{
		@Override
		public String getDispayValue(ItemStack stack, EntityPlayer player)
		{
			return getStringPlus(get(stack, player));
		}
	};
	
	public static final ItemAttribute KNOCKBACK = new IAKnockback("knockback")
	{
		@Override
		public String getDispayValue(ItemStack stack, EntityPlayer player)
		{
			return getStringPlus(get(stack, player));
		}
	};
	
	public static final ItemAttribute PHISIC_ARMOR = new IAStatic("phisic_armor")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return String.format("+%d%c", (int) RPGCommonHelper.calcPhisicResistance(get(stack, player)), '%');
        }
    };
    
    public static final ItemAttribute MAGIC_ARMOR = new IADynamic("magic_armor")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return String.format("+%d%c", (int) RPGCommonHelper.calcMagicResistance(get(stack, player)), '%');
        }
    };
	
	/*********************************************************************************/
	
	public static final ItemAttribute STR_MUL = new IAStatic("str_mul")
	{
		@Override
		public String getDispayValue(ItemStack stack, EntityPlayer player)
		{
			return getStringProcentage(get(stack, player));
		}
	};

	public static final ItemAttribute AGI_MUL = new IAStatic("agi_mul")
	{
		@Override
		public String getDispayValue(ItemStack stack, EntityPlayer player)
		{
			return getStringProcentage(get(stack, player));
		}
	};
	
	public static final ItemAttribute INT_MUL = new IAStatic("int_mul")
	{
		@Override
		public String getDispayValue(ItemStack stack, EntityPlayer player)
		{
			return getStringProcentage(get(stack, player));
		}
	};		
	
	/*********************************************************************************/
	
	public static final ItemAttribute ENCHANTABILITY = new IADynamic("ench")
	{
		@Override
		public String getDispayValue(ItemStack stack, EntityPlayer player)
		{
			return getStringInteger(get(stack, player));
		}
	};
	
	public static final ItemAttribute DURABILITY = new IADurability("durab")
	{
		@Override
		public String getDispayValue(ItemStack stack, EntityPlayer player)
		{
			return getStringInteger(get(stack, player));
		}
	};
	
	public static final ItemAttribute MAX_DURABILITY = new IADynamic("max_durab")
	{
		@Override
		public String getDispayValue(ItemStack stack, EntityPlayer player)
		{
			return getStringInteger(get(stack, player));
		}
	};
	
	public static final ItemAttribute EFFICIENCY = new IAEfficiency("effic")
	{
		@Override
		public String getDispayValue(ItemStack stack, EntityPlayer player)
		{
			return getStringInteger(get(stack, player));
		}
	};
	
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
        value = value - normalValue;
        if (value >= 0) {
            return String.format("+%.2f", value);
        }
        return String.format("%.2f", value);
    }
}
