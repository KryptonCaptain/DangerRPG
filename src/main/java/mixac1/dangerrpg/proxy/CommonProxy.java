package mixac1.dangerrpg.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mixac1.dangerrpg.init.RPGBlocks;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGConfig;
import mixac1.dangerrpg.init.RPGEntities;
import mixac1.dangerrpg.init.RPGEvents;
import mixac1.dangerrpg.init.RPGGuiHandlers;
import mixac1.dangerrpg.init.RPGItems;
import mixac1.dangerrpg.init.RPGNetwork;
import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        RPGConfig.load(e);
        
        RPGNetwork.load();
        
        RPGItems.load();
        
        RPGBlocks.load();
    }
    
    public void init(FMLInitializationEvent e)
    {
        RPGEntities.load();
        
        RPGGuiHandlers.load();
        
        RPGEvents.load();
    }
    
    public void postInit(FMLPostInitializationEvent e)
    {
        RPGCapability.load();
    }
    
    public EntityPlayer getPlayerFromMessageCtx(MessageContext ctx)
    {
        return ctx.getServerHandler().playerEntity;
    }
}
