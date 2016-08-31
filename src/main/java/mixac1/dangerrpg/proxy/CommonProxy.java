package mixac1.dangerrpg.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import mixac1.dangerrpg.client.RPGEntityFXManager.IEntityFXType;
import mixac1.dangerrpg.init.RPGBlocks;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGConfig;
import mixac1.dangerrpg.init.RPGEntities;
import mixac1.dangerrpg.init.RPGEvents;
import mixac1.dangerrpg.init.RPGGuiHandlers;
import mixac1.dangerrpg.init.RPGItems;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.util.RPGTicks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy
{
    protected RPGTicks serverTicks = new RPGTicks();

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

    public EntityPlayer getClientPlayer()
    {
        return null;
    }

    public EntityPlayer getPlayer(MessageContext ctx)
    {
        return ctx.getServerHandler().playerEntity;
    }

    public Entity getEntityByID(MessageContext ctx, int entityId)
    {
        return getPlayer(ctx).worldObj.getEntityByID(entityId);
    }

    protected RPGTicks getRPGTicks(Side side)
    {
        return serverTicks;
    }

    public void fireTick(Side side)
    {
        getRPGTicks(side).fireTick();
    }

    public int getTick(Side side)
    {
        return getRPGTicks(side).getTick();
    }

    public void spawnEntityFX(IEntityFXType fx, double x, double y, double z, double motionX, double motionY, double motionZ)
    {

    }

    public void spawnEntityFX(IEntityFXType fx, double x, double y, double z, double motionX, double motionY, double motionZ, int color)
    {

    }

    public void spawnEntityFX(IEntityFXType fx, double x, double y, double z, double motionX, double motionY, double motionZ, int color, int maxAge)
    {

    }
}
