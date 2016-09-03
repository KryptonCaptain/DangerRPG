package mixac1.dangerrpg.init;

import cpw.mods.fml.common.registry.GameRegistry;
import mixac1.dangerrpg.block.LvlupTable;
import mixac1.dangerrpg.block.ModifyTable;
import mixac1.dangerrpg.block.RPGWorkbench;
import net.minecraft.block.Block;

public abstract class RPGBlocks
{
    public static Block modifyTable = new ModifyTable();
    public static Block lvlupTable = new LvlupTable();
    public static Block rpgWorkbench = new RPGWorkbench();

    public static void load()
    {
//        registerBlock(modifyTable);
//        registerBlock(lvlupTable);
        registerBlock(rpgWorkbench);
    }

    private static void registerBlock(Block block)
    {
        GameRegistry.registerBlock(block, block.getUnlocalizedName());
    }
}
