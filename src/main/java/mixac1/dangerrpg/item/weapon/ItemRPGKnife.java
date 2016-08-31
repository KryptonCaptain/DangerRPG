package mixac1.dangerrpg.item.weapon;

import mixac1.dangerrpg.entity.projectile.EntityThrowKnife;
import mixac1.dangerrpg.entity.projectile.EntityThrowLvlItem;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRPGKnife extends ItemRPGThrowable
{
    public ItemRPGKnife(RPGToolMaterial toolMaterial, RPGToolComponent toolComponent)
    {
        super(toolMaterial, toolComponent);
    }

    @Override
    protected EntityThrowLvlItem getThrowEntity(World world, EntityLivingBase entityliving, ItemStack itemstack)
    {
        return new EntityThrowKnife(world, entityliving, itemstack, 1.3F, 3F);
    }
}
