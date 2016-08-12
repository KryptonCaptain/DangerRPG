package mixac1.dangerrpg;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mixac1.dangerrpg.init.RPGConfig;
import mixac1.dangerrpg.proxy.CommonProxy;
import net.minecraft.util.StatCollector;

@Mod(modid = DangerRPG.MODID,
     name = DangerRPG.MODNAME,
     version = DangerRPG.VERSION,
     acceptedMinecraftVersions = DangerRPG.ACCEPTED_VERSION,
     dependencies = "required-after:Forge")
public class DangerRPG
{
    public static final String MODNAME          = "Danger RPG";
    public static final String MODID            = "DangerRPG";
    public static final String VERSION          = "1.0.0";
    public static final String ACCEPTED_VERSION = "[1.7.10]";

    @Instance(DangerRPG.MODID)
    public static DangerRPG instance = new DangerRPG();

    @SidedProxy(clientSide = "mixac1.dangerrpg.proxy.ClientProxy",
                serverSide = "mixac1.dangerrpg.proxy.CommonProxy")
    public static CommonProxy  proxy;

    public static final Random rand = new Random();

    private static final Logger logger = LogManager.getLogger(DangerRPG.MODID);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }

    public static void debugDump(Object... objs)
    {
        StringBuilder buf = new StringBuilder();
        for (Object obj : objs) {
            buf.append(obj.toString()).append(" ");
        }
        DangerRPG.logger.info(buf.toString());
    }

    public static void infoLog(Object obj)
    {
        if (RPGConfig.mainEnableInfoLog) {
            DangerRPG.logger.info(obj.toString());
        }
    }

    public static String trans(String s)
    {
        return StatCollector.translateToLocal(s);
    }
}
