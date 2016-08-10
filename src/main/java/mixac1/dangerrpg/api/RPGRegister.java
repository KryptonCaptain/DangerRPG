package mixac1.dangerrpg.api;

import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.item.IADynamic;
import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.CommonEntityData;
import mixac1.dangerrpg.capability.EntityLivingData;
import mixac1.dangerrpg.capability.LvlableItem.ItemAttrParams;
import mixac1.dangerrpg.capability.PlayerData;
import mixac1.dangerrpg.client.render.item.RPGItemRenderModel;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.init.RPGRenderers;
import mixac1.dangerrpg.item.armor.RPGItemArmor;
import mixac1.dangerrpg.item.tool.RPGWeapon;
import mixac1.dangerrpg.util.Multiplier;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

public abstract class RPGRegister
{
    /**
     * Used for adding own {@link EntityAttribute} to {@link CommonEntityData}
     */
    public static void registerCommonEntityAttribute(EntityAttribute ea)
    {
        CommonEntityData.entityAttributes.add(ea);
    }

    /**
     * Used for adding own lvlable {@link EntityAttribute} to {@link CommonEntityData}
     */
    public static void registerLvlCommonEntityAttribute(EntityAttribute ea)
    {
        CommonEntityData.entityAttributes.add(ea);
        CommonEntityData.lvlProviders.add(ea.lvlProvider);
    }

    /**
     * Used for adding own {@link EntityAttribute} to {@link EntityLivingData}
     */
    public static void registerEntityAttribute(EntityAttribute ea)
    {
        EntityLivingData.entityAttributes.add(ea);
    }

    /**
     * Used for adding own lvlable {@link EntityAttribute} to {@link EntityLivingData}
     */
    public static void registerLvlEntityAttribute(EntityAttribute ea)
    {
        EntityLivingData.entityAttributes.add(ea);
        EntityLivingData.lvlProviders.add(ea.lvlProvider);
    }

    /**
     * Used for adding own {@link EntityAttribute} to {@link PlayerData}
     */
    public static void registerPlayerAttribute(EntityAttribute ea)
    {
        PlayerData.entityAttributes.add(ea);
    }

    /**
     * Used for adding own lvlable {@link EntityAttribute} to {@link PlayerData}
     */
    public static void registerLvlPlayerAttribute(EntityAttribute ea)
    {
        PlayerData.entityAttributes.add(ea);
        PlayerData.lvlProviders.add(ea.lvlProvider);
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
     * Advanced method model registering.<br>
     * Also, it is registering {@link ResourceLocation} of texture for model.<br>
     * This texture will be used, if you extends your model class from {@link RPGItemRenderModel}
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemRendererModel(Item item, RPGItemRenderModel model, String resDomain, String resPath)
    {
        MinecraftForgeClient.registerItemRenderer(item, model);
        RPGRenderers.modelTextures.put(item, new ResourceLocation(resDomain, Utils.toString(resPath, item.unlocalizedName, ".png")));
    }

    /**
     * Advanced method model registering.<br>
     * Also, it is registering {@link ResourceLocation} of texture for model.<br>
     * This texture will be used, if you extends your model class from {@link RPGItemRenderModel}
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemRendererModel(Item item, RPGItemRenderModel model, String resFullPath)
    {
        MinecraftForgeClient.registerItemRenderer(item, model);
        RPGRenderers.modelTextures.put(item, new ResourceLocation(Utils.toString(resFullPath, item.unlocalizedName, ".png")));
    }

    /**
     * Registering {@link ToolMaterial} name.<br>
     * This name will be used, if you choose constructor of {@link RPGWeapon} and others classes without using item name.<br>
     * Used for faster naming.
     */
    public static void registerToolMaterialName(ToolMaterial toolMaterial, String name)
    {
        RPGOther.toolMaterialNames.put(toolMaterial, name);
    }

    /**
     * Registering {@link ArmorMaterial} name.<br>
     * This name will be used, if you choose constructor of {@link RPGItemArmor} and others classes without using item name.<br>
     * Used for faster naming.
     */
    public static void registerArmorMaterialName(ArmorMaterial armorMaterial, String name)
    {
        RPGOther.armorMaterialNames.put(armorMaterial, name);
    }
}
