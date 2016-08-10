package mixac1.dangerrpg.item;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import mixac1.dangerrpg.init.RPGOther;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TestWand extends Item
{
    public TestWand(String name)
    {
        setUnlocalizedName(name);
        setTextureName(DangerRPG.MODID + ":" + unlocalizedName);
        setCreativeTab(RPGOther.tabDangerRPG);
        setMaxStackSize(1);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        if (!entity.worldObj.isRemote && entity instanceof EntityLivingBase) {
            EntityAttributes.HEALTH.addValue(1f, (EntityLivingBase) entity);
        }

        return true;
    }
}
