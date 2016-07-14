package mixac1.dangerrpg.init;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.network.MsgReqUpPA;
import mixac1.dangerrpg.network.MsgSyncPA;
import mixac1.dangerrpg.network.MsgSyncPAE;
import mixac1.dangerrpg.network.MsgSyncPlayerData;
import mixac1.dangerrpg.network.MsgUseItemExtra;
import mixac1.dangerrpg.network.MsgUseItemSpecial;

public class RPGNetwork
{
    public static SimpleNetworkWrapper net = new SimpleNetworkWrapper(DangerRPG.MODID);
    
    public static void load()
    {
        int i = 0;
        net.registerMessage(MsgUseItemSpecial.Handler.class, MsgUseItemSpecial.class, i, Side.SERVER);
        net.registerMessage(MsgUseItemExtra.Handler.class, MsgUseItemExtra.class, ++i, Side.SERVER);
        net.registerMessage(MsgSyncPA.Handler.class, MsgSyncPA.class, ++i, Side.CLIENT);
        net.registerMessage(MsgSyncPAE.Handler.class, MsgSyncPAE.class, ++i, Side.CLIENT);
        net.registerMessage(MsgSyncPlayerData.HandlerClient.class, MsgSyncPlayerData.class, ++i, Side.CLIENT);
        net.registerMessage(MsgSyncPlayerData.HandlerServer.class, MsgSyncPlayerData.class, ++i, Side.SERVER);
        net.registerMessage(MsgReqUpPA.Handler.class, MsgReqUpPA.class, ++i, Side.SERVER);
    }
}
