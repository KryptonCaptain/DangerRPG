package mixac1.dangerrpg.api;

import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.api.item.IADynamic;
import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.api.player.PlayerAttribute;
import mixac1.dangerrpg.api.player.PlayerAttributeE;
import mixac1.dangerrpg.capability.ItemAttrParams;
import mixac1.dangerrpg.capability.PlayerData;
import mixac1.dangerrpg.client.render.item.RPGItemRenderModel;
import mixac1.dangerrpg.init.RPGRenderers;
import mixac1.dangerrpg.util.Multiplier;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

public class RPGApi
{
    public static void registerPlayerAttribute(PlayerAttribute pa)
    {
        PlayerData.workAttributes.add(pa);
    }

    public static void registerPlayerAttributeE(PlayerAttributeE pa)
    {
        PlayerData.playerAttributes.add(pa);
    }

    public static void registerStaticItemAttribute(HashMap<ItemAttribute, ItemAttrParams> map, IAStatic attr, float value)
    {
        map.put(attr, new ItemAttrParams(value, null));
    }

    public static void registerDynamicItemAttribute(HashMap<ItemAttribute, ItemAttrParams> map, IADynamic attr, float value, Multiplier mul)
    {
        map.put(attr, new ItemAttrParams(value, mul));
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemRendererModel(Item item, RPGItemRenderModel model)
    {
        MinecraftForgeClient.registerItemRenderer(item, model);
        registerItemModelTexture(item);
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModelTexture(Item item)
    {
        RPGRenderers.MODEL_TEXTURES.put(item, new ResourceLocation("DangerRPG:textures/models/items/".concat(item.getUnlocalizedName().concat(".png"))));
    }
}
