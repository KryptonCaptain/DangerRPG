package mixac1.dangerrpg.proxy;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import mixac1.dangerrpg.init.RPGEvents;
import mixac1.dangerrpg.init.RPGKeyBinds;
import mixac1.dangerrpg.init.RPGRenderers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        super.preInit(e);

        RPGKeyBinds.load();
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        super.init(e);

        RPGRenderers.load();

        RPGEvents.loadClient();
    }

    @Override
    public void postInit(FMLPostInitializationEvent e)
    {
        super.postInit(e);
    }

    @Override
    public EntityPlayer getClientPlayer()
    {
        return FMLClientHandler.instance().getClient().thePlayer;
    }

    @Override
    public EntityPlayer getPlayer(MessageContext ctx)
    {
        return (ctx.side != Side.CLIENT) ? super.getPlayer(ctx) : getClientPlayer();
    }

    @Override
    public Entity getEntityByID(MessageContext ctx, int entityId)
    {
        return (ctx.side != Side.CLIENT) ? super.getEntityByID(ctx, entityId) :
            getClientPlayer().worldObj.getEntityByID(entityId);
    }
}
