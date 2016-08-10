package mixac1.dangerrpg.init;

import java.util.HashMap;

import mixac1.dangerrpg.item.RPGArmorMaterial;
import mixac1.dangerrpg.item.RPGToolMaterial;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;

public abstract class RPGOther
{
    public static CreativeTabs tabDangerRPG = (new CreativeTabs("tabDangerRPG")
    {
        @Override
        public Item getTabIconItem() {
            return Items.arrow;
        }
    });

    public static HashMap<ToolMaterial, String> toolMaterialNames = new HashMap<ToolMaterial, String>()
    {{
        put(ToolMaterial.WOOD, "wood");
        put(ToolMaterial.STONE, "stone");
        put(ToolMaterial.IRON, "iron");
        put(ToolMaterial.GOLD, "gold");
        put(ToolMaterial.EMERALD, "diamond");
        put(RPGToolMaterial.OBSIDIAN, "obsidian");
        put(RPGToolMaterial.BEDROCK, "bedrock");
        put(RPGToolMaterial.BLACK_MATTER, "black_matter");
        put(RPGToolMaterial.WHITE_MATTER, "white_matter");
    }};

    public static HashMap<ArmorMaterial, String> armorMaterialNames = new HashMap<ArmorMaterial, String>()
    {{
        put(ArmorMaterial.CLOTH, "cloth");
        put(ArmorMaterial.CHAIN, "chain");
        put(ArmorMaterial.IRON, "iron");
        put(ArmorMaterial.GOLD, "gold");
        put(ArmorMaterial.DIAMOND, "diamond");
        put(RPGArmorMaterial.OBSIDIAN, "obsidian");
        put(RPGArmorMaterial.BEDROCK, "bedrock");
        put(RPGArmorMaterial.BLACK_MATTER, "black_matter");
        put(RPGArmorMaterial.WHITE_MATTER, "white_matter");
    }};
}
