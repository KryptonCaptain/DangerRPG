package mixac1.dangerrpg.api.entity;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.capability.RPGableEntity;
import mixac1.dangerrpg.capability.data.RPGEntityRegister.RPGEntityData;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;

public interface IRPGEntity
{
    public EAFloat getEAMeleeDamage(EntityLivingBase entity);

    public EAFloat getEARangeDamage(EntityLivingBase entity);

    public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityData set);

    public static IRPGEntity DEFAULT_PLAYER = new IRPGEntity()
    {
        @Override
        public EAFloat getEAMeleeDamage(EntityLivingBase entity)
        {
            return EntityAttributes.MELEE_DAMAGE;
        }

        @Override
        public EAFloat getEARangeDamage(EntityLivingBase entity)
        {
            return null;
        }

        @Override
        public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityData set)
        {
            RPGableEntity.registerEntityPlayer((Class<? extends EntityPlayer>) entityClass, set);
        }
    };

    public static IRPGEntity DEFAULT_LIVING = new RPGLivingEntity();

    public static IRPGEntity DEFAULT_MOB = new RPGDefaultEntityMob();

    public static class RPGLivingEntity implements IRPGEntity
    {
        @Override
        public EAFloat getEAMeleeDamage(EntityLivingBase entity)
        {
            return null;
        }

        @Override
        public EAFloat getEARangeDamage(EntityLivingBase entity)
        {
            return null;
        }

        @Override
        public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityData set)
        {
            RPGableEntity.registerEntityLiving((Class<? extends EntityLiving>) entityClass, set);
        }
    };

    public static class RPGDefaultEntityMob extends RPGLivingEntity
    {
        @Override
        public EAFloat getEAMeleeDamage(EntityLivingBase entity)
        {
            return EntityAttributes.MELEE_DAMAGE;
        }

        @Override
        public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityData set)
        {
            super.registerAttributes(entityClass, set);
            RPGableEntity.registerEntityMob((Class<? extends EntityMob>) entityClass, set);
        }
    };

    public static class RPGCommonEntityMob extends RPGDefaultEntityMob
    {
        protected EAFloat meleeAttr;
        protected float meleeValue;

        public RPGCommonEntityMob(EAFloat meleeAttr, float meleeValue)
        {
            this.meleeAttr = meleeAttr;
            this.meleeValue = meleeValue;
        }

        @Override
        public EAFloat getEAMeleeDamage(EntityLivingBase entity)
        {
            return meleeAttr;
        }

        @Override
        public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityData set)
        {
            super.registerAttributes(entityClass, set);
            if (set.attributes.containsKey(EntityAttributes.MELEE_DAMAGE)) {
                set.attributes.remove(EntityAttributes.MELEE_DAMAGE);
            }
            set.addEntityAttribute(meleeAttr, meleeValue);
        }
    };

    public static class RPGEntityRangeMob extends RPGDefaultEntityMob
    {
        protected float value;

        public RPGEntityRangeMob(float value)
        {
            this.value = value;
        }

        @Override
        public EAFloat getEARangeDamage(EntityLivingBase entity)
        {
            return EntityAttributes.RANGE_DAMAGE;
        }

        @Override
        public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityData set)
        {
            super.registerAttributes(entityClass, set);
            set.addEntityAttribute(EntityAttributes.RANGE_DAMAGE, value);
        }
    };
}
