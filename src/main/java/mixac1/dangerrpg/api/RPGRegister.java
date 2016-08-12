package mixac1.dangerrpg.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.client.render.item.RPGItemRenderModel;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.init.RPGRenderers;
import mixac1.dangerrpg.item.armor.RPGItemArmor;
import mixac1.dangerrpg.item.tool.RPGWeapon;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

public abstract class RPGRegister
{
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
