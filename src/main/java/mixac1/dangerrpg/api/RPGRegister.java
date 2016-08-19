package mixac1.dangerrpg.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.api.item.ILvlableItem;
import mixac1.dangerrpg.client.render.item.RPGItemRenderModel;
import mixac1.dangerrpg.init.RPGRenderers;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.item.Item;
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
     * Register own {@link RPGToolComponent} for {@link Item}<br>
     * It will be use, if {@link Item} not instance of {@link ILvlableItem}
     */
    public static void registerRPGItemComponentForItem(Item item, RPGItemComponent itemComponent)
    {
        RPGItemComponent.map.put(item, itemComponent);
    }
}
