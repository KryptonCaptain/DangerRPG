package mixac1.dangerrpg.init;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.nei.NEIConfig;

public class RPGAnotherMods
{
    public static boolean isLoadNEI;

    public static void load()
    {
        isLoadNEI = Loader.isModLoaded("NotEnoughItems");
    }

    @SideOnly(Side.CLIENT)
    public static void loadClient()
    {
        if (isLoadNEI) {
            new NEIConfig().loadConfig();
        }
    }
}
