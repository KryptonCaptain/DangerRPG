package mixac1.dangerrpg.entity.projectile;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.client.RPGEntityFXManager;
import mixac1.dangerrpg.world.SpellExplosion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPowerMagicOrb extends EntityMagicOrb
{
    public EntityPowerMagicOrb(World world)
    {
        super(world);
    }

    public EntityPowerMagicOrb(World world, ItemStack stack)
    {
        super(world);
    }

    public EntityPowerMagicOrb(World world, ItemStack stack, double x, double y, double z)
    {
        super(world, stack, x, y, z);
    }

    public EntityPowerMagicOrb(World world, EntityLivingBase thrower, ItemStack stack, float speed, float deviation)
    {
        super(world, thrower, stack, speed, deviation);
    }

    public EntityPowerMagicOrb(World world, EntityLivingBase thrower, EntityLivingBase target, ItemStack stack, float speed, float deviation)
    {
        super(world, thrower, target, stack, speed, deviation);
    }

    @Override
    public void preInpact(MovingObjectPosition mop)
    {
        float r = 2;
        SpellExplosion explosion = new SpellExplosion(this, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, r);
        explosion.init(false, 1, 0, false);
        explosion.doExplosion();

        if (worldObj.isRemote) {
            int color = getColor();
            double frec = Math.PI / (6 * r);
            double x, y, z, tmp;

            for (double k = 0; k < Math.PI * 2; k += frec) {
                y = posY + r * Math.cos(k);
                tmp = Math.abs(r * Math.sin(k));
                for (double l = 0; l < Math.PI * 2; l += frec) {
                    x = posX + tmp * Math.cos(l);
                    z = posZ + tmp * Math.sin(l);
                    DangerRPG.proxy.spawnEntityFX(RPGEntityFXManager.EntityReddustFXE, x, y, z, 0, 0, 0, color);
                }
            }
        }
    }

    @Override
    public float getAirResistance()
    {
        return 0.95F;
    }

    @Override
    public float getGravity()
    {
        return 0.05F;
    }
}
