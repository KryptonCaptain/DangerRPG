package mixac1.dangerrpg.item;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class RPGArmorMaterial
{
	public static final ArmorMaterial OBSIDIAN     = EnumHelper.addArmorMaterial("OBSIDIAN",     41,   new int[] {4,  8,  6,  4}, 12);
	public static final ArmorMaterial BEDROCK      = EnumHelper.addArmorMaterial("BEDROCK",      82,   new int[] {7,  9,  8,  6}, 14);
	public static final ArmorMaterial BLACK_MATTER = EnumHelper.addArmorMaterial("BLACK_MATTER", 164,  new int[] {8,  10, 10, 8}, 19);
	public static final ArmorMaterial WHITE_MATTER = EnumHelper.addArmorMaterial("WHITE_MATTER", 206,  new int[] {9,  10, 10, 9}, 22);
}
