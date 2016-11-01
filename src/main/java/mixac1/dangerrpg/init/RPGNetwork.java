package mixac1.dangerrpg.init;

import mixac1.dangerrpg.DangerRPG;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class RPGNetwork
{
    public static SimpleNetworkWrapper net = new SimpleNetworkWrapper(DangerRPG.MODID);

    public static void initCommon(FMLPreInitializationEvent e)
    {
        int i = 0;
    }
}
