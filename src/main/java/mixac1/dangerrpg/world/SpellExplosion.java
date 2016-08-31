package mixac1.dangerrpg.world;

import mixac1.dangerrpg.entity.projectile.EntityCommonMagic;
import net.minecraft.entity.EntityLivingBase;

public class SpellExplosion extends RPGExplosion
{
    public SpellExplosion(EntityCommonMagic entity, double x, double y, double z, float explosionSize)
    {
        super(entity, x, y, z, explosionSize);
    }

    @Override
    public void applyEntityHitEffects(EntityLivingBase entity, float power)
    {
        ((EntityCommonMagic) exploder).applyEntityHitEffects(entity, isDependDist ? power : 1);
    }
}
