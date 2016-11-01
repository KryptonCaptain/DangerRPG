package mixac1.dangerrpg.proxy;

import mixac1.dangerrpg.init.RPGAnotherMods;
import mixac1.dangerrpg.init.RPGBlocks;
import mixac1.dangerrpg.init.RPGEntities;
import mixac1.dangerrpg.init.RPGEvents;
import mixac1.dangerrpg.init.RPGGuiHandlers;
import mixac1.dangerrpg.init.RPGItems;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.init.RPGRecipes;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        RPGAnotherMods.initCommon(e);

        // RPGConfig.initCommon(e);

        RPGNetwork.initCommon(e);

        RPGItems.initCommon(e);

        RPGBlocks.initCommon(e);

        RPGRecipes.initCommon(e);
    }

    public void init(FMLInitializationEvent e)
    {
        RPGEntities.initCommon(e);

        RPGGuiHandlers.initCommon(e);

        RPGEvents.initCommon(e);
    }

    public void postInit(FMLPostInitializationEvent e)
    {

    }
}
