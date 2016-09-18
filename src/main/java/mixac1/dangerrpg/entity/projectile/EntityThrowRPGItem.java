package mixac1.dangerrpg.entity.projectile;

import mixac1.dangerrpg.api.event.ItemStackEvent.HitEntityEvent;
import mixac1.dangerrpg.capability.RPGableItem;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class EntityThrowRPGItem extends EntityMaterial
{
    public EntityThrowRPGItem(World world)
    {
        super(world);
    }

    public EntityThrowRPGItem(World world, ItemStack stack)
    {
        super(world, stack);
    }

    public EntityThrowRPGItem(World world, ItemStack stack, double x, double y, double z)
    {
        super(world, stack, x, y, z);
    }

    public EntityThrowRPGItem(World world, EntityLivingBase thrower, ItemStack stack, float speed, float deviation)
    {
        super(world, thrower, stack, speed, deviation);
    }

    public EntityThrowRPGItem(World world, EntityLivingBase thrower, EntityLivingBase target, ItemStack stack, float speed, float deviation)
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
    public void applyEntityHitEffects(EntityLivingBase entity, float dmgMul)
    {
        if (beenInGround) {
            return;
        }

        ItemStack stack = this.getStack();
        if (stack != null) {
            if (ItemAttributes.SHOT_DAMAGE.hasIt(stack)) {
                phisicDamage = ItemAttributes.SHOT_DAMAGE.get(stack);
            }
            else if (ItemAttributes.MELEE_DAMAGE.hasIt(stack)) {
                phisicDamage = ItemAttributes.MELEE_DAMAGE.get(stack);
            }
            HitEntityEvent event = new HitEntityEvent(stack, entity, thrower, phisicDamage, 0, true);
            MinecraftForge.EVENT_BUS.post(event);
            phisicDamage = event.damage;
        }

        float points = entity.getHealth();

        super.applyEntityHitEffects(entity, dmgMul);

        points -= entity.getHealth();
        if (points > 0 && thrower instanceof EntityPlayer) {
            RPGableItem.upEquipment((EntityPlayer) thrower, entity, getStack(), points);
        }
    }
}
