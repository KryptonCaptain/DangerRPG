package mixac1.dangerrpg.item.gem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.GemType;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.util.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class Gem extends Item implements IHasBooksInfo
{
	public Gem(String name)
	{
		super();
		this.setTextureName(DangerRPG.MODID + ":gems/" + name);
		this.setUnlocalizedName(name);
		this.setMaxStackSize(1);
		this.setCreativeTab(RPGOther.tabDangerRPG);
	}
	
	@SideOnly(Side.CLIENT)
	public String getInformationToInfoBook(ItemStack item, EntityPlayer player, ItemStack gem)
	{
		return Translator.trans("rpgstr.type").concat(": ").concat(getGemType().getDispayName()).concat("\n\n");
	}
	
	public abstract GemType getGemType();
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getInformationToInfoBook(ItemStack item, EntityPlayer player)
	{
		return getInformationToInfoBook(item, player, null);
	}
}
