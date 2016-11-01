package mixac1.dangerrpg.init;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RPGRenderers
{
    public static void initClient(FMLInitializationEvent e)
    {
        registerBlockRenderer();
        registerItemRenderer();
        registerEntityRenderingHandler();
    }

    private static void registerItemRenderer()
    {
    }

    private static void registerEntityRenderingHandler()
    {
    }

    private static void registerBlockRenderer()
    {
        // ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLvlupTable.class,
        // RenderTestBlock.INSTANCE);
    }
}
