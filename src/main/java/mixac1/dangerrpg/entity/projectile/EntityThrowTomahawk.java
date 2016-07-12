package mixac1.dangerrpg.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityThrowTomahawk extends EntityThrowLvlItem
{
	public EntityThrowTomahawk(World world)
    {
        super(world);
    }
	
	public EntityThrowTomahawk(World world, ItemStack stack)
    {
        super(world, stack);
    }

    public EntityThrowTomahawk(World world, ItemStack stack, double x, double y, double z)
    {
        super(world, stack, x, y, z);
    }
    
    public EntityThrowTomahawk(World world, EntityLivingBase thrower, ItemStack stack, float speed, float deviation)
    {
        super(world, thrower, stack, speed, deviation);
    }

    public EntityThrowTomahawk(World world, EntityLivingBase thrower, EntityLivingBase target, ItemStack stack, float speed, float deviation)
    {
    	super(world, thrower, target, stack, speed, deviation);
    }
	
	@Override
	public void onGroundHit(MovingObjectPosition mop)
	{
		super.onGroundHit(mop);
		if (mop.sideHit == 0) {
			prevRotationPitch = rotationPitch = 180;
		}
		else if (mop.sideHit != 1) {
			prevRotationPitch = rotationPitch = 90;
		}
		else {
			prevRotationPitch = rotationPitch = 0;
		}
	}
	
	@Override
	public float getRotationOnPitch()
    {
    	return -60.0F;
    }
	
	@Override
	public boolean needAimRotation()
	{
		return false;
	}
}
