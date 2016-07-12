package mixac1.dangerrpg.item;

import java.util.HashMap;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.ILvlableItem;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemBow;
import mixac1.dangerrpg.capability.ItemAttrParams;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.init.RPGOther;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RPGItemBow extends ItemBow implements ILvlableItemBow, IHasBooksInfo
{
    public RPGToolComponent toolComponent;
    public float maxPower;
    
	public RPGItemBow(RPGToolComponent toolComponent, float maxPower, String name)
	{
		setUnlocalizedName(name);
		setTextureName(DangerRPG.MODID + ":weapons/range/" + name);
		setCreativeTab(RPGOther.tabDangerRPG);
		this.toolComponent = toolComponent;
		this.maxPower = maxPower;
	}

    @Override
    public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
    {
        LvlableItem.registerParamsItemBow(item, map);
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player)
    {
        return null;
    }

    @Override
    public RPGToolComponent getToolComponent(Item item)
    {
        return toolComponent;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useDuration)
    {
        ILvlableItem.DEFAULT_BOW.onStoppedUsing(stack, world, player, useDuration);
    }
    
    @Override
    public float getMaxCharge(ItemStack stack, EntityPlayer player)
    {
        return ILvlableItem.DEFAULT_BOW.getMaxCharge(stack, player);
    }

    @Override
    public float getPoweMul()
    {
        return maxPower;
    }

    @Override
    public ToolMaterial getToolMaterial(Item item)
    {
        return null;
    }
}
