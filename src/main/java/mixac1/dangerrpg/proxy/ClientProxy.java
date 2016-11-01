package mixac1.dangerrpg.proxy;

import mixac1.dangerrpg.init.RPGAnotherMods;
import mixac1.dangerrpg.init.RPGEvents;
import mixac1.dangerrpg.init.RPGKeyBinds;
import mixac1.dangerrpg.init.RPGRenderers;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        super.preInit(e);

        // RPGConfig.initClient(e);

        RPGKeyBinds.initClient(e);
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        super.init(e);

        RPGRenderers.initClient(e);

        RPGEvents.initClient(e);

        RPGAnotherMods.initClient(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e)
    {
        super.postInit(e);
    }
}
