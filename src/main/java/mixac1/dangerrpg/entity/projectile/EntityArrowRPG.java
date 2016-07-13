package mixac1.dangerrpg.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityArrowRPG extends EntityMaterial
{	  
	public EntityArrowRPG(World world)
    {
        super(world, new ItemStack(Items.arrow, 1));
    }

    public EntityArrowRPG(World world, double x, double y, double z)
    {
        super(world, new ItemStack(Items.arrow, 1), x, y, z);
    }
    
    public EntityArrowRPG(World world, EntityLivingBase thrower, float speed, float deviation)
    {
        super(world, thrower, new ItemStack(Items.arrow, 1), speed, deviation);
    }

    public EntityArrowRPG(World world, EntityLivingBase thrower, EntityLivingBase target, float speed, float deviation)
    {
    	super(world, thrower, target, new ItemStack(Items.arrow, 1), speed, deviation);
    }
    
    @Override
    public void applyEntityHitEffects(EntityLivingBase entity)
    {
        phisicDamage *= MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        super.applyEntityHitEffects(entity);
    }
    
    @Override
    public boolean dieAfterEntityHit()
    {
        return true;
    }
}
