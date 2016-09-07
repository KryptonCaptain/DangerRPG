package mixac1.dangerrpg.init;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.network.MsgReqUpEA;
import mixac1.dangerrpg.network.MsgSyncConfig;
import mixac1.dangerrpg.network.MsgSyncEA;
import mixac1.dangerrpg.network.MsgSyncEntityData;
import mixac1.dangerrpg.network.MsgUseItemExtra;
import mixac1.dangerrpg.network.MsgUseItemSpecial;

public abstract class RPGNetwork
{
    public static SimpleNetworkWrapper net = new SimpleNetworkWrapper(DangerRPG.MODID);

    public static void load(FMLPreInitializationEvent e)
    {
        int i = 0;
        net.registerMessage(MsgUseItemSpecial.Handler.class, MsgUseItemSpecial.class, i, Side.SERVER);
        net.registerMessage(MsgUseItemExtra.Handler.class, MsgUseItemExtra.class, ++i, Side.SERVER);
        net.registerMessage(MsgSyncEA.Handler.class, MsgSyncEA.class, ++i, Side.CLIENT);
        net.registerMessage(MsgSyncEntityData.HandlerClient.class, MsgSyncEntityData.class, ++i, Side.CLIENT);
        net.registerMessage(MsgSyncEntityData.HandlerServer.class, MsgSyncEntityData.class, ++i, Side.SERVER);
        net.registerMessage(MsgReqUpEA.Handler.class, MsgReqUpEA.class, ++i, Side.SERVER);
        net.registerMessage(MsgSyncConfig.Handler.class, MsgSyncConfig.class, ++i, Side.CLIENT);
    }
}
