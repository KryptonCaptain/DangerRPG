package mixac1.dangerrpg.item;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import mixac1.dangerrpg.init.RPGOther.RPGCreativeTabs;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTestWand extends Item
{
    public ItemTestWand(String name)
    {
        setUnlocalizedName(name);
        setTextureName(Utils.toString(DangerRPG.MODID, ":", unlocalizedName));
        setCreativeTab(RPGCreativeTabs.tabRPGAmunitions);
        setMaxStackSize(1);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        if (!entity.worldObj.isRemote && entity instanceof EntityLivingBase) {
            EntityAttributes.HEALTH.addValue(100f, (EntityLivingBase) entity);
        }
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        return stack;
    }
}