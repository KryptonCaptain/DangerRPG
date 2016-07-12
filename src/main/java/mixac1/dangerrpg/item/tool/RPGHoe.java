package mixac1.dangerrpg.item.tool;

import java.util.HashMap;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemTool;
import mixac1.dangerrpg.capability.ItemAttrParams;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.item.RPGToolComponent;
import mixac1.dangerrpg.util.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class RPGHoe extends ItemHoe implements ILvlableItemTool, IHasBooksInfo
{
	public RPGHoe(ToolMaterial toolMaterial, String name)
	{
		super(toolMaterial);
		setUnlocalizedName(name);
		setTextureName(DangerRPG.MODID + ":tools/" + name);
		setCreativeTab(RPGOther.tabDangerRPG);
		setMaxStackSize(1);
	}
	
	@Override
	public String getInformationToInfoBook(ItemStack item, EntityPlayer player)
	{
		return Translator.trans("rpgstr.no_info_yet");
	}
	
	@Override
	public RPGToolComponent getToolComponent(Item item)
	{
		return RPGToolComponent.HOE;
	}

	@Override
	public ToolMaterial getToolMaterial(Item item)
	{
		return theToolMaterial;
	}

	@Override
	public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
	{
		LvlableItem.registerParamsItemTool(item, map);
	}
}
