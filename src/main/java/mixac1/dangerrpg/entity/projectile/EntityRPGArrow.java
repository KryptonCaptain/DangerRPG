package mixac1.dangerrpg.entity.projectile;

import mixac1.dangerrpg.api.event.UpEquipmentEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class EntityRPGArrow extends EntityMaterial
{
    public EntityRPGArrow(World world)
    {
        super(world, new ItemStack(Items.arrow, 1));
    }

    public EntityRPGArrow(World world, double x, double y, double z)
    {
        super(world, new ItemStack(Items.arrow, 1), x, y, z);
    }

    public EntityRPGArrow(World world, EntityLivingBase thrower, float speed, float deviation)
    {
        super(world, thrower, new ItemStack(Items.arrow, 1), speed, deviation);
    }

    public EntityRPGArrow(World world, EntityLivingBase thrower, EntityLivingBase target, float speed, float deviation)
    {
        super(world, thrower, target, new ItemStack(Items.arrow, 1), speed, deviation);
    }

    @Override
    public void applyEntityHitEffects(EntityLivingBase entity, float dmgMul)
    {
        float points = entity.getHealth();

        super.applyEntityHitEffects(entity, dmgMul);

        points -= entity.getHealth();
        if (thrower instanceof EntityPlayer) {
            MinecraftForge.EVENT_BUS.post(new UpEquipmentEvent((EntityPlayer) thrower, entity, null, points));
        }
    }

    @Override
    public float getDamageMul()
    {
        return MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
    }

    @Override
    public boolean dieAfterEntityHit()
    {
        return true;
    }
}
