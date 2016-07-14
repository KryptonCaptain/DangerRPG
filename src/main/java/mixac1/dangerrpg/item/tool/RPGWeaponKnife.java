package mixac1.dangerrpg.item.tool;

import mixac1.dangerrpg.entity.projectile.EntityThrowKnife;
import mixac1.dangerrpg.entity.projectile.EntityThrowLvlItem;
import mixac1.dangerrpg.item.RPGToolComponent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RPGWeaponKnife extends RPGThrowableWeapon
{
    public RPGWeaponKnife(ToolMaterial toolMaterial, RPGToolComponent toolComponent, String name)
    {
        super(toolMaterial, toolComponent, name);
    }

    @Override
    protected EntityThrowLvlItem getThrowEntity(World world, EntityLivingBase entityliving, ItemStack itemstack)
    {
        return new EntityThrowKnife(world, entityliving, itemstack, 1.3F, 3F);
    }
}
