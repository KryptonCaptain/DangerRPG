package mixac1.dangerrpg.init;

import cpw.mods.fml.common.registry.GameRegistry;
import mixac1.dangerrpg.block.LvlupTable;
import mixac1.dangerrpg.block.ModifyTable;
import net.minecraft.block.Block;

public abstract class RPGBlocks
{
    public static ModifyTable modifyTable;
    public static LvlupTable lvlupTable;

    public static void load()
    {
        registerBlock(modifyTable = new ModifyTable());
        registerBlock(lvlupTable = new LvlupTable());
    }

    private static void registerBlock(Block block)
    {
        GameRegistry.registerBlock(block, block.getUnlocalizedName());
    }

    private static void addRecipes()
    {

    }
}
