package mixac1.dangerrpg.init;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.client.event.ClientEventHandlers;
import mixac1.dangerrpg.event.CommonEventHandlers;
import mixac1.dangerrpg.event.LvlableItemEventHandlers;
import net.minecraftforge.common.MinecraftForge;

public class RPGEvents
{
    public static void load()
    {
        registerEvent(new CommonEventHandlers());
        registerEvent(new LvlableItemEventHandlers());
    }
    
    @SideOnly(Side.CLIENT)
    public static void loadClient()
    {
        registerEvent(new ClientEventHandlers());
    }
    
    public static void registerEvent(Object obj)
    {
        FMLCommonHandler.instance().bus().register(obj);
        MinecraftForge.EVENT_BUS.register(obj);
    }
}
