package mixac1.dangerrpg.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityThrowKnife extends EntityThrowLvlItem
{
	public EntityThrowKnife(World world)
    {
        super(world);
    }
	
	public EntityThrowKnife(World world, ItemStack stack)
    {
        super(world, stack);
    }

    public EntityThrowKnife(World world, ItemStack stack, double x, double y, double z)
    {
        super(world, stack, x, y, z);
    }
    
    public EntityThrowKnife(World world, EntityLivingBase thrower, ItemStack stack, float speed, float deviation)
    {
        super(world, thrower, stack, speed, deviation);
    }

    public EntityThrowKnife(World world, EntityLivingBase thrower, EntityLivingBase target, ItemStack stack, float speed, float deviation)
    {
    	super(world, thrower, target, stack, speed, deviation);
    }
    
    @Override
	public float getRotationOnPitch()
    {
    	return -60.0F;
    }
    
    @Override
	public boolean needAimRotation()
	{
		return !canRotation();
	}
}
