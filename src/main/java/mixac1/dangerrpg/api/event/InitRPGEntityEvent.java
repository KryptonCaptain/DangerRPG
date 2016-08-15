package mixac1.dangerrpg.api.event;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.EntityLivingBase;

public class InitRPGEntityEvent extends Event
{
    public EntityLivingBase entity;

    public InitRPGEntityEvent(EntityLivingBase entity)
    {
        this.entity = entity;
    }
}
