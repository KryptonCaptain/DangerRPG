package mixac1.dangerrpg.entity.projectile.core;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class EntityWithStack extends EntityProjectile
{
    protected static final int DW_INDEX_STACK = 25;

    public EntityWithStack(World world)
    {
        super(world);
    }

    public EntityWithStack(World world, ItemStack stack)
    {
        this(world);
        setStack(stack);
    }

    public EntityWithStack(World world, ItemStack stack, double x, double y, double z)
    {
        super(world, x, y, z);
        setStack(stack);
    }

    public EntityWithStack(World world, EntityLivingBase thrower, ItemStack stack, float speed, float deviation)
    {
        super(world, thrower, speed, deviation);
        setStack(stack);
    }

    public EntityWithStack(World world, EntityLivingBase thrower, EntityLivingBase target, ItemStack stack, float speed, float deviation)
    {
        super(world, thrower, target, speed, deviation);
        setStack(stack);
    }

    @Override
    public void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(DW_INDEX_STACK, new ItemStack(Items.apple, 0));
    }

    public void setStack(ItemStack stack)
    {
        if (stack != null) {
            dataWatcher.updateObject(DW_INDEX_STACK, stack);
        }
    }

    public ItemStack getStack()
    {
        return dataWatcher.getWatchableObjectItemStack(DW_INDEX_STACK);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        ItemStack thrownItem = getStack();
        if (thrownItem != null) {
            nbt.setTag("stack", thrownItem.writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        NBTTagCompound tag = nbt.getCompoundTag("stack");
        if (tag != null) {
            setStack(ItemStack.loadItemStackFromNBT(tag));
        }
    }
}
