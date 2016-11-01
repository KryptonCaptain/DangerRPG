package mixac1.dangerrpg.init;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RPGBlocks
{
    public static void initCommon(FMLPreInitializationEvent e)
    {
    }

    private static void registerBlock(Block block)
    {
        GameRegistry.register(block);
    }
}
