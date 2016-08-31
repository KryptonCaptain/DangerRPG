package mixac1.dangerrpg.init;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.event.EventHandlerClient;
import mixac1.dangerrpg.event.EventHandlerCommon;
import mixac1.dangerrpg.event.EventHandlerEntity;
import mixac1.dangerrpg.event.EventHandlerLvlableItem;
import net.minecraftforge.common.MinecraftForge;

public abstract class RPGEvents
{
    public static void load()
    {
        registerEvent(new EventHandlerCommon());
        registerEvent(new EventHandlerEntity());
        registerEvent(new EventHandlerLvlableItem());
    }

    @SideOnly(Side.CLIENT)
    public static void loadClient()
    {
        registerEvent(new EventHandlerClient());
    }

    public static void registerEvent(Object obj)
    {
        FMLCommonHandler.instance().bus().register(obj);
        MinecraftForge.EVENT_BUS.register(obj);
    }
}
