package mixac1.dangerrpg.init;

import java.util.HashMap;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.api.RPGRegister;
import mixac1.dangerrpg.client.render.entity.RenderArrowRPG;
import mixac1.dangerrpg.client.render.entity.RenderBit;
import mixac1.dangerrpg.client.render.entity.RenderMaterial;
import mixac1.dangerrpg.client.render.entity.RenderThrowKnife;
import mixac1.dangerrpg.client.render.entity.RenderThrowTomahawk;
import mixac1.dangerrpg.client.render.item.RPGItemRenderIcon;
import mixac1.dangerrpg.client.render.item.RPGItemRenderModel;
import mixac1.dangerrpg.client.render.item.RenderHammer;
import mixac1.dangerrpg.client.render.item.RenderKatana;
import mixac1.dangerrpg.client.render.item.RenderKnife;
import mixac1.dangerrpg.client.render.item.RenderLongItem;
import mixac1.dangerrpg.client.render.item.RenderShadowBow;
import mixac1.dangerrpg.client.render.item.RenderSniperBow;
import mixac1.dangerrpg.entity.projectile.EntityArrowRPG;
import mixac1.dangerrpg.entity.projectile.EntityMaterial;
import mixac1.dangerrpg.entity.projectile.EntityProjectile;
import mixac1.dangerrpg.entity.projectile.EntitySniperArrow;
import mixac1.dangerrpg.entity.projectile.EntityThrowKnife;
import mixac1.dangerrpg.entity.projectile.EntityThrowTomahawk;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

@SideOnly(Side.CLIENT)
public abstract class RPGRenderers
{
    public static HashMap<Item, ResourceLocation> MODEL_TEXTURES = new HashMap<Item, ResourceLocation>();

    public static void load()
    {
        registerBlockRenderer();
        registerItemRenderer();
        registerEntityRenderingHandler();
    }

    private static void registerItemRenderer()
    {
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataIron,        RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataGold,        RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataDiamond,     RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataObsidian,    RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataBedrock,     RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataBlackMatter, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataWhiteMatter, RenderLongItem.INSTANCE);

        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaIron,        RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaGold,        RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaDiamond,     RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaObsidian,    RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaBedrock,     RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaBlackMatter, RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaWhiteMatter, RenderKatana.INSTANCE);

        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheIron,        RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheGold,        RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheDiamond,     RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheObsidian,    RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheBedrock,     RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheBlackMatter, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheWhiteMatter, RenderLongItem.INSTANCE);

        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeIron,        RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeGold,        RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeDiamond,     RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeObsidian,    RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeBedrock,     RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeBlackMatter, RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeWhiteMatter, RenderKnife.INSTANCE);

        registerItemRendererE(RPGItems.hammerIron,        RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerGold,        RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerDiamond,     RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerObsidian,    RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerBedrock,     RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerBlackMatter, RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerWhiteMatter, RenderHammer.INSTANCE);

        MinecraftForgeClient.registerItemRenderer(RPGItems.shadowBow, RenderShadowBow.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.sniperBow, RenderSniperBow.INSTANCE);

        MinecraftForgeClient.registerItemRenderer(RPGItems.testWand,  RPGItemRenderIcon.INSTANCE);
    }

    private static void registerEntityRenderingHandler()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityProjectile.class, RenderBit.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityMaterial.class, RenderMaterial.INSTANCE);

        RenderingRegistry.registerEntityRenderingHandler(EntityArrowRPG.class, RenderArrowRPG.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntitySniperArrow.class, RenderArrowRPG.INSTANCE);

        RenderingRegistry.registerEntityRenderingHandler(EntityThrowKnife.class, RenderThrowKnife.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityThrowTomahawk.class, RenderThrowTomahawk.INSTANCE);

        //RenderingRegistry.registerEntityRenderingHandler(EntityTestt.class, RenderBit.INSTANCE);
    }

    private static void registerBlockRenderer()
    {
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLvlupTable.class, RenderTestBlock.INSTANCE);
    }

    public static void registerItemRendererE(Item item, RPGItemRenderModel model)
    {
        RPGRegister.registerItemRendererModel(item, model, "DangerRPG:textures/models/items/");
    }
}
