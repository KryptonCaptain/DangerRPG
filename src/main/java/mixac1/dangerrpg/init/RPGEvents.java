package mixac1.dangerrpg.init;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RPGEvents
{
    public static void initCommon(FMLInitializationEvent e)
    {
    }

    @SideOnly(Side.CLIENT)
    public static void initClient(FMLInitializationEvent e)
    {
    }

    public static void registerEvent(Object obj)
    {
        MinecraftForge.EVENT_BUS.register(obj);
    }
}
