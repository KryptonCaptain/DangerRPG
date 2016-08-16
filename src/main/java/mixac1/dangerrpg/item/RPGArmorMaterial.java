package mixac1.dangerrpg.item;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class RPGArmorMaterial
{
    public static final RPGArmorMaterial CLOTH           = new RPGArmorMaterial("cloth",           ArmorMaterial.CLOTH);
    public static final RPGArmorMaterial CHAIN           = new RPGArmorMaterial("chain",          ArmorMaterial.CHAIN);
    public static final RPGArmorMaterial IRON            = new RPGArmorMaterial("iron",           ArmorMaterial.IRON);
    public static final RPGArmorMaterial GOLD            = new RPGArmorMaterial("gold",           ArmorMaterial.GOLD);
    public static final RPGArmorMaterial DIAMOND         = new RPGArmorMaterial("diamond",        ArmorMaterial.DIAMOND);
    public static final RPGArmorMaterial OBSIDIAN        = new RPGArmorMaterial("obsidian",       EnumHelper.addArmorMaterial("OBSIDIAN",     41,   new int[] {4,  8,  6,  4}, 12));
    public static final RPGArmorMaterial BEDROCK         = new RPGArmorMaterial("bedrock",        EnumHelper.addArmorMaterial("BEDROCK",      82,   new int[] {7,  9,  8,  6}, 14));
    public static final RPGArmorMaterial BLACK_MATTER    = new RPGArmorMaterial("black_matter",   EnumHelper.addArmorMaterial("BLACK_MATTER", 164,  new int[] {8,  10, 10, 8}, 19));
    public static final RPGArmorMaterial WHITE_MATTER    = new RPGArmorMaterial("white_matter",   EnumHelper.addArmorMaterial("WHITE_MATTER", 206,  new int[] {9,  10, 10, 9}, 22));

    public ArmorMaterial material;
    public String name;

    public RPGArmorMaterial(String name, ArmorMaterial material)
    {
        this.name = name;
        this.material = material;
    }

    public static RPGArmorMaterial getDefaultRPGArmorMaterial(ArmorMaterial material)
    {
        return new RPGArmorMaterial("", material);
    }
}
