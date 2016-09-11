package mixac1.dangerrpg.init;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mixac1.dangerrpg.block.BlockLvlupTable;
import mixac1.dangerrpg.block.BlockRPGWorkbench;
import mixac1.dangerrpg.block.BlockSyntheticBedrock;
import net.minecraft.block.Block;

public abstract class RPGBlocks
{
    public static Block modifyTable;
    public static Block lvlupTable;
    public static Block rpgWorkbench;
    public static Block syntheticBedrock;

    public static void load(FMLPreInitializationEvent e)
    {
        // registerBlock(modifyTable       = new BlockModifyTable());
        registerBlock(lvlupTable        = new BlockLvlupTable());
        registerBlock(rpgWorkbench      = new BlockRPGWorkbench());
        registerBlock(syntheticBedrock  = new BlockSyntheticBedrock());
    }

    private static void registerBlock(Block block)
    {
        GameRegistry.registerBlock(block, block.getUnlocalizedName());
    }
}
