package mixac1.dangerrpg.entity.projectile;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityMaterial extends EntityProjectile
{
    public static final int    PICKUP_NO = 0,
                               PICKUP_ALL = 1,
                               PICKUP_CREATIVE = 2,
                               PICKUP_OWNER = 3;
	protected static final int DW_INDEX_STACK = 25;
	
	public int pickupMode;
	public float phisicDamage;
	
	public EntityMaterial(World world)
    {
        super(world);
    }
	
	public EntityMaterial(World world, ItemStack stack)
    {
        this(world);
        setPickupItem(stack);
    }

    public EntityMaterial(World world, ItemStack stack, double x, double y, double z)
    {
        super(world, x, y, z);
        setPickupItem(stack);
    }
    
    public EntityMaterial(World world, EntityLivingBase thrower, ItemStack stack, float speed, float deviation)
    {
        super(world, thrower, speed, deviation);
        setPickupItem(stack);
    }

    public EntityMaterial(World world, EntityLivingBase thrower, EntityLivingBase target, ItemStack stack, float speed, float deviation)
    {
    	super(world, thrower, target, speed, deviation);
    	setPickupItem(stack);
    }
    
    @Override
	public void entityInit()
	{
		dataWatcher.addObject(DW_INDEX_STACK, new ItemStack(Items.apple, 0));
		pickupMode = PICKUP_ALL;
	}
    
    @Override
	public void applyEntityHitEffects(EntityLivingBase entity)
	{
    	DamageSource dmgSource =
    		(thrower == null) ?
    			DamageSource.causeThrownDamage(this, this) :
    			(thrower instanceof EntityPlayer) ? 
    				DamageSource.causePlayerDamage((EntityPlayer) thrower) :
    				DamageSource.causeMobDamage(thrower);
		entity.attackEntityFrom(dmgSource, phisicDamage + getMeleeHitDamage(entity));
		super.applyEntityHitEffects(entity);
	}
    
    @Override
	public void onCollideWithPlayer(EntityPlayer player)
	{
    	super.onCollideWithPlayer(player);
		if (inGround && untouch <= 0) {
			if (!worldObj.isRemote) {
				if (canPickup(player)) {
					if (!player.capabilities.isCreativeMode) {
						player.inventory.addItemStackToInventory(getPickupItem());
					}
					worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
					onItemPickup(player);
					setDead();
				}
			}
		}
	}
    
    protected boolean canPickup(EntityPlayer entityplayer)
	{
		if (pickupMode == PICKUP_ALL) {
			return true;
		}
		else if (pickupMode == PICKUP_CREATIVE) {
			return entityplayer.capabilities.isCreativeMode;
		}
		else if (pickupMode == PICKUP_OWNER) {
			return entityplayer == thrower;
		}
		else {
			return false;
		}
	}
	
	protected void onItemPickup(EntityPlayer player)
	{
		((WorldServer)this.worldObj).getEntityTracker().func_151247_a(this, new S0DPacketCollectItem(this.getEntityId(), player.getEntityId()));
	}
	
	public float getMeleeHitDamage(Entity entity)
	{
		if (entity instanceof EntityLivingBase && thrower != null) {
			return EnchantmentHelper.getEnchantmentModifierLiving(thrower, (EntityLivingBase) entity);
		}
		return 0F;
	}
	
	public void setPickupItem(ItemStack stack)
	{
		if (stack != null) {
			dataWatcher.updateObject(DW_INDEX_STACK, stack);
		}
	}
	
	public ItemStack getPickupItem()
	{
		return dataWatcher.getWatchableObjectItemStack(DW_INDEX_STACK);
	}
	
	@Override
	public float getAirResistance()
    {
    	return 0.95F;
    }
    
    @Override
	public float getWaterResistance()
    {
    	return 0.8F;
    }
    
	@Override
	public float getGravity()
	{
    	return 0.05F;
    }
	
	@Override
	public boolean dieAfterEntityHit()
    {
    	return false;
    }
    
    @Override
	public boolean dieAfterGroundHit()
    {
    	return false;
    }
    
    @Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
    	super.writeEntityToNBT(nbt);
		nbt.setByte("pickupMode", (byte) pickupMode);
		nbt.setFloat("phisicDamage", phisicDamage);
		
		ItemStack thrownItem = getPickupItem();
		if (thrownItem != null) {
			nbt.setTag("stack", thrownItem.writeToNBT(new NBTTagCompound()));
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		pickupMode = nbt.getByte("pickupMode") & 0xFF;
		phisicDamage = nbt.getFloat("phisicDamage");
		
		NBTTagCompound tag = nbt.getCompoundTag("stack");
    	if (tag != null) {
    		setPickupItem(ItemStack.loadItemStackFromNBT(tag));
    	}
	}
}
