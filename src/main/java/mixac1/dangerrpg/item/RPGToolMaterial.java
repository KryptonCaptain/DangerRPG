package mixac1.dangerrpg.item;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class RPGToolMaterial
{
    public static final ToolMaterial OBSIDIAN     = EnumHelper.addToolMaterial("OBSIDIAN",     3, 2000,  8.0F,  5.0F,  12);
    public static final ToolMaterial BEDROCK      = EnumHelper.addToolMaterial("BEDROCK",      3, 4000,  12.0F, 11.0F, 14);
    public static final ToolMaterial BLACK_MATTER = EnumHelper.addToolMaterial("BLACK_MATTER", 3, 8000,  18.0F, 21.0F, 19);
    public static final ToolMaterial WHITE_MATTER = EnumHelper.addToolMaterial("WHITE_MATTER", 3, 10000, 24.0F, 36.0F, 22);
}
