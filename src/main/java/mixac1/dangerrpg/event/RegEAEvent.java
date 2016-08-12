package mixac1.dangerrpg.event;

import cpw.mods.fml.common.eventhandler.Event;
import mixac1.dangerrpg.capability.EntityData.EntityAttributesSet;
import net.minecraft.entity.EntityLivingBase;

public class RegEAEvent extends Event
{
    public Class<? extends EntityLivingBase> entityClass;
    public EntityAttributesSet set;

    public RegEAEvent(Class<? extends EntityLivingBase> entityClass, EntityAttributesSet set)
    {
        this.entityClass = entityClass;
        this.set = set;
    }

    public static class DefaultEAEvent extends RegEAEvent
    {
        public DefaultEAEvent(Class<? extends EntityLivingBase> entityClass, EntityAttributesSet set)
        {
            super(entityClass, set);
        }
    }

    public static class EntytyLivingEAEvent extends RegEAEvent
    {
        public EntytyLivingEAEvent(Class<? extends EntityLivingBase> entityClass, EntityAttributesSet set)
        {
            super(entityClass, set);
        }
    }

    public static class PlayerEAEvent extends RegEAEvent
    {
        public PlayerEAEvent(Class<? extends EntityLivingBase> entityClass, EntityAttributesSet set)
        {
            super(entityClass, set);
        }
    }
}
