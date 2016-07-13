package mixac1.dangerrpg.item;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.itemattr.ItemAttributes;
import mixac1.dangerrpg.entity.projectile.EntityMaterial;
import mixac1.dangerrpg.entity.projectile.EntitySniperArrow;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSniperBow extends RPGItemBow
{
	public ItemSniperBow(RPGToolComponent toolComponent, float maxPower, String name)
	{
		super(toolComponent, maxPower, name);
	}
	
	@Override
    public void onStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useDuration)
    {
	    boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
        if (flag || player.inventory.hasItem(Items.arrow)) {
        	float power = useDuration / getMaxCharge(stack, player);
		    power = (power * power + power * 2.0F) / 3.0F;
		     
		    if (power < 0.9D) {
		        //return;
		    }
		    else if (power > 1.0F) {
		        power = 1.0F;
		    }
		    power = 1.0F;
		     
		    float powerMul = ItemAttributes.SHOT_POWER.hasIt(stack) ? ItemAttributes.SHOT_POWER.get(stack, player) : getPoweMul();
		    EntitySniperArrow entity = new EntitySniperArrow(world, player, power * 7, 0F);
		     
		    entity.phisicDamage = ItemAttributes.SHOT_DAMAGE.hasIt(stack) ? ItemAttributes.SHOT_DAMAGE.get(stack, player) : 2F;
		             
		    int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
		    if (k > 0) {
		        entity.phisicDamage += k * 0.5F + 0.5F;
		    }
		     
		    if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
		        entity.setFire(100);
		    }
		     
		    stack.damageItem(1, player);
		    world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (DangerRPG.rand.nextFloat() * 0.4F + 1.2F) + power * 0.5F);
		                             
		    if (flag) {
		        entity.pickupMode = EntityMaterial.PICKUP_CREATIVE;
		    }
		    else {
		        player.inventory.consumeInventoryItem(Items.arrow);
		    }
		     
		    if (!world.isRemote) {
		        world.spawnEntityInWorld(entity);
		    }
		 }
    }
}
