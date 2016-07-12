package mixac1.dangerrpg.capability;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.api.item.IGemableItem;
import mixac1.dangerrpg.item.gem.Gem;
import mixac1.dangerrpg.item.gem.GemWeaponSpecialAttack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class GemableItem
{
	public static final String IS_GEMABLE = "rpg_gemable";
	
	public static boolean isGemable(ItemStack stack)
	{
		if (stack != null &&
			stack.stackTagCompound != null &&
			stack.stackTagCompound.hasKey(IS_GEMABLE)) {
			return true;
		}
		return false;
	}
	
	public static boolean createGemableItem(ItemStack stack)
	{
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		stack.stackTagCompound.setBoolean(IS_GEMABLE, true);
		GemType[] gems = getGemTypes(stack);
		for (GemType iterator : gems) {
			iterator.attach(null, stack);
		}
		return true;
	}
	
	public static GemType[] getGemTypes(ItemStack stack)
	{
		IGemableItem type = stack.getItem() instanceof IGemableItem ? (IGemableItem) stack.getItem() : IGemableItem.DEFAULT;
		return type.getGemTypes(stack);
	}
	
	public static void onItemUseSpecial(ItemStack stack, World world, EntityPlayer player)
	{
		if (!world.isRemote && GemType.GEM_SPECIAL_ATTACK.hasIt(stack)) {
			ItemStack gem = GemType.GEM_SPECIAL_ATTACK.get(stack);
			if (gem != null && gem.getItem() instanceof GemWeaponSpecialAttack) {
				((GemWeaponSpecialAttack) gem.getItem()).upgrade(stack, world, player);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		if (isGemable(stack)) {
			GemType[] gems = getGemTypes(stack);
			for (GemType iterator : gems) {
				ItemStack gemStack = iterator.get(stack);
				if (gemStack != null && gemStack.getItem() instanceof Gem) {
					list.add("");
					list.add(EnumChatFormatting.GRAY + ((Gem) gemStack.getItem()).getGemType().getDispayName() + ": " +
							 EnumChatFormatting.RED + gemStack.getDisplayName());
				}
			}
		}
	}
}
