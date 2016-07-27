package mixac1.dangerrpg.api;

import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.EntityAttributeE;
import mixac1.dangerrpg.api.item.IADynamic;
import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.CommonEntityData;
import mixac1.dangerrpg.capability.EntityLivingData;
import mixac1.dangerrpg.capability.ItemAttrParams;
import mixac1.dangerrpg.capability.PlayerData;
import mixac1.dangerrpg.client.render.item.RPGItemRenderModel;
import mixac1.dangerrpg.init.RPGItems;
import mixac1.dangerrpg.init.RPGRenderers;
import mixac1.dangerrpg.item.armor.RPGItemArmor;
import mixac1.dangerrpg.item.tool.RPGWeapon;
import mixac1.dangerrpg.util.Multiplier;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

public class RPGRegister
{
	/**
	 * Used for adding own {@link EntityAttribute} to {@link CommonEntityData}
	 */
    public static void registerCommonEntityAttribute(EntityAttribute pa)
    {
        CommonEntityData.workAttributes.add(pa);
    }

    /**
	 * Used for adding own {@link EntityAttributeE} to {@link CommonEntityData}
	 */
    public static void registerCommonEntityAttributeE(EntityAttributeE pa)
    {
        CommonEntityData.entityAttributes.add(pa);
    }

    /**
	 * Used for adding own {@link EntityAttribute} to {@link EntityLivingData}
	 */
    public static void registerEntityAttribute(EntityAttribute pa)
    {
        EntityLivingData.workAttributes.add(pa);
    }

    /**
	 * Used for adding own {@link EntityAttributeE} to {@link EntityLivingData}
	 */
    public static void registerEntityAttributeE(EntityAttributeE pa)
    {
    	EntityLivingData.entityAttributes.add(pa);
    }

    /**
	 * Used for adding own {@link EntityAttribute} to {@link PlayerData}
	 */
    public static void registerPlayerAttribute(EntityAttribute pa)
    {
        PlayerData.workAttributes.add(pa);
    }

    /**
	 * Used for adding own {@link EntityAttributeE} to {@link PlayerData}
	 */
    public static void registerPlayerAttributeE(EntityAttributeE pa)
    {
    	PlayerData.entityAttributes.add(pa);
    }

    /**
     * Simple way for adding own {@link IAStatic} to map, when item registering their {@link ItemAttribute}s
     */
    public static void registerStaticItemAttribute(HashMap<ItemAttribute, ItemAttrParams> map, IAStatic attr, float value)
    {
        map.put(attr, new ItemAttrParams(value, null));
    }

    /**
     * Simple way for adding own {@link IADynamic} to map, when item registering their {@link ItemAttribute}s
     */
    public static void registerDynamicItemAttribute(HashMap<ItemAttribute, ItemAttrParams> map, IADynamic attr, float value, Multiplier mul)
    {
        map.put(attr, new ItemAttrParams(value, mul));
    }

    /**
     * Advanced method model registering.
     * Also, it is registering {@link ResourceLocation} of texture for model.
     * This texture will be used, if you extends your model class from {@link RPGItemRenderModel}
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemRendererModel(Item item, RPGItemRenderModel model, String resDomain, String resPath)
    {
        MinecraftForgeClient.registerItemRenderer(item, model);
        RPGRenderers.MODEL_TEXTURES.put(item, new ResourceLocation(resDomain, resPath.concat(item.getUnlocalizedName().concat(".png"))));
    }

    /**
     * Advanced method model registering.
     * Also, it is registering {@link ResourceLocation} of texture for model.
     * This texture will be used, if you extends your model class from {@link RPGItemRenderModel}
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemRendererModel(Item item, RPGItemRenderModel model, String resFullPath)
    {
        MinecraftForgeClient.registerItemRenderer(item, model);
        RPGRenderers.MODEL_TEXTURES.put(item, new ResourceLocation(resFullPath.concat(item.unlocalizedName.concat(".png"))));
    }

    /**
     * Registering {@link ToolMaterial} name.
     * This name will be used, if you choose constructor of {@link RPGWeapon} and others classes without using item name.
     * Used for faster naming.
     */
    public static void registerToolMaterialName(ToolMaterial toolMaterial, String name)
    {
    	RPGItems.TOOL_MATERIAL_NAMES.put(toolMaterial, name);
    }

    /**
     * Registering {@link ArmorMaterial} name.
     * This name will be used, if you choose constructor of {@link RPGItemArmor} and others classes without using item name.
     * Used for faster naming.
     */
    public static void registerArmorMaterialName(ArmorMaterial armorMaterial, String name)
    {
    	RPGItems.ARMOR_MATERIAL_NAMES.put(armorMaterial, name);
    }
}
