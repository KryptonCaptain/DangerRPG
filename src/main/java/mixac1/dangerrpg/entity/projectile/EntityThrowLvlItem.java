package mixac1.dangerrpg.entity.projectile;

import mixac1.dangerrpg.api.event.ItemStackEvent.HitEntityEvent;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class EntityThrowLvlItem extends EntityMaterial
{
    public EntityThrowLvlItem(World world)
    {
        super(world);
    }
    
    public EntityThrowLvlItem(World world, ItemStack stack)
    {
        super(world, stack);
    }

    public EntityThrowLvlItem(World world, ItemStack stack, double x, double y, double z)
    {
        super(world, stack, x, y, z);
    }
    
    public EntityThrowLvlItem(World world, EntityLivingBase thrower, ItemStack stack, float speed, float deviation)
    {
        super(world, thrower, stack, speed, deviation);
    }

    public EntityThrowLvlItem(World world, EntityLivingBase thrower, EntityLivingBase target, ItemStack stack, float speed, float deviation)
    {
        super(world, thrower, target, stack, speed, deviation);
    }
    
    @Override
    public void entityInit()
    {
        super.entityInit();
        pickupMode = PICKUP_ALL;
    }
    
    @Override
    public void applyEntityHitEffects(EntityLivingBase entity)
    {
        ItemStack stack = this.getPickupItem();
        if (stack != null) {
            if (ItemAttributes.SHOT_DAMAGE.hasIt(stack)) {
                phisicDamage = ItemAttributes.SHOT_DAMAGE.get(stack);
            }
            else if (ItemAttributes.MELEE_DAMAGE.hasIt(stack)) {
                phisicDamage = ItemAttributes.MELEE_DAMAGE.get(stack);
            }
            MinecraftForge.EVENT_BUS.post(new HitEntityEvent(stack, entity, thrower));
        }
        super.applyEntityHitEffects(entity);
    }
}
