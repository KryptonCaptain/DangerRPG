package mixac1.dangerrpg.init;

import cpw.mods.fml.common.registry.GameRegistry;
import mixac1.dangerrpg.item.RPGArmorMaterial;
import mixac1.dangerrpg.item.RPGItemBow;
import mixac1.dangerrpg.item.RPGToolComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.item.armor.RPGItemArmor;
import mixac1.dangerrpg.item.gem.GemWeaponWitherSkull;
import mixac1.dangerrpg.item.tool.RPGAxe;
import mixac1.dangerrpg.item.tool.RPGHoe;
import mixac1.dangerrpg.item.tool.RPGMultiTool;
import mixac1.dangerrpg.item.tool.RPGPickaxe;
import mixac1.dangerrpg.item.tool.RPGSpade;
import mixac1.dangerrpg.item.tool.RPGWeapon;
import mixac1.dangerrpg.item.tool.RPGWeaponKnife;
import mixac1.dangerrpg.item.tool.RPGWeaponTomahawk;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;

public class RPGItems
{
	public static Item swordTraining = new RPGWeapon(ToolMaterial.WOOD,	RPGToolComponent.TRAINING, "sword_training");
	
	public static Item swordObsidian     = new RPGWeapon(RPGToolMaterial.OBSIDIAN,     RPGToolComponent.SWORD, "sword_obsidian");
	public static Item swordBedrock      = new RPGWeapon(RPGToolMaterial.BEDROCK,      RPGToolComponent.SWORD, "sword_bedrock");
	public static Item swordBlackMatter  = new RPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGToolComponent.SWORD, "sword_black_matter");
	public static Item swordWhiteMatter  = new RPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGToolComponent.SWORD, "sword_white_matter");
	
	public static Item naginataIron        = new RPGWeapon(ToolMaterial.IRON,            RPGToolComponent.NAGINATA, "naginata_iron");
	public static Item naginataGold        = new RPGWeapon(ToolMaterial.GOLD,            RPGToolComponent.NAGINATA, "naginata_gold");
	public static Item naginataDiamond     = new RPGWeapon(ToolMaterial.EMERALD,         RPGToolComponent.NAGINATA, "naginata_diamond");
	public static Item naginataObsidian    = new RPGWeapon(RPGToolMaterial.OBSIDIAN,     RPGToolComponent.NAGINATA, "naginata_obsidian");
	public static Item naginataBedrock     = new RPGWeapon(RPGToolMaterial.BEDROCK,      RPGToolComponent.NAGINATA, "naginata_bedrock");
	public static Item naginataBlackMatter = new RPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGToolComponent.NAGINATA, "naginata_black_matter");
	public static Item naginataWhiteMatter = new RPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGToolComponent.NAGINATA, "naginata_white_matter");
	
	public static Item katanaIron        = new RPGWeapon(ToolMaterial.IRON,            RPGToolComponent.KATANA, "katana_iron");
	public static Item katanaGold        = new RPGWeapon(ToolMaterial.GOLD,            RPGToolComponent.KATANA, "katana_gold");
	public static Item katanaDiamond     = new RPGWeapon(ToolMaterial.EMERALD,         RPGToolComponent.KATANA, "katana_diamond");
	public static Item katanaObsidian    = new RPGWeapon(RPGToolMaterial.OBSIDIAN,     RPGToolComponent.KATANA, "katana_obsidian");
	public static Item katanaBedrock     = new RPGWeapon(RPGToolMaterial.BEDROCK,      RPGToolComponent.KATANA, "katana_bedrock");
	public static Item katanaBlackMatter = new RPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGToolComponent.KATANA, "katana_black_matter");
	public static Item katanaWhiteMatter = new RPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGToolComponent.KATANA, "katana_white_matter");
	
	public static Item scytheIron        = new RPGWeapon(ToolMaterial.IRON,            RPGToolComponent.SCYTHE, "scythe_iron");
	public static Item scytheGold        = new RPGWeapon(ToolMaterial.GOLD,            RPGToolComponent.SCYTHE, "scythe_gold");
	public static Item scytheDiamond     = new RPGWeapon(ToolMaterial.EMERALD,         RPGToolComponent.SCYTHE, "scythe_diamond");
	public static Item scytheObsidian    = new RPGWeapon(RPGToolMaterial.OBSIDIAN,     RPGToolComponent.SCYTHE, "scythe_obsidian");
	public static Item scytheBedrock     = new RPGWeapon(RPGToolMaterial.BEDROCK,      RPGToolComponent.SCYTHE, "scythe_bedrock");
	public static Item scytheBlackMatter = new RPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGToolComponent.SCYTHE, "scythe_black_matter");
	public static Item scytheWhiteMatter = new RPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGToolComponent.SCYTHE, "scythe_white_matter");
	
	public static Item hammerIron        = new RPGWeapon(ToolMaterial.IRON,            RPGToolComponent.HAMMER, "hammer_iron");
	public static Item hammerGold        = new RPGWeapon(ToolMaterial.GOLD,            RPGToolComponent.HAMMER, "hammer_gold");
	public static Item hammerDiamond     = new RPGWeapon(ToolMaterial.EMERALD,         RPGToolComponent.HAMMER, "hammer_diamond");
	public static Item hammerObsidian    = new RPGWeapon(RPGToolMaterial.OBSIDIAN,     RPGToolComponent.HAMMER, "hammer_obsidian");
	public static Item hammerBedrock     = new RPGWeapon(RPGToolMaterial.BEDROCK,      RPGToolComponent.HAMMER, "hammer_bedrock");
	public static Item hammerBlackMatter = new RPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGToolComponent.HAMMER, "hammer_black_matter");
	public static Item hammerWhiteMatter = new RPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGToolComponent.HAMMER, "hammer_white_matter");
	
	public static Item tomahawkIron        = new RPGWeaponTomahawk(ToolMaterial.IRON,            RPGToolComponent.TOMAHAWK, "tomahawk_iron");
	public static Item tomahawkGold        = new RPGWeaponTomahawk(ToolMaterial.GOLD,            RPGToolComponent.TOMAHAWK, "tomahawk_gold");
	public static Item tomahawkDiamond     = new RPGWeaponTomahawk(ToolMaterial.EMERALD,         RPGToolComponent.TOMAHAWK, "tomahawk_diamond");
	public static Item tomahawkObsidian    = new RPGWeaponTomahawk(RPGToolMaterial.OBSIDIAN,     RPGToolComponent.TOMAHAWK, "tomahawk_obsidian");
	public static Item tomahawkBedrock     = new RPGWeaponTomahawk(RPGToolMaterial.BEDROCK,      RPGToolComponent.TOMAHAWK, "tomahawk_bedrock");
	public static Item tomahawkBlackMatter = new RPGWeaponTomahawk(RPGToolMaterial.BLACK_MATTER, RPGToolComponent.TOMAHAWK, "tomahawk_black_matter");
	public static Item tomahawkWhiteMatter = new RPGWeaponTomahawk(RPGToolMaterial.WHITE_MATTER, RPGToolComponent.TOMAHAWK, "tomahawk_white_matter");
	
	public static Item knifeIron        = new RPGWeaponKnife(ToolMaterial.IRON,            RPGToolComponent.KNIFE, "knife_iron");
	public static Item knifeGold        = new RPGWeaponKnife(ToolMaterial.GOLD,            RPGToolComponent.KNIFE, "knife_gold");
	public static Item knifeDiamond     = new RPGWeaponKnife(ToolMaterial.EMERALD,         RPGToolComponent.KNIFE, "knife_diamond");
	public static Item knifeObsidian    = new RPGWeaponKnife(RPGToolMaterial.OBSIDIAN,     RPGToolComponent.KNIFE, "knife_obsidian");
	public static Item knifeBedrock     = new RPGWeaponKnife(RPGToolMaterial.BEDROCK,      RPGToolComponent.KNIFE, "knife_bedrock");
	public static Item knifeBlackMatter = new RPGWeaponKnife(RPGToolMaterial.BLACK_MATTER, RPGToolComponent.KNIFE, "knife_black_matter");
	public static Item knifeWhiteMatter = new RPGWeaponKnife(RPGToolMaterial.WHITE_MATTER, RPGToolComponent.KNIFE, "knife_white_matter");
	
	public static Item axeObsidian     = new RPGAxe(RPGToolMaterial.OBSIDIAN,     "axe_obsidian");
	public static Item axeBedrock      = new RPGAxe(RPGToolMaterial.BEDROCK,      "axe_bedrock");
	public static Item axeBlackMatter  = new RPGAxe(RPGToolMaterial.BLACK_MATTER, "axe_black_matter");
	public static Item axeWhiteMatter  = new RPGAxe(RPGToolMaterial.WHITE_MATTER, "axe_white_matter");
	
	public static Item hoeObsidian     = new RPGHoe(RPGToolMaterial.OBSIDIAN,     "hoe_obsidian");
	public static Item hoeBedrock      = new RPGHoe(RPGToolMaterial.BEDROCK,      "hoe_bedrock");
	public static Item hoeBlackMatter  = new RPGHoe(RPGToolMaterial.BLACK_MATTER, "hoe_black_matter");
	public static Item hoeWhiteMatter  = new RPGHoe(RPGToolMaterial.WHITE_MATTER, "hoe_white_matter");
	
	public static Item pickaxeObsidian     = new RPGPickaxe(RPGToolMaterial.OBSIDIAN,     "pickaxe_obsidian");
	public static Item pickaxeBedrock      = new RPGPickaxe(RPGToolMaterial.BEDROCK,      "pickaxe_bedrock");
	public static Item pickaxeBlackMatter  = new RPGPickaxe(RPGToolMaterial.BLACK_MATTER, "pickaxe_black_matter");
	public static Item pickaxeWhiteMatter  = new RPGPickaxe(RPGToolMaterial.WHITE_MATTER, "pickaxe_white_matter");
	
	public static Item shovelObsidian     = new RPGSpade(RPGToolMaterial.OBSIDIAN,     "shovel_obsidian");
	public static Item shovelBedrock      = new RPGSpade(RPGToolMaterial.BEDROCK,      "shovel_bedrock");
	public static Item shovelBlackMatter  = new RPGSpade(RPGToolMaterial.BLACK_MATTER, "shovel_black_matter");
	public static Item shovelWhiteMatter  = new RPGSpade(RPGToolMaterial.WHITE_MATTER, "shovel_white_matter");
	
	public static Item multitoolIron        = new RPGMultiTool(ToolMaterial.IRON,            "multitool_iron");
	public static Item multitoolGold        = new RPGMultiTool(ToolMaterial.GOLD,            "multitool_gold");
	public static Item multitoolDiamond     = new RPGMultiTool(ToolMaterial.EMERALD,         "multitool_diamond");
	public static Item multitoolObsidian    = new RPGMultiTool(RPGToolMaterial.OBSIDIAN,     "multitool_obsidian");
	public static Item multitoolBedrock     = new RPGMultiTool(RPGToolMaterial.BEDROCK,      "multitool_bedrock");
	public static Item multitoolBlackMatter = new RPGMultiTool(RPGToolMaterial.BLACK_MATTER, "multitool_black_matter");
	public static Item multitoolWhiteMatter = new RPGMultiTool(RPGToolMaterial.WHITE_MATTER, "multitool_white_matter");
	
	public static Item[] armorObsidian    = RPGItemArmor.createFullSet(RPGArmorMaterial.OBSIDIAN,     "obsidian");
	public static Item[] armorBedrock     = RPGItemArmor.createFullSet(RPGArmorMaterial.BEDROCK,      "bedrock");
	public static Item[] armorBlackMatter = RPGItemArmor.createFullSet(RPGArmorMaterial.BLACK_MATTER, "black_matter");
	public static Item[] armorWhiteMatter = RPGItemArmor.createFullSet(RPGArmorMaterial.WHITE_MATTER, "white_matter");
	
	public static Item shadowBow = new RPGItemBow(RPGToolComponent.SHADOW_BOW, 4F, "shadowBow");
	
	public static Item gemWitherSkull = new GemWeaponWitherSkull("gem_wither_skull");
	
	public static void load()
	{
		registerItems();
		addRecipes();
	}
	
	private static void registerItems()
	{
		registerItem(swordTraining);
		
		registerItem(knifeIron);
		registerItem(tomahawkIron);
		registerItem(katanaIron);
		registerItem(naginataIron);
		registerItem(scytheIron);
		registerItem(hammerIron);
		registerItem(multitoolIron);
		
		registerItem(knifeGold);
		registerItem(tomahawkGold);
		registerItem(katanaGold);
		registerItem(naginataGold);
		registerItem(scytheGold);
		registerItem(hammerGold);
		registerItem(multitoolGold);
		
		registerItem(knifeDiamond);
		registerItem(tomahawkDiamond);
		registerItem(katanaDiamond);
		registerItem(naginataDiamond);
		registerItem(scytheDiamond);
		registerItem(hammerDiamond);
		registerItem(multitoolDiamond);
		
		registerItem(knifeObsidian);
		registerItem(tomahawkObsidian);
		registerItem(swordObsidian);
		registerItem(katanaObsidian);
		registerItem(naginataObsidian);
		registerItem(scytheObsidian);
		registerItem(hammerObsidian);
		registerItem(axeObsidian);
		registerItem(shovelObsidian);
		registerItem(pickaxeObsidian);
		registerItem(hoeObsidian);
		registerItem(multitoolObsidian);
		
		registerItem(knifeBedrock);
		registerItem(tomahawkBedrock);
		registerItem(swordBedrock);
		registerItem(katanaBedrock);
		registerItem(naginataBedrock);
		registerItem(scytheBedrock);
		registerItem(hammerBedrock);
		registerItem(axeBedrock);
		registerItem(shovelBedrock);
		registerItem(pickaxeBedrock);
		registerItem(hoeBedrock);
		registerItem(multitoolBedrock);
		
		registerItem(knifeBlackMatter);
		registerItem(tomahawkBlackMatter);
		registerItem(swordBlackMatter);
		registerItem(katanaBlackMatter);
		registerItem(naginataBlackMatter);
		registerItem(scytheBlackMatter);
		registerItem(hammerBlackMatter);
		registerItem(axeBlackMatter);
		registerItem(shovelBlackMatter);
		registerItem(pickaxeBlackMatter);
		registerItem(hoeBlackMatter);
		registerItem(multitoolBlackMatter);
		
		registerItem(knifeWhiteMatter);
		registerItem(tomahawkWhiteMatter);
		registerItem(swordWhiteMatter);
		registerItem(katanaWhiteMatter);
		registerItem(naginataWhiteMatter);
		registerItem(scytheWhiteMatter);
		registerItem(hammerWhiteMatter);
		registerItem(axeWhiteMatter);
		registerItem(shovelWhiteMatter);
		registerItem(pickaxeWhiteMatter);
		registerItem(hoeWhiteMatter);
		registerItem(multitoolWhiteMatter);
		
		registerItem(shadowBow);
		
		registerArmor(armorObsidian);
		registerArmor(armorBedrock);
		registerArmor(armorBlackMatter);
		registerArmor(armorWhiteMatter);
			
		registerItem(gemWitherSkull);
	}
	
	private static void addRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(swordBlackMatter, 1), new Object[]{ " # ", " # ", " X ",('X'), Items.blaze_rod, ('#'), Items.iron_ingot});
	}
	
	private static void registerItem(Item item)
	{
		GameRegistry.registerItem(item, item.getUnlocalizedName());
	}
	
	private static void registerArmor(Item[] armor)
	{
		for (Item item : armor) {
			registerItem(item);
		}
	}
}
