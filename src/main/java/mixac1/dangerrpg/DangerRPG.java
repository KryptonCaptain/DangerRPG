package mixac1.dangerrpg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mixac1.dangerrpg.proxy.CommonProxy;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = DangerRPG.MODID,
    name = DangerRPG.MODNAME,
    version = DangerRPG.VERSION,
    acceptedMinecraftVersions = DangerRPG.ACCEPTED_VERSION,
    dependencies = DangerRPG.DEPENDENCIES)
public class DangerRPG
{
    public static final String MODNAME          = "DangerRPG";
    public static final String MODID            = "dangerrpg";
    public static final String VERSION          = "${version}";
    public static final String ACCEPTED_VERSION = "[1.10.2]";
    public static final String DEPENDENCIES     = "required-after:Forge";

    @Instance(DangerRPG.MODID)
    public static DangerRPG INSTANCE = new DangerRPG();

    @SidedProxy(clientSide = "mixac1.dangerrpg.proxy.ClientProxy", serverSide = "mixac1.dangerrpg.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static final Logger logger = LogManager.getLogger(MODID);

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

    public static void debugLog(Object... objs)
    {
        DangerRPG.logger.debug(Utils.toString(' ', objs));
    }

    public static void infoLog(Object... objs)
    {
        DangerRPG.logger.info(Utils.toString(objs));
    }

    public static String trans(String s)
    {
        return I18n.translateToLocal(s);
    }
}